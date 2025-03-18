package com.sfusprint;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents the game map, including walls, coins, puddles, and compass card.
 * Handles player and enemy spawn locations and grid interactions.
 */
public class GameMap {
    private int[][] grid;
    private static final int COIN = 2;
    private static final int PUDDLE = 3;
    private static final int COMPASS = 4;
    private static final int EMPTY = 0;
    private Point playerStart;
    private List<Point> enemyStartPositions = new ArrayList<>();

    /**
     * Initializes the game map with predefined layout, 
     * places the compass card, and sets spawn locations.
     */
    public GameMap(GamePanel panel) {
        this.grid = new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 1, 0, 2, 0, 0, 0, 0, 0, 1, 0, 5, 1},
            {1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1},
            {1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 3, 1, 1, 1, 1, 1, 0, 1, 1, 0, 2, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 2, 0, 0, 1},
            {1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1},
            {1, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 3, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 1, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1},
            {1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 0, 1, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 2, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
            {1, 3, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        
        placeCompassCard(panel);
        setPlayerStart();
        setEnemyStartPositions();
    }

    /**
     * Randomly places the compass card (`4`) in an empty (`0`) tile.
     */
    private void placeCompassCard(GamePanel panel) {
        List<int[]> emptyTiles = new ArrayList<>();
        Random random = new Random();

        // find all empty tiles (0) in the grid
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == EMPTY) {
                    emptyTiles.add(new int[]{y, x});
                }
            }
        }

        // randomly select a tile for the compass
        if (!emptyTiles.isEmpty()) {
            int[] selectedTile = emptyTiles.get(random.nextInt(emptyTiles.size()));
            // grid[selectedTile[0]][selectedTile[1]] = COMPASS;
            int row = selectedTile[0];
            int col = selectedTile[1];

            grid[row][col] = COMPASS; 
            
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
           
            scheduler.schedule(() -> {
                if(panel.getGameManager().getCurrenState()==GameState.gameRunning){
            if (grid[row][col] == COMPASS) {
                grid[row][col] = EMPTY; // Remove compass from grid
            }
            
            scheduler.shutdown(); // Stop the scheduler after execution
        }}, 10, TimeUnit.SECONDS); // Change "10" to the desired delay in seconds
        }
}

    /**
     * Sets the player's starting position in the bottom-left corner.
     */
    private void setPlayerStart() {
        playerStart = new Point(1, grid.length - 2);
    }

    /**
     * Sets enemy start positions in random empty locations.
     */
    private void setEnemyStartPositions() {
        List<int[]> emptyTiles = new ArrayList<>();
        Random random = new Random();

        for (int y = 1; y < grid.length - 1; y++) {
            for (int x = 1; x < grid[y].length - 1; x++) {
                if (grid[y][x] == EMPTY) {
                    emptyTiles.add(new int[]{y, x});
                }
            }
        }

        for (int i = 0; i < 2 && !emptyTiles.isEmpty(); i++) { // spawn two enemies
            int[] selectedTile = emptyTiles.remove(random.nextInt(emptyTiles.size()));
            enemyStartPositions.add(new Point(selectedTile[1], selectedTile[0]));
        }
    }

    /**
     * Gets the player's starting position.
     *
     * @return The player's start position as a Point.
     */
    public Point getPlayerStart() {
        return playerStart;
    }

    /**
     * Gets the enemy starting positions.
     *
     * @return A list of enemy start positions as Points.
     */
    public List<Point> getEnemyStartPositions() {
        return enemyStartPositions;
    }

    /**
     * Returns the game grid.
     *
     * @return The 2D array representing the game map.
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * Checks if a given point is walkable.
     * Walkable tiles include empty spaces, coins, compass, puddles, and exit (`5`).
     *
     * @param point The point to check.
     * @return True if the tile is walkable, false otherwise.
     */
    public boolean isWalkable(Point point) {
        int x = point.x;
        int y = point.y;
        return grid[y][x] == EMPTY || grid[y][x] == COIN || grid[y][x] == COMPASS || grid[y][x] == PUDDLE || grid[y][x] == 5;
    }
}
