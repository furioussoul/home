package tmp;

import org.junit.Test;

public class Sort {

    private int[] origin = {9, 0, 3, 2, 8, 1, 6, 101, 7, 5, 4, 11, 2, 111};

    public void print(int[] origin) {
        for (int x : origin) {
            System.out.print(x + " ");
        }
    }

    public void swap(int[] arr, int l, int h) {
        int tmp = arr[l];
        arr[l] = arr[h];
        arr[h] = tmp;
    }

    public int partition(int[] arr, int l, int h) {
        int i = l;
        int j = h + 1;
        int t = arr[l];
        while (true) {
            while (arr[++i] <= t && i != h) ;
            while (arr[--j] >= t && j != l) ;

            if (i >= j) break;

            swap(arr, i, j);
        }

        swap(arr, l, j);

        return j;
    }

    public void rapidSort(int[] arr, int l, int h) {
        if (l >= h) {
            return;
        }
        int m = partition(origin, l, h);
        rapidSort(origin, 0, m - 1);
        rapidSort(origin, m + 1, h);
    }


    public void shuffle(int[] arr) {
        int i = (int) (Math.random() * 10 / 10);
        int j = (int) (Math.random() * 10 / 10);
        swap(arr, i, j);
    }

    @Test
    public void testRapidSort() {
        System.out.println("testRapidSort");
        shuffle(origin);
        int m = partition(origin, 0, origin.length - 1);
        rapidSort(origin, 0, m);
    }

    @Test
    public void testInsertSort() {
        System.out.println("testInsertSort");
        for (int i = 1; i < origin.length; i++) {
            for(int j = i;j>0&&origin[j-1]>origin[j];j--){
                swap(origin,j-1,j);
            }
        }
    }

    @Test
    public void testR1RapidSort() {
        if (origin.length < 20) {
            testInsertSort();
        } else {
            testRapidSort();
        }

        print(origin);
    }
}
