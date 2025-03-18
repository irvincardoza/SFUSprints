package com.sfusprint;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Objects;

import javax.imageio.ImageIO;
public class GameStateManager {
    private GameState currentGameState;
    private BufferedImage mainPageImg ,pauseMenuImg;

 
    public GameStateManager(){
        currentGameState = GameState.MainPg;//sets initial game state to Main page
       
    }

    /**
     * Returns current state of the game
     * @return current state of the game
     */
    public GameState getCurrenState(){
        return currentGameState;
    }

    /**
     * Returns new state of the game
     * @param newState new state of the game
     */
    public void setState(GameState newState){
        currentGameState = newState;
        System.out.println("state changed to " + currentGameState);
    }

    /**
     * loads the main game page
     */
    public void loadMainPg(GamePanel panel,Graphics g){
        try {
            mainPageImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Main.png")));

                String text = "Controls:\n 'P' to play \n 'Q' to exit/close game \n 'ESC' to pause/resume game\n";
                int pgWidth =(int)((panel.getWidth()) *0.33);
                int pgHeight = (int)(panel.getHeight() *0.5);
                g.setColor(new Color(30,30,30,200));//colour of the background
                    g.fillRect(pgWidth,pgHeight-50,pgWidth,pgHeight/2); 
                    g.setFont(new Font("Monospaced",Font.BOLD,30));
                    g.setColor(Color.white);
                    String []lines = text.split("\n");
                    for (int i =0;i<lines.length;i++){
                    g.drawString(lines[i] ,pgWidth,pgHeight+ (i*50));
                    }
            
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

/**
 * Loads the pause menu page
 */
public void loadPauseMenu(){
    try{
    pauseMenuImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/pause.png")));
}
    catch(Exception e){
        e.printStackTrace();
    }
}

    /**
     * draws main game page
     * @param g
     * @param panel
     */
     public void renderMain(Graphics g, GamePanel panel){
        if (currentGameState ==GameState.MainPg){
            if(mainPageImg!=null){
                g.drawImage(mainPageImg,0,0,panel.getWidth(), panel.getHeight(),null);
            }
            
        }
    }

    /**
     * Renders the pause page
     * @param g
     * @param panel
     */
    public void renderPause(Graphics g, GamePanel panel){
        if (currentGameState == GameState.gamePaused){
            if(pauseMenuImg!=null){
                int scaledWidth = (int)((panel.getWidth()) * 0.6);
                int scaledHeight = (int)(panel.getHeight()* 0.6);            
                BufferedImage scaledImg = resizeImg(pauseMenuImg,scaledWidth,scaledHeight);
                g.drawImage(scaledImg,(panel.getWidth()-scaledWidth)/2, (panel.getHeight()-scaledHeight)/2,null);
            }
        }
    }
    public BufferedImage resizeImg(BufferedImage original,int width, int height){
        BufferedImage resized = new BufferedImage(width, height, original.getType());
        Graphics g = resized.getGraphics();
        g.drawImage(original, 0, 0,width,height, null);
        return resized;
    }
}
