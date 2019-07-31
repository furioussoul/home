package chapter18.structures.btree;

import java.nio.ByteBuffer;
import java.util.List;

public interface INode {

    void setNext(int id);
    int id();
    void setId(int id);

    int type();

    void setType(int type);

    boolean isLeaf();

    boolean isFull();

    void serialize(final ByteBuffer buf);
    void deSerialize(final ByteBuffer buf);

    void setKeySize(int size);
    int keySize();

    int getKey(int i);
    void setKey(int i, int k);

    int getChild(int i);
    void setChild(int index, int childId);


    int nodeByteSize();
}
