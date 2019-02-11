package clrs.chapter8;

import org.junit.Test;

/**
 * Created by szj on 2016/6/12.
 * 计数排序
 */
public class CountSort {
    private int[] a = {9, 0, 8, 12, 6, 5, 4, 11, 2, 1}; // input

    private int[] b = new int[10]; // output

    private int[] c = new int[20]; // count how many numbers smaller than it

    @Test
    public void main(){
        countSort(a);
        for(int x : b){
            System.out.print(x + " ");
        }
    }

    public void countSort(int[] sort){
        // store count of every element  in array c
        for(int i = 0; i < sort.length; i++){
            c[sort[i]] += 1;
        }
        // store count of elements not bigger than c[i] in array c
        for(int i = 1; i < c.length; i++){
            c[i] = c[i] + c[i-1];
        }
        // already known count of elements not bigger than every elemnts in the input,
        // sort input by it
        for(int i = a.length - 1; i >0; i--){
            b[c[sort[i]] - 1] = sort[i];
            c[sort[i]] -= 1;
        }
    }
}
