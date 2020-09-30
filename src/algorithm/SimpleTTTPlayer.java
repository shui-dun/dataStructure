package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 落子顺序如下
 * 1 直接落子获胜
 * 2 阻止玩家获胜
 * 3 在角上落子
 * 4 在中心落子
 * 5 在边上落子
 */
public class SimpleTTTPlayer implements TTTPlayer {
    private List<Integer> corner = new ArrayList<>();

    private List<Integer> side = new ArrayList<>();

    private TicTacToe ticTacToe;

    private TicTacToe.Piece me;

    private TicTacToe.Piece enemy;

    @Override
    public void init(TicTacToe ticTacToe, TicTacToe.Piece me, TicTacToe.Piece enemy) {
        this.ticTacToe = ticTacToe;
        this.me = me;
        this.enemy = enemy;
        Collections.addAll(corner, 1, 3, 7, 9);
        Collections.addAll(side, 2, 4, 6, 8);
        Collections.shuffle(corner);
        Collections.shuffle(side);
    }

    @Override
    public int next() {
        int choice = ticTacToe.almostWin(me);
        if (choice != 0) {
            return choice;
        }
        choice = ticTacToe.almostWin(enemy);
        if (choice != 0) {
            return choice;
        }
        for (int value : corner) {
            if (ticTacToe.get(value) == TicTacToe.Piece.BLANK) {
                return value;
            }
        }
        if (ticTacToe.get(5) == TicTacToe.Piece.BLANK) {
            return 5;
        }
        for (int value : side) {
            if (ticTacToe.get(value) == TicTacToe.Piece.BLANK) {
                return value;
            }
        }
        return 0;
    }
}
