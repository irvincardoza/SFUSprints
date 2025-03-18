package com.sfusprint;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.sfusprint.Entity.Player;

/**
 * Handles user input for player movement using keyboard keys.
 * Supports both WASD and arrow keys for movement.
 */
public class KeyboardHandler implements KeyListener {
    private Player player;
    private GameStateManager gameStateManager;
    
    /**
     * Constructs a KeyboardHandler to manage player movement.
     *
     * @param player The player object to be controlled.
     */
    public KeyboardHandler(Player player,GameStateManager gameStateManager) {
        this.player = player;
        this.gameStateManager =gameStateManager;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Handles key press events and moves the player accordingly.
     *
     * @param e The KeyEvent containing information about the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        
        
        if(e.getKeyCode() == KeyEvent.VK_P){
            if(gameStateManager.getCurrenState() == GameState.MainPg||gameStateManager.getCurrenState()==GameState.gamePaused){
                System.out.println("game state changing");
                gameStateManager.setState(GameState.gameRunning); 
                
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_Q){
            if(gameStateManager.getCurrenState()==GameState.MainPg){
            System.exit(0);
            }
            else if(gameStateManager.getCurrenState()==GameState.gamePaused){
                gameStateManager.setState(GameState.MainPg);
               
            }
        }
        else if(e.getKeyCode() ==KeyEvent.VK_ESCAPE){
            if(gameStateManager.getCurrenState() == GameState.gameRunning){
                gameStateManager.setState(GameState.gamePaused); //pauses the game
                gameStateManager.loadPauseMenu();
            }
            else if(gameStateManager.getCurrenState() == GameState.gamePaused){
                    gameStateManager.setState(GameState.gameRunning);//unpauses
            }
        }
        
        if(gameStateManager.getCurrenState()==GameState.gameRunning){
        Point direction = new Point(0, 0);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: direction.setLocation(0, -1); player.setUp(true) ;break; //up
            case KeyEvent.VK_S: direction.setLocation(0, 1); player.setDown(true); break; //down
            case KeyEvent.VK_A: direction.setLocation(-1, 0);  player.setLeft(true);break; //left
            case KeyEvent.VK_D: direction.setLocation(1, 0); player.setRight(true); break; //right

            case KeyEvent.VK_UP: direction.setLocation(0, -1); break; //up
            case KeyEvent.VK_DOWN: direction.setLocation(0, 1); break; //down
            case KeyEvent.VK_LEFT: direction.setLocation(-1, 0); break; //left
            case KeyEvent.VK_RIGHT: direction.setLocation(1, 0); break; //right
            default: return;
        }

        player.move(direction); //attempt to move the player
    }
}

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:  player.setUp(false) ;break; //up
            case KeyEvent.VK_S:  player.setDown(false); break; //down
            case KeyEvent.VK_A:   player.setLeft(false);break; //left
            case KeyEvent.VK_D:  player.setRight(false); break; //right

            default: return;
        }
    }

}

