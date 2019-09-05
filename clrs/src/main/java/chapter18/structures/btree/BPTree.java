package chapter18.structures.btree;

import chapter18.io.FileBlockStore;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

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

    FileBlockStore storage;

    @Override
    public Message get(int k) {

        INode x = root;
        return get(x, k);
    }


    @Override
    public Message rangeGet(int from, int to) {
        Message message = get(root, from);
        if (message == null) {
            return null;
        }
        INode node = message.getNode();


        List<Integer> keys = new LinkedList<>();
        message.setKeys(keys);


        do {
            for (int i = 0; i < node.keySize(); i++) {
                int key = node.getKey(i);
                if (key >= from && key <= to) {
                    keys.add(key);
                }
                if (key > to) {
                    return message;
                }

            }
        } while ((node = readNodeById(node.getNext())) != null);


        return message;
    }


    private Message get(INode x, int k) {

        int i = 0;

        while (i < x.keySize() && k > x.getKey(i)) {
            i++;
        }

        if (i < x.keySize() && k == x.getKey(i)) {

            return new Message(x, i);

        } else if (x.isLeaf()) {

            if (k < x.getKey(0)) {
                return new Message(x, i);
            } else {
                return null;
            }

        } else {
            INode c = readNode(x, i);
            NodeFactory.release(x);
            return get(c, k);
        }
    }

    @Override
    public void put(int k) {

        INode r = root;

        if (r.isFull()) {

            BaseNode s = new BaseNode(this);
            s.setType(BaseNode.LEAF);
            s.setId(NodeFactory.allocatedId(false));



            this.root = s;
            s.setType(BaseNode.INTERNAL);
            s.setChild(0, r.id());

            splitChild(s, r, 0);
            insertNoFull(s, k);
        } else {
            insertNoFull(r, k);
        }
    }


    private void splitChild(INode x, INode y, int i) {


        System.out.println(String.format("%s node: %s, split at key=[%s] ",
                y.type() == BaseNode.LEAF ? "leaf" : "internal", y.id(), y.getKey(order - 1)));

        INode z = NodeFactory.newNode(y.isLeaf(), true);
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

        y.setKeySize(order);
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
        NodeFactory.release(z);
        NodeFactory.release(y);

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
            NodeFactory.release(x);
        } else {
            while (i >= 0 && k < x.getKey(i)) {
                i--;
            }
            i++;

            int tmp = i;

            INode c = readNode(x, i);

            if (c.isFull()) {
                splitChild(x, c, i);
                if (k > x.getKey(i)) {
                    i++;
                }
            }

            if (tmp != i) {
                c = readNode(x, i);
            }
            insertNoFull(c, k);
            NodeFactory.release(x);
        }
    }

    private INode readNodeById(int id) {

        if (id == 0) {
            return null;
        }

        boolean leaf = id < 0;
        int nodeId = Math.abs(id);
        final ByteBuffer buf = storage.get(nodeId);

        INode node = NodeFactory.newNode(leaf, false);

        node.setId(id);

        node.deSerialize(buf);

        storage.release(buf);

        return node;
    }

    private INode readNode(INode p, int i) {
        long begin = System.currentTimeMillis();
        int id = p.getChild(i);
        INode iNode = readNodeById(id);

        long end = System.currentTimeMillis();

//        System.out.println(String.format("readNode spent: %s", end - begin));

        return iNode;
    }

    @Override
    public void sync() {
        storage.sync();
    }

    private void flushNode(INode node) {

        long begin = System.currentTimeMillis();

        final FileBlockStore.WriteBuffer wbuf = storage.set(Math.abs(node.id()));
        final ByteBuffer buf = wbuf.buf();
        node.serialize(buf);
        buf.flip();
        wbuf.save();

//        storage.sync();

        long end = System.currentTimeMillis();

//        System.out.println(String.format("flushNode spent: %s", end - begin));

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


        NodeFactory.init(this);

        this.root = NodeFactory.newNode(true, true);

        BaseNode root = new BaseNode(this);
        root.setType(BaseNode.LEAF);
        root.setId(NodeFactory.allocatedId(true));
        this.root = root;

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

    public INode root() {
        return root;
    }
}
