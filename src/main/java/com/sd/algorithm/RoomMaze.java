package com.sd.algorithm;

/**
 * 此种迷宫维护一个二维数组，数组中每一个元素代表迷宫中的一个格，包含迷宫边界
 */
public interface RoomMaze extends Maze {
    /**
     * @return 数组中每一个元素代表迷宫中的一个格，false代表通道，true代表围墙（障碍物）
     */
    boolean[][] getMap();
}
