package com.sd.algorithm;

import java.util.ArrayList;

/**
 * 此种迷宫维护两个二维数组，代表迷宫的围墙
 */
public interface WallMaze extends Maze {
    /**
     * @return 数组中每个元素代表一个竖墙，不包括迷宫的边界
     */
    ArrayList<ArrayList<Boolean>> getVertical();

    /**
     * @return 数组中每个元素代表一个横墙，不包括迷宫的边界
     */
    ArrayList<ArrayList<Boolean>> getHorizontal();

}
