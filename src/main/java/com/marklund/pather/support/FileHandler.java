package com.marklund.pather.support;


import com.marklund.pather.dao.Node;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileHandler {
    public final static FileHandler INSTANCE;

    static {
        INSTANCE = new FileHandler();
    }

    private FileHandler(){}

    public BufferedImage readImage(String fileName) {
        try {
            return ImageIO.read(
                    new File("_Resources/" + fileName)
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveImage(BufferedImage image, String fileName) {
        try {
            ImageIO.write(image, "png", new File("_Resources/" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage drawInMaze(BufferedImage image, int x, int y, int red){
        Color color = new Color(red, 100, 200);
        image.setRGB(x, y, color.getRGB());
        return image;
    }

    public void drawSolution(BufferedImage image, List<Node> solution){
        for (int i = 0; i < solution.size(); i++) {
            Node current = solution.get(i);
            if (i+1 >= solution.size())
                continue;

            Node next = solution.get(i + 1);
            if (current.getY() == next.getY()) {
                int xMin = Math.min(current.getX(), next.getX());
                int xMax = Math.max(current.getX(), next.getX());
                for (int j = xMin; j < xMax; j++){
                    drawInMaze(image, j, current.getY(), 100);
                }
            }else if (current.getX() == next.getX()) {
                int yMin = Math.min(current.getY(), next.getY());
                int yMax = Math.max(current.getY(), next.getY())+1;
                for (int j = yMin; j < yMax; j++){
                    drawInMaze(image, current.getX(), j, 100);
                }
            }
        }
        saveImage(image, "solution.png");
    }
}
