package com.sd.algorithm;

/**
 * 迷宫的接口
 */
public interface Maze {
    /**
     * 重新生成迷宫
     */
    void rebuild();

    /**
     * @return 迷宫对应的字符串
     */
    String draw();

    /**
     * @return 迷宫长度
     */
    int getM();

    /**
     * @return 迷宫高度
     */
    int getN();
}
