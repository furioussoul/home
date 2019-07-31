package chapter18.structures.btree;

import java.util.List;

public class Message {

    INode node;
    int keyIndex;
    List keys;

    public Message(INode node, int keyIndex) {
        this.node = node;
        this.keyIndex = keyIndex;
    }

    public INode getNode() {
        return node;
    }

    public void setNode(INode node) {
        this.node = node;
    }

    public int getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(int keyIndex) {
        this.keyIndex = keyIndex;
    }

    public List getKeys() {
        return keys;
    }

    public void setKeys(List keys) {
        this.keys = keys;
    }
}
