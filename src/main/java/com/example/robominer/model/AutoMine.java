package com.example.robominer.model;

import com.example.robominer.util.MineralType;

import java.util.*;

public class AutoMine {
    private static final int[] DX = {-1, 1, 0, 0};
    private static final int[] DY = {0, 0, -1, 1};
    private static final int SIZE = 10;
    private static final int INF = Integer.MAX_VALUE;

    private Grid grid;
    private int[][] dist;
    private boolean[][] visited;
    private int[][] predecessor;

    public AutoMine(Grid grid) {
        this.grid = grid;
        initialize();
    }

    private void initialize() {
        dist = new int[SIZE][SIZE];
        visited = new boolean[SIZE][SIZE];
        predecessor = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(dist[i], INF);
            Arrays.fill(predecessor[i], -1);
        }
    }

//    private boolean isValid(int x, int y) {
//        return x >= 0 && x < SIZE && y >= 0 && y < SIZE && grid[x][y] != 'x';
//    }

    private void dijkstra(int startX, int startY) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(startX, startY, 0));
        dist[startX][startY] = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int x = current.x;
            int y = current.y;

            if (visited[x][y]) continue;
            visited[x][y] = true;

            for (int i = 0; i < 4; i++) {
                int newX = x + DX[i];
                int newY = y + DY[i];
                if (grid.isPositionValid(newX, newY)) {
                    Secteur target = grid.getSecteur(newX, newY);
                    if(!(target instanceof Water)) {
                        int newDist = dist[x][y] + 1; // Assuming each move costs 1
                        if (newDist < dist[newX][newY]) {
                            dist[newX][newY] = newDist;
                            predecessor[newX][newY] = x * SIZE + y; // Store predecessor as a single integer
                            pq.add(new Node(newX, newY, newDist));
                        }
                    }
                }
            }
        }
    }

    private List<int[]> reconstructPath(int startX, int startY, int endX, int endY) {
        List<int[]> path = new ArrayList<>();
        if (dist[endX][endY] == INF) return path; // No path found

        for (int at = endX * SIZE + endY; at != -1; at = predecessor[at / SIZE][at % SIZE]) {
            int x = at / SIZE;
            int y = at % SIZE;
            path.add(new int[]{x, y});
        }
        Collections.reverse(path);
        return path;
    }

    public List<int[]> findPathToNearestMine(Robot robot) {
        initialize();
        int[] currentPos = robot.getCurrentPosition();
        int startX = currentPos[0];
        int startY = currentPos[1];
        MineralType type = robot.getMineralType();
        dijkstra(startX, startY);

        int minDist = INF;
        int mineX = -1, mineY = -1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Secteur target = grid.getSecteur(i, j);
                if (dist[i][j] < minDist) {
                    if (robot.isFinding() && target instanceof Mine) { // finding mine
                        Mine mine = (Mine) target;
                        if (robot.getMineralType() == mine.getMineralType() && mine.getQuantity() > 0) {
                            minDist = dist[i][j];
                            mineX = i;
                            mineY = j;
                        }
                    } else if (robot.isDepositing() && target instanceof Warehouse) {
                        Warehouse warehouse = (Warehouse) target;
                        if (robot.getMineralType() == warehouse.getMineralType()) {
                            minDist = dist[i][j];
                            mineX = i;
                            mineY = j;
                        }
                    }
                }
            }
        }

        if (mineX == -1) return new ArrayList<>(); // No mine found
        return reconstructPath(startX, startY, mineX, mineY);
    }
}
