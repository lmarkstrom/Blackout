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
    private int direction;
    private int ground;

    /**
     * Player class that handle player movment and animation
     * @param keyHandler
     * @param panel
     * @author Elsa
     */
    public Player(KeyHandler keyHandler, Panel panel){
        this.keyHandler = keyHandler;
        size = panel.tileSize;
        x = 0;
        xx = panel.width/2;
        ground = panel.height-3*size;
        y = 0;
        direction = size;
        loadTextures();
    }

    /**
     * load the textures
     * and create new Animation
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
     * uppdates the movment 
     */
    public void update(){
       if (keyHandler.right){
            x += 10;
            animation.start();
            direction = size;
       } else if(keyHandler.left){
            x -= 10;
            direction = -size;
            animation.start();
       }  
       if(keyHandler.up && y >= ground){ 
            y -= 200;
            collide = false;
            keyHandler.up = false;
       }

       if(!keyHandler.left && !keyHandler.right){
            animation.stop();
            animation.reset();
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

        g.drawImage(animation.getSprite(), xx-100, y, direction, size, null);
        // går baklänges ska första size va -
        //TODO teleportera inte när du byter riktining
        // så du måste lösa på något annat sätt med direction

    }
}
