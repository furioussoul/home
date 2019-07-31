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
            INode s = newNode(false,true);
            this.root = s;
            s.setType(BaseNode.INTERNAL);
            s.setChild(0, r.id());

            System.out.println(String.format("split at key=[%s] ", k));

            splitChild(s, r, 0);
            insertNoFull(s, k);
        } else {
            insertNoFull(r, k);
        }
    }


    private void splitChild(INode x, INode y, int i) {


        INode z = newNode(y.isLeaf(),true);
        z.setType(y.type());
        y.setNext(z.id());

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
        x.setChild(i + 1, z.id());


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

            INode c = readNode(x, i);

            if (c.isFull()) {
                splitChild(x, c, i);
                if (k > x.getKey(i)) {
                    i++;
                }
            }

            insertNoFull(readNode(x, i), k);
        }

    }

    private INode readNode(INode p, int i) {
        int id = p.getChild(i);
        boolean leaf = id < 0;
        int nodeId = Math.abs(id);
        final ByteBuffer buf = storage.get(nodeId);

        INode node = newNode(leaf,false);
        node.setId(id);

        node.deSerialize(buf);

        storage.release(buf);

        return node;
    }


    private void flushNode(INode node) {

        final FileBlockStore.WriteBuffer wbuf = storage.set(Math.abs(node.id()));
        final ByteBuffer buf = wbuf.buf();
        node.serialize(buf);
        buf.flip();
        wbuf.save();

        storage.sync();
    }

    private INode newNode(boolean leaf,boolean newId) {
        BaseNode node = new BaseNode(this);
        node.setType(BaseNode.LEAF);
        if(newId){
            node.setId(allocatedId(leaf));
        }
        return node;
    }

    private int allocatedId(boolean leaf) {
        int id = ID.incrementAndGet();
        if (leaf) {
            return -id;
        } else {
            return id;
        }
    }


    @Override
    public void init(String filename, int blockSize) {

        if ((blockSize & (blockSize - 1)) != 0) {
            log.error("Block size must be pow of 2!");
            return;
        }


        this.filename = filename;
        this.blockSize = blockSize;

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


        this.root = newNode(true,true);
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
        //k+v+child pointer
        int kvSize = 4 + 8;
        int childSize = 4;

        int order = ((blockSize - childSize) / (kvSize + childSize) + 1) / 2;

        System.out.println(order);

        return order;
    }
}
