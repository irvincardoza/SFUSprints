package com.sfusprint;
import com.sfusprint.entity.Player;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 *  GamePanel class javadoc in progress
 */
public class GamePanel extends JPanel{
    private GameLogic game;
    private Directions direction;
    private BufferedImage img;
    private MouseInput mouseInput = new MouseInput();
    private Player player;
    public GamePanel(GameLogic game) {

        addKeyListener(new KeyboardHandler(this));
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        setFocusable(true);
        requestFocusInWindow();
        this.game = game;
        player = new Player(this);
        getImage();
    }

    public void getImage() {
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Student sprite.png")));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
        g.drawImage(img, player.getPosition().x, player.getPosition().y, null);
    }

    public Player getPlayer() {
        return player;
    }

    public void setDirection(Directions direction) {
        this.direction = direction;

    }


}
