package clrs.chapter13;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 红黑树，维持以下5个性质就是平衡搜索树
 * <p>性质1：每个节点是红色或黑色
 * <p>性质2：根节点是黑色
 * <p>性质3：叶节点nil是黑色
 * <p>性质4：如果一个节点是红色，那么他的两个子节点都是黑色
 * <p>性质5：对于每个节点，从该节点到其所有后代叶节点nil的简单路径上，包含相同数量的黑色节点
 * Created by szj on 2016/6/29.
 */
public class RedBlackTree {

    private static String RED = "red";
    private static String BLACK = "black";
    private Node nil = new Node(null, BLACK);
    private Node root = null;

    private void leftRotate(Node x) {
        if (x != nil && x.right != nil) {
            Node y = x.right;

            x.right = y.left;
            y.left.parent = x;

            y.parent = x.parent;
            if (x.parent == nil) {
                root = y;
            } else if (x == x.parent.left) {
                x.parent.left = y;
            } else if (x == x.parent.right) {
                x.parent.right = y;
            }
            x.parent = y;
            y.left = x;
        }
    }

    private void rightRotate(Node x) {
        if (x != nil && x.left != nil) {
            Node y = x.left;
            x.left = y.right;
            y.right.parent = x;
            y.parent = x.parent;
            if (x.parent == nil) {
                root = y;
            } else if (x.parent.left == x) {
                x.parent.left = y;
            } else if (x.parent.right == x) {
                x.parent.right = y;

            }
            y.right = x;
            x.parent = y;
        }
    }

    private void insert(Integer e) {
        Node insertNode = new Node(e);
        if (this.root == null) {
            this.root = insertNode;
            this.root.color = BLACK;
            this.root.parent = nil;
            this.root.left = nil;
            this.root.right = nil;
            return;
        }
        Node parentOfInsertPosition = findParentOfInsertPosition(insertNode);

        if (e < parentOfInsertPosition.key) {
            parentOfInsertPosition.left = insertNode;
        } else {
            parentOfInsertPosition.right = insertNode;
        }
        insertNode.color = RED;
        insertNode.parent = parentOfInsertPosition;
        insertNode.left = nil;
        insertNode.right = nil;
        insertFix(insertNode);
    }

    private Node findParentOfInsertPosition(Node insertNode) {
        Node insertPosition = this.root;
        Node parentOfInsertPosition = null;
        while (insertPosition != nil) {
            parentOfInsertPosition = insertPosition;//cache parentPosition of insertPosition
            if (insertNode.key < insertPosition.key) {
                insertPosition = insertPosition.left;
            } else {
                insertPosition = insertPosition.right;
            }
        }
        return parentOfInsertPosition;
    }

    private void insertFix(Node fix) {
        while (fix.parent.color.equals(RED)) {
            if (fix.parent == fix.parent.parent.left) {
                Node uncle = fix.parent.parent.right;
                if (uncle.color.equals(RED)) {
                    fix.parent.color = BLACK;
                    uncle.color = BLACK;
                    fix.parent.parent.color = RED;
                    fix = fix.parent.parent;
                } else if (fix == fix.parent.right) {
                    fix = fix.parent;
                    leftRotate(fix);
                } else {
                    fix.parent.parent.color = RED;
                    fix.parent.color = BLACK;
                    rightRotate(fix.parent.parent);
                }
            } else {
                Node uncle = fix.parent.parent.left;
                if (uncle.color.equals(RED)) {
                    fix.parent.color = BLACK;
                    uncle.color = BLACK;
                    fix.parent.parent.color = RED;
                    fix = fix.parent.parent;
                } else if (fix == fix.parent.left) {
                    fix = fix.parent;
                    rightRotate(fix);
                } else {
                    fix.parent.parent.color = RED;
                    fix.parent.color = BLACK;
                    leftRotate(fix.parent.parent);
                }
            }
        }
        this.root.color = BLACK;
    }

    private void delete(Node n) {
        if (n != null && n != nil) {
            Node y = n;
            String yOriginalColor = y.color;
            Node x;
            if (n.left == nil) {
                x = n.right;
                transPlant(n, n.right);
            } else if (n.right == null) {
                x = n.left;
                transPlant(n, n.left);
            } else {
                y = minimum(n.right);
                x = y.right;
                if (y.parent != n) {
                    transPlant(y, y.right);
                    y.right = n.right;
                    n.right.parent = y;
                }
                transPlant(n, y);
                y.left = n.left;
                n.left.parent = y;
                y.color = n.color;
            }
            if (yOriginalColor.equals(BLACK)) {
                deleteFixUp(x);
            }
        }
    }

    private void deleteFixUp(Node x) {
        while (x != root && Objects.equals(x.color, BLACK)) {
            if (x.parent.left == x) {
                Node w = x.parent.right;
                if (w.color.equals(RED)) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    leftRotate(x.parent);
                } else if (w.left.color.equals(BLACK) && w.right.color.equals(BLACK)) {
                    w.color = RED;
                    x = x.parent;
                } else if (w.left.color.equals(RED) && w.right.color.equals(BLACK)) {
                    w.left.color = BLACK;
                    w.color = RED;
                    rightRotate(w);
                } else if (w.right.color.equals(RED)) {
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w.color.equals(RED)) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rightRotate(x.parent);
                } else if (w.left.color.equals(BLACK) && w.right.color.equals(BLACK)) {
                    w.color = RED;
                    x = x.parent;
                } else if (w.right.color.equals(RED) && w.left.color.equals(BLACK)) {
                    w.right.color = BLACK;
                    w.color = RED;
                    leftRotate(w);
                } else if (w.left.color.equals(RED)) {
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        root.color = BLACK;
    }

    private void transPlant(Node target, Node source) {
        if (target == root) {
            this.root = source;
            source.left = target.left;
            source.right = target.right;
        } else {
            if (target == target.parent.left) {
                target.parent.left = source;
            } else {
                target.parent.right = source;
            }
            source.parent = target.parent;
        }
    }

    private Node search(Integer x) {
        Node y = root;
        while (y != nil && y.key != x) {
            if (y.key < x) {
                y = y.right;
            } else {
                y = y.left;
            }
        }
        return y;
    }

    public Node minimum(Node node) {
        Node temp = node;
        while (node != nil) {
            temp = node;
            node = node.left;
        }
        return temp;
    }

    private void walk() {
        if (root != nil) {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                Node n = queue.poll();
                if (n.left != nil) {
                    queue.add(n.left);
                }
                if (n.right != nil) {
                    queue.add(n.right);
                }
                System.out.print(n.key + "   ");
            }
        }
    }

    @Test
    public void main() throws JsonProcessingException {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);
        tree.insert(7);
        tree.delete(tree.search(4));
        tree.delete(tree.search(3));
        tree.delete(tree.search(1));
        tree.delete(tree.search(2));
        tree.delete(tree.search(5));
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(tree.root));
    }

    public static class Node {
        public Integer key;
        public String color;
        public Integer level;
        public Node left;
        public Node right;
        @JsonIgnore
        public Node parent;

        public Node(Integer key) {
            this.key = key;
        }

        public Node(Integer key, String color) {
            this.key = key;
            this.color = color;
        }
    }
}
