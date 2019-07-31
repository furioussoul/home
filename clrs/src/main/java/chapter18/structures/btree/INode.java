package chapter18.structures.btree;

import java.nio.ByteBuffer;
import java.util.List;

public interface INode {


    int type();

    void setType(int type);

    boolean isLeaf();

    void setChild(int index, INode child);

    boolean isFull();

    void serialize(final ByteBuffer buf);

    void setKeySize(int size);
    int keySize();

    int getKey(int i);
    void setKey(int i, int k);
    INode getChild(int i);

    List<Integer> getKeys();
    List<INode> getChildren();
}
