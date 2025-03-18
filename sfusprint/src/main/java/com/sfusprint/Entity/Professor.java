package com.sfusprint.Entity;

import java.awt.Point;

import com.sfusprint.GameLogic;
import com.sfusprint.GameMap;

/**
 * Represents a Professor enemy that actively chases the player.
 */
public class Professor extends Enemy {

    /**
     * Constructs a Professor enemy at the specified position.
     *
     * @param position  The initial position of the Professor.
     * @param gameLogic The game logic instance managing the game state.
     * @param gameMap   The game map the enemy navigates.
     * @param player    The player the Professor is chasing.
     */
    public Professor(Point position, GameLogic gameLogic, GameMap gameMap, Player player) {
        super(position, gameLogic, gameMap, player);
    }

    /**
     * Returns the character symbol representing the Professor.
     *
     * @return The character 'P' representing a Professor.
     */
    @Override
    public char getSymbol() {
        return 'P';
    }

    @Override
    /**
     * Calculates the heuristic (Euclidean distance) between two points.
     *
     * @param a The starting point.
     * @param b The target point.
     * @return The Euclidean distance between points a and b.
     */
    protected int heuristic(Point a, Point b) {
        return (int) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }
}
