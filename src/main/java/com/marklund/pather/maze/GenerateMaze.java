package com.marklund.pather.maze;

import com.marklund.pather.dao.Node;
import com.marklund.pather.support.FileHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

public class GenerateMaze extends MazeMaker {

    private BufferedImage image;
    private int width;
    private int height;
    private Node start;
    private Node end;

    private void drawInMaze(int y, int x){
        Color color = new Color(0, 100, 200);
        image.setRGB(x, y, color.getRGB());
    }

    private boolean[] makeIntMatrix(BufferedImage image){
        boolean[] matrix = new boolean[image.getHeight() * image.getWidth()];
        int counter = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++){
                matrix[counter++] = (colorTester.isWhite(new Color(image.getRGB(x, y))));
            }
        }
        return matrix;
    }


    @Override
    public Node makeMaze(BufferedImage image) {
        this.image = image;

        width = image.getWidth();
        height = image.getHeight();
        boolean[] rowData = makeIntMatrix(image);

        Node[] topNodes = listOfNodesAtFirstLine.apply(width, 0);

        int count = 0;


        for (int x = 1; x < width-1; x++) {
            if (rowData[x]) {
                start = new Node(0, x);
                topNodes[x] = start;
                count++;
                FileHandler.INSTANCE.drawInMaze(image, x, 0, 0);
            }
        }

        for(int y = 1; y < height-1; y++){

            int rowOffset = y * width;
            int rowAboveOffset = rowOffset - width;
            int rowBelowOffset = rowOffset + width;

            boolean prevNode;
            boolean currentNode = false;
            boolean next = rowData[rowOffset+1];

            Node leftNode = null;

            for (int x = 1; x < width-1; x++) {
                prevNode = currentNode;
                currentNode = next;
                next = rowData[rowOffset+x+1];

                Node node = null;

                if (!currentNode)
                    continue;

                if (prevNode){
                    if (next){
                        if (rowData[rowAboveOffset+x] || rowData[rowBelowOffset+x]){
                            node = new Node(y, x);
                            leftNode.setNeighbors(1, node);
                            node.setNeighbors(3, leftNode);
                            leftNode = node;
                        }
                    }else{
                        node = new Node(y, x);
                        leftNode.setNeighbors(1, node);
                        node.setNeighbors(3, leftNode);
                        leftNode = null;
                    }
                }else{
                    if (next){
                        node = new Node(y, x);
                        leftNode = node;
                    }else{
                        if (!rowData[rowAboveOffset+x] || !rowData[rowBelowOffset+x]){
                            node = new Node(y, x);
                        }
                    }
                }

                if (node != null){
                    if (rowData[rowAboveOffset+x]){
                        Node temp = topNodes[x];
                        temp.setNeighbors(2, node);
                        node.setNeighbors(0, temp);
                    }
                    if (rowData[rowBelowOffset+x]){
                        topNodes[x] = node;
                    } else
                        topNodes[x] = null;

                    count++;
                    FileHandler.INSTANCE.drawInMaze(image, x, y, 0);
                }
            }
        }
        int rowoffset = (height - 1) * width;
        for (int x = 0; x < width; x++) {
            if (rowData[rowoffset+x]) {
                end = new Node(height - 1, x);
                Node temp = topNodes[x];
                temp.setNeighbors(2, end);
                end.setNeighbors(0, temp);
                FileHandler.INSTANCE.drawInMaze(image, x, end.getY(), 0);
                count++;
                break;
            }
        }
        FileHandler.INSTANCE.saveImage(image, "mazeNode.png");

        return start;
    }


    private BiFunction<Number, Number, Node[]> listOfNodesAtFirstLine = (number, row)-> {
        Node[] nodes = new Node[number.intValue()];
        for (int i = 0; i < number.intValue(); i++) {
            nodes[i] = new Node(row.intValue(),i);
        }
        return nodes;
    };

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public BufferedImage getImage() {
        return image;
    }
}
