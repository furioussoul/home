package chapter18.structures.btree;

import java.nio.ByteBuffer;

/**
 * Demo class
 *
 * @author 孙证杰
 * @email 200765821@qq.com
 * @date 2019/7/30 14:23
 */
public class BaseNode implements INode {

    Itree tree;
    int childrenSize;
    int[] keys;
    long[] data;
    INode prev;
    INode next;
    INode parent;

    public BaseNode(Itree tree) {
        this.tree = tree;
        this.keys = new int[tree.maxOrder()];
        this.data = new long[tree.maxOrder()];
    }

    @Override
    public void setKeys(int[] keys) {
        this.keys = keys;
    }

    @Override
    public int[] keys() {
        return keys;
    }

    @Override
    public void setData(long[] data) {
        this.data = data;
    }

    @Override
    public long[] data() {
        return data;
    }

    @Override
    public int childrenSize() {
        return childrenSize;
    }

    @Override
    public INode[] children() {
        return new INode[0];
    }

    @Override
    public void setChildrenSize(int size) {
        childrenSize = size;
    }

    @Override
    public void incrementChildren() {
        childrenSize++;
    }

    @Override
    public int type() {
        return 0;
    }

    @Override
    public void setType(int type) {

    }

    @Override
    public int offset() {
        return 0;
    }

    @Override
    public void setId(int nodeId) {

    }

    @Override
    public void setChild(int index, INode child) {

    }

    @Override
    public void setParent(INode parent) {
        this.parent = parent;
    }

    @Override
    public INode getPrev() {
        return prev;
    }

    @Override
    public void setPrev(INode prev) {
        this.prev = prev;
    }

    @Override
    public INode getNext() {
        return next;
    }

    @Override
    public void setNext(INode next) {
        this.next = next;
    }

    @Override
    public INode parent() {
        return parent;
    }

    @Override
    public boolean isFull() {
        return childrenSize() == tree.maxOrder();
    }

    @Override
    public void serialize(ByteBuffer buf) {

    }
}
