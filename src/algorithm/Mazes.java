package algorithm;

import javafx.util.Pair;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 迷宫工具类
 */
public class Mazes {

    public static String drawRoom(RoomMaze maze) {
        StringBuilder ret = new StringBuilder(maze.getM() * (maze.getN() + 1));
        for (int x = 0; x < maze.getM(); x++) {
            for (int y = 0; y < maze.getN(); y++) {
                if (maze.getMap()[x][y]) {
                    ret.append('〇');
                } else {
                    ret.append('　');
                }
            }
            ret.append('\n');
        }
        return ret.toString();
    }

    private static char getChar(int x, int y, String s, WallMaze maze) {
        Boolean high = null, low = null, left = null, right = null;
        if (y == 0) {
            left = false;
            high = low = true;
        } else if (y == maze.getN()) {
            right = false;
            high = low = true;
        }
        if (x == 0) {
            high = false;
            left = right = true;
        } else if (x == maze.getM()) {
            low = false;
            left = right = true;
        }
        if (high == null)
            high = maze.getVertical().get(x - 1).get(y - 1);
        if (left == null)
            left = maze.getHorizontal().get(x - 1).get(y - 1);
        if (right == null)
            right = maze.getHorizontal().get(x - 1).get(y);
        if (low == null)
            low = maze.getVertical().get(x).get(y - 1);
        int result = 0;
        if (high) result |= 8;
        if (low) result |= 4;
        if (left) result |= 2;
        if (right) result |= 1;
        return s.charAt(result);
    }

    public static String drawWall(WallMaze maze) {
        StringBuilder ret = new StringBuilder((maze.getM() + 1) * (2 * maze.getN() + 3));
        Set<Character> setLeft = new HashSet<>();
        Collections.addAll(setLeft, '┌', '└', '─', '├', '┬', '┴', '┼');
        String s = "   ─ ┌┐┬ └┘┴│├┤┼";
        for (int x = 0; x <= maze.getM(); x++) {
            for (int y = 0; y <= maze.getN(); y++) {
                char ch = getChar(x, y, s, maze);
                ret.append(ch);
                if (setLeft.contains(ch)) {
                    ret.append('─');
                } else {
                    ret.append(' ');
                }
            }
            ret.append('\n');
        }
        return ret.toString();
    }

    public static String drawTrack(RoomMaze maze, List<Pair<Integer, Integer>> track) {
        StringBuilder s = new StringBuilder(drawRoom(maze));
        for (Pair<Integer, Integer> pair : track) {
            s.setCharAt((maze.getN() + 1) * pair.getKey() + pair.getValue(), '麤');
        }
        return s.toString();
    }

    public static Pair<Integer, Integer> searchBegin(RoomMaze maze) {
        int min = Math.min(maze.getM(), maze.getN()) - 1;
        for (int i = 1; i < min; i++) {
            for (int x = 1; x <= i; x++) {
                if (!maze.getMap()[x][i]) {
                    return new Pair<>(x, i);
                }
            }
            for (int y = 1; y < i; y++) {
                if (!maze.getMap()[i][y]) {
                    return new Pair<>(i, y);
                }
            }
        }
        return null;
    }

    public static Pair<Integer, Integer> searchEnd(RoomMaze maze) {
        int min = Math.min(maze.getM(), maze.getN()) - 1;
        for (int i = min; i > 0; i--) {
            for (int x = i; x <= min; x++) {
                if (!maze.getMap()[x][i]) {
                    return new Pair<>(x, i);
                }
            }
            for (int y = min; y > i; y--) {
                if (!maze.getMap()[i][y]) {
                    return new Pair<>(i, y);
                }
            }
        }
        return null;
    }
}
