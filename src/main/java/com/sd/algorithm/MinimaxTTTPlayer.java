package com.sd.algorithm;

/**
 * Minimax算法
 */
public class MinimaxTTTPlayer implements TTTPlayer {

    private TicTacToe ticTacToe;

    private TicTacToe.Piece me;

    private TicTacToe.Piece enemy;

    @Override
    public void init(TicTacToe ticTacToe, TicTacToe.Piece me, TicTacToe.Piece enemy) {
        this.ticTacToe = ticTacToe;
        this.me = me;
        this.enemy = enemy;
    }

    private static class Move {
        private int position;  // 落子位置
        private int priority;  // 优先级别

        public Move(int position, int priority) {
            this.position = position;
            this.priority = priority;
        }
    }

    private Move computerOptimalMove() {
        if (ticTacToe.isFull()) {
            return new Move(0, 0);
        }
        int choice;
        if ((choice = ticTacToe.almostWin(me)) != 0) {
            return new Move(choice, 1);
        }
        Move ret = new Move(0, Integer.MIN_VALUE);
        for (int i = 1; i <= 9; i++) {
            if (ticTacToe.get(i) == TicTacToe.Piece.BLANK) {
                ticTacToe.set(i, me);
                Move response = humanOptimalMove();
                if (response.priority > ret.priority) {
                    ret.priority = response.priority;
                    ret.position = i;
                }
                ticTacToe.set(i, TicTacToe.Piece.BLANK);
            }
        }
        return ret;
    }

    private Move humanOptimalMove() {
        if (ticTacToe.isFull()) {
            return new Move(0, 0);
        }
        int choice;
        if ((choice = ticTacToe.almostWin(enemy)) != 0) {
            return new Move(choice, -1);
        }
        Move ret = new Move(0, Integer.MAX_VALUE);
        for (int i = 1; i <= 9; i++) {
            if (ticTacToe.get(i) == TicTacToe.Piece.BLANK) {
                ticTacToe.set(i, enemy);
                Move response = computerOptimalMove();
                if (response.priority < ret.priority) {
                    ret.priority = response.priority;
                    ret.position = i;
                }
                ticTacToe.set(i, TicTacToe.Piece.BLANK);
            }
        }
        return ret;
    }

    @Override
    public int next() {
        return computerOptimalMove().position;
    }
}
