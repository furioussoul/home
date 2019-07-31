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

    List<Integer> keys;
    List<Long> data;
    List<INode> children;

    public BaseNode(Itree tree) {
        this.tree = tree;
        this.keys = new ArrayList<>(2*tree.order()-1);
        this.data = new ArrayList<>();
        this.children = new ArrayList<>(2*tree.order());
    }

    @Override
    public boolean isFull() {
        return keys.size() == tree.order() * 2 - 1;
    }

    @Override
    public void setChild(int index, INode child) {
        if(children.size() == index){
            this.children.add(child);
        }else {
            this.children.set(index, child);
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
    public List<Integer> getKeys() {
        return keys;
    }

    @Override
    public void setKey(int i, int k) {
        if(keys.size()== i){
            keys.add(k);
        }else {
            keys.set(i, k);
        }
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
    public INode getChild(int i) {
        return children.get(i);
    }

    @Override
    public List<INode> getChildren() {
        return children;
    }

    public void setChildren(List<INode> children) {
        this.children = children;
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
    public void serialize(ByteBuffer buf) {

    }
}
