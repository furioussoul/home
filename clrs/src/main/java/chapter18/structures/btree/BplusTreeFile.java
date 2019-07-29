/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package chapter18.structures.btree;

import chapter18.holders.DataHolder;
import chapter18.io.FileBlockStore;
import chapter18.io.FileStreamStore;
import chapter18.pool.BufferStacker;
import chapter18.structures.bitset.SimpleBitSet;
import chapter18.structures.hash.IntHashMap;
import chapter18.structures.hash.IntLinkedHashMap;
import chapter18.utils.HexStrings;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Implementation of B+Tree in File
 * This class is Thread-Safe
 *
 * @author Guillermo Grandes / guillermo.grandes[at]gmail.com
 */
public final class BplusTreeFile<K extends DataHolder<K>, V extends DataHolder<V>> extends BplusTree<K, V> {
    private static final Logger log = Logger.getLogger(BplusTreeFile.class);
    /**
     * MAGIC_1 in begining of metadata
     */
    private static final int MAGIC_1 = 0x42D6AECB;
    /**
     * MAGIC_1 in ending of metadata
     */
    private static final int MAGIC_2 = 0x6B708B42;
    /**
     * Default cache size in bytes
     */
    private static final int DEFAULT_CACHE_SIZE_BYTES = 16 * 1024 * 1024;
    /**
     * Redo Queue for Dedicated Thread
     */
    final ArrayBlockingQueue<ByteBuffer> redoQueue = new ArrayBlockingQueue<ByteBuffer>(0x1);
    /**
     * Disable all Caches
     */
    private final boolean disableAllCaches = false;
    /**
     * Full erase of block when freed
     */
    private final boolean cleanBlocksOnFree = false;
    /**
     * Enable IO Stats (performance loss)
     */
    private final boolean enableIOStats = false;
    /**
     * Enable dirty blocks check for debugging only
     */
    private final boolean enableDirtyCheck = false;
    /**
     * File for storage
     */
    private final File fileStorage;
    /**
     * File for free blocks
     */
    private final File fileFreeBlocks;
    /**
     * File for redo
     */
    private final File fileRedo;
    /**
     * Storage Object
     */
    private final FileBlockStore storage;
    /**
     * Redo Storage
     */
    private final FileStreamStore redoStore;
    // Remember parameters for recovery
    private final transient boolean autoTune;
    private final transient int b_size;
    private final transient String fileName;
    /**
     * Write cache for internal dirty nodes
     */
    @SuppressWarnings("rawtypes")
    private final IntHashMap<Node> dirtyInternalNodes = new IntHashMap<Node>(1024, Node.class);
    /**
     * Write cache for leaf dirty nodes
     */
    @SuppressWarnings("rawtypes")
    private final IntHashMap<Node> dirtyLeafNodes = new IntHashMap<Node>(1024, Node.class);
    /**
     * Use DirectByteBuffer/true or HeapByteBuffer/false ?
     */
    private final boolean isDirect = true; // use DirectByteBuffer o HeapByteBuffer
    /**
     * Pool of ByteBuffers
     */
    private final BufferStacker bufstack;
    /**
     * I/O Stats of nodes
     */
    private final IntHashMap<IOStat> iostats = new IntHashMap<IOStat>(256, IOStat.class);
    /**
     * Read cache (elements) for Leaf Nodes
     */
    private int readCacheLeaf = 128;
    /**
     * Read cache (elements) for Internal Nodes
     */
    private int readCacheInternal = 128;
    /**
     * Bitset with id of free blocks to reuse
     */
    private SimpleBitSet freeBlocks;        // META-DATA: bitset of free blocks in storage
    /**
     * Current blockid/nodeid from underlying storage
     */
    private int storageBlock = 0;            // META-DATA: id of last blockid for nodes
    /**
     * Bitset with id of dirty blocks pending to commit
     */
    private SimpleBitSet dirtyCheck = new SimpleBitSet();
    /**
     * Use Redo?
     */
    private boolean useRedo = true;
    /**
     * Use Dedicated Thread for Redo?
     */
    private boolean useRedoThread = false;
    /**
     * Disable Populate Cache
     */
    private boolean disablePopulateCache = false;

    // ===================================== Node management
    /**
     * Disable Auto Sync Store
     */
    private boolean disableAutoSyncStore = false;
    /**
     * Redo Thread
     */
    private Thread redoThread = null;
    private AtomicBoolean doShutdownRedoThread = new AtomicBoolean();
    /**
     * Max size of cache in bytes
     */
    private int maxCacheSizeInBytes = DEFAULT_CACHE_SIZE_BYTES;
    /**
     * Read cache for internal nodes
     */
    @SuppressWarnings("rawtypes")
    private IntLinkedHashMap<Node> cacheInternalNodes;
    /**
     * Read cache for leaf nodes
     */
    @SuppressWarnings("rawtypes")
    private IntLinkedHashMap<Node> cacheLeafNodes;
    /**
     * Comparator for write cache / dirty nodes by nodeid
     */
    private Comparator<Node<K, V>> dirtyComparatorByID = new Comparator<Node<K, V>>() {
        @Override
        public int compare(final Node<K, V> o1, final Node<K, V> o2) {
            if (o1 == null) {
                if (o2 == null) {
                    return 0; // o1 == null & o2 == null
                }
                return 1; // o1 == null & o2 != null
            }
            if (o2 == null) {
                return -1; // o1 != null & o2 == null
            }
            final int thisVal = (o1.id < 0 ? -o1.id : o1.id);
            final int anotherVal = (o2.id < 0 ? -o2.id : o2.id);
            return ((thisVal < anotherVal) ? -1 : ((thisVal == anotherVal) ? 0 : 1));
        }
    };
    /**
     * Max allocated Internal nodes
     */
    private int maxInternalNodes = 0;
    /**
     * Max allocated Leaf Nodes
     */
    private int maxLeafNodes = 0;

