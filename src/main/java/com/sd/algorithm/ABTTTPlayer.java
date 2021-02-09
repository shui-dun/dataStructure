package com.sd.algorithm;


/**
 * 经过alpha-beta优化的minimax算法
 */
public class ABTTTPlayer implements TTTPlayer {
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

    private Move computerOptimalMove(int alpha, int beta) {
        if (ticTacToe.isFull()) {
            return new Move(0, 0);
        }
        int choice;
        if ((choice = ticTacToe.almostWin(me)) != 0) {
            return new Move(choice, 1);
        }
        Move ret = new Move(0, alpha);
        for (int i = 1; i <= 9 && ret.priority < beta; i++) {
            if (ticTacToe.get(i) == TicTacToe.Piece.BLANK) {
                ticTacToe.set(i, me);
                Move response = humanOptimalMove(ret.priority, beta);
                if (response.priority > ret.priority) {
                    ret.priority = response.priority;
                    ret.position = i;
                }
                ticTacToe.set(i, TicTacToe.Piece.BLANK);
            }
        }
        return ret;
    }

    private Move humanOptimalMove(int alpha, int beta) {
        if (ticTacToe.isFull()) {
            return new Move(0, 0);
        }
        int choice;
        if ((choice = ticTacToe.almostWin(enemy)) != 0) {
            return new Move(choice, -1);
        }
        Move ret = new Move(0, beta);
        for (int i = 1; i <= 9 && ret.priority > alpha; i++) {
            if (ticTacToe.get(i) == TicTacToe.Piece.BLANK) {
                ticTacToe.set(i, enemy);
                Move response = computerOptimalMove(alpha, ret.priority);
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
        return computerOptimalMove(-1, 1).position;
    }
}
