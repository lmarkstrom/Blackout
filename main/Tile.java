package main;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
Tile class represents an individual tile in the game. It contains information
about the tile's image, collision properties, and whether it is a point or not.
*/
public class Tile {
    public boolean collision;
    public boolean point;
    public boolean damage;
    public BufferedImage image;

    /**
    Constructor for the Tile class.
    @param collision Indicates whether the tile is solid (has collision) or not.
    @param path The path to the image file for this tile.
    @throws IOException If an error occurs while reading the image file.
    */
    public Tile(boolean collision, String path, boolean damage) throws IOException{
        this.collision = collision;
        this.point = false;
        this.damage = damage; 
        this.image = ImageIO.read(getClass().getResourceAsStream(path));
    }
}