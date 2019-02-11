package chapter2;

import org.junit.Test;

/**
 * Created by szj on 2016/6/23.
 * 冒泡排序
 */
public class BubbleSort {
    public static int[] sort = {0, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    @Test
    public void main() {
        for (int i = 0; i < sort.length - 1; i++) {// bubble lenth times
            for (int j = 0; j < sort.length - i - 1; j++) {// compare with next element
                if (sort[j] > sort[j + 1]) {
                    int temp = sort[j];
                    sort[j] = sort[j + 1];
                    sort[j + 1] = temp;
                }
            }
        }
        for (int i : sort) {
            System.out.print(i + " ");
        }
    }
}
