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
        this.image = image;
        setMazeSizeBasedOnImage(this.image);
        Integer[][] maze = new Integer[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isStart(y, x)) {
                    start = new Integer[]{y,x};
                }

                if (isEnd(y,x)){
                    end = new Integer[]{y,x};
                }

                maze[y][x] = (ColorTester.INSTANCE.isWhite(new Color(image.getRGB(x,y))))? 1 : 0;
            }
        }
        return maze;
    }

    private void setMazeSizeBasedOnImage(BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
    }

    private boolean isStart(int y, int x) {
       return (y == 0 && ColorTester.INSTANCE.isWhite(new Color(image.getRGB(x,y))));
    }

    private boolean isEnd(int y, int x) {
        return (y == height-1 && ColorTester.INSTANCE.isWhite(new Color(image.getRGB(x,y))));
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
