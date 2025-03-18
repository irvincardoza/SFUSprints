package com.sfusprint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sfusprint.Entity.Enemy;
import com.sfusprint.Entity.Player;
import com.sfusprint.Entity.Professor;
import com.sfusprint.Entity.SecurityGuard;

/**
 * The GamePanel class represents the visual representation of the game.
 * It handles rendering the game grid, player, enemies, and UI elements.
 */
public class GamePanel extends JPanel {
    private GameLogic gameLogic;
    private Player player;
    private BufferedImage instructionPg;
    private BufferedImage playerIdle, playerUp, playerLeft, playerRight;
    private BufferedImage professorSprite;
    private BufferedImage securityGuardSprite;
    private BufferedImage wallSprite;
    private BufferedImage coinSprite;
    private BufferedImage puddleSprite;
    private BufferedImage compassSprite;
    private BufferedImage floorSprite;
    private BufferedImage bus;
    private GameStateManager gameManager;

    private int TILE_SIZE = 50;


    /**
     * Constructs the GamePanel and initializes game logic.
     */
    public GamePanel() {
        
        gameManager = new GameStateManager();
        gameLogic = new GameLogic(this);
        player = gameLogic.getPlayer();
        System.out.println("current game state" + gameManager.getCurrenState());
        addKeyListener(new KeyboardHandler(gameLogic.getPlayer(),gameManager));
        setFocusable(true);
        requestFocusInWindow();
        //gameManager.setState(GameState.MainPg);
       
       // if(gameManager.getCurrenState()==GameState.gameRunning){
        loadInstructionPage();
        loadSprites();
        //}
    }

