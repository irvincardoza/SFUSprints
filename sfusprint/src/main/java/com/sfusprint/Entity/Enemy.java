package com.sfusprint.Entity;

import java.awt.Point;
import java.util.*;

import com.sfusprint.GameLogic;
import com.sfusprint.GameMap;

/**
 * Represents an enemy in the game that chases the player.
 * Enemies use the A* pathfinding algorithm to determine their movement.
 */
public abstract class Enemy extends Character {
    protected GameLogic gameLogic;
    protected GameMap gameMap;
    protected Player player;

    /**
     * Constructs an enemy at the specified position.
     *
     * @param position  The initial position of the enemy.
     * @param gameLogic The game logic instance managing game events.
     * @param gameMap   The game map used for navigation.
     * @param player    The player that the enemy chases.
     */
    public Enemy(Point position, GameLogic gameLogic, GameMap gameMap, Player player) {
        super(position, 1); // moves 1 square per tick
        this.gameLogic = gameLogic;
        this.gameMap = gameMap;
        this.player = player;
    }

    /**
     * Moves the enemy toward the player using the A* pathfinding algorithm.
     */
    public void move() {
        List<Point> path = findPath(position, player.getPosition());

        if (!path.isEmpty()) {
            Point nextMove = path.get(0);
            gameLogic.attemptMove(this, nextMove);

            if (nextMove.equals(player.getPosition())) {
                System.out.println("Player caught by " + this.getClass().getSimpleName() + "!");
            }
        }
    }

    /**
     * Implements the A* pathfinding algorithm to find the shortest path to the player.
     *
     * @param start The enemy's current position.
     * @param goal  The target position (player's position).
     * @return A list of points representing the path to the goal.
     */
    private List<Point> findPath(Point start, Point goal) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Set<Point> closedList = new HashSet<>();

        openList.add(new Node(start, null, 0, heuristic(start, goal)));

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.position.equals(goal)) {
                return reconstructPath(current);
            }

            closedList.add(current.position);

            for (Point neighbor : getNeighbors(current.position)) {
                if (!closedList.contains(neighbor)) {
                    int g = current.g + 1;
                    int h = heuristic(neighbor, goal);
                    openList.add(new Node(neighbor, current, g, h));
                }
            }
        }

        return new ArrayList<>();
    }

    /**
     * Retrieves valid neighboring tiles that the enemy can move to.
     *
     * @param pos The current position of the enemy.
     * @return A list of valid neighbor positions.
     */
    private List<Point> getNeighbors(Point pos) {
        List<Point> neighbors = new ArrayList<>();
        int x = pos.x;
        int y = pos.y;

        Point[] moves = {
            new Point(x, y - 1), // up
            new Point(x, y + 1), // down
            new Point(x - 1, y), // left
            new Point(x + 1, y)  // right
        };

        for (Point move : moves) {
            if (gameMap.isWalkable(move)) {
                neighbors.add(move);
            }
        }
        return neighbors;
    }

    // /**
    //  * Calculates the heuristic (Manhattan distance) between two points.
    //  *
    //  * @param a The starting point.
    //  * @param b The target point.
    //  * @return The Manhattan distance between points a and b.
    //  */
    // private int heuristic(Point a, Point b) {
    //     return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    // }

    protected abstract int heuristic(Point a, Point b);

    /**
     * Reconstructs the path from the goal back to the start.
     *
     * @param node The goal node from which the path is traced back.
     * @return A list of points representing the path from start to goal.
     */
    private List<Point> reconstructPath(Node node) {
        List<Point> path = new ArrayList<>();
        while (node.parent != null) {
            path.add(0, node.position);
            node = node.parent;
        }
        return path;
    }

    /**
     * Represents a node used in the A* pathfinding algorithm.
     */
    private static class Node {
        Point position;
        Node parent;
        int g, f;

        /**
         * Constructs a node in the A* search tree.
         *
         * @param position The position of the node.
         * @param parent   The parent node in the path.
         * @param g        The cost from the start node to this node.
         * @param h        The heuristic estimate from this node to the goal.
         */
        Node(Point position, Node parent, int g, int h) {
            this.position = position;
            this.parent = parent;
            this.g = g;
            this.f = g + h;
        }
    }

    /**
     * Returns the character symbol representing the enemy.
     *
     * @return The character symbol of the enemy (e.g., 'S' for SecurityGuard, 'P' for Professor).
     */
    public abstract char getSymbol();
}
