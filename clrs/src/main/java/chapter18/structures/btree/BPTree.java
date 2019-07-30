package chapter18.structures.btree;

import chapter18.io.FileBlockStore;
import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class BPTree implements Itree {

    private static final Logger log = Logger.getLogger(BPTree.class);


    private static int INVALID_OFFSET = 0xdeadbeef;
    private static int LEAF = 0;
    private static int INTERNAL = 1;
    String filename;
    int level;
    INode root;
    long fileSize;
    int blockSize;
    Link freeBlocks;


    public static final AtomicInteger ID = new AtomicInteger(0);
    public static final String FILE_PATH = "/tmp/bptree.data";
    public static final int BLOCK_SIZE = 512; //kb
    FileBlockStore storage = new FileBlockStore(FILE_PATH, BLOCK_SIZE, false);

    static boolean isLeaf(INode node) {
        return node.type() == LEAF;
    }

    static boolean listEmpty(final Link head) {
        return head.next == head;
    }

    @Override
    public long get(int key) {
        int ret = -1;
        INode node = this.root;
        while (node != null) {
            int i = Arrays.binarySearch(node.keys(), key);
            if (isLeaf(node)) {
                ret = i >= 0 ? node.keys()[i] : -1;
                break;
            } else {
                if (i >= 0) {
                    node = node.children()[i + 1];
                } else {
                    i = -i - 1;
                    node = node.children()[i];
                }
            }
        }

        return ret;
    }

    @Override
    public INode put(int key, long data) {
        return insert(key, data);
    }

    private INode insert(int key, long data) {
        INode node = this.root;
        while (node != null) {
            if (isLeaf(node)) {
                return leafInsert(node, key, data);
            } else {
                int i = keyBinarySearch(node, key);
                if (i >= 0) {
                    node = node.children()[i + 1];
                } else {
                    i = -i - 1;
                    node = node.children()[i];
                }
            }
        }

        /* new root */
        INode root = newLeaf();
        root.keys()[0] = key;
        root.data()[0] = data;
        root.incrementChildren();
        tree -> root = new_node_append(tree, root);
        tree -> level = 1;
        node_flush(tree, root);
        return 0;
    }

    private void left_node_add(INode node, INode left) {
        new_node_append(left);

        INode prev = node_fetch(node.prev.blockIndex);
        if (prev != null) {
            prev.next = left;
            left.prev = prev;
            nodeFlush(prev);
        } else {
            left -> prev = INVALID_OFFSET;
        }
        left.next = node;
        node.prev = left;
    }

    private INode node_fetch(int blockIndex) {

        return null;
    }

    private INode new_node_append(INode node) {
        /* assign new offset to the new node */
        if (listEmpty(this.freeBlocks)) {
            tree -> file_size += _block_size;
        } else {
            Link block;
            block = freeBlocks.next;
            list_del( & block -> link)
            node -> self = block -> offset;
            free(block);
        }
        return node -> self;
    }

    private void updateSubNode(INode parent, int index, INode subNode) {
        parent.children()[index] = subNode;
        subNode.setParent(parent);
        nodeFlush(subNode);
    }

    private INode delete(int key, long data) {

    }

    private int keyBinarySearch(INode node, int key) {
        int[] keys = node.keys();
        int len = isLeaf(node) ? node.keys().length : node.childrenSize() - 1;
        int low = -1;
        int high = len;

        while (low + 1 < high) {
            int mid = low + (high - low) / 2;
            if (key > keys[mid]) {
                low = mid;
            } else {
                high = mid;
            }
        }

        if (high >= len || keys[high] != key) {
            return -high - 1;
        } else {
            return high;
        }
    }

    private int leafInsert(INode leaf, int key, long data) {
        /* Search key location */
        int insert = keyBinarySearch(leaf, key);
        if (insert >= 0) {
            /* Already exists */
            return -1;
        }
        insert = -insert - 1;

        /* leaf is full */
        if (leaf.childrenSize() == _max_entries) {
            int split_key;
            /* split = [m/2] */
            int split = (_max_entries + 1) / 2;
            INode sibling = newLeaf();

            /* sibling leaf replication due to location of insertion */
            if (insert < split) {
                split_key = leaf_split_left(tree, leaf, sibling, key, data, insert);
            } else {
                split_key = leaf_split_right(tree, leaf, sibling, key, data, insert);
            }

            /* build new parent */
            if (insert < split) {
                return parent_node_build(tree, sibling, leaf, split_key);
            } else {
                return parent_node_build(tree, leaf, sibling, split_key);
            }
        } else {
            leaf_simple_insert(tree, leaf, key, data, insert);
            node_flush(tree, leaf);
        }

        return 0;
    }

    private INode newLeaf() {
        INode node = newNode();
        node.setType(LEAF);
        return node;
    }

    private INode newNode() {
        node -> self = INVALID_OFFSET;
        node -> parent = INVALID_OFFSET;
        node -> prev = INVALID_OFFSET;
        node -> next = INVALID_OFFSET;
        node -> children = 0;
        return node;
    }

    private void leafSimpleInsert(INode leaf, int key, long data, int insert) {

        System.arraycopy(leaf.keys(), insert, leaf.keys(), insert + 1, leaf.keys().length - insert);
        leaf.keys()[insert] = key;
        leaf.data()[insert] = data;
        leaf.incrementChildren();
    }

    private void nonLeafSimpleInsert(INode node, INode leftCh, INode rightCh,
                                     int key, int insert) {
//        void *memmove( void* dest, const void* src, size_t count );
//        memmove( & key(node)[insert + 1], &key(node)[insert], (node -> childrenSize - 1 - insert) * sizeof(key_t));
//        memmove( & sub(node)[insert + 2], &sub(node)[insert + 1], (node -> childrenSize - 1 - insert) * sizeof(off_t));

        /* insert new key and sub-nodes */
        node.keys()[insert] = key;
        subNodeUpdate(node, insert, leftCh);
        subNodeUpdate(node, insert + 1, rightCh);
        node.incrementChildren();
    }


    private void subNodeUpdate(INode parent, int index, INode subNode) {

        parent.setChild(index, subNode);
        subNode.setParent(parent);
        nodeFlush(subNode);
    }

    private void subNodeFlush(INode parent, INode subNode) {
        subNode.setParent(parent);
        nodeFlush(subNode);
    }

    private void nodeFlush(INode node) {

        final FileBlockStore.WriteBuffer wbuf = storage.set(ID.incrementAndGet());
        final ByteBuffer buf = wbuf.buf();
        node.serialize(buf);
        buf.flip();
        wbuf.save();

        storage.sync();
    }

    void leftNodeAdd(INode node, INode left) {

        new_node_append(left);

        INode prev = node_fetch(node.prev);
        if (prev != null) {
            prev.next = left.self;
            left.prev = prev.self;
            nodeFlush(prev);
        } else {
            left.prev = INVALID_OFFSET;
        }
        left.next = node.self;
        node.prev = left.self;
    }

    void rightNodeAdd(INode node, INode right) {
        new_node_append(tree, right);

        struct bplus_node *next = node_fetch(tree, node -> next);
        if (next != NULL) {
            next -> prev = right -> self;
            right -> next = next -> self;
            node_flush(tree, next);
        } else {
            right -> next = INVALID_OFFSET;
        }
        right -> prev = node -> self;
        node -> next = right -> self;
    }

    private int parent_node_build(INode l_ch,
                                  INode r_ch, int key) {
        if (l_ch.parent() == null && r_ch.parent() == null) {
            /* new parent */
            INode parent = newInternalNode();
            parent.keys()[0] = key;
            parent.children()[0] = l_ch;
            parent.children()[1] = r_ch;
            parent.incrementChildren(2);

            l_ch.setParent(parent);
            r_ch.setParent(parent);

            this.incrementLevel();

            nodeFlush(l_ch);
            nodeFlush(r_ch);
            nodeFlush(parent);
            return 0;

        } else if (r_ch.parent() == null) {
            return non_leaf_insert(tree, node_fetch(tree, l_ch -> parent), l_ch, r_ch, key);
        } else {
            return non_leaf_insert(tree, node_fetch(tree, r_ch -> parent), l_ch, r_ch, key);
        }
    }

    private INode newInternalNode() {
        INode node = newNode();
        node.setType(INTERNAL);
        return node;
    }

    private INode newLeafNode() {
        INode node = newNode();
        node.setType(LEAF);
        return node;
    }

    @Override
    public long rangeGet(int key1, int key2) {
        return 0;
    }

    @Override
    public Itree init(String filename, int blockSize) {

        int i;
        INode node;


        if ((blockSize & (blockSize - 1)) != 0) {
            log.error("Block size must be pow of 2!");
            return null;
        }

        if (blockSize < INode.byteSize()) {
            log.error("block size is too small for one node!");
            return null;
        }

        int _max_order = (blockSize - INode.byteSize()) / (4 + 8);
        int _max_entries = (blockSize - INode.byteSize()) / (4 + 8);
        if (_max_order <= 2) {
            log.error("block size is too small for one node!");
            return null;
        }


        this.freeBlocks = new Link();
        initList(this.freeBlocks);

        this.filename = filename;

        this.root = null;
        this.fileSize = 0;


        storage.delete();
        storage.open();


        return this;
    }

    @Override
    public void destroy() {

    }

    @Override
    public int openFile(String filename) {
        return 0;
    }

    @Override
    public void closeFile(int fd) {

    }


    @Override
    public void dump() {

    }

    @Override
    public void initList(Link link) {
        link.prev = link;
        link.next = link;
    }


    int pread(int fd, ByteBuffer byteBuffer, int count, int offset) {
        return 1;
    }
}
