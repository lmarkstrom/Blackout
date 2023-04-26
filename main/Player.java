package main;

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
    private int size;
    private KeyHandler keyHandler;
    private BufferedImage image, walk;
    private Animation animation;

    /**
     * Player class that handle player movment and animation
     * @param keyHandler
     * @param panel
     * @author Elsa
     * 
     */
    public Player(KeyHandler keyHandler, Panel panel){
        this.keyHandler = keyHandler;
        size = panel.tileSize;
        x = 0;
        xx = panel.width/2;
        // y = panel.height/2;
        y = 0;
        loadTextures();
    }

    /**
     * load textures
     */
    private void loadTextures(){
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/tex/player.png"));
            walk = ImageIO.read(getClass().getResourceAsStream("/tex/walk.png"));

            animation = new Animation(walk, 10, 2, 1);

        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    /**
     * uppdate movment
     */
    public void update(){
       if (keyHandler.right){
            x += 10;
            keyHandler.right = false;
       } else if(keyHandler.left){
            x -= 10;
            keyHandler.left = false;
       }  
       if(keyHandler.up){
            y -= 200;
            keyHandler.up = false;
            collide = false;
       }

       if(!collide){
            y += 5;
        }
        animation.update();
    }



    /**
     * draw player
     * @param g
     */
    public void draw(Graphics g){

        g.drawImage(image, xx, y, size, size, null);


        g.drawImage(animation.getSprite(), xx-100, y, size, size, null);

    }
}
