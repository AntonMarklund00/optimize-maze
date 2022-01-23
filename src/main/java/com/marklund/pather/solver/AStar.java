package com.marklund.pather.solver;

import com.marklund.pather.dao.Node;
import com.marklund.pather.maze.GenerateMaze;

public class AStar extends Djikstras{
    public AStar(GenerateMaze maze) {
        super(maze);
    }

    @Override
    protected int getHeuristics(Node start, Node end) {
        int x = Math.abs(start.getX() - end.getX());
        int y = Math.abs(start.getY() - end.getY());
        return x + y;
    }
}