    /**
     * Displays a game over message when the player is caught.
     *
     * @param enemy The enemy that caught the player.
     */
    public void showGameOverMessage(Enemy enemy) {
        JOptionPane.showMessageDialog(this, "Player caught by " + enemy.getClass().getSimpleName() + "!", "Game Over", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    /**
     * Loads the instruction page image.
     */
    private void loadInstructionPage() {
        try {
            instructionPg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/instructionPg.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all game sprites including the player, enemies, and tiles.
     */
    private void loadSprites() {
        try {
            playerUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Student_up.png")));
            playerIdle = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Student.png")));
            playerLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Student_left.png")));
            playerRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Student_right.png")));
            professorSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/instructorSprite.png")));
            securityGuardSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/securitySprite.png")));

            // load tile sprites
            wallSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/wall.png")));
            floorSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rocks2.png")));
            coinSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/coin.png")));
            puddleSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/puddle.png")));
            compassSprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/compass.png")));
            bus = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/bus.png")));

            // debugging output
            if (wallSprite == null) System.out.println("error: wall.png not loaded!");
            if (floorSprite == null) System.out.println("error: rocks2.png not loaded!");
            if (coinSprite == null) System.out.println("error: coin.png not loaded!");
            if (puddleSprite == null) System.out.println("error: puddle.png not loaded!");
            if (compassSprite == null) System.out.println("error: compass.png not loaded!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed to load one or more tile images!");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] grid = gameLogic.getGameMap().getGrid();
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int leftPanelWidth = panelWidth / 3;  // left panel takes up 1/3
        int rightPanelWidth = panelWidth - leftPanelWidth; // grid area takes 2/3
        gameManager.renderMain(g, this);
        if(gameManager.getCurrenState() ==GameState.MainPg){
            gameManager.renderMain(g, this);
            gameManager.loadMainPg(this, g);
        }
        
        else if(gameManager.getCurrenState() == GameState.gameRunning || gameManager.getCurrenState() == GameState.gamePaused){
        
        adjustTileSize();
     

        // set background to black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panelWidth, panelHeight);

        // draw the left panel (instructions + timer)
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, leftPanelWidth, panelHeight);

        if (instructionPg != null) {
            g.drawImage(instructionPg, 10, panelHeight / 3, leftPanelWidth - 20, panelHeight / 2, null);
        }

        g.setFont(new Font("Monospaced", Font.BOLD, 40));
        g.setColor(Color.WHITE);
        g.drawString("score: " + gameLogic.getPlayer().getScore(), 30, 100);
        g.drawString("time: " + gameLogic.getTimeRemaining(), 30, 200);

        // calculate grid positioning
        int gridWidth = grid[0].length * TILE_SIZE;
        int gridHeight = grid.length * TILE_SIZE;

        // center the grid in the right panel
        int gridStartX = leftPanelWidth + (rightPanelWidth - gridWidth) / 2;
        int gridStartY = (panelHeight - gridHeight) / 2;

        // draw the game grid centered on the right-hand side
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                int drawX = gridStartX + x * TILE_SIZE;
                int drawY = gridStartY + y * TILE_SIZE;

                // draw the floor first (always)
                g.drawImage(floorSprite, drawX, drawY, TILE_SIZE, TILE_SIZE, null);

                // overlay specific objects
                BufferedImage tileImage = null;

                if (grid[y][x] == 1) {
                    tileImage = wallSprite; //walls
                } else if (grid[y][x] == 2) {
                    tileImage = coinSprite; //coin
                } else if (grid[y][x] == 3) {
                    tileImage = puddleSprite; //puddle
                } else if (grid[y][x] == 4) {
                    tileImage = compassSprite; //compass card
                } else if (grid[y][x] == 5) {
                    tileImage = floorSprite; //exit floor
                }

                // draw the tile if it's not null
                if (tileImage != null) {
                    g.drawImage(tileImage, drawX, drawY, TILE_SIZE, TILE_SIZE, null);
                }

                // draw "EXIT" text for exit square
                if (grid[y][x] == 5) {
                    g.drawImage(bus, drawX, drawY, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        // draw the player
        Point playerPos = gameLogic.getPlayer().getPosition();
        if (playerUp != null) {
            if (player.isUp()) {
                g.drawImage(playerUp, gridStartX + playerPos.x * TILE_SIZE, gridStartY + playerPos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
            else if (player.isIdle()) {
                g.drawImage(playerIdle, gridStartX + playerPos.x * TILE_SIZE, gridStartY + playerPos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
            if (player.isLeft()) {
                g.drawImage(playerLeft, gridStartX + playerPos.x * TILE_SIZE, gridStartY + playerPos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
            else if (player.isRight()) {
                g.drawImage(playerRight, gridStartX + playerPos.x * TILE_SIZE, gridStartY + playerPos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
        }

        // draw enemies
        for (Enemy enemy : gameLogic.getEnemies()) {
            Point enemyPos = enemy.getPosition();
            BufferedImage enemySprite = null;

            if (enemy instanceof Professor) {
                enemySprite = professorSprite;
            } else if (enemy instanceof SecurityGuard) {
                enemySprite = securityGuardSprite;
            }

            if (enemySprite != null) {
                g.drawImage(enemySprite, gridStartX + enemyPos.x * TILE_SIZE, gridStartY + enemyPos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
            }
        }
        if(gameManager.getCurrenState()==GameState.gamePaused){
            gameManager.loadPauseMenu();
            gameManager.renderPause(g, this);
        }
    }

}

    /**
     * Adjusts the tile size dynamically based on the game window dimensions.
     */
    private void adjustTileSize() {
        int availableWidth = getWidth() - (getWidth() / 4);
        int availableHeight = getHeight();
        TILE_SIZE = Math.min(availableWidth / gameLogic.getGameMap().getGrid()[0].length, availableHeight / gameLogic.getGameMap().getGrid().length);
    }

    /**
     * Passes keycode into game manager
     * @param keyCode
     */
   /*  public void handleKeyboardInput(int keyCode){
        gameManager.handleKeyboardInput(keyCode);
    }*/
    
    public GameStateManager getGameManager(){
        return gameManager;
    }
}
