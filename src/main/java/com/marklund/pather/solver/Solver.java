package com.marklund.pather.solver;

import com.marklund.pather.maze.GenerateMaze;
import com.marklund.pather.maze.MakeMazeMatrix;
import com.marklund.pather.maze.MazeMaker;

import java.util.List;

public abstract class Solver <T>{

    protected MazeMaker maze;
    protected T start;
    protected T end;
    protected T[] prev;

    public Solver(MazeMaker maze) {
        this.maze = maze;
    }

    abstract void solve();

    abstract List<T> getSolution();

    public MazeMaker getMaze() {
        return maze;
    }

    public void setMaze(MazeMaker maze) {
        this.maze = maze;
    }

    public T getStart() {
        return start;
    }

    public void setStart(T start) {
        this.start = start;
    }

    public T getEnd() {
        return end;
    }

    public void setEnd(T end) {
        this.end = end;
    }

    public T[] getPrev() {
        return prev;
    }

    public void setPrev(T[] prev) {
        this.prev = prev;
    }
}
