package chapter18;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * B+树
 *
 * @author 孙证杰
 * @email 200765821@qq.com
 * @date 2019/7/26 12:42
 */
public class BplusTree {

    static final int CACHE_NUM = 5;
    File fd;
    int level;
    Node root;
    List<Node> cache;
    int[] cacheUsed;

    public BplusTree(File file, int blockSize) {

        if(file == null){
            Log.debug("init BplusTree file is null");
            return;
        }

        if(file.getName().length()>1024){
            Log.debug("init BplusTree filename's length should'nt over 1024!");
            return;
        }

        if((blockSize & (blockSize -1)) != 0){
            Log.debug("init BplusTree block size mut be power of 2");
            return;
        }

        if(blockSize < Node.size()){
            Log.debug("init BplusTree block size mut be greater than Node.size()");
            return;
        }

        int maxOrder = blockSize /  Node.size();

        if(maxOrder <= 2){
            Log.debug("init BplusTree block size mut be greater than 3*Node.size()");
        }


        this.cache = new LinkedList<>();
        this.cacheUsed = new int[CACHE_NUM];
    }

    boolean isLeaf(Node node) {
        return node.type == Node.NodeType.LEAF.code;
    }



    Node cacheRefer(BplusTree tree) {
        for (int i = 0; i < CACHE_NUM; i++) {
            if (tree.cacheUsed[i] == 0) {
                tree.cacheUsed[i] = 1;
                char *buf = tree->caches + _block_size * i;
                return (struct bplus_node *) buf;
            }
        }
    }

    Node cacheDefer(BplusTree tree) {
        for (int i = 0; i < CACHE_NUM; i++) {

        }
    }

    Node newNode(BplusTree tree) {


    }

    Node newInternalNode(BplusTree tree) {
        Node node = newNode(tree);
        node.type = Node.NodeType.INTERNAL.code;
        return node;
    }

    Node newLeafNode(BplusTree tree) {
        Node node = newNode(tree);
        node.type = Node.NodeType.LEAF.code;
        return node;
    }

    Node seekNode(BplusTree tree, int offset) {
        for (int i = 0; i < CACHE_NUM; i++) {

        }
    }

    /**
     * 将node写入磁盘
     */
    void flushNode(BplusTree tree, Node node) {
        if(tree == null){
            Log.debug("flushNode tree is null");
            return;
        }

        if(node == null){
            Log.debug("flushNode node is null");
            return ;
        }

        try{
            ObjectOutputStream fw = new ObjectOutputStream(new FileOutputStream(fd));
            fw.writeObject(node);
            cacheDefer(tree);
        }catch (IOException ex){
            Log.error("flushNode error", ex);
        }
    }

    public static class Node implements Serializable {
        Node self;
        Node parent;
        Node prev;
        Node next;
        int type;
        int childrenSize;


        public static int size(){
            //todo 计算node序列化后的字节大小

            return 0;
        }

        public static enum NodeType {

            LEAF(0), INTERNAL(1);

            int code;

            NodeType(int code) {
                this.code = code;
            }
        }
    }

    public static class ListHead {

    }

}
