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
public class Player extends Entity {
    
    private int size;
    private int fallHeight = 0;

    private KeyHandler keyHandler;
    private BufferedImage idle, walk;
    private Animation animation;
    private Animation idleAnimation;

    /**
     * Constructs a new Player object with the given KeyHandler and Panel.
     * Initializes the player's position, size, and direction.
     * Loads the player's textures and sets up the animation.
     *
     * @param keyHandler The KeyHandler object for handling keyboard input.
     * @param panel The Panel object representing the game panel.
     */
    public Player(KeyHandler keyHandler, Panel panel){
        super();
        maxHealth = 100;
        maxStamina = 10000;
        health = maxHealth;
        stamina = maxStamina;
        this.isPlayer = true;
        this.keyHandler = keyHandler;
        this.panel = panel;
        size = panel.tileSize;
        x = 0;
        xx = panel.width/2;
        y = panel.height/2;
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
            idle = ImageIO.read(getClass().getResourceAsStream("/tex/idle.png"));
            walk = ImageIO.read(getClass().getResourceAsStream("/tex/walk.png"));

            animation = new Animation(walk, 10, 1, 4);
            idleAnimation = new Animation(idle, 30, 1, 4);
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
            direction = size;
            if(collideX == false){  
                x += speed;
                stamina -= 1;
            }
       } else if(keyHandler.left){
            animation.start();
            direction = -size;
            if(collideX == false){
                x -= speed;
                stamina -= 1;
            }
       } else direction = 0;
       if(keyHandler.up && isGrounded && !collideTop){
            dy -= jumpHeight;
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
            if(dy > 0) fallHeight += 1;
            takeFallDamage();
            System.out.println(fallHeight);
        } else {
            dy = 0;
            fallHeight = 0;
        }
        
        super.updateCollission();
        animation.update();
        idleAnimation.update();
    }

    private void takeFallDamage(){
        if(fallHeight > 28){
            health -= 5;
            fallHeight = 0;
        }
    }

    /**
     * Renders the player's sprite and walking animation on the graphics context.
     *
     * @param g The Graphics object to draw the player on.
     */
    public void draw(Graphics g){
        if (direction == 0){
            idleAnimation.start();
            g.drawImage(idleAnimation.getSprite(), xx-size/2, y, size, size, null);
        }else{
            idleAnimation.stop();
            g.drawImage(animation.getSprite(), xx-direction/2, y, direction, size, null);
        }
    }
}