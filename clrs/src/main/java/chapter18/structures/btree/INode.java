package chapter18.structures.btree;

import java.nio.ByteBuffer;

public interface INode {



    static int byteSize() {
        return 1;
    }

    void setKeys(int[] keys);
    int[] keys();

    void setData(long[] data);
    long[] data();

    int childrenSize();
    INode[] children();

    void setChildrenSize(int size);

    void incrementChildren();
    int type();

    void setType(int type);
    int offset();

    void setId(int nodeId);

    void setChild(int index, INode child);

    void setParent(INode parent);

    INode getPrev();

    void setPrev(INode prev);

    INode getNext();

    void setNext(INode next);

    INode parent();

    boolean isFull();

    void serialize(final ByteBuffer buf);
}
