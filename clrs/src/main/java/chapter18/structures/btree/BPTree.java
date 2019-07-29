package chapter18.structures.btree;

import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BPTree implements Itree {

    private static final Logger log = Logger.getLogger(BPTree.class);


    private static int INVALID_OFFSET = 0xdeadbeef;
    private static int LEAF = 0;
    private static int INTERNAL = 1;
    private static final int MIN_CACHE_NUM = 5;
    List caches;
    int used[];
    String filename;
    int fd;
    int level;
    INode root;
    long fileSize;
    int blockSize;
    Link freeBlocks;


    static boolean isLeaf(INode node) {
        return node.type() == LEAF;
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
                    node = seek(node.children()[i + 1]);
                } else {
                    i = -i - 1;
                    node = seek(node.children()[i]);
                }
            }
        }

        return ret;
    }

    @Override
    public int put(int key, long data) {

        if (data) {
            return bplus_tree_insert(tree, key, data);
        } else {
            return bplus_tree_delete(tree, key);
        }
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

        this.caches = new ArrayList(MIN_CACHE_NUM);

        this.fd = openFile(filename);

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

    @Override
    public INode seek(INode node) {


    }

    int pread(int fd, ByteBuffer byteBuffer, int count, int offset) {
        return 1;
    }
}
