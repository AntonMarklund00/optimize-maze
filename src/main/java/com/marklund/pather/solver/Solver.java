package com.marklund.pather.solver;

import com.marklund.pather.maze.GenerateMaze;

import java.util.List;

public abstract class Solver <T>{

    protected GenerateMaze maze;
    protected T start;
    protected T end;
    protected T[] prev;

    public Solver(GenerateMaze maze) {
        this.maze = maze;
    }

    abstract void solve();

    abstract List<T> getSolution();


}
