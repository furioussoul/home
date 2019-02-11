package utils;

import clrs.chapter13.RedBlackTree;
import org.junit.Test;

import java.util.Stack;

/**
 * Created by szj on 2016/6/29.
 */
public class TreeWalker {
    private StringBuffer html = new StringBuffer();
    int a = 3;

    public void walk(RedBlackTree.Node node) {
        if (node != null) {
            if(node.left != null || node.right != null){
                html.append("<ul>\n");
            }
            html.append("<li>" + node.key + "</li>\n");
            walk(node.left);
            walk(node.right);
            if(node.left != null || node.right != null){
                html.append("</ul>\n");
            }
        }
    }

    public void run(RedBlackTree.Node node){

    }

    @Test
    public void main(){
//        RedBlackTree tree = new RedBlackTree();
//        tree.insert(6);
//        tree.insert(5);
//        tree.insert(3);
//        tree.insert(4);
//        tree.insert(7);
//        walk(tree.root);
//        System.out.println(html.toString());
        System.setProperty("utils.TreeWalker.a","3");
        System.out.println(Integer.getInteger("utils.TreeWalker.a",2));
        System.out.println(Integer.getInteger("utils.TreeWalker.a",1));
    }
}
