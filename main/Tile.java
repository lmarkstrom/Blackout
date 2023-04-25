package main;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
    public boolean collision;
    public boolean point;
    public BufferedImage image;

    public Tile(boolean collision, String path) throws IOException{
        this.collision = collision;
        this.point = false;
        this.image = ImageIO.read(getClass().getResourceAsStream(path));
    }
}