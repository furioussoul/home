package chapter18.test;

import chapter18.structures.btree.BPTree;
import chapter18.structures.btree.Message;
import chapter18.structures.btree.NodeFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * TestBPTree
 *
 * @author 孙证杰
 * @email 200765821@qq.com
 * @date 2019/7/30 16:51
 */
public class TestBPTree {

    public static final String FILE_PATH = "/tmp/bptree.data";
    public static final int BLOCK_SIZE = 32 * 1024; //kb


    int TOTAL = 100 * 10000;
    int FLUSH_SIZE = 100000;

    @Test
    public void testPutAndGet() {

        System.out.println(String.format("start test : BLOCK_SIZE:%s, FLUSH_SIZE:%s,TOTAL:%s", BLOCK_SIZE, FLUSH_SIZE
                , TOTAL));

        BPTree bpTree = new BPTree();
        bpTree.init(FILE_PATH, BLOCK_SIZE);

        long begin = System.currentTimeMillis();


        for (int i = 0; i < TOTAL; i++) {
            bpTree.put(i);
            if (i % FLUSH_SIZE == 0) {
                bpTree.sync();
            }
        }

        long end = System.currentTimeMillis();

        System.out.println("write spent : " + (end - begin));

        NodeFactory.init(bpTree);

        begin = System.currentTimeMillis();

        for (int i = 0; i < TOTAL; i++) {
            Message message = bpTree.get(i);
            Assert.assertNotNull(message);
            Assert.assertEquals(i, message.getNode().getKey(message.getKeyIndex()));
            NodeFactory.release(message.getNode());
        }

        end = System.currentTimeMillis();

        System.out.println("read spent : " + (end - begin));



        int from = 100;
        int to = 105;

        assertRangeGet(bpTree, from, to);
    }


    @Test
    public void testRangeGet() {
        BPTree bpTree = new BPTree();
        bpTree.init(FILE_PATH, BLOCK_SIZE);

        long totalBegin = System.currentTimeMillis();

        for (int i = 0; i <= 2000000000; i++) {
            if (i % 1000000 == 0) {
                long begin = System.currentTimeMillis();
                System.out.println();
                bpTree.sync();
                long end = System.currentTimeMillis();
//                System.out.println(String.format("put %s, sync spent: %s", i, end - begin));
            }
            bpTree.put(i);
        }

        long totalEnd = System.currentTimeMillis();

        System.out.println("finish put, totally spent: " + (totalEnd - totalBegin));


        int from = 100;
        int to = 105;

        assertRangeGet(bpTree, from, to);


        from = 300000;
        to = 3000000;

        assertRangeGet(bpTree, from, to);

        from = 6000000;
        to = 60000000;

        assertRangeGet(bpTree, from, to);

        from = -100;
        to = 3000;

        Message message = bpTree.rangeGet(from, to);
        Assert.assertNotNull(message);
        List keys = message.getKeys();
        System.out.println(String.format("TOTAL: %s, first: %s, last: %s", keys.size(), keys.get(0), keys.get(keys.size() - 1)));
    }

    private void assertRangeGet(BPTree bpTree, int from, int to) {
        List<Integer> expect = new LinkedList<>();
        for (int i = from; i <= to; i++) {
            expect.add(i);
        }

        int expectSize = expect.size();

        Message message = bpTree.rangeGet(from, to);
        Assert.assertNotNull(message);
        expect.retainAll(message.getKeys());
        Assert.assertEquals(expectSize, expect.size());
        System.out.println(String.format("pass form: %s, to: %s", from, to));
        NodeFactory.release(message.getNode());
    }


    private void printKeys(List<Integer> keys) {
        if (keys == null) {
            System.out.println("keys is empty");
        }

        for (Integer key : keys) {
            System.out.println(key);
        }
    }
}
