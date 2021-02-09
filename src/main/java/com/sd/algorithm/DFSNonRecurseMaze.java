package com.sd.algorithm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

/**
 * 使用非递归的dfs生成迷宫
 */
public class DFSNonRecurseMaze implements WallMaze {

    private int m;
    private int n;
    private ArrayList<ArrayList<Boolean>> verticalEdges;
    private ArrayList<ArrayList<Boolean>> horizontalEdges;
    private ArrayList<ArrayList<Boolean>> usable;
    private int[][] direct = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private ArrayList<Integer> dir = new ArrayList<>(4);

    public DFSNonRecurseMaze(int m, int n) {
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
        usable = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            usable.add(new ArrayList<>(n));
            for (int x = 0; x < n; x++) {
                usable.get(i).add(true);
            }
        }
        Collections.addAll(dir, 0, 1, 2, 3);
        rebuild();
    }

    @Override
    public ArrayList<ArrayList<Boolean>> getVertical() {
        return verticalEdges;
    }

    @Override
    public ArrayList<ArrayList<Boolean>> getHorizontal() {
        return horizontalEdges;
    }

    private static class Pos {
        private int x;
        private int y;
        private int d;

        public Pos(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }

    private void breakWall(Pos pos) {
        switch (pos.d) {
            case 0:
                horizontalEdges.get(pos.x - 1).set(pos.y, false);
                return;
            case 1:
                horizontalEdges.get(pos.x).set(pos.y, false);
                return;
            case 2:
                verticalEdges.get(pos.x).set(pos.y - 1, false);
                return;
            default:
                verticalEdges.get(pos.x).set(pos.y, false);
        }
    }

    private Pos nextPos(Pos pos) {
        Collections.shuffle(dir);
        for (int i : dir) {
            int nextX = pos.x + direct[i][0];
            int nextY = pos.y + direct[i][1];
            if (nextX < 0 || nextX >= m || nextY < 0 || nextY >= n || !usable.get(nextX).get(nextY)) {
                continue;
            }
            return new Pos(nextX, nextY, i);
        }
        return null;
    }

    private void dfs(Pos pos) {
        Deque<Pos> stack = new ArrayDeque<>();
        stack.push(pos);
        breakWall(pos);
        usable.get(pos.x).set(pos.y, false);
        while (!stack.isEmpty()) {
            Pos p = stack.peekLast();
            p = nextPos(p);
            while (p != null) {
                stack.push(p);
                breakWall(p);
                usable.get(p.x).set(p.y, false);
                p = nextPos(p);
            }
            stack.pollLast();
        }
    }

    @Override
    public void rebuild() {
        dfs(new Pos(m / 2, n / 2, 0));
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
