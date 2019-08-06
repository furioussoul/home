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
    private static int MIN_CACHE_NUM = 25;
    private static int[] USED = new int[MIN_CACHE_NUM];
    private static INode[] NODE_CACHE = new INode[MIN_CACHE_NUM];
    static int newSize;
    static int releaseSize;

    public static void init(Itree tree) {
        TREE = tree;
        for (int i = 0; i < MIN_CACHE_NUM; i++) {
            BaseNode node = new BaseNode(TREE);
            node.setType(BaseNode.LEAF);
            NODE_CACHE[i] = node;
        }
    }

    public static INode newNode(boolean leaf, boolean newId) {

        INode node = null;
        for (int i = 0; i < MIN_CACHE_NUM; i++) {
            if (USED[i] == 0) {
                USED[i] = 1;
                node = NODE_CACHE[i];
                node.setCacheIndex(i);
                if (newId) {
                    node.setId(allocatedId(leaf));
                }
                break;
            }
        }

        newSize++;
//        System.out.println("newSize : " + newSize);
        return node;
    }

    public static void release(INode node) {
        if(node.cacheIndex() > -1){
            releaseSize++;
//            System.out.println("releaseSize : " + releaseSize);
            USED[node.cacheIndex()] = 0;
            node.setId(0);
            node.setNext(0);
            node.clearKey();
            node.clearChildren();
            node.clearData();
        }

    }

    public static int allocatedId(boolean leaf) {
        int id = ID.incrementAndGet();
        if (leaf) {
            return -id;
        } else {
            return id;
        }
    }
}
