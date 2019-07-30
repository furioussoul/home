package chapter18.test;

import chapter18.structures.btree.BPTree;
import chapter18.structures.btree.BaseNode;
import chapter18.structures.btree.INode;
import org.junit.Test;

/**
 * Demo class
 *
 * @author 孙证杰
 * @email 200765821@qq.com
 * @date 2019/7/30 16:51
 */
public class TestBPTree {


    @Test
    public void test_leaf_split_left() {
        BPTree bpTree = new BPTree();
        bpTree.maxOrder = 6;

        INode leaf = new BaseNode(bpTree);
        leaf.setChildrenSize(6);
        leaf.setKeys(new int[]{1, 4, 6, 8, 10, 12});
        leaf.setData(new long[]{311, 311, 311, 311, 311, 311});
        INode left = new BaseNode(bpTree);
        int key = 1;
        long data = 314;
        int insert = 1;

        int leafsLeastKeyAfterSplit = bpTree.leaf_split_left(leaf, left, key, data, insert);
        System.out.println(leafsLeastKeyAfterSplit);
    }
}
