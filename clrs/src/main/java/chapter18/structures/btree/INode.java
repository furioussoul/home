package chapter18.structures.btree;

import java.nio.ByteBuffer;

public interface INode {



    static int byteSize() {
        return 1;
    }

    int[] keys();

    long[] data();

    int childrenSize();
    INode[] children();

    void incrementChildren(int size);
    int type();

    void setType(int type);
    int offset();

    void setId(int nodeId);

    void setChild(int index, INode child);

    void setParent(INode parent);

    INode parent();

    void serialize(final ByteBuffer buf);
}
