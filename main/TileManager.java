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
    public final int maxTiles = 20;
    public final int maxObjects = 20;
    public int map[][];
    public int mapObj[][];
    public int mapSizeX;
    public int mapSizeY;
    private int posX;
    public int bgIndex;

    // objects
    private Panel panel;
    public Tile[] tiles;
    public Object[] objects;
    private Player player;
    private BufferedImage background1;
    private BufferedImage background2;
    
    /**
    Constructor for the TileManager class. 
    @param panel The game panel object.
    @param player The player object.
    */
    public TileManager(Panel panel, Player player, String levelPath, int bgIndex){
        this.bgIndex = bgIndex;
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
            background1 = ImageIO.read(getClass().getResourceAsStream("/tex/bg/bgForest.png"));
            background2 = ImageIO.read(getClass().getResourceAsStream("/tex/bg/bgCity2.png"));
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
            tile[0] = new Tile(false, "/tex/tiles/0empty.png");
            tile[1] = new Tile(true, "/tex/tiles/1dirt.png");
            tile[2] = new Tile(true, "/tex/tiles/2street.png");
            tile[3] = new Tile(true, "/tex/tiles/3grass.png");
            tile[4] = new Tile(true, "/tex/tiles/4streetEndR.png");
            tile[5] = new Tile(true, "/tex/tiles/5streetEndL.png");
            tile[6] = new Tile(true, "/tex/tiles/6dirtEndR.png");
            tile[7] = new Tile(true, "/tex/tiles/7dirtEndL.png");
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
            objects[0] = new Object("/tex/tiles/0empty.png", false);
            objects[1] = new Object("/tex/obj/car.png", true);
            objects[2] = new Object("/tex/obj/donken.png", 4, 3 , "donken");
            objects[3] = new Object("/tex/obj/house.png", 6, 6 , "next");
            objects[4] = new Object("/tex/obj/redLightGreen.png", 1, 2, "greenLight");
            // collision border
            objects[6] = new Object("/tex/tiles/0empty.png", true);
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
        if(bgIndex == 0){
            g.drawImage(background1, 0, 0, panel.width, panel.height, null);
        }else{
            g.drawImage(background2, 0, 0, panel.width, panel.height, null);
        }
        
        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                try {
                    g.drawImage(tiles[map[x][y]].image, (x * panel.tileSize - posX), (y * panel.tileSize), panel.tileSize, panel.tileSize, null);
                if(mapObj[x][y] != 0) {
                    Object obj = objects[mapObj[x][y]];
                    if(obj.isVisible) g.drawImage(obj.image, (x * panel.tileSize - posX), ((y - obj.rows + 1) * panel.tileSize), panel.tileSize*obj.cols, panel.tileSize*obj.rows, null);
                }
                } catch (Exception e) {
                    // TODO: handle exception
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
            case "c":
                return 3;
            case "d":
                return 4;
            case "f":
                return 5;
            case "I":
                return 6;
            default:
                return 0;
        }
    }
}
