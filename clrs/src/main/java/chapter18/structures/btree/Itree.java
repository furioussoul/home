package chapter18.structures.btree;

public interface Itree {

    void dump();
    long get(int key);

    INode put(int key, long data);
    long rangeGet(int key1, int key2);
    Itree init(String filename, int blockSize);
    void destroy();
    int openFile(String filename);
    void closeFile(int fd);

    int incrementLevel();

    int allocNodeId(final boolean isLeaf);
    void initList(Link link);

    class Link {
        Link prev;
        Link next;
    }
}