    // ===================================== Meta data Managemenet

    /**
     * Create B+Tree in File
     *
     * @param autoTune if true the tree try to find best b-order for leaf/internal nodes to fit in a block of
     *                 b_size bytes
     * @param b_size   if autoTune is true is the blockSize, if false is the b-order for leaf/internal nodes
     * @param typeK    the class type of Keys
     * @param typeV    the class type of Values
     * @param fileName base file name (example: /tmp/test)
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public BplusTreeFile(final boolean autoTune, int b_size, final Class<K> typeK, final Class<V> typeV,
                         final String fileName) throws InstantiationException, IllegalAccessException {
        super(autoTune, false, b_size, typeK, typeV);
        //
        this.autoTune = autoTune;
        this.b_size = b_size;
        this.fileName = fileName;
        //
        createReadCaches();
        //
        fileStorage = new File(fileName + ".data");
        fileFreeBlocks = new File(fileName + ".free");
        fileRedo = new File(fileName + ".redo");
        //
        bufstack = BufferStacker.getInstance(blockSize, isDirect);
        freeBlocks = new SimpleBitSet();
        storage = new FileBlockStore(fileStorage.getAbsolutePath(), blockSize, isDirect);
        redoStore = new FileStreamStore(fileRedo.getAbsolutePath(), blockSize << 1);
        redoStore.setSyncOnFlush(false);
        redoStore.setFlushOnWrite(true);
        validState = false;
    }

    private static String getTimeStamp() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmmss");
        return sdf.format(new Date());
    }

    // ===================================== Store Management

    private static boolean renameFileToBroken(final File orig, final String ts) {
        return orig.renameTo(new File(orig.getAbsolutePath() + ".broken." + ts));
    }

    @Override
    protected boolean clearStorage() {
        // Reset Storage
        storage.delete();
        redoStore.delete();
        return (storage.open() && redoStore.open());
    }

    @Override
    protected void clearStates() {
        // Clear Caches
        clearReadCaches();
        clearWriteCaches();
        // Reset Counters
        maxInternalNodes = 0;
        maxLeafNodes = 0;
        storageBlock = 0;
        // Reset bitsets
        freeBlocks = new SimpleBitSet();
        dirtyCheck = new SimpleBitSet();
        //
        // Reset Root node
        super.clearStates();
        // Sync changes
        validState = writeMetaData(false);
        sync();
    }

    @Override
    public int getHighestNodeId() {
        return storageBlock;
    }

    @Override
    protected int allocNode(final boolean isLeaf) {
        int id = freeBlocks.nextSetBit(0);
        if (id < 0) {
            if (isLeaf) {
                maxLeafNodes++;
            } else {
                maxInternalNodes++;
            }
            id = ++storageBlock;
        } else {
            freeBlocks.clear(id);
        }
        return (isLeaf ? id : -id);
    }

    @Override
    protected void freeNode(final Node<K, V> node) {
        if (readOnly) {
            throw new InvalidStateException();
        }
        final int nodeid = node.id;
        if (nodeid == Node.NULL_ID) {
            log.error(this.getClass().getName() + "::freeNode(" + nodeid + ") ERROR");
            return;
        }
        // Se marca como borrado
        node.delete();
        putNode(node);
    }

    @Override
    protected Node<K, V> getNode(final int nodeid) {
        // log.debug("getNode(" +nodeid+ ")");
        if (nodeid == Node.NULL_ID) {
            log.error(this.getClass().getName() + "::getNode(" + nodeid + ") ERROR");
            return null;
        }
        if (disableAllCaches) {
            return getNodeFromStore(nodeid);
        }
        return getNodeCache(nodeid);
    }

    /**
     * Get node from file
     *
     * @param nodeid int with nodeid
     * @return Node<K, V>
     */
    private Node<K, V> getNodeFromStore(final int nodeid) {
        final int index = nodeid < 0 ? -nodeid : nodeid;
        final ByteBuffer buf = storage.get(index);
        final Node<K, V> node = Node.deserialize(buf, this);
        if (rootIdx == node.id) {
            log.warn(this.getClass().getName() + "::getNodeFromStore(" + nodeid + ") WARN LOADED ROOT NODE");
        }
        storage.release(buf);
        if (enableIOStats) {
            getIOStat(nodeid).incPhysRead();
        }
        return node;
    }

    @Override
    protected void putNode(final Node<K, V> node) {
        if (readOnly) {
            throw new InvalidStateException();
        }

        if (disableAllCaches) {
            putNodeToStore(node);
            return;
        }
        setNodeDirty(node);
    }

    /**
     * Put a node in file
     *
     * @param node
     */
    private void putNodeToStore(final Node<K, V> node) {
        final int nodeid = node.id;
        final int index = (nodeid < 0 ? -nodeid : nodeid);
        final FileBlockStore.WriteBuffer wbuf = storage.set(index);
        final ByteBuffer buf = wbuf.buf();
        if (node.isDeleted()) {    // This block is for delete
            if (cleanBlocksOnFree) {
                buf.clear();
                int cx = (blockSize >> 3); // division by 8
                while (cx-- > 0) {
                    buf.putLong(0);    // Fill with zeroes
                }
                buf.flip();
            } else {
                node.clean(buf);    // Generate zeroed minimal buffer
            }
            freeBlocks.set(index);    // Mark block as free
        } else {
            node.serialize(buf);
        }
        wbuf.save();
        if (enableDirtyCheck) {
            dirtyCheck.clear(index);
        }
        if (enableIOStats) {
            getIOStat(nodeid).incPhysWrite();
        }
    }

