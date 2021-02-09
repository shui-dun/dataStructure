package com.sd.algorithm;

import com.sd.ds.IntDisjointSet;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 使用随机Kruskal算法生成迷宫
 */
public class RandomKruskalMaze implements WallMaze {
    private IntDisjointSet set;
    private int m;
    private int n;
    private ArrayList<ArrayList<Boolean>> verticalEdges;
    private ArrayList<ArrayList<Boolean>> horizontalEdges;
    private ArrayList<Edge> usable;

    @Override
    public ArrayList<ArrayList<Boolean>> getVertical() {
        return verticalEdges;
    }

    @Override
    public ArrayList<ArrayList<Boolean>> getHorizontal() {
        return horizontalEdges;
    }

    private static class Edge {
        boolean isRight; // true代表该边位于rightEdges中，false代表该边位于bottomEdges中
        int x;
        int y;

        public Edge(boolean isRight, int x, int y) {
            this.isRight = isRight;
            this.x = x;
            this.y = y;
        }
    }

    public RandomKruskalMaze(int m, int n) {
        set = new IntDisjointSet(m * n);
        this.m = m;
        this.n = n;
        verticalEdges = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            verticalEdges.add(new ArrayList<>(n - 1));
            for (int x = 0; x < n - 1; x++) {
                verticalEdges.get(i).add(true);
            }
        }
        horizontalEdges = new ArrayList<>(m - 1);
        for (int i = 0; i < m - 1; i++) {
            horizontalEdges.add(new ArrayList<>(n));
            for (int x = 0; x < n; x++) {
                horizontalEdges.get(i).add(true);
            }
        }
        usable = new ArrayList<>(m * n);
        rebuild();
    }

    private void initUsable() {
        usable.clear();
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n - 1; y++) {
                usable.add(new Edge(true, x, y));
            }
        }
        for (int x = 0; x < m - 1; x++) {
            for (int y = 0; y < n; y++) {
                usable.add(new Edge(false, x, y));
            }
        }
        Collections.shuffle(usable);
    }

    private void breakWall() {
        Edge edge = usable.get(usable.size() - 1);
        int a, b;
        if (edge.isRight) {
            a = set.find(n * edge.x + edge.y);
            b = set.find(n * edge.x + edge.y + 1);
            if (a != b) {
                set.union(a, b);
                verticalEdges.get(edge.x).set(edge.y, false);
            }
        } else {
            a = set.find(n * edge.x + edge.y);
            b = set.find(n * (edge.x + 1) + edge.y);
            if (a != b) {
                set.union(a, b);
                horizontalEdges.get(edge.x).set(edge.y, false);
            }
        }
        usable.remove(usable.size() - 1);
    }

    @Override
    public void rebuild() {
        initUsable();
        while (!usable.isEmpty()) {
            breakWall();
        }
    }

    public void quickCreate() {
        initUsable();
        int last = m * n - 1;
        while (!set.inSameSet(0, last)) {
            breakWall();
        }
    }

    @Override
    public String draw() {
        return Mazes.drawWall(this);
    }

    @Override
    public int getM() {
        return m;
    }

    @Override
    public int getN() {
        return n;
    }

}