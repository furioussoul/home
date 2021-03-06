package chapter6;

import org.junit.Test;

/**
 * Created by szj on 2016/6/11.
 * 堆排序
 */
public class HeapSort {

    private static int MIN_QUEUE_TYPE = 1; // 最小堆
    private static int MAX_QUEUE_TYPE = 2; // 最大堆
    private int[] origin = {9, 0, 8, 1, 6, 5, 4, 11, 2, 101,111,222};

    @Test
    public void buildMax() {
        buildHeap(origin, 2);
        for (int x : origin) {
            System.out.print(x + " ");
        }
    }

    @Test
    public void buildMin() {
        buildHeap(origin, 1);
        for (int x : origin) {
            System.out.print(x + " ");
        }
    }

    public void buildHeap(int[] sort, int queueType) {
        if (queueType == HeapSort.MAX_QUEUE_TYPE) {
            for (int i = (origin.length - 1) / 2; i >= 0; i--) {
                maxHeapify(i);
            }
        } else if (queueType == HeapSort.MIN_QUEUE_TYPE) {
            //recurse , heepify from (lastIndex-1)/2 to 0 can make it be heep
            for (int i = (origin.length - 1) / 2; i >= 0; i--) {
                minHeapify(i);
            }
        }
    }

    public void minHeapify(int index) {
        int leftSonIndex = left(index);
        int rightSonIndex = right(index);
        int smallestIndex = index;
        //index not out of heap size
        if (leftSonIndex < origin.length
                && origin[index] > origin[leftSonIndex]) {
            smallestIndex = leftSonIndex;
        }
        if (rightSonIndex < origin.length
                && origin[smallestIndex] > origin[rightSonIndex]) {
            smallestIndex = rightSonIndex;
        }
        //no need change
        if (smallestIndex == index) {
            return;
        }
        //change father and two sons
        int temp = origin[index];
        origin[index] = origin[smallestIndex];
        origin[smallestIndex] = temp;
        //instructions up may change the origin[smallestIndex] to a bigger number
        //that's why recurse the smallestIndex
        minHeapify(smallestIndex);
    }

    public void maxHeapify(int index) {
        int l = left(index);
        int r = right(index);
        int m = index;
        if (l < origin.length
                && origin[index] < origin[l]) {
            m = l;
        }
        if (r < origin.length
                && origin[m] < origin[r]) {
            m = r;
        }
        if (m == index) {
            return;
        }
        int temp = origin[index];
        origin[index] = origin[m];
        origin[m] = temp;
        maxHeapify(m);
    }

    private int left(int index) {
        return (index + 1) * 2 - 1;
    }

    private int right(int index) {
        return (index + 1) * 2;
    }
}
