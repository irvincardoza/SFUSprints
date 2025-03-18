package com.sfusprint.Entity;

import java.awt.Point;

import com.sfusprint.GameLogic;
import com.sfusprint.GameMap;

/**
 * Represents a SecurityGuard enemy that actively chases the player.
 */
public class SecurityGuard extends Enemy {

    /**
     * Constructs a SecurityGuard enemy at the specified position.
     *
     * @param position  The initial position of the SecurityGuard.
     * @param gameLogic The game logic instance managing the game state.
     * @param gameMap   The game map the enemy navigates.
     * @param player    The player the SecurityGuard is chasing.
     */
    public SecurityGuard(Point position, GameLogic gameLogic, GameMap gameMap, Player player) {
        super(position, gameLogic, gameMap, player);
    }

    /**
     * Returns the character symbol representing the SecurityGuard.
     *
     * @return The character 'S' representing a SecurityGuard.
     */
    @Override
    public char getSymbol() {
        return 'S';
    }

    @Override
    /**
     * Calculates the heuristic (Manhattan distance) between two points.
     *
     * @param a The starting point.
     * @param b The target point.
     * @return The Manhattan distance between points a and b.
     */
    protected int heuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}
