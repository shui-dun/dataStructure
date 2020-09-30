package algorithm;

import java.util.Scanner;

public class HumanTTTPlayer implements TTTPlayer {
    private TicTacToe ticTacToe;

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void init(TicTacToe ticTacToe, TicTacToe.Piece me, TicTacToe.Piece enemy) {
        this.ticTacToe = ticTacToe;
    }

    @Override
    public int next() {
        int choice;
        do {
            System.out.print("请输入有效坐标：");
            choice = scanner.nextInt();
        } while (!ticTacToe.verify(choice));
        return choice;
    }
}
