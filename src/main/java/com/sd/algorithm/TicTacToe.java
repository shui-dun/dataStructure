package com.sd.algorithm;

import java.util.Arrays;

/**
 * 井字棋游戏
 */
public class TicTacToe {
    enum Piece {
        FIRST, SECOND, BLANK
    }

    /**
     * 棋盘如下
     * 1 2 3
     * 4 5 6
     * 7 8 9
     */
    private Piece[] board = new Piece[10];

    private void draw() {
        for (int i = 1; i <= 9; i++) {
            if (board[i] == Piece.BLANK) {
                System.out.printf("%d ", i);
            } else if (board[i] == Piece.FIRST) {
                System.out.print("o ");
            } else {
                System.out.print("x ");
            }
            if (i % 3 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    boolean verify(int choice) {
        if (choice <= 0 || choice > 9) {
            return false;
        }
        return board[choice] == Piece.BLANK;
    }

    private boolean win(Piece piece) {
        return (board[1] == piece && board[2] == piece && board[3] == piece) || (board[4] == piece && board[5] == piece && board[6] == piece) || (board[7] == piece && board[8] == piece && board[9] == piece) || (board[1] == piece && board[4] == piece && board[7] == piece) || (board[2] == piece && board[5] == piece && board[8] == piece) || (board[3] == piece && board[6] == piece && board[9] == piece) || (board[1] == piece && board[5] == piece && board[9] == piece) || (board[3] == piece && board[5] == piece && board[7] == piece);
    }

    Piece get(int i) {
        return board[i];
    }

    void set(int i, Piece piece) {
        board[i] = piece;
    }

    /**
     * @return 如果只差一个字就赢了，返回赢的坐标，否则返回〇
     */
    int almostWin(Piece piece) {
        for (int i = 1; i <= 9; i++) {
            if (board[i] == Piece.BLANK) {
                board[i] = piece;
                if (win(piece)) {
                    board[i] = Piece.BLANK;
                    return i;
                }
                board[i] = Piece.BLANK;
            }
        }
        return 0;
    }

    boolean isFull() {
        for (int i = 1; i <= 9; i++) {
            if (board[i] == Piece.BLANK) {
                return false;
            }
        }
        return true;
    }

    private boolean end() {
        if (isFull()) {
            System.out.println("这是平局。");
            return true;
        } else if (win(Piece.FIRST)) {
            System.out.println("FIRST玩家获胜");
            return true;
        } else if (win(Piece.SECOND)) {
            System.out.println("SECOND玩家获胜");
            return true;
        } else {
            return false;
        }
    }

    private TicTacToe() {
        Arrays.fill(board, Piece.BLANK);
    }

    public static void play(TTTPlayer first, TTTPlayer second) {
        TicTacToe t = new TicTacToe();
        first.init(t, Piece.FIRST, Piece.SECOND);
        second.init(t, Piece.SECOND, Piece.FIRST);
        System.out.println("欢迎进入井字棋游戏");
        t.draw();
        while (true) {
            System.out.println("FIRST玩家执子：");
            t.board[first.next()] = Piece.FIRST;
            t.draw();
            if (t.end()) {
                break;
            }
            System.out.println("SECOND玩家执子：");
            t.board[second.next()] = Piece.SECOND;
            t.draw();
            if (t.end()) {
                break;
            }
        }
    }
}
