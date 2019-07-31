package chapter18.structures.btree;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo class
 *
 * @author 孙证杰
 * @email 200765821@qq.com
 * @date 2019/7/30 14:23
 */
public class BaseNode implements INode {

    public static int LEAF = 0;
    public static int INTERNAL = 1;

    Itree tree;
    int type;
    int keySize;
    int id;
    int next;

    List<Integer> keys;
    List<Long> data;
    List<Integer> children;

    public BaseNode(Itree tree) {
        this.tree = tree;
        this.keys = new ArrayList<>(2 * tree.order() - 1);
        this.data = new ArrayList<>();
        this.children = new ArrayList<>(2 * tree.order());
    }

    @Override
    public int getNext() {
        return next;
    }

    @Override
    public void setNext(int id) {
        this.next = id;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isFull() {
        return keys.size() == tree.order() * 2 - 1;
    }

    @Override
    public void setChild(int index, int childId) {
        if (children.size() == index) {
            this.children.add(childId);
        } else {
            this.children.set(index, childId);
        }
    }

    @Override
    public void setKeySize(int size) {
        this.keySize = size;
    }

    @Override
    public int keySize() {
        return keySize;
    }

    @Override
    public void setKey(int i, int k) {
        if (keys.size() == i) {
            keys.add(k);
        } else {
            keys.set(i, k);
        }
    }

    @Override
    public int getChild(int i) {
        return children.get(i);
    }

    @Override
    public int getKey(int i) {
        return keys.get(i);
    }

    public void setKeys(List<Integer> keys) {
        this.keys = keys;
    }

    public List<Long> getData() {
        return data;
    }

    public void setData(List<Long> data) {
        this.data = data;
    }


    @Override
    public int type() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }


    @Override
    public boolean isLeaf() {
        return this.type() == LEAF;
    }

    @Override
    public int nodeByteSize() {
        if (!isLeaf()) {
            return (2 * tree.order() - 1) * (4 + 4) - 4;
        } else {
            return (2 * tree.order() - 1) * (4 + 8) + 4;
        }
    }

    @Override
    public void serialize(ByteBuffer buf) {
        buf.clear();
        buf.putInt(id);
        buf.putInt(keySize);
        for (int i = 0; i < keySize; i++) {
            buf.putInt(keys.get(i));
        }

        if (type == LEAF) {
            buf.putInt(next);
        } else {
            for (int i = 0; i < keySize + 1; i++) {
                buf.putInt(children.get(i));
            }
        }
    }

    @Override
    public void deSerialize(ByteBuffer buf) {
        id = buf.getInt();
        type = id < 0 ? LEAF : INTERNAL;
        keySize = buf.getInt();
        for (int i = 0; i < keySize; i++) {
            keys.add(buf.getInt());
        }
        if (type == LEAF) {
            next = buf.getInt();
        } else {
            for (int i = 0; i < keySize + 1; i++) {
                children.add(buf.getInt());
            }
        }
    }
}
