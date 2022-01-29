package com.marklund.pather;

import com.marklund.pather.maze.GenerateMaze;
import com.marklund.pather.maze.MakeMazeMatrix;
import com.marklund.pather.solver.AStar;
import com.marklund.pather.solver.Breadthfirst;
import com.marklund.pather.solver.Djikstras;
import com.marklund.pather.solver.MatrixBreadthFirst;
import com.marklund.pather.support.FileHandler;

import java.awt.image.BufferedImage;


public class Main {
    public static void main(String[] args) {
        String fileName = "super-big-maze.png";
        FileHandler fileHandler = FileHandler.INSTANCE;
        BufferedImage image = fileHandler.readImage(fileName);
        GenerateMaze maze = new GenerateMaze(image);

        System.out.println("MAZE GENERATION");

        // Make a optimized maze
        long startTimeMakeMaze = System.currentTimeMillis();
        maze.makeMaze();
        long endTimeMakeMaze = System.currentTimeMillis();
        System.out.println("Time to make optimized maze: " + (endTimeMakeMaze - startTimeMakeMaze) + "ms");

        // Make a matrix of the maze
        long startTimeMakeMatrixMaze = System.currentTimeMillis();
        MakeMazeMatrix matrixMaze = new MakeMazeMatrix(fileHandler.readImage(fileName));
        Integer[][] matrix = matrixMaze.makeMaze();
        long endTimeMakeMatrixMaze = System.currentTimeMillis();
        System.out.println("Time to make matrix maze: " + (endTimeMakeMatrixMaze - startTimeMakeMatrixMaze) + "ms");


        System.out.println("---".repeat(20));

        System.out.println("BREADTH FIRST SEARCH OPTIMIZED");

        // Breadth first search optimized
        Breadthfirst solver = new Breadthfirst(maze);
        long startTimeSolveMaze = System.currentTimeMillis();
        solver.solve();
        long endTimeSolveMaze = System.currentTimeMillis();
        System.out.println("Time to solve maze: " + (endTimeSolveMaze - startTimeSolveMaze) + "ms");


        System.out.println("---".repeat(20));

        System.out.println("BREADTH FIRST SEARCH NORMAL");
        // make matrix maze

        MatrixBreadthFirst matrixSolver = new MatrixBreadthFirst(matrixMaze, matrix);
        long startTimeSolveMatrixMaze = System.currentTimeMillis();
        matrixSolver.solve();
        long endTimeSolveMatrixMaze = System.currentTimeMillis();
        System.out.println("Time to solve matrix maze: " + (endTimeSolveMatrixMaze - startTimeSolveMatrixMaze) + "ms");


        System.out.println("---".repeat(20));

        System.out.println("DJIKSTRA'S SEARCH OPTIMIZED");

        Djikstras djikstras = new Djikstras(maze);
        long startTimeDjikstras = System.currentTimeMillis();
        djikstras.solve();
        long endTimeDjikstras = System.currentTimeMillis();
        System.out.println("Time to solve Djikstras: " + (endTimeDjikstras - startTimeDjikstras) + "ms");

        System.out.println("---".repeat(20));

        System.out.println("A* SEARCH OPTIMIZED");
        AStar aStar = new AStar(maze);
        long startTimeAStar = System.currentTimeMillis();
        aStar.solve();
        long endTimeAStar = System.currentTimeMillis();
        System.out.println("Time to solve AStar: " + (endTimeAStar - startTimeAStar) + "ms");

        // draw the solution
        FileHandler.INSTANCE.drawSolution(image, solver.getSolution());
    }
}
