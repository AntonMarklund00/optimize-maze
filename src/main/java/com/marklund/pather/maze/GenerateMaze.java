package com.marklund.pather.maze;

import com.marklund.pather.dao.Node;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

public class GenerateMaze extends MazeMaker<Node, Node>{

    private BufferedImage image;
    private Node[] topNodes;
    boolean[] rowData;
    private int width;
    private int height;
    private Node start;
    private Node end;

    public GenerateMaze(BufferedImage image) {
        this.image = image;
        rowData = makeBooleanArrayBasedOnImageColors(image);
    }

    private boolean[] makeBooleanArrayBasedOnImageColors(BufferedImage image){
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
    public Node makeMaze() {
        setMazeSizeBasedOnImageSize(image);
        topNodes = listOfNodesAtFirstLine.apply(width, 0);

        findStartPosition();
        generateGraphFromMazeImage();
        findEndPosition();

        return start;
    }

    private BiFunction<Number, Number, Node[]> listOfNodesAtFirstLine = (number, row)-> {
        Node[] nodes = new Node[number.intValue()];
        for (int i = 0; i < number.intValue(); i++) {
            nodes[i] = new Node(row.intValue(),i);
        }
        return nodes;
    };

    private void findStartPosition(){
        for (int x = 1; x < width-1; x++) {
            if (rowData[x]) {
                start = new Node(0, x);
                topNodes[x] = start;
            }
        }
    }

    private void generateGraphFromMazeImage() {
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

                Node newNode = null;

                if (!currentNode)
                    continue;

                if (prevNode){ // Previous node was a path
                    if (next){ // If next node is path
                        if (isPathAboveOrBelow(rowAboveOffset+x, rowBelowOffset+x)){
                            newNode = new Node(y, x);
                            leftNode.setNeighbors(1, newNode);
                            newNode.setNeighbors(3, leftNode);
                            leftNode = newNode;
                        }
                    }else{ // if next node is wall (current node is end of corridor)
                        newNode = new Node(y, x);
                        leftNode.setNeighbors(1, newNode);
                        newNode.setNeighbors(3, leftNode);
                        leftNode = null;
                    }
                }else{
                    if (next){ // if next node is path
                        newNode = new Node(y, x);
                        leftNode = newNode;
                    }else {
                        if(isNotPathAboveOrBelow(rowAboveOffset+x, rowBelowOffset+x)){
                            newNode = new Node(y, x);
                        }
                    }
                }

                if (newNode != null){
                    if (rowData[rowAboveOffset+x]){ // Clear above
                        Node temp = topNodes[x];
                        temp.setNeighbors(2, newNode);
                        newNode.setNeighbors(0, temp);
                    }

                    if (rowData[rowBelowOffset+x]){ // Clear below
                        topNodes[x] = newNode;
                    } else
                        topNodes[x] = null;
                }
            }
        }
    }

    private boolean isPathAboveOrBelow(int rowAboveOffset, int rowBelowOffset) {
        return rowData[rowAboveOffset] || rowData[rowBelowOffset];
    }

    private boolean isNotPathAboveOrBelow(int rowAboveOffset, int rowBelowOffset) {
        return !rowData[rowAboveOffset] || !rowData[rowBelowOffset];
    }

    private void setMazeSizeBasedOnImageSize(BufferedImage image) {
        width = image.getWidth();
        height = image.getHeight();
    }

    private void findEndPosition(){
        int rowOffset = (height - 1) * width;
        for (int x = 0; x < width; x++) {
            if (rowData[rowOffset+x]) {
                end = new Node(height - 1, x);
                Node temp = topNodes[x];
                temp.setNeighbors(2, end);
                end.setNeighbors(0, temp);
                break;
            }
        }
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
    public Node getStart() {
        return start;
    }

    @Override
    public Node getEnd() {
        return end;
    }

    public BufferedImage getImage() {
        return image;
    }
}
