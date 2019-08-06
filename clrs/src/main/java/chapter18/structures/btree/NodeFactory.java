package chapter18.structures.btree;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * NodeFactory
 *
 * @author 孙证杰
 * @email 200765821@qq.com
 * @date 2019/8/6 11:09
 */
public class NodeFactory {

    private static final AtomicInteger ID = new AtomicInteger(0);
    private static Itree TREE;
    private static int MIN_CACHE_NUM = 10;
    private static int[] USED = new int[10];
    private static INode[] NODE_CACHE = new INode[10];

    public static void init(Itree tree) {
        TREE = tree;
        for (int i = 0; i < MIN_CACHE_NUM; i++) {
            BaseNode node = new BaseNode(TREE);
            NODE_CACHE[i] = node;
        }
    }

    public static INode newNode(boolean leaf, boolean newId, BPTree tree) {
        INode node = null;
        for (int i = 0; i < MIN_CACHE_NUM; i++) {
            if (USED[i] == 0) {
                USED[i] = 1;
                node = NODE_CACHE[i];
            }
        }

        node.setType(leaf);

   /*     BaseNode node = new BaseNode(tree);
        node.setType(BaseNode.LEAF);
        if (newId) {
            node.setId(allocatedId(leaf));
        }
        return node;*/
    }

    public static void release(INode node) {
        USED[node.cacheIndex()] = 0;
    }

    private static int allocatedId(boolean leaf) {
        int id = ID.incrementAndGet();
        if (leaf) {
            return -id;
        } else {
            return id;
        }
    }
}
