public class DP{


    /**
     * 动态规划自底向上切割钢条
     * 思考子问题图
     * 这里只给出了n=7时的最优解，如果要求解切割方法，需要重构解。
     */
    public static class BottomUpCutRod{
        //长度对应的价格
        private static int[] price = {0,1,5,8,9,10,17,17,20,24,30};
        private static int[] memorization = {0,0,0,0,0,0,0,0,0,0};
      
        public static void main(String[] args) {
            int maxP = cutRod(price, 7);
            System.out.println(maxP);
            assert maxP==18;
        }

        /**
         * 时间复杂度O（n^2)
         * 相对于自顶向下，减少了递归的空间复杂度
         * @Param price 段价格
         * @Param n 总长度
         * @Return 最大营收
         */
        private static int cutRod(int[] price, int n){
            for(int i = 1; i <= n;i++){
                int q = Integer.MIN_VALUE;
                for(int j = 1; j <= i;j++){
                    q = Integer.max(q,price[j]+memorization[i-j]);
                }
                memorization[i] = q;
            }

           return memorization[n];
        }
    }
}