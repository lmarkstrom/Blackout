package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
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
    public final int maxTiles = 10;
    public final int maxObjects = 20;
    public int map[][];
    public int mapObj[][];
    public int mapSizeX;
    public int mapSizeY;
    private int posX;

    // objects
    private Panel panel;
    public Tile[] tiles;
    public Object[] objects;
    private Player player;
    private BufferedImage background;
    
    /**
    Constructor for the TileManager class. 
    @param panel The game panel object.
    @param player The player object.
    */
    public TileManager(Panel panel, Player player, String levelPath, String bgPath){
        this.panel = panel;
        this.player = player;
        this.tiles = new Tile[maxTiles];
        this.objects = new Object[20]; 
        setMaxXY(levelPath);
        this.map = new int[mapSizeX][mapSizeY];
        this.mapObj = new int[mapSizeX][mapSizeY];
        this.tiles = getTileImage();
        this.objects = getObjectImage();

        loadMap(levelPath);
        try {
            background = ImageIO.read(getClass().getResourceAsStream(bgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setMaxXY(String path) {
        try {
            InputStream stream = getClass().getResourceAsStream(path);
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
        Tile[] tile = new Tile[maxTiles];
        try {
            tile[0] = new Tile(false, "/tex/tiles/0empty.png", false);
            tile[1] = new Tile(true, "/tex/tiles/1dirt.png", false);
            tile[2] = new Tile(true, "/tex/tiles/street.png", false);
            tile[3] = new Tile(false, "/tex/tiles/3flagPole.png", false);
            tile[4] = new Tile(false, "/tex/tiles/4flagTop.png", false);
            tile[5] = new Tile(false, "/tex/tiles/5playerHouse.png", false);
        } catch (Exception e) {
            System.out.println(e);
        }
        return tile;
    }

    /**
    Loads the images for the different objects and returns an array of Tile
    objects.
    
    @return An array of Tile objects containing the loaded images.
    */
    private Object[] getObjectImage() {
        Object[] objects = new Object[maxObjects];
        try {
            objects[1] = new Object("/tex/obj/car.png", true);
            objects[2] = new Object("/tex/obj/donken.png", 4, 3 , "donken");
        } catch (Exception e) {
            System.out.println(e);
        }
        return objects;
    }

    /**
    Updates the camera position according to the player's position.
    */
    public void update(){
        posX = player.cam;
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
                if(mapObj[x][y] != 0) {
                    Object obj = objects[mapObj[x][y]];
                    g.drawImage(obj.image, (x * panel.tileSize - posX), ((y - obj.rows + 1) * panel.tileSize), panel.tileSize*obj.cols, panel.tileSize*obj.rows, null);
                }
            }
        }
    }

    /**
     * Loads the map layout from a text file into a 2D array.
     */
    public void loadMap(String path){
        try {
            InputStream stream = getClass().getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            
            for (int y = 0; y < mapSizeY; y++) {
                String line = reader.readLine();
                String nums[] = line.split(" ");
                for (int x = 0; x < mapSizeX; x++) {
                    int num = 0;
                    int num2 = 0;
                    try {
                        num = Integer.parseInt(nums[x]);
                        
                    } catch (Exception e) {
                        if (nums[x].equals("e")){
                            panel.enemies.add(new Enemy(panel, x*panel.tileSize, y*panel.tileSize, player, 2, false));
                            num2 = 0;
                        } else {
                            num2 = translateObj(nums[x]); 
                        }
                    }
                    map[x][y] = num;
                    mapObj[x][y] = num2;   
                }
            }

            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int translateObj(String num){
        switch(num){
            case "a":
                return 1;
            case "b":
                return 2;
            default:
                return 0;
        }
    }
}
