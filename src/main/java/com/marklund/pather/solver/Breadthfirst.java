package com.marklund.pather.solver;

import com.marklund.pather.dao.Node;
import com.marklund.pather.maze.GenerateMaze;
import java.util.*;

public class Breadthfirst extends Solver<Node> {
    private GenerateMaze maze;
    private Node start;
    private Node end;
    private Node[] prev;

    public Breadthfirst(GenerateMaze maze){
        this.maze = maze;
    }

    @Override
    public void solve(){
        start = maze.getStart();
        end = maze.getEnd();
        int width = maze.getWidth();

        Queue<Node> queue = new LinkedList();
        queue.add(start);
        int[] shape = new int[2];
        shape[0] = width;
        shape[1] = maze.getHeight();

        prev = new Node[shape[0] * shape[1]];
        Arrays.fill(prev, null);
        Boolean[] visited = new Boolean[shape[0] * shape[1]];
        Arrays.fill(visited, false);

        int count = 0;

        visited[start.getY() * width + start.getX()] = true;

        while (!queue.isEmpty()){
            count++;
            Node current = queue.remove();

            if (current.getX() == end.getX() && current.getY() == end.getY()){
                System.out.println("Found end in " + count + " steps");
                getSolution();
                break;
            }

            for (Node neighbor : current.getNeighbors()){
                if (neighbor != null){
                    int nodePosition = neighbor.getY() * width + neighbor.getX();
                    if (!visited[nodePosition]){
                        queue.add(neighbor);
                        visited[nodePosition] = true;
                        prev[nodePosition] = current;
                    }
                }
            }
        }
    }

    @Override
    public List<Node> getSolution(){
        ArrayList<Node> solution = new ArrayList<>();
        Node current = end;
        while (current != null){
            solution.add(current);
            current = prev[current.getY() * maze.getWidth() + current.getX()];
            if (current == start){
                solution.add(current);
                break;
            }
        }
        return solution;
    }
}
