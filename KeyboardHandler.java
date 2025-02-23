package com.sfusprint;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * This class handles user input to move the Player
 */
public class KeyboardHandler implements KeyListener {
    private GamePanel gamePanel;

    public KeyboardHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * This method captures the user's keystrokes and maps each to the Player's direction.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                System.out.println("W");
                System.out.println(gamePanel.getPlayer().getPosition());
                gamePanel.getPlayer().setUp(true);
                gamePanel.getPlayer().move();
                break;
            case KeyEvent.VK_S:
                System.out.println("S");
                System.out.println(gamePanel.getPlayer().getPosition());
                gamePanel.getPlayer().setDown(true);
                gamePanel.getPlayer().move();
                break;
            case KeyEvent.VK_A:
                System.out.println("A");
                System.out.println(gamePanel.getPlayer().getPosition());
                gamePanel.getPlayer().setLeft(true);
                gamePanel.getPlayer().move();
                break;
            case KeyEvent.VK_D:
                System.out.println("D");
                System.out.println(gamePanel.getPlayer().getPosition());
                gamePanel.getPlayer().setRight(true);
                gamePanel.getPlayer().move();
                break;
            default:
                System.out.println("INVALID KEY");
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getPlayer().setUp(false);
                break;
            case KeyEvent.VK_S:
                gamePanel.getPlayer().setDown(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getPlayer().setRight(false);
                break;
            default:
                System.out.println("INVALID KEY");
                break;
        }
    }
}
