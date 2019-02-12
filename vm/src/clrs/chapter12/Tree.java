package clrs.chapter12;

import org.junit.Test;

/**
 * Created by szj on 2016/6/26.
 * 二叉搜索树
 */
public class Tree {

    private Node root;

    //up search or down search
    public Node predecessor(Node node) {
        Node predecessor = null;
        if (node.left != null) {
            return maximum(node.left);
        } else {
            predecessor = node.parent;
            while (predecessor != null && predecessor.left == node) {
                node = predecessor;
                predecessor = predecessor.parent;
            }
        }
        return predecessor;
    }

    public Node successor(Node node) {
        Node successor = null;
        if (node.right != null) {
            return minimum(node.right);
        } else {
            successor = node.parent;
            while (successor != null && successor.right == node) {
                node = successor;
                successor = successor.parent;
            }
        }
        return successor;
    }

    public void insert(Integer e) {
        Node insertNode = new Node(e);
        if (this.root == null) {
            this.root = insertNode;
            return;
        }
        Node parentOfInsertPosition = findParentOfInsertPosition(insertNode);

        if (e < parentOfInsertPosition.element) {
            parentOfInsertPosition.left = insertNode;
        } else {
            parentOfInsertPosition.right = insertNode;
        }
        insertNode.parent = parentOfInsertPosition;
    }

    private Node findParentOfInsertPosition(Node insertNode) {
        Node insertPosition = this.root;
        Node parentOfInsertPosition = null;
        while (insertPosition != null) {
            parentOfInsertPosition = insertPosition;//cache parentPosition of insertPosition
            if (insertNode.element < insertPosition.element) {
                insertPosition = insertPosition.left;
            } else {
                insertPosition = insertPosition.right;
            }
        }
        return parentOfInsertPosition;
    }

    public Node search(int element) {
        Node y = root;
        while (y != null && y.element != element) {
            if (y.element < element) {
                y = y.right;
            } else {
                y = y.left;
            }
        }
        return y;
    }

    public Node maximum(Node node) {
        Node temp = null;
        while (node != null) {
            temp = node;
            node = node.right;
        }
        return temp;
    }

    public Node minimum(Node node) {
        Node temp = null;
        while (node != null) {
            temp = node;
            node = node.left;
        }
        return temp;
    }

    //仅仅移植父节点关系
    private void transplant(Node x, Node y) {
        if (x == null) {
            throw new IllegalArgumentException("x is null");
        }
        if (x.parent == null) {
            root = y;
        } else if (x.parent.left == x) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        if (y != null) {
            y.parent = x.parent;
        }
    }

    public void delete(Node z) {
        if (z == null) {
            throw new IllegalArgumentException("z is null");
        }
        if (z.left == null) {
            transplant(z, z.right);
        } else if (z.right == null) {
            transplant(z, z.left);
        } else {
            Node y = minimum(z.right);
            if (z != y.parent) {
                transplant(y, y.right);
                y.right = z.right;
                z.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            z.left.parent = y;
        }
    }

    //中序
    private void walk(Node node) {

        if (node == null) {
            return;
        }

        walk(node.left);
        System.out.print(node.element);
        walk(node.right);
    }

    @Test
    public void main() {
        Tree tree = new Tree();
        tree.insert(2);
        tree.insert(6);
        tree.insert(6);
        tree.delete(tree.search(2));
        tree.walk(tree.root);
    }

    private static class Node {
        private Node parent;
        private Node left;
        private Node right;
        private Integer element;

        private Node(Integer e) {
            this.element = e;
        }

        @Override
        public String toString() {
            return element + "";
        }
    }
}
