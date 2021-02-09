package com.sd.algorithm;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 使用回溯法求出八皇后问题所有解
 */
public class EightQueens {
    private int size;

    private LinkedList<LinkedList<Integer>> answers = new LinkedList<>();

    private LinkedList<Integer> trace = new LinkedList<>();

    private boolean[][] map;

    private void dfs(int x, int y) {
        if (!map[x][y]) {
            return;
        }
        trace.add(y);
        if (x == size - 1) {
            answers.add(new LinkedList<>(trace));
            trace.removeLast();
            return;
        }
        List<Pair<Integer, Integer>> modified = updateMap(x, y);
        for (int i = 0; i < size; i++) {
            dfs(x + 1, i);
        }
        recoverMap(modified);
        trace.removeLast();
    }

    private List<Pair<Integer, Integer>> updateMap(int x, int y) {
        List<Pair<Integer, Integer>> modified = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            if (map[x][i]) {
                map[x][i] = false;
                modified.add(new Pair<>(x, i));
            }
        }
        for (int i = 0; i < size; i++) {
            if (map[i][y]) {
                map[i][y] = false;
                modified.add(new Pair<>(i, y));
            }
        }
        int x0 = x - 1, y0 = y - 1;
        while (x0 >= 0 && y0 >= 0) {
            if (map[x0][y0]) {
                map[x0][y0] = false;
                modified.add(new Pair<>(x0, y0));
            }
            x0--;
            y0--;
        }
        x0 = x + 1;
        y0 = y + 1;
        while (x0 < size && y0 < size) {
            if (map[x0][y0]) {
                map[x0][y0] = false;
                modified.add(new Pair<>(x0, y0));
            }
            x0++;
            y0++;
        }
        x0 = x - 1;
        y0 = y + 1;
        while (x0 >= 0 && y0 < size) {
            if (map[x0][y0]) {
                map[x0][y0] = false;
                modified.add(new Pair<>(x0, y0));
            }
            x0--;
            y0++;
        }
        x0 = x + 1;
        y0 = y - 1;
        while (x0 < size && y0 >= 0) {
            if (map[x0][y0]) {
                map[x0][y0] = false;
                modified.add(new Pair<>(x0, y0));
            }
            x0++;
            y0--;
        }
        return modified;
    }

    private void recoverMap(List<Pair<Integer, Integer>> modified) {
        for (Pair<Integer, Integer> pair : modified) {
            map[pair.getKey()][pair.getValue()] = true;
        }
    }

    public EightQueens() {
        this(8);
    }

    public EightQueens(int size) {
        this.size = size;
        map = new boolean[size][size];
        for (int x = 0; x < size; x++) {
            Arrays.fill(map[x], true);
        }
        for (int i = 0; i < size; i++) {
            dfs(0, i);
        }
    }

    public LinkedList<LinkedList<Integer>> getAnswers() {
        return answers;
    }

    public void draw() {
        for (List<Integer> list : answers) {
            for (int i : list) {
                for (int x = 0; x < i; x++) {
                    System.out.print('〇');
                }
                System.out.print('♛');
                for (int x = 0; x < size - i - 1; x++) {
                    System.out.print('〇');
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
