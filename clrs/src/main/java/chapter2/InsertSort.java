package chapter2;

import org.junit.Test;

/**
 * Created by szj on 2016/6/9.
 * ctmb排序，比翻转2x树还难
 */
public class InsertSort {
    //card
    public static int[] sort = {0, 9, 6, 7, 16, 5, 4, 23, 2, 1};

    @Test
    public void insertSort() {
        // 从第二个元素开始
        for (int i = 1; i < sort.length; i++) {
            int pick = sort[i];

            int j = i - 1;
            while (j > -1 && sort[j] > pick) {
                // 如果 前面的元素比pick大，则将前面元素后移一位
                sort[j + 1] = sort[j];
                j--;
            }
            // 将pick插入空槽
            sort[j + 1] = pick;
        }
        for (int x : sort) {
            System.out.print(x + " ");
        }
    }
}
