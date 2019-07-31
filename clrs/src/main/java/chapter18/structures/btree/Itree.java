package chapter18.structures.btree;

public interface Itree {

    long get(int k);
    void put(int k);
    void init(String filename, int blockSize);

    int order();
}