    @Override
    protected void releaseNodes() {
        final int maxTotalNodes = maxCacheSizeInBytes / blockSize;
        final long ts = System.currentTimeMillis();
        //
        // Calculate how mem is in use
        final int initialDirtyNodesInMem = dirtyLeafNodes.size() + dirtyInternalNodes.size();
        final int initialCacheNodesInMem = cacheLeafNodes.size() + cacheInternalNodes.size();
        final int initialTotalNodesInMem = initialDirtyNodesInMem + initialCacheNodesInMem;
        final boolean doClean = (initialTotalNodesInMem >= maxTotalNodes);
        if (!doClean) {
            return;
        }
        //
        // Commit excess write-buffers
        final boolean autoSync = (initialDirtyNodesInMem >= (maxTotalNodes / 10)); // 10% of nodes are dirty
        if (autoSync) {
            privateSync(true, false);
        }
        //
        // Discard excess read-buffers
        final int currentCacheNodesInMem = cacheLeafNodes.size() + cacheInternalNodes.size();
        // After clean dirty read-cache are full
        final boolean autoEvict = (currentCacheNodesInMem >= maxTotalNodes);
        final int evictedCacheInternal, evictedCacheLeaf;
        if (autoEvict) {
            evictedCacheInternal = removeEldestElementsFromCache(cacheInternalNodes, readCacheInternal);
            evictedCacheLeaf = removeEldestElementsFromCache(cacheLeafNodes, readCacheLeaf);
        } else {
            evictedCacheInternal = 0;
            evictedCacheLeaf = 0;
        }
        //
        // Show stats
        if (autoSync) {
            final int evictedTotal = evictedCacheInternal + evictedCacheLeaf;
            final int currentUsedMem = (dirtyLeafNodes.size() + dirtyInternalNodes.size()
                    + cacheLeafNodes.size() + cacheInternalNodes.size())
                    * blockSize / 1024;
            final StringBuilder sb = new StringBuilder();
            // @formatter:off
            sb
                    .append("releaseNodes()")
                    .append(" maxNodes=").append(maxLeafNodes).append("L/").append(maxInternalNodes).append("I")
                    .append(" autoSync=").append(autoSync)
                    .append(" dirtys=").append(initialDirtyNodesInMem)
                    .append(" caches=").append(initialCacheNodesInMem)
                    .append(" evicted=").append(evictedTotal)
                    .append(" initialMem=").append(initialTotalNodesInMem * blockSize / 1024).append("KB")
                    .append(" currentMem=").append(currentUsedMem).append("KB")
                    .append(" ts=").append(System.currentTimeMillis() - ts);
            // @formatter:on
            if (enableDirtyCheck) {
                sb.append(" dirtyBlocks=").append(dirtyCheck.toString());
            }
            log.info(sb.toString());
        }
    }

    /**
     * Evict from Read Cache excess nodes
     *
     * @param hash    cache to purge
     * @param maxSize max elements to hold in cache
     * @return int number of elements evicted
     */
    @SuppressWarnings("rawtypes")
    private final int removeEldestElementsFromCache(final IntLinkedHashMap<Node> hash, final int maxSize) {
        final int evict = hash.size() - maxSize;
        if (evict <= 0) {
            return 0;
        }

        for (int count = 0; count < evict; count++) {
            hash.removeEldest();
        }
        return evict;
    }

    /**
     * Write metadata to file
     *
     * @param isClean mark file with clean/true or unclean/false
     * @return boolean if operation is ok
     */
    private boolean writeMetaData(final boolean isClean) {
        if (readOnly) {
            return true;
        }
        final FileBlockStore.WriteBuffer wbuf = storage.set(0);
        final ByteBuffer buf = wbuf.buf();
        boolean isOK = false;
        // @formatter:off
        buf
                .putInt(MAGIC_1)
                .putInt(blockSize)
                .putInt(b_order_leaf)
                .putInt(b_order_internal)
                .putInt(storageBlock)
                .putInt(rootIdx)
                .putInt(lowIdx)
                .putInt(highIdx)
                .putInt(elements)
                .putInt(height)
                .putInt(maxInternalNodes)
                .putInt(maxLeafNodes)
                .put((byte) (isClean ? 0xEA : 0x00))
                .putInt(MAGIC_2)
                .flip();
        // @formatter:on
        isOK = wbuf.save();
        if (isClean) {
            storage.sync();
        }
        try {
            if (isClean) {
                SimpleBitSet.serializeToFile(fileFreeBlocks, freeBlocks);
            } else {
                fileFreeBlocks.delete();
            }
        } catch (IOException e) {
            log.error("IOException in writeMetaData(" + isClean + ")", e);
        }
        if (log.isDebugEnabled()) {
            log.debug(this.getClass().getName() + "::writeMetaData() elements=" + elements + " rootIdx="
                    + rootIdx + " lastNodeId=" + storageBlock + " freeBlocks=" + freeBlocks.cardinality());
        }
        return isOK;
    }

    /**
     * Read metadata from file
     *
     * @return true if file is clean or not
     * @throws InvalidDataException if metadata is invalid
     */
    private boolean readMetaData() throws InvalidDataException {
        final ByteBuffer buf = storage.get(0);
        int magic1, magic2, t_b_order_leaf, t_b_order_internal, t_blockSize; // sanity
        boolean isClean = false;
        magic1 = buf.getInt();
        if (magic1 != MAGIC_1) {
            throw new InvalidDataException("Invalid metadata (MAGIC1)");
        }
        t_blockSize = buf.getInt();
        if (t_blockSize != blockSize) {
            throw new InvalidDataException("Invalid metadata (blockSize) " + t_blockSize + " != " + blockSize);
        }
        t_b_order_leaf = buf.getInt();
        t_b_order_internal = buf.getInt();
        if (t_b_order_leaf != b_order_leaf) {
            throw new InvalidDataException("Invalid metadata (b-order leaf) " + t_b_order_leaf + " != "
                    + b_order_leaf);
        }
        if (t_b_order_internal != b_order_internal) {
            throw new InvalidDataException("Invalid metadata (b-order internal) " + t_b_order_internal
                    + " != " + b_order_internal);
        }
        storageBlock = buf.getInt();
        rootIdx = buf.getInt();
        lowIdx = buf.getInt();
        highIdx = buf.getInt();
        elements = buf.getInt();
        height = buf.getInt();
        maxInternalNodes = buf.getInt();
        maxLeafNodes = buf.getInt();
        isClean = (buf.get() == ((byte) 0xEA));
        magic2 = buf.getInt();
        if (magic2 != MAGIC_2) {
            throw new InvalidDataException("Invalid metadata (MAGIC2)");
        }
        if (log.isDebugEnabled()) {
            log.debug(this.getClass().getName() + "::readMetaData() elements=" + elements + " rootIdx="
                    + rootIdx);
        }
        storage.release(buf);
        // Clear Caches
        clearReadCaches();
        clearWriteCaches();
        if (isClean && fileFreeBlocks.exists()) {
            try {
                final SimpleBitSet newFreeBlocks = SimpleBitSet.deserializeFromFile(fileFreeBlocks);
                freeBlocks = newFreeBlocks;
            } catch (IOException e) {
                log.error("IOException in readMetaData()", e);
            }
        }
        return isClean;
    }

