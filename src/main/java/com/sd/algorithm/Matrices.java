package com.sd.algorithm;

import java.util.*;
import java.util.Random;

public class Matrices {
    private static long minMultiplyPath2(ArrayList<double[][]> list, long[] columns, int[][] path, long[][] minCount, int left, int right) {
        if (minCount[left][right] != -1) {
            return minCount[left][right];
        }
        if (left == right) {
            minCount[left][left] = 0;
            return 0;
        }
        long min = Long.MAX_VALUE;
        for (int i = left; i < right; i++) {
            long newVal = minMultiplyPath2(list, columns, path, minCount, left, i) + minMultiplyPath2(list, columns, path, minCount, i + 1, right) + columns[left - 1] * columns[right] * columns[i];
            if (newVal < min) {
                min = newVal;
                path[left][right] = i;
            }
        }
        minCount[left][right] = min;
        return min;
    }

    public static double[][] multiply2(ArrayList<double[][]> list) {
        int[][] path = new int[list.size() + 1][list.size() + 1];
        long[][] minCount = new long[list.size() + 1][list.size() + 1];
        for (long[] longs : minCount) {
            Arrays.fill(longs, -1);
        }
        long[] columns = getColumns(list);
        minMultiplyPath2(list, columns, path, minCount, 1, list.size());
        return multiplySub(list, path, 1, list.size());
    }

    /**
     * @return 列表中每个矩阵的列数，下标从一开始，columns[0]表示第一个矩阵的行数
     */
    private static long[] getColumns(ArrayList<double[][]> list) {
        long[] columns = new long[list.size() + 1];
        columns[0] = list.get(0).length;
        for (int i = 0; i < list.size() - 1; i++) {
            int a = list.get(i)[0].length;
            int b = list.get(i + 1).length;
            if (a != b) {
                throw new IllegalArgumentException("前一个矩阵的列数和后一个矩阵的行数不相等，不能相乘！");
            }
            columns[i + 1] = a;
        }
        columns[list.size()] = list.get(list.size() - 1)[0].length;
        return columns;
    }

    private static int[][] minMultiplyPath(ArrayList<double[][]> list) {
        long[] columns = getColumns(list);
        long[][] minCount = new long[list.size() + 1][list.size() + 1];
        int[][] path = new int[list.size() + 1][list.size() + 1];
        for (int i = 1; i <= list.size(); i++) {
            minCount[i][i] = 0;
        }
        for (int span = 1; span < list.size(); span++) {
            for (int left = 1; left <= list.size() - span; left++) {
                int right = left + span;
                long min = Long.MAX_VALUE;
                for (int i = left; i < right; i++) {
                    long newVal = minCount[left][i] + minCount[i + 1][right] + columns[left - 1] * columns[right] * columns[i];
                    if (newVal < min) {
                        min = newVal;
                        path[left][right] = i;
                    }
                }
                minCount[left][right] = min;
            }
        }
        return path;
    }

    public static double[][] multiply(ArrayList<double[][]> list) {
        int[][] path = minMultiplyPath(list);
        return multiplySub(list, path, 1, list.size());
    }

    private static double[][] multiplySub(ArrayList<double[][]> list, int[][] path, int begin, int end) {
        if (begin == end) {
            return list.get(begin - 1);
        }
        int mid = path[begin][end];
        return multiply(multiplySub(list, path, begin, mid), multiplySub(list, path, mid + 1, end));
    }

    public static double[][] simpleMultiply(ArrayList<double[][]> list) {
        double[][] temp = list.get(0);
        Iterator<double[][]> iter = list.iterator();
        iter.next();
        while (iter.hasNext()) {
            temp = multiply(temp, iter.next());
        }
        return temp;
    }

    public static double[][] multiply(double[][] a, double[][] b) {
        assert a[0].length == b.length;
        int m = a.length, n = b.length, l = b[0].length;
        double[][] c = new double[m][l];
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < l; y++) {
                c[x][y] = 0;
                for (int z = 0; z < n; z++) {
                    c[x][y] += a[x][z] * b[z][y];
                }
            }
        }
        return c;
    }

    public static double[][] add(double[][] a, double[][] b) {
        assert a.length == b.length && a[0].length == b[0].length;
        int m = a.length, n = a[0].length;
        double[][] c = new double[m][n];
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                c[x][y] = a[x][y] + b[x][y];
            }
        }
        return c;
    }

    public static double[][] randMat(int m, int n, int begin, int end, Random random) {
        if (random == null) {
            random = new Random();
        }
        double[][] mat = new double[m][n];
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                mat[x][y] = begin + random.nextDouble() * (end - begin);
            }
        }
        return mat;
    }

    public static void printMat(double[][] mat) {
        int m = mat.length, n = mat[0].length;
        for (int x = 0; x < m; x++) {
            System.out.print('[');
            for (int y = 0; y < n; y++) {
                System.out.print(String.format("%f,\t", mat[x][y]));
            }
            System.out.println("\b\b]");
        }
    }

    public static boolean equals(double[][] a, double[][] b) {
        int m = a.length, n = a[0].length;
        if (m != b.length || n != b[0].length) {
            return false;
        }
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                double offset = Math.abs((a[x][y] - b[x][y])) / a[x][y];
                if (offset > 0.000000000001) {
                    return false;
                }
            }
        }
        return true;
    }
}
