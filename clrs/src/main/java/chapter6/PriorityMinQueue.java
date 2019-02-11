package chapter6;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szj on 2016/6/11.
 */
public class PriorityMinQueue {

    private List<Integer> minQueue;

    public PriorityMinQueue(){
        minQueue = new ArrayList<>();
    }

    //get minimun
    public Integer heapMinimun(){
        return minQueue.get(0);
    }

    /**extract minimun and remove it**/
    public Integer heapExtractMin(){
        Integer obj = minQueue.get(0);// cache first element
        minQueue.set(0, minQueue.get(minQueue.size()-1));// set last element on first element
        minQueue.remove(minQueue.size()-1);// remove first element
        minHeapify(0); // maintain minimunHeap characteristic
        return obj;
    }

    public void minHeapify(int index) {
        int leftSonIndex = left(index);
        int rightSonIndex = right(index);
        int smallest = index;
        //index not out of heap size
        if (leftSonIndex < minQueue.size()
                && minQueue.get(index) > minQueue.get(leftSonIndex)) {
            smallest = leftSonIndex;
        }
        if (rightSonIndex < minQueue.size()
                && minQueue.get(smallest) > minQueue.get(rightSonIndex)) {
            smallest = rightSonIndex;
        }
        if (smallest == index) {
            return;
        }
        int temp = minQueue.get(index);
        minQueue.set(index, minQueue.get(smallest));
        minQueue.set(smallest, temp);
        minHeapify(smallest);
    }

    //insert element
    public void heapInsert(int key){
        minQueue.add(Integer.MAX_VALUE);
        heapIncreaseKey(minQueue.size()-1, key);
    }

    //must be used by heapInsert,real do insert element
    private void heapIncreaseKey(int index, int key){
        if(key > minQueue.get(index)){
            throw new RuntimeException("key is bigger than Integer.MAX_VALUE");
        }
        minQueue.set(index, key);
        while(index > 0 && minQueue.get(parent(index)) > key){
            int father = minQueue.get(parent(index));
            minQueue.set(parent(index), key);
            minQueue.set(index, father);
            index = parent(index);
        }
    }

    private int parent(int index){
        return (index-1)/2;
    }

    // return leftson's index
    private int left(int index) {
        return (index+1)*2 - 1;
    }

    private int right(int index) {
        return (index+1)*2;
    }

    @Test
    public void main(){
        PriorityMinQueue queue = new PriorityMinQueue();
        for(int i = 10; i > 0; i--){
            queue.heapInsert(i);
        }
        for(int i = 0; i < queue.minQueue.size(); i++){
            System.out.print(queue.minQueue.get(i) + " ");
        }
        System.out.println();
        int size = queue.minQueue.size();
        while(size-->0){
            System.out.print(queue.heapExtractMin() + " ");
        }
    }
}
