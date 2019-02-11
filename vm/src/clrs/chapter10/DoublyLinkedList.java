package clrs.chapter10;

import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by szj on 2016/6/24.
 */
public class DoublyLinkedList<E> {

    private Node<E> first;
    private Node<E> last;
    private int size;

    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(E e, Node<E> prev, Node<E> next) {
            this.element = e;
            this.prev = prev;
            this.next = next;
        }
    }

    // add element at the head of list
    public void linkFirst(E e) {
        Node<E> f = first;// cache
        Node<E> newNode = new Node(e, null, f);// newNode的next设置为当前first
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }


    public Node<E> search(int index) {
        checkElementIndex(index);
        Node<E> node = first;
        for(int i = 0; i < index; i++){
            node = node.next;
        }
        return node;
    }

    private void checkElementIndex(int index) {
        if (!isElementIndex(index)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isElementIndex(int index) {
        return index > -1 && index < size;
    }

    @Test
    public void main() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.linkFirst(1);
        list.linkFirst(3);
        list.linkFirst(4);
        list.linkFirst(2);
        list.linkFirst(0);
        list.linkFirst(5);
        Node<Integer> t = null;
        Node<Integer> n = list.first;
        while (n != null) {
            System.out.print(n.element + " ");
            if (n.next == null) {
                t = n;
            }
            n = n.next;
        }
        System.out.println();
        while (t != null) {
            System.out.print(t.element + " ");
            t = t.prev;
        }
        System.out.println();
        System.out.print(list.search(2).element);
    }
}