    /**
     * Create/Clear file
     */
    public synchronized void create() {
        clear();
    }

    /**
     * Recovery (readOnly can't be enabled)
     *
     * @param forceFullRecovery to force full recovery and disallow incremental recovery
     * @return boolean if all right
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvalidStateException
     */
    public synchronized boolean recovery(final boolean forceFullRecovery) {
        if (storage.isOpen() || redoStore.isOpen() || readOnly) {
            throw new InvalidStateException();
        }
        if (!storage.open() || !redoStore.open()) {
            storage.close();
            redoStore.close();
            return false;
        }
        final ByteBuffer buf = bufstack.pop();
        final long tsBegin = System.currentTimeMillis();
        // Try to read Redo Meta Sync State to choose recovery mode
        boolean recoveryIncremental = false;
        if (!forceFullRecovery) {
            buf.clear();
            if (redoStore.isEmpty()) {
                recoveryIncremental = true;
            } else if (redoStore.readFromEnd(4, buf) < 0) {
                if (redoStore.isValid()) {
                    recoveryIncremental = true;
                }
            } else {
                final int head = buf.get();
                final int value1 = buf.get();
                final int value2 = buf.get();
                final int foot = buf.get();
                if ((head == 0x0C) && (head == foot) && (value1 == value2)) {
                    log.info("Meta Sync State=" + HexStrings.nativeAsHex((byte) head));
                }
            }
        }
        log.info(recoveryIncremental ? "Incremental Recovery Allowed" : "Full Recovery Needed");

        final boolean oldUseRedo = useRedo;
        final K factoryK = factoryK();
        final V factoryV = factoryV();
        BplusTreeFile<K, V> treeTmp = null;
        if (recoveryIncremental) {
            log.info("Recovery in Incremental Mode (Replay Redo)");

            treeTmp = this;
            useRedo = false;    // HACK (don't call setUseRedo, destructive)
            validState = true;    // HACK
        } else {
            log.info("Recovery in Full Mode (Scan datafiles)");
            //
            final int blocks = storage.sizeInBlocks();
            try {
                treeTmp = new BplusTreeFile<K, V>(autoTune, b_size, getGenericFactoryK().type,
                        getGenericFactoryV().type, fileName + ".recover");
            } catch (InstantiationException e) {
                log.error("InstantiationException in recovery()", e);
                return false;
            } catch (IllegalAccessException e) {
                log.error("IllegalAccessException in recovery()", e);
                return false;
            }
            //
            treeTmp.clear();
            treeTmp.setUseRedo(false);
            treeTmp.setDisableAutoSyncStore(true);
            //
            // Reconstruct Tree, Scan BlockStore for data
            log.info("Blocks to Scan: " + blocks);
            for (int index = 1; index < blocks; index++) {
                try {
                    if ((index % 100) == 0) {
                        log.info("Recovering block [" + index + "/" + blocks + "]");
                    }
                    final Node<K, V> node = getNodeFromStore(index); // read
                    if (node.isLeaf()) {
                        final LeafNode<K, V> leafNode = (LeafNode<K, V>) node;
                        for (int i = 0; i < node.allocated; i++) {
                            final K key = leafNode.keys[i];
                            final V value = leafNode.values[i];
                            treeTmp.put(key, value);
                        }
                    }
                } catch (Node.InvalidNodeID e) {
                    // Skip
                }
            }
        }
        //
        // Apply Redo Store
        final long size = redoStore.size();
        long offset = 0;
        int count = 1;
        while (offset < size) {
            buf.clear();
            if ((offset = redoStore.read(offset, buf)) < 0) {
                break;
            }
            if ((count++ % 100) == 0) {
                log.info("Applying Redo [offset=" + offset + "]");
            }
            final int head = buf.get();
            switch (head) {
                case 0x0A: // PUT
                    treeTmp.put(factoryK.deserialize(buf), factoryV.deserialize(buf));
                    break;
                case 0x0B: // REMOVE
                    treeTmp.remove(factoryK.deserialize(buf));
                    break;
                case 0x0C: // META
                    log.info("Meta type=" + HexStrings.nativeAsHex(buf.get()));
                    break;
                default: // Unknown
                    log.info("Redo Operation=" + HexStrings.nativeAsHex((byte) head) + " Unknown");
                    break;
            }
        }
        treeTmp.close();
        storage.close();
        redoStore.close();
        //
        if (recoveryIncremental) {
            useRedo = oldUseRedo;    // HACK (don't call setUseRedo, destructive)
            log.info("Incremental Recovery completed time=" + (System.currentTimeMillis() - tsBegin) + "ms");
            return true;
        }
        // Rename data files
        final String ts = getTimeStamp();
        if (renameFileToBroken(fileStorage, ts) && renameFileToBroken(fileRedo, ts)) {
            // Rename tmp to good
            treeTmp.fileStorage.renameTo(fileStorage);
            // Remove tmp redo
            treeTmp.fileRedo.delete();
            // Remove tmp free blocks
            treeTmp.fileFreeBlocks.delete();
            // Remove broken free blocks
            fileFreeBlocks.delete();
            log.info("Full Recovery completed time=" + (System.currentTimeMillis() - tsBegin) + "ms");
            return true;
        }
        log.info("Full Recovery failed time=" + (System.currentTimeMillis() - tsBegin) + "ms");
        return false;
    }

