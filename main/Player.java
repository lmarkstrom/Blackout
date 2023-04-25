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
    private BufferedImage image, walk, walk1, walk2;
    private BufferedImage[] walkingFrames;
    private Animation walkRight;
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
        // init();
    }

    private void init(){
        walkingFrames = new BufferedImage[] {getSprite(walk, 0, 1), getSprite(walk, 0, 2)};
        walkRight = new Animation(walkingFrames, 10);
        animation = new Animation(walkingFrames, 10);
    }

    /**
     * load textures
     */
    private void loadTextures(){
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/tex/player.png"));
            walk = ImageIO.read(getClass().getResourceAsStream("/tex/walk.png"));
            walk1 = ImageIO.read(getClass().getResourceAsStream("/tex/walk-1.png.png"));
            walk2 = ImageIO.read(getClass().getResourceAsStream("/tex/walk-2.png.png"));
            // walkingFrames.add(walk);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private BufferedImage getSprite(BufferedImage sprite, int xGrid, int yGrid){
        return sprite.getSubimage(xGrid*size, yGrid*size, size, size);
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
        // animation.update();

    }



    /**
     * draw player
     * @param g
     */
    public void draw(Graphics g){

        g.drawImage(image, xx, y, size, size, null);

        // int height = walk.getHeight()/2;
        // g.drawImage(walk, 300, 300, size, size, null);
        // g.drawImage(getSprite(walk), 300, 300, size, size, null);
        g.drawImage(walk2, 300, 300, size, size, null);


    }
}
