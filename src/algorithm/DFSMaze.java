package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 使用dfs生成迷宫
 */
public class DFSMaze implements RoomMaze {
    private int m;
    private int n;
    private boolean[][] map;
    private int[][] direct = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int[][][] forward = {{{1, -1}, {1, 0}, {1, 1}, {0, -1}, {0, 1}}, {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}}, {{-1, 1}, {0, 1}, {1, 1}, {-1, 0}, {1, 0}}, {{-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {1, 0}}};
    private ArrayList<Integer> choice;

    public DFSMaze(int m, int n) {
        this.m = m;
        this.n = n;
        map = new boolean[m][n];
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                map[x][y] = true;
            }
        }
        choice = new ArrayList<>();
        Collections.addAll(choice, 0, 1, 2, 3);
        rebuild();
    }

    @Override
    public void rebuild() {
        Random random = new Random();
        int x = random.nextInt(m - 2) + 1;
        int y = random.nextInt(n - 2) + 1;
        for (int i : choice) {
            dfs(x, y, i);
        }
    }

    private void dfs(int x, int y, int d) {
        if (x == 0 || y == 0 || x == m - 1 || y == n - 1) {
            return;
        }
        for (int i = 0; i < 5; i++) {
            int forwardX = forward[d][i][0];
            int forwardY = forward[d][i][1];
            if (!map[x + forwardX][y + forwardY]) {
                return;
            }
        }
        map[x][y] = false;
        Collections.shuffle(choice);
        for (int i : choice) {
            dfs(x + direct[i][0], y + direct[i][1], i);
        }
    }

    @Override
    public String draw() {
        return Mazes.drawRoom(this);
    }

    @Override
    public int getM() {
        return m;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public boolean[][] getMap() {
        return map;
    }
}
