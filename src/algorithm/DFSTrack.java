package algorithm;

import javafx.util.Pair;

import java.util.*;

import static algorithm.Mazes.searchBegin;
import static algorithm.Mazes.searchEnd;

/**
 * 使用dfs找到迷宫中所有路径
 */
public class DFSTrack {
    private RoomMaze maze;
    private int beginX;
    private int beginY;
    private int endX;
    private int endY;
    private boolean[][] visited;
    private Set<List<Pair<Integer, Integer>>> trackSet = new HashSet<>();
    private int[][] direct = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    private static class Pos {
        private int x;
        private int y;
        private int d;

        public Pos(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }

    private Pos nextPos(Pos cur) {
        for (int dir = cur.d; dir < 4; dir++) {
            int newX = cur.x + direct[dir][0];
            int newY = cur.y + direct[dir][1];
            if (newX != 0 && newX != maze.getM() - 1 && newY != 0 && newY != maze.getN() - 1 && !maze.getMap()[newX][newY] && !visited[newX][newY]) {
                cur.d = dir + 1;
                return new Pos(newX, newY, 0);
            }
        }
        cur.d = 4;
        return null;
    }

    private void dfs() {
        Deque<Pos> stack = new ArrayDeque<>();
        stack.push(new Pos(beginX, beginY, 0));
        visited[beginX][beginY] = true;
        while (!stack.isEmpty()) {
            Pos pos = stack.peekLast();
            pos = nextPos(pos);
            while (pos != null) {
                visited[pos.x][pos.y] = true;
                stack.addLast(pos);
                if (pos.x == endX && pos.y == endY) {
                    List<Pair<Integer, Integer>> track = new LinkedList<>();
                    for (Pos p : stack) {
                        track.add(new Pair<>(p.x, p.y));
                    }
                    trackSet.add(track);
                    break;
                }
                pos = nextPos(pos);
            }
            pos = stack.pollLast();
            visited[pos.x][pos.y] = false;
        }
    }

    public static DFSTrack create(RoomMaze maze) {
        Pair<Integer, Integer> pairBegin = searchBegin(maze);
        Pair<Integer, Integer> pairEnd = searchEnd(maze);
        if (pairBegin == null || pairEnd == null) {
            throw new RuntimeException();
        }
        return create(maze, pairBegin.getKey(), pairBegin.getValue(), pairEnd.getKey(), pairEnd.getValue());
    }

    public static DFSTrack create(RoomMaze maze, int beginX, int beginY, int endX, int endY) {
        DFSTrack track = new DFSTrack();
        track.maze = maze;
        track.beginX = beginX;
        track.beginY = beginY;
        track.endX = endX;
        track.endY = endY;
        if (maze.getMap()[beginX][beginY] || maze.getMap()[endX][endY]) {
            throw new RuntimeException();
        }
        track.visited = new boolean[maze.getM()][maze.getN()];
        for (int x = 0; x < maze.getM(); x++) {
            for (int y = 0; y < maze.getN(); y++) {
                track.visited[x][y] = false;
            }
        }
        track.dfs();
        return track;
    }

    public Set<List<Pair<Integer, Integer>>> getTracks() {
        return trackSet;
    }

    public List<String> drawTracks() {
        List<String> list = new LinkedList<>();
        for (List<Pair<Integer, Integer>> track : trackSet) {
            list.add(Mazes.drawTrack(maze, track));
        }
        return list;
    }
}
