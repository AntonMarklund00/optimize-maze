package com.marklund.pather.maze;

import com.marklund.pather.dao.Node;

import java.awt.image.BufferedImage;

public abstract class MazeMaker <T, E>{
    protected final ColorTester colorTester = ColorTester.INSTANCE;

    public abstract T makeMaze();

    public abstract E getStart();
    public abstract E getEnd();
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract BufferedImage getImage();


}
