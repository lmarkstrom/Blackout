package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
TileManager class is responsible for managing the game tiles, loading the map
layout, and rendering the tiles on the screen. It uses a 2D array to store
the map layout and a separate array to store the tile images.
*/
public class TileManager {
    // constants
    public int map[][];
    public int mapSizeX;
    public int mapSizeY;
    private int posX;

    // objects
    private Panel panel;
    public Tile[] tiles;
    private Player player;
    private BufferedImage background;
    
    /**
    Constructor for the TileManager class. 
    @param panel The game panel object.
    @param player The player object.
    */
    public TileManager(Panel panel, Player player){
        this.panel = panel;
        this.player = player;
        this.tiles = new Tile[10];
        setMaxXY();
        this.map = new int[mapSizeX][mapSizeY];
        this.tiles = getTileImage();

        loadMap();
    }

    private void setMaxXY() {
        try {
            InputStream stream = getClass().getResourceAsStream("/data/map.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            
            int maxX = -1;
            int maxY = -1;
            String currentLine = "";
            String lastLine = "";
            
            while (currentLine != null) {
                lastLine = currentLine;
                currentLine = reader.readLine();
                maxY++;
            }
            maxX = lastLine.split(" ").length;

            reader.close();            
            this.mapSizeX = maxX;
            this.mapSizeY = maxY;
        } catch (Exception e) {
            System.out.println(e);
        }    
    }

    /**
    Loads the images for the different tiles and returns an array of Tile
    objects.
    
    @return An array of Tile objects containing the loaded images.
    */
    private Tile[] getTileImage() {
        Tile[] tile = new Tile[10];
        try {
            tile[0] = new Tile(false, "/tex/0empty.png");
            tile[1] = new Tile(true, "/tex/1dirt.png");
            tile[2] = new Tile(true, "/tex/2grass.png");
            tile[3] = new Tile(false, "/tex/3flagPole.png");
            tile[4] = new Tile(false, "/tex/4flagTop.png");
            tile[5] = new Tile(false, "/tex/5playerHouse.png");
        } catch (Exception e) {
            System.out.println(e);
        }
        return tile;
    }

    /**
    Updates the camera position according to the player's position.
    */
    public void update(){
        posX = player.x;
    }
    
    /**
    Draws the tiles on the game panel.
    
    @param g The Graphics object to draw the tiles with.
    */
    public void draw(Graphics g) {
        g.drawImage(background, 0, 0, panel.width, panel.height, null);
        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                g.drawImage(tiles[map[x][y]].image, (x * panel.tileSize - posX), (y * panel.tileSize), panel.tileSize, panel.tileSize, null);
            }
        }
    }

    /**
     * Loads the map layout from a text file into a 2D array.
     */
    public void loadMap(){
        try {
            InputStream stream = getClass().getResourceAsStream("/data/map.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            
            for (int y = 0; y < mapSizeY; y++) {
                String line = reader.readLine();
                String nums[] = line.split(" ");
                for (int x = 0; x < mapSizeX; x++) {
                    int num = 0;
                    try {
                        num = Integer.parseInt(nums[x]);
                    } catch (Exception e) {
                        panel.enemies.add(new Enemy(panel, x*panel.tileSize, y*panel.tileSize, player, 2));
                    }
                    map[x][y] = num;
                }
            }

            reader.close();
            background = ImageIO.read(getClass().getResourceAsStream("/tex/gameBg.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
