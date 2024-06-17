package org.example;

public class Main {

    public static int knapsack(int[] v, int[] w, int W)
    {
        int[][] T = new int[v.length + 1][W + 1];
        for (int i = 1; i <= v.length; i++)
        {
            for (int j = 0; j <= W; j++)
            {
                if (w[i-1] > j) {
                    T[i][j] = T[i-1][j];
                }
                else {
                    T[i][j] = Integer.max(T[i-1][j], T[i-1][j-w[i-1]] + v[i-1]);
                }
            }
        }
        return T[v.length][W];
    }
    public static void main(String[] args)
    {
        int[] v = { 1, 2, 3, 8, 6, 4 };
        int[] w = { 1, 2, 3, 8, 6, 4 };

        int W = 25;

        System.out.println(knapsack(v, w, W) == W);
    }
}
