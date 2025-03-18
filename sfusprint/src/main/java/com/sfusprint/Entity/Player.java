package com.sfusprint.Entity;

import java.awt.Point;

import com.sfusprint.GameLogic;

/**
 * Represents the player character controlled by the user.
 * The player can move, collect coins, and interact with the game environment.
 */
public class Player extends Character {
    private GameLogic gameLogic;
    private int score = 0;
    private boolean up, down, left, right, idle;

    /**
     * Constructs a Player at the specified starting position.
     *
     * @param position  The initial position of the player.
     * @param gameLogic The game logic instance managing the game state.
     */
    public Player(Point position, GameLogic gameLogic) {
        super(position, 1); // moves 1 square at a time
        this.gameLogic = gameLogic;
    }

    /**
     * Attempts to move the player in a given direction.
     *
     * @param direction The movement direction as a Point (x, y).
     */
    public void move(Point direction) {

        idle = true;
        Point newPosition = new Point(position.x + direction.x, position.y + direction.y);
        gameLogic.attemptMove(this, newPosition);
    }

    /**
     * Increases the player's score by 1 when they collect a coin.
     */
    public void increaseScore() {
        score++;
    }

    /**
     * Increases the player's score by a specified amount.
     *
     * @param score The amount to add to the player's score.
     */
    public void increaseScore(int score) {
        this.score += score;
    }
    public void decreaseScore(int score) {
        this.score -= score;
    }

    /**
     * Retrieves the player's current score.
     *
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }
    
    public void setUp(boolean direction) {
        up = direction;
    }

    public void setDown(boolean direction) {
        down = direction;
    }

    public void setLeft(boolean direction) {
        left = direction;
    }

    public void setRight(boolean direction) {
        right = direction;
    }

    public boolean isIdle() {
        return idle;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }
    public GameLogic getGameLogic(){
        return gameLogic;
    }

}
