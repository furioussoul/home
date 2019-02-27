package chapter1;

import org.junit.Test;

public class BinarySearch {


    int array[] = {1, 2, 3, 4, 5, 6, 100};

    @Test
    public void doSearch(){

        int target = 100;

        int l = 0;
        int r = array.length - 1;
        while(l<=r){
            int m = l+(r-l)/2;
            if(target == array[m]){
                System.out.println(l);
                return ;
            }else if(target>array[m]){
                l = m+1;
            }else {
                r = m-1;
            }
        }
        System.out.println(-1);
        return;
    }
}
