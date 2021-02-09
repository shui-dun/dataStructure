package com.sd.algorithm;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.sd.algorithm.Mazes.searchBegin;
import static com.sd.algorithm.Mazes.searchEnd;

/**
 * 使用bfs找到迷宫最短路径
 */
public class BFSTrack {
    private RoomMaze maze;
    private int[][] direct = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private int beginX;
    private int beginY;
    private int endX;
    private int endY;
    private boolean[][] visited;
    private LinkedList<Pair<Integer, Integer>> track = new LinkedList<>();

    private static class Pos {
        int x;
        int y;
        Pos pre;

        public Pos(int x, int y, Pos pre) {
            this.x = x;
            this.y = y;
            this.pre = pre;
        }
    }

    private void bfs() {
        Queue<Pos> queue = new LinkedList<>();
        queue.offer(new Pos(beginX, beginY, null));
        visited[beginX][beginY] = true;
        while (!queue.isEmpty()) {
            Pos pos = queue.poll();
            for (int i = 0; i < 4; i++) {
                int newX = pos.x + direct[i][0];
                int newY = pos.y + direct[i][1];
                if (newX != 0 && newX != maze.getM() - 1 && newY != 0 && newY != maze.getN() - 1 && !maze.getMap()[newX][newY] && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    Pos newPos = new Pos(newX, newY, pos);
                    queue.offer(newPos);
                    if (newX == endX && newY == endY) {
                        for (Pos p = newPos; p != null; p = p.pre) {
                            track.addFirst(new Pair<>(p.x, p.y));
                        }
                        return;
                    }
                }
            }
        }
    }

    public static BFSTrack create(RoomMaze maze) {
        Pair<Integer, Integer> pairBegin = searchBegin(maze);
        Pair<Integer, Integer> pairEnd = searchEnd(maze);
        if (pairBegin == null || pairEnd == null) {
            throw new RuntimeException();
        }
        return create(maze, pairBegin.getKey(), pairBegin.getValue(), pairEnd.getKey(), pairEnd.getValue());
    }

    public static BFSTrack create(RoomMaze maze, int beginX, int beginY, int endX, int endY) {
        BFSTrack track = new BFSTrack();
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
        track.visited = new boolean[maze.getM()][maze.getN()];
        for (int x = 0; x < maze.getM(); x++) {
            for (int y = 0; y < maze.getN(); y++) {
                track.visited[x][y] = false;
            }
        }
        track.bfs();
        return track;
    }

    public List<Pair<Integer, Integer>> getTrack() {
        return track;
    }

    public String drawTrack() {
        return Mazes.drawTrack(maze, track);
    }
}
