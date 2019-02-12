package clrs.chapter10;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szj on 2016/6/23.
 */
public class Stack<E> {

    private List<E> stack = new ArrayList<>();

    @Test
    public void main() {
        Stack<Integer> s = new Stack<>();
        s.push(10);
        s.push(5);
        s.push(7);
        s.push(6);
        s.push(6);
        s.push(6);
        s.push(6);
        s.push(6);
        s.push(9);
        Integer e = s.pop();
        while (e != null) {
            System.out.print(e + " ");
            e = s.pop();
        }
    }

    public boolean isEmpty() {
        return stack.size() == 0;
    }

    public void push(E e) {
        stack.add(e);
    }

    public E pop() {
        return stack.size() == 0 ? null : stack.remove(stack.size() - 1);
    }
}
