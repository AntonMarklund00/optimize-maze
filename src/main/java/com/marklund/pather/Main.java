package com.marklund.pather;

import com.marklund.pather.maze.GenerateMaze;
import com.marklund.pather.maze.MakeMazeMatrix;
import com.marklund.pather.solver.Breadthfirst;
import com.marklund.pather.solver.MatrixBreadthFirst;
import com.marklund.pather.support.FileHandler;

import java.awt.image.BufferedImage;


public class Main {
    public static void main(String[] args) {
        GenerateMaze maze = new GenerateMaze();
        FileHandler fileHandler = FileHandler.INSTANCE;

        // Generate a maze
        BufferedImage image = fileHandler.readImage("maze.png");
        long startTimeMakeMaze = System.currentTimeMillis();
        maze.makeMaze(image);
        long endTimeMakeMaze = System.currentTimeMillis();
        System.out.println("Time to make maze: " + (endTimeMakeMaze - startTimeMakeMaze) + "ms");

        Breadthfirst solver = new Breadthfirst(maze);

        // Solve the maze
        long startTimeSolveMaze = System.currentTimeMillis();
        solver.solve();
        long endTimeSolveMaze = System.currentTimeMillis();
        System.out.println("Time to solve maze: " + (endTimeSolveMaze - startTimeSolveMaze) + "ms");

        // draw the maze
        FileHandler.INSTANCE.drawSolution(image, solver.getSolution());

        System.out.println("---".repeat(20));

        // make matrix maze
        long startTimeMakeMatrixMaze = System.currentTimeMillis();
        MakeMazeMatrix matrixMaze = new MakeMazeMatrix();
        Integer[][] matrix = matrixMaze.makeMaze(fileHandler.readImage("maze.png"));
        long endTimeMakeMatrixMaze = System.currentTimeMillis();
        System.out.println("Time to make matrix maze: " + (endTimeMakeMatrixMaze - startTimeMakeMatrixMaze) + "ms");

        MatrixBreadthFirst matrixSolver = new MatrixBreadthFirst(matrixMaze, matrix);
        long startTimeSolveMatrixMaze = System.currentTimeMillis();
        matrixSolver.solve();
        long endTimeSolveMatrixMaze = System.currentTimeMillis();
        System.out.println("Time to solve matrix maze: " + (endTimeSolveMatrixMaze - startTimeSolveMatrixMaze) + "ms");
    }
}
