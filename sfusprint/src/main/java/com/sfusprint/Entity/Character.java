package com.sfusprint.Entity;

import java.awt.Point;

/**
 * Represents all moving entities in the game, including the player and enemies.
 * Each character has a position and a movement speed.
 */
public abstract class Character {
    protected Point position;
    protected int speed;
    

    /**
     * Constructs a character with a specified position and speed.
     *
     * @param position The starting position of the character.
     * @param speed    The speed at which the character moves.
     */
    public Character(Point position, int speed) {
        this.position = position;
        this.speed = speed;
    }

    /**
     * Retrieves the current position of the character.
     *
     * @return The character's current position as a Point.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets a new position for the character.
     *
     * @param position The new position to set.
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Retrieves the movement speed of the character.
     *
     * @return The character's speed.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets a new movement speed for the character.
     *
     * @param speed The new speed to set.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
