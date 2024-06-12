package com.example.robominer.model;

import com.example.robominer.util.MineralType;

import java.util.*;

public class Graph {
    public Node[][] nodes;
    private int rows;
    private int cols;

    public Graph(Grid grid) {
        this.rows = grid.getRows();
        this.cols = grid.getCols();
        this.nodes = new Node[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                nodes[i][j] = new Node(i, j, grid.getSecteur(i, j));
            }
        }
    }

    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int row = node.getRow();
        int col = node.getCol();

        // Vérifier les cellules adjacentes
        if (row > 0 && nodes[row - 1][col].isWalkable()) {
            neighbors.add(nodes[row - 1][col]); // Cellule du haut
        }
        if (row < rows - 1 && nodes[row + 1][col].isWalkable()) {
            neighbors.add(nodes[row + 1][col]); // Cellule du bas
        }
        if (col > 0 && nodes[row][col - 1].isWalkable()) {
            neighbors.add(nodes[row][col - 1]); // Cellule de gauche
        }
        if (col < cols - 1 && nodes[row][col + 1].isWalkable()) {
            neighbors.add(nodes[row][col + 1]); // Cellule de droite
        }

        return neighbors;
    }

    public List<Node> dijkstra(Node source, Node target) {
        resetNodeDistances(); // Ensure all nodes are reset before running Dijkstra
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));
        source.setDistance(0);
        queue.offer(source);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current == target) {
                return reconstructPath(target);
            }

            for (Node neighbor : getNeighbors(current)) {
                int newDistance = current.getDistance() + 1;
                if (newDistance < neighbor.getDistance()) {
                    queue.remove(neighbor);
                    neighbor.setDistance(newDistance);
                    neighbor.setPreviousNode(current);
                    queue.offer(neighbor);
                }
            }
        }

        return Collections.emptyList();
    }

    public void resetNodeDistances() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                nodes[i][j].setDistance(Integer.MAX_VALUE);
                nodes[i][j].setPreviousNode(null);
            }
        }
    }

    private List<Node> reconstructPath(Node target) {
        List<Node> path = new ArrayList<>();
        Node current = target;
        while (current != null) {
            path.add(0, current);
            current = current.getPreviousNode();
        }
        return path;
    }

    public Node getNodeAt(int[] position) {
        return nodes[position[0]][position[1]];
    }

    public Node getClosestMineNode(Node node, MineralType mineralType) {
        Node closestMine = null;
        int closestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Node currentNode = nodes[i][j];
                if (currentNode.getSecteur() instanceof Mine && currentNode.getMineralType() == mineralType) {
                    int distance = dijkstra(node, currentNode).size();
                    if (distance < closestDistance) {
                        closestMine = currentNode;
                        closestDistance = distance;
                    }
                }
            }
        }
        return closestMine;
    }

    public Node getClosestWarehouseNode(Node startNode, MineralType mineralType) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));
        Set<Node> visited = new HashSet<>();
        startNode.setDistance(0);
        queue.offer(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            visited.add(currentNode);

            if (currentNode.getSecteur() instanceof Warehouse && ((Warehouse) currentNode.getSecteur()).getMineralType() == mineralType) {
                return currentNode;
            }

            for (Node neighbor : getNeighbors(currentNode)) {
                if (!visited.contains(neighbor)) {
                    int distance = currentNode.getDistance() + 1;
                    if (distance < neighbor.getDistance()) {
                        neighbor.setDistance(distance);
                        neighbor.setPreviousNode(currentNode);
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return null;
    }




}
