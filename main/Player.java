package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Player  {
    
    public int x;
    private int xx;
    public int y;
    public int speed;
    public int gravity;
    public boolean collide;
    public int movement;
    public Color red = Color.RED;
    private KeyHandler keyHandler;
    private BufferedImage image;
    private Panel panel;

    public Player(KeyHandler keyHandler, Panel panel){
        this.keyHandler = keyHandler;
        this.panel = panel;
        x = 0;
        xx = panel.width/2;
        // y = panel.height/2;
        y = 0;
        loadTextures();
    }

    private void loadTextures(){
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/tex/player.png"));
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void update(){
       if (keyHandler.right){
            x += 10;
            keyHandler.right = false;
       } else if(keyHandler.left){
            x -= 10;
            keyHandler.left = false;
       } else if(keyHandler.up){
            y -= 100;
            keyHandler.up = false;
            collide = false;
       }

       if(!collide){
            y += 5;
            System.out.println(y);
            System.out.println(collide);
        }
       
    }

    public void draw(Graphics g){

        g.drawImage(image, xx, y, panel.tileSize, panel.tileSize, null);

    }
}
