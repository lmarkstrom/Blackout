package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Object {
    public BufferedImage image;
    public int offsetCol = 0;
    public int offsetRow = 0;

    /**
    Constructor for the Tile class.
    @param collision Indicates whether the tile is solid (has collision) or not.
    @param path The path to the image file for this tile.
    @throws IOException If an error occurs while reading the image file.
    */
    public Object(String path) throws IOException{
        this.image = ImageIO.read(getClass().getResourceAsStream(path));
    }

    public Object(String path, int offsetCol, int offsetRow) throws IOException{
        this.offsetCol = offsetCol;
        this.offsetRow = offsetRow;
        this.image = ImageIO.read(getClass().getResourceAsStream(path));
    }
}
