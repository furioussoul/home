package chapter18.structures.btree;

import java.nio.ByteBuffer;

public interface INode {

    int cacheIndex();

    void setCacheIndex(int i);
    int getNext();
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

    void clearKey();
    int getKey(int i);
    void setKey(int i, int k);

    void clearChildren();
    int getChild(int i);
    void setChild(int index, int childId);

    void clearData();
    int nodeByteSize();
}