    /**
     * Open file
     *
     * @return boolean if all right
     * @throws InvalidDataException if metadata is invalid
     */
    public synchronized boolean open() throws InvalidDataException {
        boolean allRight = false;
        if (storage.isOpen() || redoStore.isOpen()) {
            throw new InvalidStateException();
        }
        storage.open(readOnly);
        redoStore.open(readOnly);
        try {
            if ((storage.sizeInBlocks() == 0) && (redoStore.isEmpty())) {
                clearStates();
                validState = true;
                return true;
            }
            try {
                boolean isClean = readMetaData();
                // log.debug(this.hashCode() + "::open() clean=" + isClean);
                if (isClean) {
                    if (writeMetaData(false)) {
                        populateCache();
                        allRight = true;
                    }
                } else {
                    // Broken
                    throw new InvalidDataException("NEED RECOVERY");
                }
            } catch (InvalidDataException e) {
                validState = false;
                storage.close();
                redoStore.close();
                throw e;
            }
        } finally {
            releaseNodes();
        }
        validState = true;
        return allRight;
    }

    /**
     * Close storage file and sync pending changes/dirty nodes
     */
    public synchronized void close() {
        if (storage.isOpen()) {
            if (useRedoThread && (redoThread != null)) {
                stopRedoThread(redoThread);
            }
            sync();
            writeMetaData(true);
            if (enableDirtyCheck) {
                log.info("dirtyCheck=" + dirtyCheck);
            }
        }
        storage.close();
        redoStore.close();
        clearReadCaches();
        clearWriteCaches();
        validState = false;
        // log.debug(this.hashCode() + "::close() done");
    }

    /**
     * Clear tree and Delete associated files
     */
    public synchronized void delete() {
        try {
            clear();
        } catch (Exception ign) {
        }
        try {
            close();
        } catch (Exception ign) {
        }
        try {
            fileRedo.delete();
        } catch (Exception ign) {
        }
        try {
            fileStorage.delete();
        } catch (Exception ign) {
        }
        try {
            fileFreeBlocks.delete();
        } catch (Exception ign) {
        }
    }

    /**
     * Use Redo system?
     *
     * @param useRedo (default true)
     */
    public synchronized void setUseRedo(final boolean useRedo) {
        if (validState && this.useRedo && !useRedo) { // Remove Redo
            redoQueue.clear();
            redoStore.clear();
        }
        this.useRedo = useRedo;
    }

    /**
     * Use Dedicated Thread for Redo?
     *
     * @param useRedoThread (default false)
     */
    public synchronized void setUseRedoThread(final boolean useRedoThread) {
        if (this.useRedoThread && !useRedoThread) { // Stop Redo Thread
            if (redoThread != null) {
                stopRedoThread(redoThread);
            }
        }
        this.useRedoThread = useRedoThread;
    }

    // ===================================== READ CACHE

    /**
     * Disable Populate Cache?
     * Populate Caches is pre-read datastore to cache nodes
     *
     * @param disablePopulateCache (default false)
     */
    public synchronized void setDisablePopulateCache(final boolean disablePopulateCache) {
        this.disablePopulateCache = disablePopulateCache;
    }

    /**
     * Disable AutoSync Storage?
     * If disable is true, when autoSync is invoked, storage sync is not forced (speed-up, but less secure)
     *
     * @param disableAutoSyncStore (default false)
     */
    public synchronized void setDisableAutoSyncStore(final boolean disableAutoSyncStore) {
        this.disableAutoSyncStore = disableAutoSyncStore;
    }

    /**
     * Enable mmap of files (default is not enabled), call before use {@link #open()}
     * <p/>
     * Recommended use of: {@link #enableMmapIfSupported()}
     * <p/>
     * <b>NOTE:</b> 32bit JVM can only address 2GB of memory, enable mmap can throw
     * <b>java.lang.OutOfMemoryError: Map failed</b> exceptions
     */
    public synchronized void enableMmap() {
        if (validState) {
            throw new InvalidStateException();
        }
        storage.enableMmap();
    }

    /**
     * Enable mmap of files (default is not enabled) if JVM is 64bits, call before use {@link #open()}
     */
    public synchronized void enableMmapIfSupported() {
        if (validState) {
            throw new InvalidStateException();
        }
        storage.enableMmapIfSupported();
    }

    /**
     * Enable Locking of files (default is not enabled), call before use {@link #open()}
     */
    public synchronized void enableLocking() {
        if (validState) {
            throw new InvalidStateException();
        }
        storage.enableLocking();
    }

    private void createRedoThread() {
        if (useRedoThread && (redoThread == null)) {
            redoThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        doShutdownRedoThread.set(false);
                        ByteBuffer buf = null;
                        while (!doShutdownRedoThread.get()) {
                            buf = redoQueue.poll(1000, TimeUnit.MILLISECONDS);
                            if (buf != null) {
                                redoStore.write(buf);
                                bufstack.push(buf);
                            }
                        }
                        endProcess();
                    } catch (InterruptedException ie) {
                        endProcess();
                        Thread.currentThread().interrupt(); // Preserve
                    } catch (Exception e) {
                        log.error("Exception in createRedoThread()", e);
                    } finally {
                        redoThread = null;
                    }
                }

