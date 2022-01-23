package com.marklund.pather.solver;

import com.marklund.pather.dao.Node;
import com.marklund.pather.maze.GenerateMaze;
import com.marklund.pather.support.MyMinHeap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Djikstras extends Solver<Node> {

    public Djikstras(GenerateMaze maze) {
        super(maze);
    }

    @Override
    public void solve() {
        start = (Node) maze.getStart();
        end = (Node) maze.getEnd();
        int width = maze.getWidth();
        int height = maze.getHeight();

        Boolean[] visited = new Boolean[width * height];
        Arrays.fill(visited, false);

        prev = new Node[width * height];
        Arrays.fill(prev, null);

        MyMinHeap<Node> heap = new MyMinHeap<Node>(width * height);
        start.setDistance(0);
        start.setHeuristic(getHeuristics(start, end));
        heap.insert(start, start.getDistance() + start.getHeuristic());

        int count = 0;

        while (!heap.isEmpty()) {
            Node current = heap.remove();
            count++;
            if (current.equals(end)) {
                System.out.println("Found end in " + count + " steps");
                break;
            }
            for (Node neighbor : current.getNeighbors()) {
                if(neighbor == null) continue;

                int distanceFromStart;
                if (neighbor.getX() == current.getX()) distanceFromStart = (current.getDistance() + Math.abs(neighbor.getY() - current.getY()));
                else distanceFromStart = (current.getDistance() + Math.abs(neighbor.getX() - current.getX()));

                if (!visited[neighbor.getY() * width + neighbor.getX()] || distanceFromStart < neighbor.getDistance()) {
                    neighbor.setHeuristic(getHeuristics(neighbor, end));
                    neighbor.setDistance(distanceFromStart);
                    prev[neighbor.getY() * width + neighbor.getX()] = current;
                    visited[neighbor.getY() * width + neighbor.getX()] = true;
                    heap.insert(neighbor, neighbor.getDistance() + neighbor.getHeuristic());
                }
            }
        }
    }

    protected int getHeuristics(Node start, Node end) {
        return 0;
    }

    @Override
    public List<Node> getSolution() {
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
