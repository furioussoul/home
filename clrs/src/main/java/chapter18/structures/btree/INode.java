package chapter18.structures.btree;

public interface INode {



    static int byteSize() {
        return 1;
    }

    int[] keys();
    INode[] children();
    int type();
    int offset();
}
