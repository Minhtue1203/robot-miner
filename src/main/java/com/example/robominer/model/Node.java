package com.example.robominer.model;

class Node implements Comparable<Node> {
    int x, y, dist;

    Node(int x, int y, int dist) {
        this.x = x;
        this.y = y;
        this.dist = dist;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.dist, other.dist);
    }
}
