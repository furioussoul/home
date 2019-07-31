package chapter18.test;

import chapter18.structures.btree.BPTree;
import chapter18.structures.btree.BaseNode;
import chapter18.structures.btree.INode;
import org.junit.Test;

/**
 * TestBPTree
 *
 * @author 孙证杰
 * @email 200765821@qq.com
 * @date 2019/7/30 16:51
 */
public class TestBPTree {

    public static final String FILE_PATH = "/tmp/bptree.data";
    public static final int BLOCK_SIZE = 512; //kb

    @Test
    public void testTree() {
        BPTree bpTree = new BPTree();
        bpTree.init(FILE_PATH, BLOCK_SIZE);

        for (int i = 0; i < 1000; i++) {
            bpTree.put(i);
        }


        for (int i = 0; i < 1000; i++) {
            System.out.println(bpTree.get(i));
        }

    }
}
