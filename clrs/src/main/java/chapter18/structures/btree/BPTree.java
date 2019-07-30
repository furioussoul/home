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
    public int maxOrder;


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

        this.incrementLevel();
        flushNode(root);

        return root;
    }

    /**
     * add left to node's left
     * [prev]-[left]-[node]
     *
     * @param node
     * @param left
     */
    private void addLeftNode(INode node, INode left) {

        INode prev = node.getPrev();
        if (prev != null) {
            prev.setNext(left);
            left.setPrev(prev);
            flushNode(prev);
        }
        left.setNext(node);
        node.setPrev(left);
    }

    /**
     * add right to node's left
     * [node]-[right]-[next]
     *
     * @param node
     * @param right
     */
    void addRightNode(INode node, INode right) {

        INode next = node.getNext();
        if (next != null) {
            next.setPrev(right);
            right.setNext(next);
            flushNode(next);
        }
        right.setPrev(node);
        node.setNext(right);
    }

    private INode node_fetch(int blockIndex) {

        return null;
    }

    private void updateSubNode(INode parent, int index, INode subNode) {
        parent.children()[index] = subNode;
        subNode.setParent(parent);
        flushNode(subNode);
    }

    private INode delete(int key, long data) {
        return null;
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

    private INode leafInsert(INode leaf, int key, long data) {

        int insertIndex = keyBinarySearch(leaf, key);
        if (insertIndex >= 0) {
            return null;
        }

        insertIndex = -insertIndex - 1;

        if (leaf.isFull()) {

            int midIndex = (maxOrder + 1) / 2;
            INode sibling = newLeaf();

            if (insertIndex < midIndex) {
                int splitKey = leafSplitLeft(leaf, sibling, key, data, insertIndex);
                return buildParentNode(sibling, leaf, splitKey);

            } else {
                int splitKey = leafSplitRight(leaf, sibling, key, data, insertIndex);
                return buildParentNode(leaf, sibling, splitKey);

            }

        } else {
            simpleLeafInsert(leaf, key, data, insertIndex);
            flushNode(leaf);
        }

        return leaf;
    }

    private void simpleLeafInsert(INode leaf, int key, long data, int insert) {
        System.arraycopy(leaf.keys(), insert, leaf.keys(), insert + 1, leaf.childrenSize() - insert);
        System.arraycopy(leaf.data(), insert, leaf.data(), insert + 1, leaf.childrenSize() - insert);

        leaf.keys()[insert] = key;
        leaf.data()[insert] = data;

        leaf.incrementChildren();
    }

    public int leafSplitLeft(INode leaf, INode left, int key, long data, int insert) {

        int midIndex = (leaf.childrenSize() + 1) / 2;

        addLeftNode(leaf, left);


        left.setChildrenSize(midIndex);
        leaf.setChildrenSize(maxOrder - midIndex + 1);

        /**
         * mv leaf's [0,insert-1] elements to left
         */
        System.arraycopy(leaf.keys(), 0, left.keys(), 0, insert);
        System.arraycopy(leaf.data(), 0, left.data(), 0, insert);

        left.keys()[insert] = key;
        left.data()[insert] = data;

        /**
         * mv leaf's [insert,midIndex- 1] elements to left's [insert+1,midIndex]
         */
        System.arraycopy(leaf.keys(), insert, left.keys(), insert + 1, midIndex - insert - 1);
        System.arraycopy(leaf.data(), insert, left.data(), insert + 1, midIndex - insert - 1);

        /**
         * shift leaf keys to left
         */
        System.arraycopy(leaf.keys(), midIndex - 1, leaf.keys(), 0, leaf.childrenSize());
        System.arraycopy(leaf.data(), midIndex - 1, leaf.data(), 0, leaf.childrenSize());


        return leaf.keys()[0];
    }

    private int leafSplitRight(INode leaf, INode right, int key, long data, int insert) {

        int midIndex = (leaf.childrenSize() + 1) / 2;

        addRightNode(leaf, right);

        right.setChildrenSize(maxOrder - midIndex + 1);
        leaf.setChildrenSize(midIndex);

        int pivot = insert - midIndex;

        System.arraycopy(leaf.keys(), midIndex, right.keys(), 0, pivot);
        System.arraycopy(leaf.data(), midIndex, right.data(), 0, pivot);

        right.keys()[pivot] = key;
        right.data()[pivot] = data;

        /**
         * copy high elements to right,so that don't need mv self'e elements
         */
        System.arraycopy(leaf.keys(), insert, right.keys(), pivot + 1, maxOrder - insert);
        System.arraycopy(leaf.data(), insert, right.data(), pivot + 1, maxOrder - insert);

        return right.keys()[0];
    }


    private INode newLeaf() {
        INode node = newNode();
        node.setType(LEAF);
        return node;
    }

    private INode newNode() {
        return new BaseNode(this);
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
        flushNode(subNode);
    }

    private void subNodeFlush(INode parent, INode subNode) {
        subNode.setParent(parent);
        flushNode(subNode);
    }

    private void flushNode(INode node) {

        final FileBlockStore.WriteBuffer wbuf = storage.set(ID.incrementAndGet());
        final ByteBuffer buf = wbuf.buf();
        node.serialize(buf);
        buf.flip();
        wbuf.save();

        storage.sync();
    }


    /**
     * build parent node for leftChild and rightChild
     *
     * @param leftChild
     * @param rightChild
     * @param key
     * @return
     */
    private INode buildParentNode(INode leftChild, INode rightChild, int key) {
        if (leftChild.parent() == null && rightChild.parent() == null) {
            /* new parent */
            INode parent = newInternalNode();
            parent.keys()[0] = key;
            parent.children()[0] = leftChild;
            parent.children()[1] = rightChild;
            parent.setChildrenSize(2);

            leftChild.setParent(parent);
            rightChild.setParent(parent);

            this.incrementLevel();

            flushNode(leftChild);
            flushNode(rightChild);
            flushNode(parent);
            return parent;

        } else if (rightChild.parent() == null) {
            return internalNodeInsert(leftChild.parent(), leftChild, rightChild, key);
        } else {
            return internalNodeInsert(rightChild.parent(), leftChild, rightChild, key);
        }
    }

    private INode internalNodeInsert(INode parent, INode leftChild, INode rightChild, int key) {

        int insertIndex = keyBinarySearch(parent, key);
        insertIndex = -insertIndex - 1;

        if (parent.isFull()) {
            int splitKey = -1;

            int midIndex = (parent.childrenSize() + 1) / 2;

            INode sibling = newInternalNode();

            if (insertIndex < midIndex) {

                splitKey = internalNodeSplitLeft(parent, sibling, leftChild, rightChild, key, insertIndex);
                return buildParentNode(sibling, parent, splitKey);

            } else {
                if (insertIndex == midIndex) {
                    splitKey = non_leaf_split_right1(node, sibling, l_ch, rightChild, key, insertIndex);
                } else {
                    splitKey = non_leaf_split_right2(node, sibling, l_ch, rightChild, key, insertIndex);
                }
                return buildParentNode(parent, sibling, splitKey);
            }

        } else {
            non_leaf_simple_insert(parent, leftChild, rightChild, key, insertIndex);
            flushNode(parent);
        }
        return 0;
    }

    private int internalNodeSplitLeft(INode parent, INode left, INode leftChild, INode rightChild, int key, int insert) {

        int split = (maxOrder + 1) / 2;

        addLeftNode(parent, left);

        left.setChildrenSize(split);
        parent.setChildrenSize(maxOrder - split + 1);


        System.arraycopy(parent.keys(), 0, left.keys(), 0, insert);
        System.arraycopy(parent.children(), 0, left.children(), 0, insert);

        left.keys()[insert] = key;

        System.arraycopy(parent.keys(), insert, left.keys(), insert + 1, split - insert - 1);
        System.arraycopy(parent.children(), insert, left.children(), insert + 1, split - insert - 1);

        for (int i = 0; i < left.childrenSize(); i++) {
            if (i != insert && i != insert + 1) {
                subNodeFlush(left, left.children()[i]);
            }
        }


        int splitKey;

        if (insert == split - 1) {
            /* left child in split left node and right child in original right one */
            sub_node_update(left, insert, leftChild);
            sub_node_update(parent, 0, rightChild);
            splitKey = key;
        } else {
            /* both new children in split left node */
            sub_node_update(left, insert, leftChild);
            sub_node_update(left, insert + 1, rightChild);
            parent.data()[0] = parent.keys()[split - 1];
            splitKey = parent.keys()[split - 2];
        }

        /* sum = node->children = 1 + (node->children - 1) */
        /* right node left shift from key[split - 1] to key[children - 2] */
      /*  memmove( & key(node)[0], &key(node)[split - 1], (node -> children - 1) * sizeof(key_t));
        memmove( & sub(node)[1], &sub(node)[split], (node -> children - 1) * sizeof(off_t));
*/
        System.arraycopy(parent.keys(), split - 1, parent.keys(), 0, parent.childrenSize() - 1);
        System.arraycopy(parent.children(), split, parent.children(), 1, parent.childrenSize() - 1);

        return splitKey;
    }

 /*   static key_t non_leaf_split_right1(struct bplus_tree *tree, struct bplus_node *node,
                                       struct bplus_node *right, struct bplus_node *l_ch,
                                       struct bplus_node *r_ch, key_t key, int insert) {
        int i;

        *//* split = [m/2] *//*
        int split = (_max_order + 1) / 2;

        *//* split as right sibling *//*
        right_node_add(tree, node, right);

        *//* split key is key[split - 1] *//*
        key_t split_key = key(node)[split - 1];

        *//* calculate split nodes' children (sum as (order + 1))*//*
        int pivot = 0;
        node -> children = split;
        right -> children = _max_order - split + 1;

        *//* insert new key and sub-nodes *//*
        key(right)[0] = key;
        sub_node_update(tree, right, pivot, l_ch);
        sub_node_update(tree, right, pivot + 1, r_ch);

        *//* sum = right->children = 2 + (right->children - 2) *//*
     *//* replicate from key[split] to key[_max_order - 2] *//*
        memmove( & key(right)[pivot + 1], &key(node)[split], (right -> children - 2) * sizeof(key_t));
        memmove( & sub(right)[pivot + 2], &sub(node)[split + 1], (right -> children - 2) * sizeof(off_t));

        *//* flush sub-nodes of the new splitted right node *//*
        for (i = pivot + 2; i < right -> children; i++) {
            sub_node_flush(tree, right, sub(right)[i]);
        }

        return split_key;
    }

    static key_t non_leaf_split_right2(struct bplus_tree *tree, struct bplus_node *node,
                                       struct bplus_node *right, struct bplus_node *l_ch,
                                       struct bplus_node *r_ch, key_t key, int insert) {
        int i;

        *//* split = [m/2] *//*
        int split = (_max_order + 1) / 2;

        *//* split as right sibling *//*
        right_node_add(tree, node, right);

        *//* split key is key[split] *//*
        key_t split_key = key(node)[split];

        *//* calculate split nodes' children (sum as (order + 1))*//*
        int pivot = insert - split - 1;
        node -> children = split + 1;
        right -> children = _max_order - split;

        *//* sum = right->children = pivot + 2 + (_max_order - insert - 1) *//*
     *//* replicate from key[split + 1] to key[insert] *//*
        memmove( & key(right)[0], &key(node)[split + 1], pivot * sizeof(key_t));
        memmove( & sub(right)[0], &sub(node)[split + 1], pivot * sizeof(off_t));

        *//* insert new key and sub-node *//*
        key(right)[pivot] = key;
        sub_node_update(tree, right, pivot, l_ch);
        sub_node_update(tree, right, pivot + 1, r_ch);

        *//* replicate from key[insert] to key[order - 1] *//*
        memmove( & key(right)[pivot + 1], &key(node)[insert], (_max_order - insert - 1) * sizeof(key_t));
        memmove( & sub(right)[pivot + 2], &sub(node)[insert + 1], (_max_order - insert - 1) * sizeof(off_t));

        *//* flush sub-nodes of the new splitted right node *//*
        for (i = 0; i < right -> children; i++) {
            if (i != pivot && i != pivot + 1) {
                sub_node_flush(tree, right, sub(right)[i]);
            }
        }

        return split_key;
    }*/


    private void non_leaf_simple_insert(INode node, INode l_ch, INode r_ch,
                                        int key, int insert) {

        System.arraycopy(node.keys(), insert, node.keys(), insert + 1, node.keys().length - insert);
        System.arraycopy(node.data(), insert + 1, node.data(), insert + 2, node.data().length - insert - 1);

        node.keys()[insert] = key;
        sub_node_update(node, insert, l_ch);
        sub_node_update(node, insert + 1, r_ch);
        node.incrementChildren();
    }

    private void sub_node_update(INode parent, int index, INode subNode) {
        parent.children()[index] = subNode;
        subNode.setParent(parent);
        flushNode(subNode);
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

        maxOrder = blockSize - INode.byteSize();
        if (maxOrder <= 2) {
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
    public int incrementLevel() {
        return 0;
    }

    @Override
    public int allocNodeId(boolean isLeaf) {
        return 0;
    }


    @Override
    public void dump() {

    }

    @Override
    public void initList(Link link) {
        link.prev = link;
        link.next = link;
    }

    @Override
    public int maxOrder() {
        return maxOrder;
    }


    int pread(int fd, ByteBuffer byteBuffer, int count, int offset) {
        return 1;
    }
}
