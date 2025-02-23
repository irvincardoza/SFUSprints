package com.sfusprint;

import com.sfusprint.entity.Character;
import com.sfusprint.gameobject.Item;
import com.sfusprint.gameobject.Obstacle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.*;
import java.util.Random;


/**
 * GameMap handles the creation of the playable area. It parses a text file and turns it into renderable images.
 *
 */
public class GameMap {
    private final int GAME_MAP_WIDTH = 16;
    private final int GAME_MAP_HEIGHT = 16;
    private int[][] grid;
    private BufferedImage tile1, wall, coin, compass, puddle; // atm its only one tile, but it eventually will be a tileset
    private BufferedImage[] tiles; // holds the pngs of the tiles
    private BufferedImage map;
    private List<Obstacle> obstacleList;
    private List<Item> items=new ArrayList<>(); // list of items
    private List<Character> characters;

    public GameMap() {
        grid = new int[GAME_MAP_HEIGHT][GAME_MAP_WIDTH];
        importTiles();
        loadMap();
    }

    /**
     * This method reads in .png files and loads them into a BufferedImage array.
     */
    public void importTiles() {
        try {
            tile1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/rocks2.png")));
            wall = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/wall.png")));
            coin = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/coin.png")));
            compass = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/compass.png")));
            puddle = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/puddle.png")));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        tiles = new BufferedImage[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i] = tile1;
            }
        }

    }

    /**
     * BufferedImages getter
     * @param index the index
     * @return the image at the specified index
     */
    public BufferedImage getTile(int index) {
        if (index < 0 || index >= tiles.length) {
            throw new IndexOutOfBoundsException("Tile index out of range: " + index);
        }
        return tiles[index];
    }

    /**
     * This method reads in a text file containing numbers that represent tiles and obstacles and loads it into a
     * 2D array.
     */
    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/intMap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;
            int col = 0;
            while (row < GAME_MAP_HEIGHT && col < GAME_MAP_WIDTH) {
                String line = br.readLine();
                while (col < GAME_MAP_WIDTH) {
                    String[] tokens = line.split(" ");
                    int number = Integer.parseInt(tokens[col]);
                    grid[row][col] = number;
                    col++;
                }
                if (col == GAME_MAP_WIDTH) {
                    col = 0;
                    row++;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int[][] getGameMap() {
        return grid;
    }

    public void draw(Graphics g) {
        
        for (int row = 0; row < GAME_MAP_HEIGHT; row++) { // ROW -> HEIGHT
            for (int col = 0; col < GAME_MAP_WIDTH; col++) { // COL - > WIDTH
                // this is bad for the open/close principle
                // fix: make a Tile class have that have its own draw method
                    switch (grid[row][col]) {
                        case 0:
                            g.drawImage(tile1, col * 32, row * 32, null);
                            break;
                        case 1:
                            g.drawImage(wall, col * 32, row * 32, null);
                            break;
                        case 2:
                            g.drawImage(puddle, col * 32, row * 32, null);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + grid[col][row]);

                }
              

            }
        }
        for (Item item : items) {
            item.draw(g);  // This calls the draw method from Item.java
        }

    }


    public void place_items(int coinCount, int compassCount){
        Random rand =new Random();
        int placedCoins=0;
        int placedCard=0;
        while(placedCoins<coinCount || placedCard<compassCount){
            int row=rand.nextInt(GAME_MAP_HEIGHT);
            int col=rand.nextInt(GAME_MAP_WIDTH);
            if(grid[row][col]==0){
                Point pos = new Point(col*32,row*32);
                if(placedCoins<coinCount){
                    items.add(new Item(pos, Item.ItemType.COIN));
                    placedCoins++;
                }
                else if(placedCard<compassCount){
                    items.add(new Item(pos, Item.ItemType.COMPASS));
                    placedCard++;
                }
                grid[row][col] = 3;
            }
        }

    }
   
    

}
