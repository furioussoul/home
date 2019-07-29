package chapter18.structures.btree;

public interface Itree {

    void dump();
    long get(int key);
    int put(int key, long data);
    long rangeGet(int key1, int key2);
    Itree init(String filename, int blockSize);
    void destroy();
    int openFile(String filename);
    void closeFile(int fd);

    void initList(Link link);
    INode seek(INode node);

    public static class Link {
        Link prev;
        Link next;
    }
}
