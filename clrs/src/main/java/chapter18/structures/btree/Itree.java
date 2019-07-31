package chapter18.structures.btree;

public interface Itree {

    Message get(int k);
    Message rangeGet(int from, int to);
    void put(int k);
    void init(String filename, int blockSize);

    void sync();

    int order();
}