                private final void endProcess() {
                    if (!redoQueue.isEmpty()) {
                        ByteBuffer buf = null;
                        while ((buf = redoQueue.poll()) != null) {
                            redoStore.write(buf);
                        }
                    }
                    redoStore.sync();
                }
            });
            redoThread.start();
        }

    }

    private void stopRedoThread(final Thread localRedoThread) {
        try {
            doShutdownRedoThread.set(true);
            localRedoThread.join(3000);
            localRedoThread.interrupt();
            localRedoThread.join(30000);
        } catch (Exception e) {
            log.error("Exception in stopRedoThread(" + localRedoThread + ")", e);
        }
    }

    /**
     * submit put to redo
     *
     * @param key
     * @param value
     */
    @Override
    protected void submitRedoPut(final K key, final V value) {
        if (!useRedo) {
            return;
        }
        createRedoThread();
        final ByteBuffer buf = bufstack.pop();
        buf.put((byte) 0x0A); // PUT HEADER
        key.serialize(buf);
        value.serialize(buf);
        buf.put((byte) 0x0A); // PUT FOOTER
        buf.flip();
        if (useRedoThread) {
            try {
                redoQueue.put(buf);
            } catch (InterruptedException e) {
                log.error("InterruptedException in submitRedoPut(key, value)", e);
            }
        } else {
            redoStore.write(buf);
            bufstack.push(buf);
        }
    }

    /**
     * submit remove to redo
     *
     * @param key
     */
    @Override
    protected void submitRedoRemove(final K key) {
        if (!useRedo) {
            return;
        }
        createRedoThread();
        final ByteBuffer buf = bufstack.pop();
        buf.put((byte) 0x0B); // REMOVE HEADER
        key.serialize(buf);
        buf.put((byte) 0x0B); // REMOVE FOOTER
        buf.flip();
        if (useRedoThread) {
            try {
                redoQueue.put(buf);
            } catch (InterruptedException e) {
                log.error("InterruptedException in submitRedoRemove(key)", e);
            }
        } else {
            redoStore.write(buf);
            bufstack.push(buf);
        }
    }

    /**
     * submit metadata to redo
     *
     * @param futureUse ignored (byte in the range of 0-0x7F)
     */
    @Override
    protected void submitRedoMeta(final int futureUse) {
        if (!useRedo) {
            return;
        }
        createRedoThread();
        final ByteBuffer buf = bufstack.pop();
        buf.putInt(0x0C0C0C0C); // META HEADER/FOOTER
        buf.flip();
        if (useRedoThread) {
            try {
                redoQueue.put(buf);
            } catch (InterruptedException e) {
                log.error("InterruptedException in submitRedoMeta(" + futureUse + ")", e);
            }
        } else {
            redoStore.write(buf);
            bufstack.push(buf);
        }
    }

    /**
     * Dump tree in text form to a file
     *
     * @param file
     * @throws FileNotFoundException
     */
    public void dumpStorage(final String file) throws FileNotFoundException {
        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream(file));
            dumpStorage(out);
        } finally {
            try {
                out.close();
            } catch (Exception ign) {
            }
        }
    }

    /**
     * Dump tree in text form to PrintStream (System.out?)
     *
     * @param out PrintStream
     */
    public synchronized void dumpStorage(final PrintStream out) {
        if (!validState) {
            throw new InvalidStateException();
        }
        try {
            final StringBuilder sb = new StringBuilder(4096);
            sb.append("#").append("ID").append("\t").append("Node").append("\n");
            for (int i = 1; i < storageBlock; i++) {
                final Node<K, V> node = getNode(i);
                sb.append(i).append((rootIdx == i) ? "R\t" : "\t").append(node).append("\n");
                if ((i % 1000) == 0) {
                    out.print(sb.toString());
                    sb.setLength(0);
                }
            }
            if (sb.length() > 0) {
                out.print(sb.toString());
                sb.setLength(0);
            }
        } finally {
            releaseNodes();
        }
    }

    // ===================================== WRITE CACHE

    /**
     * @return current value of cache in bytes
     */
    public synchronized int getMaxCacheSizeInBytes() {
        return maxCacheSizeInBytes;
    }

    /**
     * Clear caches and Set new value of maximal bytes used for nodes in cache.
     *
     * <p>
     * <ul>
     * <li>Calculo aproximado del numero de elementos que se pueden mantener en memoria: <br/>
     * elementos=nodos*(b-leaf-order*2/3)
     * <li>Calculo aproximado del numero de nodos necesarios para mantener en memoria N elementos: <br/>
     * nodos=elementos/(b-leaf-order*2/3)
     * <li>El cache necesario para almacenar N nodos: <br/>
     * cache-size=nodos*blocksize
     * </ul>
     *
     * <p>
     * Ejemplos, para almacenar 5millones de registros (bloque de 1k):
     *
     * <ul>
     * <li>LongHolder (8 bytes) b-leaf-order=63 <br/>
     * 5000000/(63*2/3) = 119047nodos * 1024bytes = 121.904.128 bytes
     * <li>IntHolder (4 bytes) b-leaf-order=127 <br/>
     * 5000000/(127*2/3) = 59055nodos * 1024bytes = 60.472.320 bytes
     * </ul>
     *
     * @param newsize size of cache in bytes (0 only clear caches)
     */
    public synchronized void setMaxCacheSizeInBytes(final int newsize) {
        if (validState) {
            log.info(this.getClass().getName() + "::setMaxCacheSizeInBytes newsize=" + newsize
                    + " flushing write-cache");
            privateSync(true, false);
            clearReadCaches();
        }
        if (newsize >= 1024) { // 1KB minimal
            maxCacheSizeInBytes = newsize;
            createReadCaches();
        }
    }

    /**
     * Recalculate size of read caches
     */
    private void recalculateSizeReadCaches() {
        final int maxCacheNodes = (maxCacheSizeInBytes / blockSize);
        readCacheInternal = Math.max((int) (maxCacheNodes * .05f), 37);
        readCacheLeaf = Math.max((int) (maxCacheNodes * .95f), 37);
    }

    /**
     * Create read caches
     */
    private void createReadCaches() {
        recalculateSizeReadCaches();
        if (log.isDebugEnabled()) {
            log.debug(this.getClass().getName() + "::createReadCaches readCacheInternal=" + readCacheInternal
                    + " readCacheLeaf=" + readCacheLeaf);
        }
        cacheInternalNodes = createCacheLRUlinked(readCacheInternal);
        cacheLeafNodes = createCacheLRUlinked(readCacheLeaf);
    }

    /**
     * Clear read caches
     */
    private final void clearReadCaches() {
        // Clear without shrink
        cacheInternalNodes.clear(false);
        cacheLeafNodes.clear(false);
    }

    /**
     * Create a LRU hashmap of size maxSize
     *
     * @param maxSize
     * @return IntLinkedHashMap
     */
    @SuppressWarnings("rawtypes")
    private IntLinkedHashMap<Node> createCacheLRUlinked(final int maxSize) {
        return new IntLinkedHashMap<Node>((int) (maxSize * 1.5f), Node.class, true);
    }

    /**
     * Populate read cache if cache is enabled
     */
    private void populateCache() {
        if (disableAllCaches || disablePopulateCache) {
            return;
        }
        // Populate Cache
        final long ts = System.currentTimeMillis();
        for (int index = 1; ((index < storageBlock) && (cacheInternalNodes.size() < readCacheInternal) && (cacheLeafNodes
                .size() < readCacheLeaf)); index++) {
            if (freeBlocks.get(index)) {
                continue; // skip free
            }
            try {
                final Node<K, V> node = getNodeFromStore(index); // read
                (node.isLeaf() ? cacheLeafNodes : cacheInternalNodes).put(node.id, node);
            } catch (Node.InvalidNodeID e) {
                freeBlocks.set(index); // mark index as free
            }
        }
        log.info("Populated read cache ts=" + (System.currentTimeMillis() - ts) + " blocks=" + storageBlock
                + " elements=" + elements);
    }

    /**
     * Get node from cache
     *
     * @param nodeid int with nodeid
     * @return Node<K, V>
     */
    @SuppressWarnings("unchecked")
    private Node<K, V> getNodeCache(final int nodeid) {
        final boolean isLeaf = Node.isLeaf(nodeid);
        boolean responseFromCache = true;
        Node<K, V> node = (isLeaf ? dirtyLeafNodes : dirtyInternalNodes).get(nodeid);
        if (node == null) {
            node = (isLeaf ? cacheLeafNodes : cacheInternalNodes).get(nodeid);
            if (node == null) {
                if (log.isDebugEnabled()) {
                    log.debug("diskread node id=" + nodeid);
                }
                node = getNodeFromStore(nodeid);
                responseFromCache = false;
                (node.isLeaf() ? cacheLeafNodes : cacheInternalNodes).put(nodeid, node);
            }
        }
        if (enableIOStats) {
            if (responseFromCache) {
                getIOStat(nodeid).incCacheRead();
            }
        }
        return node;
    }

    // ===================================== POOL BUFFER

    /**
     * Put a node in dirty cache
     *
     * @param node
     */
    private void setNodeDirty(final Node<K, V> node) {
        final int nodeid = node.id;
        final int index = nodeid < 0 ? -nodeid : nodeid;
        (node.isLeaf() ? dirtyLeafNodes : dirtyInternalNodes).put(nodeid, node);
        (node.isLeaf() ? cacheLeafNodes : cacheInternalNodes).remove(nodeid);
        if (enableIOStats) {
            getIOStat(nodeid).incCacheWrite();
        }
        if (enableDirtyCheck) {
            dirtyCheck.set(index);
        }
    }

    /**
     * Write all dirty nodes
     */
    public synchronized void sync() {
        if (!validState) {
            throw new InvalidStateException();
        }
        try {
            privateSync(true, true);
        } finally {
            releaseNodes();
        }
    }

    // ===================================== STATS

    /**
     * set callback called when buffers where synched to disk
     *
     * @param callback
     */
    public void setCallback(final FileBlockStore.CallbackSync callback) {
        storage.setCallback(callback);
    }

    /**
     * Write all dirty nodes
     */
    @SuppressWarnings("unchecked")
    private void privateSync(final boolean syncInternal, final boolean forceSyncStore) {
        if (readOnly) {
            return;
        }
        final long ts = System.currentTimeMillis();
        boolean isDirty = false;
        if (!dirtyLeafNodes.isEmpty() || !dirtyInternalNodes.isEmpty()) {
            submitRedoMeta(0); // Meta Sync (full recover needed)
        }
        //
        // Write Leaf Nodes
        if (!dirtyLeafNodes.isEmpty()) {
            isDirty = true;
            final Node<K, V>[] dirtyBlocks = dirtyLeafNodes.getValues();
            Arrays.sort(dirtyBlocks, dirtyComparatorByID);
            for (Node<K, V> node : dirtyBlocks) {
                if (node == null) {
                    break;
                }
                // if (log.isDebugEnabled()) log.debug("node.id=" + node.id);
                dirtyLeafNodes.remove(node.id);
                putNodeToStore(node);
                if (!node.isDeleted()) {
                    cacheLeafNodes.put(node.id, node);
                }
            }
            if (!dirtyLeafNodes.isEmpty()) {
                dirtyLeafNodes.clear(false); // Clear without shrink
            }
        }
        // Write Internal Nodes
        if (syncInternal && !dirtyInternalNodes.isEmpty()) {
            isDirty = true;
            final Node<K, V>[] dirtyBlocks = dirtyInternalNodes.getValues();
            Arrays.sort(dirtyBlocks, dirtyComparatorByID);
            for (Node<K, V> node : dirtyBlocks) {
                if (node == null) {
                    break;
                }
                // if (log.isDebugEnabled()) log.debug("node.id=" + node.id);
                dirtyInternalNodes.remove(node.id);
                putNodeToStore(node);
                if (!node.isDeleted()) {
                    cacheInternalNodes.put(node.id, node);
                }
            }
            if (!dirtyInternalNodes.isEmpty()) {
                dirtyInternalNodes.clear(false); // Clear without shrink
            }
        }
        //
        if (isDirty) {
            writeMetaData(false);
            if (forceSyncStore || !disableAutoSyncStore) {
                storage.sync();
            }
            redoQueue.clear();
            redoStore.clear();
        }
        if (log.isDebugEnabled()) {
            final StringBuilder sb = new StringBuilder();
            // @formatter:off
            sb
                    .append(this.getClass().getName()).append("::sync()")
                    .append(" elements=").append(elements)
                    .append(" Int=").append(maxInternalNodes)
                    .append(" Leaf=").append(maxLeafNodes)
                    .append(" dirty{")
                    .append(" Int=").append(dirtyInternalNodes.size())
                    .append(" Leaf=").append(dirtyLeafNodes.size())
                    .append(" }")
                    .append(" cache{")
                    .append(" Int=").append(cacheInternalNodes.size())
                    .append(" Leaf=").append(cacheLeafNodes.size())
                    .append(" }")
                    .append(" storage{")
                    .append(" total=").append(storage.sizeInBlocks())
                    .append(" free=").append(freeBlocks.cardinality())
                    .append(" }")
                    .append(" time=").append(System.currentTimeMillis() - ts);
            // @formatter:on
            log.debug(sb.toString());
        }
        // clearWriteCaches();
    }

    /**
     * Clear write caches without sync dirty nodes
     */
    private final void clearWriteCaches() {
        // Clear without shrink
        dirtyInternalNodes.clear(false);
        dirtyLeafNodes.clear(false);
    }

    /**
     * Return or Create if not exist an IOStat object for a nodeid
     *
     * @param nodeid
     * @return IOStat object
     */
    private IOStat getIOStat(final int nodeid) {
        IOStat io = iostats.get(nodeid);
        if (io == null) {
            io = new IOStat(nodeid);
            iostats.put(nodeid, io);
        }
        return io;
    }

    /**
     * Dump IOStats of tree to a file
     *
     * @param file
     * @throws FileNotFoundException
     */
    public void dumpStats(final String file) throws FileNotFoundException {
        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream(file));
            dumpStats(out);
        } finally {
            try {
                out.close();
            } catch (Exception ign) {
            }
        }
    }

    /**
     * Dump IOStats of tree to PrintStream (System.out?)
     *
     * @param out PrintStream
     */
    @SuppressWarnings("unused")
    public synchronized void dumpStats(final PrintStream out) {
        if (!validState) {
            throw new InvalidStateException();
        }
        out.println("=== Stats ===");
        out.println("maxAllocatedInternalNodes=" + maxInternalNodes);
        out.println("maxAllocatedLeafNodes=" + maxLeafNodes);
        out.println("readCacheSizeInternalNodes=" + readCacheInternal);
        out.println("readCacheSizeLeafNodes=" + readCacheLeaf);
        out.println("leafNodeSize=" + leafNodeFactory.getStructMaxSize());
        out.println("internalNodeSize=" + internalNodeFactory.getStructMaxSize());
        out.println("blockSize=" + blockSize);
        out.println("currentCacheSize=" + (maxCacheSizeInBytes / 1024 / 1024) + "MB");
        out.println("minRecomendedCacheSize="
                + ((blockSize * (maxInternalNodes + maxLeafNodes)) / 1024 / 1024) + "MB");

        if (!enableIOStats) {
            out.println("=== IOStats ===");
            out.println("not enabled");
            return;
        }
        //
        final IOStat[] ios = iostats.getValues();
        final Comparator<IOStat> ioComparator = new Comparator<IOStat>() {
            @Override
            public int compare(final IOStat o1, final IOStat o2) {
                if (o1 == null) {
                    if (o2 == null) {
                        return 0; // o1 == null & o2 == null
                    }
                    return 1; // o1 == null & o2 != null
                }
                if (o2 == null) {
                    return -1; // o1 != null & o2 == null
                }
                final long thisVal = ((o1.id < 0 ? 0 : 1) << 63) + (o1.physRead << 16) + o1.physWrite; // o1.id;
                final long anotherVal = ((o2.id < 0 ? 0 : 1) << 63) + (o2.physRead << 16) + o2.physWrite; // o2.id;
                return ((thisVal < anotherVal) ? -1 : ((thisVal == anotherVal) ? 0 : 1));
            }
        };
        Arrays.sort(ios, ioComparator);
        for (final IOStat io : ios) {
            if (io == null) {
                break;
            }
            out.println(io.toString());
        }
    }

    /**
     * Class to hold I/O stats of nodes (physical read/write), (cache read/write)
     */
    private static class IOStat {
        public final int id;
        public int physRead = 0;
        public int physWrite = 0;
        public int cacheRead = 0;
        public int cacheWrite = 0;

        public IOStat(final int nodeid) {
            this.id = nodeid;
        }

        public IOStat incPhysRead() {
            physRead++;
            return this;
        }

        public IOStat incPhysWrite() {
            physWrite++;
            return this;
        }

        public IOStat incCacheRead() {
            cacheRead++;
            return this;
        }

        public IOStat incCacheWrite() {
            cacheWrite++;
            return this;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            // @formatter:off
            sb
                    .append(Node.isLeaf(id) ? "L" : "I")
                    .append(id < 0 ? -id : id)
                    .append(" pr=").append(physRead)
                    .append(" pw=").append(physWrite)
                    .append(" cr=").append(cacheRead)
                    .append(" cw=").append(cacheWrite);
            // @formatter:on
            return sb.toString();
        }
    }
}
