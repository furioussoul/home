package chapter9;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szj on 2016/6/22.
 * 随机快速选择
 */
public class RandomizedSelect {

    private static List<Integer> input = new ArrayList<>();
    static {
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(6);
        input.add(7);
        input.add(8);
        input.add(9);
        input.add(10);
        input.add(11);
        input.add(4);
        input.add(5);
    }

    @Test
    public void main() {
        int ele = randomizedSelect(input, 0, input.size() - 1, 10);
        System.out.println(ele);
    }

    //选择第i小的元素
    public int randomizedSelect(List<Integer> input, int p, int r, int k) {
        if (r == p) {//边际情况，3个找一个，不是中间那个
            return input.get(r);
        }
        int q = partition(input, p, r);
        int t = q - p + 1;//p到q有几个数
        if (t == k) {//找到了
            return input.get(q);
        } else if (t > k) {
            return randomizedSelect(input, p, q - 1, k);//找左边
        } else {
            return randomizedSelect(input, q + 1, r, k - t);//找右边
        }
    }


    private int partition(List<Integer> input, int p, int r) {
        int i = p - 1;
        int temp = input.get(r);
        for (int j = p; j < r; j++) {
            //if x is smaller than temp, change input[x] with input[i+1], becasuse elements before i+1 are smaller than temp
            //after change elements from p to i+1 are smaller than temp
            if (input.get(j) <= temp) {
                i++;
                //change
                int y = input.get(i);
                input.set(i, input.get(j));
                input.set(j, y);
            }
        }
        //change last element with input[i+1]
        i++;
        input.set(r, input.get(i));
        input.set(i, temp);
        return i;
    }
}
