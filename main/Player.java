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
    private int size;
    private KeyHandler keyHandler;
    private BufferedImage image, walk;
    private Animation animation;
    private int direction;
    public boolean isGrounded;
    private Panel panel;
    public boolean isInMenu;

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
        this.panel = panel;
        this.isInMenu = false;
        size = panel.tileSize;
        x = 0;
        xx = panel.width/2;
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

            animation = new Animation(walk, 10, 1, 4);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void set(int newX, int newY) {
        this.xx = newX;
        this.y = newY;
        panel.repaint();
    }

    private void victoryAnimation(int y) throws InterruptedException {
        int currentx = panel.width/2;
        int currenty = y;

        while (currenty < 484) {
            currenty += 1;
            Thread.sleep(10);
            set(currentx, currenty);
        }

        while (currentx < panel.width/2 + panel.width/5) {
            currentx += 1;
            Thread.sleep(10);
            set(currentx, currenty);
        }

        // TODO gör att karaktären vrider på sig 180 grader innan slutscenen

        // TODO gör en meny för när spelaren klarat banan
        isInMenu = true;

    }

    /**
     * Updates the player's position and movement based on keyboard input.
     * Handles player jumping and gravity.
     * Updates the walking animation if the player is moving.
     * @throws InterruptedException
     */
    public void update(){
        if (x >= 2800) {
            try {
                victoryAnimation(y);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       if (keyHandler.right){
            animation.start();
            x += 10;
            direction = size;
       } else if(keyHandler.left){
            animation.start();
            x -= 10;
            direction = -size;
       }  
       if(keyHandler.up && isGrounded){ 
            dy -= 15;
            isGrounded = false;
            keyHandler.up = false;
       }

       if(!keyHandler.left && !keyHandler.right){
            animation.stop();
            animation.reset();
       } 

       if(!isGrounded){
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

        //g.drawImage(image, xx, y, size, size, null);

        g.drawImage(animation.getSprite(), xx-direction/2, y, direction, size, null);
    }
}
