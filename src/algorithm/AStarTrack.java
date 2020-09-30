package algorithm;

import javafx.util.Pair;

import java.util.*;

import static algorithm.Mazes.searchBegin;
import static algorithm.Mazes.searchEnd;

/**
 * 使用A*算法求解迷宫最短路径
 */
public class AStarTrack {
    private RoomMaze maze;
    private int[][] direct = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private int beginX;
    private int beginY;
    private int endX;
    private int endY;
    private Pos[][] posMap;
    private LinkedList<Pair<Integer, Integer>> track = new LinkedList<>();

    private static class Pos implements Comparable<Pos> {
        private int x;
        private int y;
        private Pos pre;
        private int g;
        private int h;

        public Pos(int x, int y, Pos pre, int g, int h) {
            this.x = x;
            this.y = y;
            this.pre = pre;
            this.g = g;
            this.h = h;
        }

        @Override
        public int compareTo(Pos o) {
            return Double.compare(g + h, o.g + o.h);
        }
    }

    public static AStarTrack create(RoomMaze maze) {
        Pair<Integer, Integer> pairBegin = searchBegin(maze);
        Pair<Integer, Integer> pairEnd = searchEnd(maze);
        if (pairBegin == null || pairEnd == null) {
            throw new RuntimeException();
        }
        return create(maze, pairBegin.getKey(), pairBegin.getValue(), pairEnd.getKey(), pairEnd.getValue());
    }

    public static AStarTrack create(RoomMaze maze, int beginX, int beginY, int endX, int endY) {
        AStarTrack track = new AStarTrack();
        track.maze = maze;
        track.beginX = beginX;
        track.beginY = beginY;
        track.endX = endX;
        track.endY = endY;
        if (maze.getMap()[beginX][beginY]) {
            throw new RuntimeException(String.format("起点(%d, %d)不能通过", beginX, beginY));
        }
        if (maze.getMap()[endX][endY]) {
            throw new RuntimeException(String.format("终点(%d, %d)不能通过", endX, endY));
        }
        track.posMap = new Pos[maze.getM()][maze.getN()];
        track.search();
        return track;
    }

    private int computeH(int x, int y) {
        return Math.abs(x - endX) + Math.abs(y - endY);
    }

    public void search() {
        Set<Pos> closeSet = new HashSet<>();
        PriorityQueue<Pos> openQueue = new PriorityQueue<>();
        posMap[beginX][beginY] = new Pos(beginX, beginY, null, 0, computeH(beginX, beginY));
        openQueue.offer(posMap[beginX][beginY]);
        while (!openQueue.isEmpty()) {
            Pos pos = openQueue.poll();
            if (closeSet.contains(pos)) {
                continue;
            }
            closeSet.add(pos);
            if (pos.x == endX && pos.y == endY) {
                for (Pos p = pos; p != null; p = p.pre) {
                    track.addFirst(new Pair<>(p.x, p.y));
                }
                return;
            }
            for (int i = 0; i < 4; i++) {
                int newX = pos.x + direct[i][0];
                int newY = pos.y + direct[i][1];
                if (newX != 0 && newX != maze.getM() - 1 && newY != 0 && newY != maze.getN() - 1 && !maze.getMap()[newX][newY] && !closeSet.contains(posMap[newX][newY])) {
                    if (posMap[newX][newY] == null) {
                        posMap[newX][newY] = new Pos(newX, newY, pos, pos.g + 2, computeH(newX, newY));
                        openQueue.offer(posMap[newX][newY]);
                    } else {
                        Pos adjPos = posMap[newX][newY];
                        int newG = pos.g + 2;
                        if (newG < adjPos.g) {
                            adjPos.g = newG;
                            adjPos.pre = pos;
                            openQueue.offer(adjPos);
                        }
                    }
                }
            }
        }
    }

    public List<Pair<Integer, Integer>> getTrack() {
        return track;
    }

    public String drawTrack() {
        return Mazes.drawTrack(maze, track);
    }
}
