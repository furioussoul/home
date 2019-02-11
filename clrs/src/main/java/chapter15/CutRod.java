package chapter15;

import org.junit.Test;

/**
 * Created by admin on 2016/12/17.
 */
public class CutRod {

    private int[] price;
    private int[] cache;

    public int calMaxIncome(int length) {
        if (length == 0) {
            return 0;
        }
        if(cache[length-1] >= 0){
            return cache[length-1];
        }
        int maxPrice = -1;
        for (int i = 1; i <= length; i++) {
            maxPrice = max(maxPrice, price[i - 1] + calMaxIncome(length - i));
        }
        cache[length-1] = maxPrice;
        return maxPrice;
    }

    private int max(int p0, int p1) {
        return p0 > p1 ? p0 : p1;
    }

    private void init() {
        this.price = new int[40];
        this.cache = new int[40];
        for (int i = 0; i < 40; i++) {
            this.price[i] = i*2 + i;
            this.cache[i] = -1;
        }
    }

    @Test
    public void test() {
        init();
        System.out.print(calMaxIncome(40));
    }
}
