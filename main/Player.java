package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * The Player class represents a character in the game.
 * It handles player movement, animation, and rendering.
 *
 * @author Elsa
 */
public class Player  {
    
    public int x;
    private int xx;
    public int y;
    public int dy;
    public int speed;
    public int gravity;
    public boolean collideX;
    public boolean collideY;
    private int movement;
    private int size;
    private KeyHandler keyHandler;
    private BufferedImage image, walk;
    private Animation animation;
    private int direction;
    private int ground;

    /**
     * Constructs a new Player object with the given KeyHandler and Panel.
     * Initializes the player's position, size, and direction.
     * Loads the player's textures and sets up the animation.
     *
     * @param keyHandler The KeyHandler object for handling keyboard input.
     * @param panel The Panel object representing the game panel.
     */
    public Player(KeyHandler keyHandler, Panel panel){
        this.keyHandler = keyHandler;
        size = panel.tileSize;
        x = 0;
        xx = panel.width/2;
        ground = panel.height-3*size;
        y = 0;
        gravity = 1;
        direction = size;
        loadTextures();
    }

    /**
     * Loads the textures for the player's sprite and walking animation.
     * Creates a new Animation object for the walking animation.
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
     * Updates the player's position and movement based on keyboard input.
     * Handles player jumping and gravity.
     * Updates the walking animation if the player is moving.
     */
    public void update(){
       if (keyHandler.right){
            animation.start();
            x += 10;
            direction = size;
       } else if(keyHandler.left){
            animation.start();
            x -= 10;
            direction = -size;
       }  
       if(keyHandler.up && y >= ground){ 
            dy -= 15;
            collideY = false;
            keyHandler.up = false;
       }

       if(!keyHandler.left && !keyHandler.right){
            animation.stop();
            animation.reset();
       } 

       if(!collideY){
            y += dy;
            dy += gravity;
        } else {
            dy = 0;
        }
        animation.update();
    }

    /**
     * Renders the player's sprite and walking animation on the graphics context.
     *
     * @param g The Graphics object to draw the player on.
     */
    public void draw(Graphics g){

        g.drawImage(image, xx, y, size, size, null);

        g.drawImage(animation.getSprite(), xx-direction/2, y, direction, size, null);
    }
}
