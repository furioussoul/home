package chapter18.structures.btree;

import chapter18.io.FileBlockStore;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * B+树
 *
 * @author 孙证杰
 * @email 200765821@qq.com
 * @date 2019/7/31 10:00
 */
public class BPTree implements Itree {

    private static final Logger log = Logger.getLogger(BPTree.class);


    INode root;
    public int order;

    String filename;
    int blockSize;

    public static final AtomicInteger ID = new AtomicInteger(0);
    FileBlockStore storage;

    @Override
    public long get(int k) {

        INode x = root;
        return get(x, k);
    }

    private long get(INode x, int k) {

        int i = 0;

        while (i < x.keySize() && k > x.getKey(i)) {
            i++;
        }

        if (i < x.keySize() && k == x.getKey(i)) {

            return x.getKey(i);

        } else if (x.isLeaf()) {
            return -1;
        } else {
            INode c = readNode(x, i);
            return get(c, k);
        }
    }

    @Override
    public void put(int k) {

        INode r = root;

        if (r.isFull()) {
            INode s = newNode();
            this.root = s;
            s.setType(BaseNode.INTERNAL);
            s.setChild(0, r);
            splitChild(s, 0);
            insertNoFull(s, k);
        } else {
            insertNoFull(r, k);
        }
    }


    private void splitChild(INode x, int i) {

        INode z = newNode();
        INode y = x.getChild(i);
        z.setType(y.type());


        //mv t-1 keys from y to z
        //for now, y has t keys
        for (int j = 0; j < order - 1; j++) {
            z.setKey(j, y.getKey(j + order));
        }

        if (!y.isLeaf()) {
            //mv t children from y to z
            for (int j = 0; j < order; j++) {
                z.setChild(j, y.getChild(j + order));
            }
        }

        y.setKeySize(order - 1);
        z.setKeySize(order - 1);


        //x.keys[order-1] split y and z
        for (int j = x.keySize() - 1; j >= i; j--) {
            x.setKey(j + 1, x.getKey(j));
        }
        x.setKey(i, y.getKey(order - 1));

        //z becomes x'child
        for (int j = x.keySize(); j >= i + 1; j--) {
            x.setChild(j + 1, x.getChild(j));
        }
        x.setChild(i + 1, z);


        x.setKeySize(x.keySize() + 1);

        flushNode(x);
        flushNode(z);
        flushNode(y);
    }


    private void insertNoFull(INode x, int k) {
        int i = x.keySize() - 1;
        if (x.isLeaf()) {
            while (i >= 0 && k < x.getKey(i)) {
                x.setKey(i + 1, x.getKey(i));
                i--;
            }

            x.setKey(i + 1, k);
            x.setKeySize(x.keySize() + 1);
            flushNode(x);
        } else {
            while (i >= 0 && k < x.getKey(i)) {
                i--;
            }
            i++;

            INode c = x.getChild(i);

            if (c.isFull()) {
                splitChild(x, i);
                if (k > x.getKey(i)) {
                    i++;
                }
            }

            insertNoFull(x.getChild(i), k);
        }

    }

    private INode readNode(INode p, int i) {
        //todo disk
        return p.getChild(i);
    }


    private void flushNode(INode node) {

        final FileBlockStore.WriteBuffer wbuf = storage.set(ID.incrementAndGet());
        final ByteBuffer buf = wbuf.buf();
        node.serialize(buf);
        buf.flip();
        wbuf.save();

        storage.sync();
    }

    private INode newNode() {
        BaseNode node = new BaseNode(this);
        node.setType(BaseNode.LEAF);
        return node;
    }


    @Override
    public void init(String filename, int blockSize) {

        if ((blockSize & (blockSize - 1)) != 0) {
            log.error("Block size must be pow of 2!");
            return;
        }


        order = getOptimalOrder();
        if (order <= 2) {
            log.error("block size is too small for one node!");
            return;
        }

        try {

            File file = new File(filename);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.getParentFile().mkdir();
                file.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.filename = filename;

        this.root = newNode();
        this.root.setType(BaseNode.LEAF);
        this.storage = new FileBlockStore(filename, blockSize, false);

        storage.delete();
        storage.open();
    }

    @Override
    public int order() {
        return order;
    }

    public int getOptimalOrder() {
        return 10;
    }
}
