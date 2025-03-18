package com.sfusprint;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.sfusprint.Entity.Enemy;
import com.sfusprint.Entity.Player;
import com.sfusprint.Entity.Professor;
import com.sfusprint.Entity.SecurityGuard;

/**
 * Manages game logic, including player movement, enemy behavior,
 * game timer, and interactions with the game environment.
 */
public class GameLogic {
    private Player player;
    private GameMap gameMap;
    private List<Enemy> enemies;
    private boolean isPlayerStuck = false;
    private int timeRemaining = 60;
    private Timer gameTimer;
    private GamePanel gamePanel ;
    private int totalCoins;
    private boolean isGameRunning = true;

    /**
     * Initializes the game logic, sets up player and enemy positions,
     * starts the game timer, and initializes the game loop.
     *
     * @param gamePanel The game panel associated with the game logic.
     */
    public GameLogic(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.gameMap = new GameMap(gamePanel);
       // if(gamePanel.getGameManager().getCurrenState()==GameState.gameRunning){
        this.player = new Player(gameMap.getPlayerStart(), this);
        this.enemies = new ArrayList<>();
        


        // get enemy spawn positions from the game map
        List<Point> enemyPositions = gameMap.getEnemyStartPositions();
       // if(gamePanel.getGameManager().getCurrenState() ==GameState.gameRunning){
        // ensure first enemy is a professor, second is a security guard

            if (enemyPositions.size() >= 2) {
                enemies.add(new Professor(enemyPositions.get(0), this, gameMap, player));
                enemies.add(new SecurityGuard(enemyPositions.get(1), this, gameMap, player));
            }

        totalCoins = countTotalCoins(); // count total coins at game start
        
        startTimer();
        startGameLoop();
    }
//}
   // }

    public Player getPlayer() {
        return player;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Counts the total number of coins on the grid at game start.
     *
     * @return The total number of coins in the game.
     */
    private int countTotalCoins() {
        int count = 0;
        int[][] grid = gameMap.getGrid();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 2) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Starts separate game loops:
     * - One for enemy movement (always running).
     * - One for player movement (pausable when stuck).
     * - One for repainting (to ensure smooth rendering).
     */
    private void startGameLoop() {
        // enemy movement thread (always running)
       
        Thread enemyThread = new Thread(() -> {
            while (isGameRunning) {
                try {
                    if (player.getScore() < 0) {
                        isGameRunning = false;
                        gameTimer.stop();
                        JOptionPane.showMessageDialog(gamePanel, "You have negative score, you missed your bus!", "game over", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    }
                    Thread.sleep(500); // move enemies every second
                    moveEnemies();
                    checkIfPlayerCaught();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        enemyThread.setDaemon(true);
        enemyThread.start();

        // player movement thread (pauses when stuck)
        Thread playerThread = new Thread(() -> {
            while (isGameRunning) {
                try {
                    Thread.sleep(1000); // player movement checked frequently
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        playerThread.setDaemon(true);
        playerThread.start();

        // repaint thread (ensures screen updates even when the player is stuck)
        Thread repaintThread = new Thread(() -> {
            while (isGameRunning) {
                try {
                    Thread.sleep(100); // repaint every 50ms
                    gamePanel.repaint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });
        repaintThread.setDaemon(true);
        repaintThread.start();
    }

    /**
     * Starts the game timer, decreasing time remaining every second.
     * Stops the game if time runs out.
     */
    private void startTimer() {
        gameTimer = new Timer(1000, e -> {
            if (timeRemaining > 0) {
                if(gamePanel.getGameManager().getCurrenState()==GameState.gameRunning){
                timeRemaining--;
                }
            } 
            else {
                gameTimer.stop();
                System.out.println("time's up!");
                isGameRunning = false; // stop game when time runs out
            }
        });
        gameTimer.start();
    }

    /**
     * Moves all enemies based on their AI logic.
     */
    private void moveEnemies() {
        if(gamePanel.getGameManager().getCurrenState() ==GameState.gameRunning){
        for (Enemy enemy : enemies) {
            enemy.move();
        }
    }}

    /**
     * Handles player movement and interactions with the game environment.
     *
     * @param character The character attempting to move.
     * @param newPosition The new position the character is attempting to move to.
     */
    public void attemptMove(Player character, Point newPosition) {
        if (character instanceof Player && isPlayerStuck) {
            return;
        }

        if (gameMap.isWalkable(newPosition)) {
            int[][] grid = gameMap.getGrid();

            // player picks up a coin
            if (character instanceof Player && grid[newPosition.y][newPosition.x] == 2) {
                ((Player) character).increaseScore();
                grid[newPosition.y][newPosition.x] = 0;
                totalCoins--;
            }

            // player picks up the compass card (bonus +10 points)
            if (character instanceof Player && grid[newPosition.y][newPosition.x] == 4) {
                ((Player) character).increaseScore(10);
                grid[newPosition.y][newPosition.x] = 0;
            }

            // player reaches exit square (5) and has all coins
            if (character instanceof Player && grid[newPosition.y][newPosition.x] == 5) {
                checkIfAllCoinsCollected();
            }

            // player steps on a puddle
            if (character instanceof Player && grid[newPosition.y][newPosition.x] == 3) {
                freezePlayer(newPosition);
            }

            character.setPosition(newPosition);
        }
    }

    /**
     * Attempts to move an enemy to the specified position.
     * The enemy can move only if there is no wall at the new position.
     *
     * @param enemy      The enemy attempting to move.
     * @param newPosition The target position.
     */
    public void attemptMove(Enemy enemy, Point newPosition) {
        int[][] grid = gameMap.getGrid();

        // Check if the new position is a wall
        if (grid[newPosition.y][newPosition.x] != 1) { // 1 represents a wall
            enemy.setPosition(newPosition);
        }
    }  


    /**
     * Checks if the player has collected all coins before allowing exit.
     * Ends the game if all coins are collected.
     */
    private void checkIfAllCoinsCollected() {
        if (totalCoins == 0) {
            isGameRunning = false; // stop all threads
            gameTimer.stop();
            JOptionPane.showMessageDialog(gamePanel, "well done! you can get to your bus now!\nfinal score: " + player.getScore(), "game over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    /**
     * Checks if any enemy has caught the player.
     * Ends the game if an enemy reaches the player's position.
     */
    private void checkIfPlayerCaught() {
        for (Enemy enemy : enemies) {
            if (enemy.getPosition().equals(player.getPosition())) {
                isGameRunning = false; // stop all threads
                gameTimer.stop();
                System.out.println("game over!");
                gamePanel.showGameOverMessage(enemy);
            }
        }
    }

    /**
     * Freezes the player for 3 seconds when stepping on a puddle.
     * The player is unable to move during this time, but enemies continue moving.
     */
    private void freezePlayer(Point newPosition) {
        isPlayerStuck = true;
        int[][] grid = gameMap.getGrid();
        System.out.println("player is stuck in a puddle!");
        
        player.decreaseScore(1);

        // create a separate thread to unfreeze after 3 seconds
        new Thread(() -> {
            try {
                Thread.sleep(1500); // wait for 3 seconds
                grid[newPosition.y][newPosition.x] = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isPlayerStuck = false;
            System.out.println("player can move again.");
        }).start();
    }
    public GamePanel getGamePanel(){
        return gamePanel;
    }
}
