package algorithm;

/**
 *  使用手动输入的二维数组生成迷宫，不需要输入边界
 */
public class ManualMaze implements RoomMaze {
    private boolean[][] map;
    private int m;
    private int n;

    public ManualMaze(int[][] map) {
        int m = map.length + 2, n = map[0].length + 2;
        this.map = new boolean[m][n];
        // 在四周加上围墙
        for (int i = 0; i < m; i++) {
            this.map[i][0] = true;
            this.map[i][n - 1] = true;
        }
        for (int i = 0; i < n; i++) {
            this.map[0][i] = true;
            this.map[m - 1][i] = true;
        }
        for (int x = 1; x < m - 1; x++) {
            for (int y = 1; y < n - 1; y++) {
                this.map[x][y] = map[x-1][y-1] != 0;
            }
        }
        this.m = m;
        this.n = n;
    }

    @Override
    public boolean[][] getMap() {
        return map;
    }

    @Override
    public void rebuild() {
        throw new UnsupportedOperationException();
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
}
