package com.marklund.pather;

import com.marklund.pather.maze.GenerateMaze;
import com.marklund.pather.solver.Breadthfirst;
import com.marklund.pather.support.FileHandler;


public class Main {
    public static void main(String[] args) {
        GenerateMaze maze = new GenerateMaze();
        FileHandler fileHandler = FileHandler.INSTANCE;

        // Generate a maze
        long startTimeMakeMaze = System.currentTimeMillis();
        maze.makeMaze(fileHandler.readImage("big-maze.png"));
        long endTimeMakeMaze = System.currentTimeMillis();
        System.out.println("Time to make maze: " + (endTimeMakeMaze - startTimeMakeMaze) + "ms");

        Breadthfirst solver = new Breadthfirst(maze);

        // Solve the maze
        long startTimeSolveMaze = System.currentTimeMillis();
        solver.solve();
        long endTimeSolveMaze = System.currentTimeMillis();
        System.out.println("Time to solve maze: " + (endTimeSolveMaze - startTimeSolveMaze) + "ms");

        // draw the maze
        FileHandler.INSTANCE.drawSolution(maze.getImage(), solver.getSolution());
    }
}
