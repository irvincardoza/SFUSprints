package com.sfusprint;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * The entry point of the game. Initializes and launches the game window.
 */
public class Main {

    /**
     * The main method that starts the game.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Starting Game...");

        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Grid-Based Movement");
                GamePanel gamePanel = new GamePanel();

                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setUndecorated(false);
                frame.add(gamePanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                gamePanel.requestFocusInWindow();

                System.out.println("Game window created successfully!");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error initializing the game: " + e.getMessage());
            }
        });
    }
}
