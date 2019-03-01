package tmp;

import org.junit.Test;

public class HW {

    String origin = "adad";

    String outPut = "";
    int tmpTa[][] = new int[100][100];

    public int max(int x, int y) {
        return x > y ? x : y;
    }

    public int countMaxLen(String origin, int l, int h) {
        if(l>=h) return 1;
        if (origin.charAt(l) == origin.charAt(h)) {
            int k;
            if(tmpTa[l][h] == 0) {
                k = countMaxLen(origin, l + 1, h - 1) + 2;
                tmpTa[l][h] = k;
                outPut = origin.charAt(l) + outPut + origin.charAt(h);
            }
            else k = tmpTa[l][h];
            return k;
        } else {
            return max(countMaxLen(origin, l, h - 1), countMaxLen(origin, l + 1, h));
        }
    }


    @Test
    public void testHW() {
        int i = countMaxLen(origin, 0, origin.length() - 1);
        System.out.println(i);
        System.out.println(outPut);
    }
}
