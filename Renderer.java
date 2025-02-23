package com.sfusprint;
import com.sfusprint.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Renderer is the class used to initialize the game window and draw GameObjects on it.
 */
public class Renderer {
    /**This is the game window**/
    private JFrame frame;
    private GamePanel panel;
    /**This contains the map data such as the layout**/
    private GameMap map;
    /**A list of Characters that exist in the game**/
    private List<Character> characters;

    /**
     * This method creates a JFrame used as a window for the game
     * @param panel at the moment this serves as a container for the Player sprite
     */
    public Renderer(GamePanel panel) {
        this.map = new GameMap();
        frame = new JFrame();
        frame.setTitle("Game Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 512);
        frame.add(panel);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

    }

    /**
     * This function renders the game map into the screen.
     * @param g the container for map
     */
    public void renderMap(Graphics g) {
        if (map != null) {
            map.draw(g);
        }
        else {
            System.out.println("Map is null");
        }

    }


}
