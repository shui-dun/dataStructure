package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 使用递归分割算法生成迷宫
 */
public class RecursiveDivideMaze implements RoomMaze {
    private int m;
    private int n;
    private boolean[][] map;
    private Random random = new Random();
    private ArrayList<Integer> choice = new ArrayList<>(4);

    public RecursiveDivideMaze(int m, int n) {
        this(m, n, null);
    }

    public RecursiveDivideMaze(int m, int n, Integer seed) {
        if (seed != null) {
            random.setSeed(seed);
        }
        this.m = m;
        this.n = n;
        map = new boolean[m][n];
        for (int i = 0; i < m; i++)
            map[i][0] = true;
        for (int i = 0; i < m; i++)
            map[i][n - 1] = true;
        for (int i = 0; i < n; i++)
            map[0][i] = true;
        for (int i = 0; i < n; i++)
            map[m - 1][i] = true;
        for (int x = 1; x < m - 1; x++) {
            for (int y = 1; y < n - 1; y++) {
                map[x][y] = false;
            }
        }
        Collections.addAll(choice, 0, 1, 2, 3);
        rebuild();
    }


    @Override
    public void rebuild() {
        createRoom(0, m - 1, 0, n - 1);
    }

    /**
     * @return (begin, end)间的随机奇/偶数
     */
    private int randomNum(int begin, int end, boolean isOdd) {
        int rand = Math.abs(random.nextInt()) % (end - begin - 1) + begin + 1;
        if (rand % 2 == (isOdd ? 0 : 1)) {
            if (rand != end - 1) {
                rand += 1;
            } else {
                rand -= 1;
            }
        }
        return rand;
    }

    private void createRoom(int x0, int x1, int y0, int y1) {
        if (x1 - x0 <= 3 || y1 - y0 <= 3) {
            return;
        }
        // 在横纵坐标都为偶数的地方建墙
        int randX = randomNum(x0 + 1, x1 - 1, false);
        int randY = randomNum(y0 + 1, y1 - 1, false);
        for (int i = x0 + 1; i < x1; i++) {
            map[i][randY] = true;
        }
        for (int i = y0 + 1; i < y1; i++) {
            map[randX][i] = true;
        }
        // 在横纵坐标都为奇数的地方挖墙
        Collections.shuffle(choice, random);
        for (int i = 0; i < 3; i++) {
            int choose = choice.get(i);
            int breakX, breakY;
            if (choose == 0) {
                breakX = randomNum(x0, randX, true);
                breakY = randY;
            } else if (choose == 1) {
                breakX = randomNum(randX, x1, true);
                breakY = randY;
            } else if (choose == 2) {
                breakX = randX;
                breakY = randomNum(y0, randY, true);
            } else {
                breakX = randX;
                breakY = randomNum(randY, y1, true);
            }
            map[breakX][breakY] = false;
        }
        // 递归分割
        createRoom(x0, randX, y0, randY);
        createRoom(x0, randX, randY, y1);
        createRoom(randX, x1, y0, randY);
        createRoom(randX, x1, randY, y1);
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
