package com.marklund.pather.maze;

import com.marklund.pather.dao.Node;

import java.awt.image.BufferedImage;

public abstract class MazeMaker {
    protected final ColorTester colorTester = ColorTester.INSTANCE;

    public abstract Node makeMaze(BufferedImage image);

}
