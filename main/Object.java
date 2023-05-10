package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Object {
    public BufferedImage image;
    public int cols = 1;
    public int rows = 1;
    public boolean collision = false;
    public boolean donkeCutscene = false;
    public String actionString = "";
    public boolean isVisible = true;

    /**
    Constructor for the Tile class.
    @param collision Indicates whether the tile is solid (has collision) or not.
    @param path The path to the image file for this tile.
    @throws IOException If an error occurs while reading the image file.
    */
    public Object(String path, boolean collision) throws IOException{
        this.collision = collision;
        this.image = ImageIO.read(getClass().getResourceAsStream(path));
    }

    public Object(String path, int cols, int rows, String actionString) throws IOException{
        this.actionString = actionString;
        this.cols = cols;
        this.rows = rows;
        this.image = ImageIO.read(getClass().getResourceAsStream(path));
    }

    public Object(String path, boolean isVisible, String actionString) throws IOException{
        this.isVisible = isVisible;
        this.collision = false;
        this.actionString = actionString;
        this.image = ImageIO.read(getClass().getResourceAsStream(path));
        
    }
}
