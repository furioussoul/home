package chapter10;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szj on 2016/6/23.
 */
public class Queue<E> {

    private int size = 10;
    private int head = 0;
    private int tail = 0;
    private Object[] queue = new Object[size];

    @Test
    public void main(){
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(2);
        queue.enqueue(1);
        queue.enqueue(5);
        queue.enqueue(4);
        queue.enqueue(3);
        queue.enqueue(6);
        queue.enqueue(7);
        Integer e = queue.dequeue();
        while (e != null){
            System.out.print(e + " ");
            e = queue.dequeue();
        }
    }


    public void enqueue(E e){
        if(isFull()){
            throw new RuntimeException("queue is full");
        }
        queue[tail] = e;
        if(tail == size - 1){
            tail = 0;
        }else{
            tail++;
        }
    }
    public E dequeue(){
        if(isEmpty()){
            throw new RuntimeException("queue is empty");
        }
        Object e = queue[head];
        if(head == size - 1){
            head = 0;
        }else{
            head++;
        }
        return (E)e;
    }

    private boolean isFull(){
        return tail + 1 == head ? true : false;
    }
    private boolean isEmpty(){
        return head == tail ? true : false;
    }
}
