package com.sfusprint;

import com.sfusprint.entity.Enemy;
import com.sfusprint.entity.Player;

import java.awt.*;
import java.util.List;

/**
 * GameLogic is the main class that initializes the game and runs the game loop
 */
public class GameLogic
{
    private Thread gameThread;
    private GamePanel panel;
    private Renderer GameRenderer;
    private int timeRemaining;
    private List<Enemy> enemies;
    private Player player;
    private GameMap GameMap;

    public GameLogic() {
        panel = new GamePanel(this);
        
        this.GameMap = new GameMap();
        this.GameRenderer = new Renderer(panel);
        StartGame();
    }

    public void StartGame() {
        player = new Player(panel);
        if (GameMap != null) {
            // Place items only if GameMap is initialized
            GameMap.place_items(5, 2);  // 5 coins, 2 compass cards
        } else {
            System.out.println("GameMap is null in StartGame");
        }
    }


    public GamePanel getPanel() {
        return panel;
    }

    public Player getPlayer() {
        return player;
    }
    public GameMap getGameMap() {
        return GameMap;
    }

    public void render(Graphics g) {
        if (GameRenderer != null) {
            GameRenderer.renderMap(g);
        }
        else {
            System.out.println("GameRenderer is null");
        }
        

    }
}
