package com.marklund.pather.maze;

import com.marklund.pather.dao.Node;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MakeMazeMatrix extends MazeMaker<Integer[][], Integer[]>{

    private BufferedImage image;
    private int width;
    private int height;
    private Integer[] start;
    private Integer[] end;

    @Override
    public Integer[][] makeMaze(BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
        this.image = image;
        Integer[][] maze = new Integer[width][height];
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                if (y == 0 && ColorTester.INSTANCE.isWhite(new Color(image.getRGB(x,y)))){
                    start = new Integer[]{y,x};
                }else if (y == height-1 && ColorTester.INSTANCE.isWhite(new Color(image.getRGB(x,y)))){
                    end = new Integer[]{y,x};
                }
                maze[y][x] = (ColorTester.INSTANCE.isWhite(new Color(image.getRGB(x,y))))? 1 : 0;
            }
        }
        return maze;
    }

    @Override
    public Integer[] getStart() {
        return start;
    }

    @Override
    public Integer[] getEnd() {
        return end;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

}
