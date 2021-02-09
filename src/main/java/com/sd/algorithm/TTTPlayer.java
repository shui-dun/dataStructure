package com.sd.algorithm;

/**
 * 井字棋游戏的电脑玩家
 */
public interface TTTPlayer {

    /**
     * 初始化
     *
     * @param ticTacToe 棋盘
     * @param me        我方执子
     * @param enemy    敌方执子
     */
    void init(TicTacToe ticTacToe, TicTacToe.Piece me, TicTacToe.Piece enemy);

    /**
     * @return 下一步棋落子位置
     */
    int next();
}
