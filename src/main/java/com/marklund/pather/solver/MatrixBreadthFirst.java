package com.marklund.pather.solver;

import com.marklund.pather.maze.MakeMazeMatrix;
import com.marklund.pather.support.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class MatrixBreadthFirst extends Solver<Integer[]> {

    private Integer[][] matrix;
    private List<Integer[]> solution = new ArrayList<>();
    public MatrixBreadthFirst(MakeMazeMatrix maze, Integer[][] matrix) {
        super(maze);
        this.matrix = matrix;
    }

    @Override
    public void solve() {
        int width = this.maze.getWidth();
        int height = this.maze.getHeight();
        Queue<Integer[]> queue = new java.util.LinkedList<>();
        Integer[] start = (Integer[]) this.maze.getStart();
        Integer[] end = (Integer[]) this.maze.getEnd();
        queue.add(start);
        boolean[][] visited = new boolean[width][height];
        visited[start[0]][start[1]] = true;
        int[][] directions = Constants.CORNERS_INCLUDED;

        int count = 0;
        while (!queue.isEmpty()) {
            Integer[] current = queue.remove();
            count++;

            if (Objects.equals(current[0], end[0]) && Objects.equals(current[1], end[1])) {
                System.out.println("Found End in " + count + "steps");
                break;
            }

            for (int[] direction : directions) {
                int y = current[0] + direction[0];
                int x = current[1] + direction[1];
                if (y < 0 || x < 0 || y >= height || x >= width || visited[y][x] || this.matrix[y][x] == 0) {
                    continue;
                }
                visited[y][x] = true;
                queue.add(new Integer[]{y, x});
            }

        }
    }

    @Override
    List<Integer[]> getSolution() {
        return null;
    }
}
