package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * The Player class represents a character in the game.
 * It handles player movement, animation, and rendering.
 *
 * @author Elsa
 */
public class Player extends Entity {
    
    private int fallHeight = 0;
    public KeyHandler keyHandler;
    private BufferedImage idle, walk, jump, crouch;
    private Animation walkAnimation, idleAnimation, jumpAnimation, crouchAnimation;
    public Animation animation;

    private BufferedImage dance1, dance2, dance3;
    public Animation danceAnimation1, danceAnimation2, danceAnimation3;

    public int anger; 

    private ArrayList<SoundEffects> snoring = new ArrayList<>();
    private ArrayList<SoundEffects> injuries = new ArrayList<>();
    private ArrayList<SoundEffects> jumping = new ArrayList<>();
    private Random random = new Random();


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
        cam = 0;
        x = panel.width/2;
        y = panel.height/2;
        direction = size;
        injuries.add(SoundEffects.ramlar); //TODO denna skullle unna va när man dör istället? 
        injuries.add(SoundEffects.ramlar2);
        injuries.add(SoundEffects.ramlar3);
        snoring.add(SoundEffects.snark);
        snoring.add(SoundEffects.snark2);
        snoring.add(SoundEffects.snark3);
        snoring.add(SoundEffects.hemmaAntligen);
        snoring.add(SoundEffects.hemmaAntligen2);
        jumping.add(SoundEffects.hopp);
        jumping.add(SoundEffects.hopp2);
        jumping.add(SoundEffects.hopp3);
        jumping.add(SoundEffects.hopp4);
        loadTextures();
        animation = idleAnimation;
        animation.start();
    }

    /**
     * Loads the textures for the player's sprite and walking animation.
     * Creates a new Animation object for the walking animation.
     */
    private void loadTextures(){
        try{
            idle = ImageIO.read(getClass().getResourceAsStream("/tex/anim/idle.png"));
            walk = ImageIO.read(getClass().getResourceAsStream("/tex/anim/walk.png"));
            jump = ImageIO.read(getClass().getResourceAsStream("/tex/anim/jump.png"));
            crouch = ImageIO.read(getClass().getResourceAsStream("/tex/anim/crouch.png"));

            walkAnimation = new Animation(walk, 10, 1, 4);
            idleAnimation = new Animation(idle, 30, 1, 4);
            jumpAnimation = new Animation(jump, 10, 1, 4);
            crouchAnimation = new Animation(crouch, 20, 1, 2);

            dance1 = ImageIO.read(getClass().getResourceAsStream("/tex/dance1.png"));
            dance2 = ImageIO.read(getClass().getResourceAsStream("/tex/dance2.png"));
            dance3 = ImageIO.read(getClass().getResourceAsStream("/tex/dance3.png"));

            danceAnimation1 = new Animation(dance1, 10, 1, 2);
            danceAnimation2 = new Animation(dance2, 10, 1, 2);
            danceAnimation3 = new Animation(dance3, 10, 1, 2);
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void set(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        panel.repaint();
    }

    private void victoryAnimation(int y) throws InterruptedException {
        int currentx = panel.width/2;
        int currenty = y;

        snoring.get(random.nextInt(snoring.size())).play();

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
        if (cam >= 2800) {
            try {
                victoryAnimation(y);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (keyHandler.right){
            animation = walkAnimation;
            animation.start();
            direction = size;
            if(collideX == false){  
                cam += speed;
                stamina -= 1;
            }
       } else if(keyHandler.left){
            animation = walkAnimation;
            animation.start();
            direction = -size;
            if(collideX == false){
                cam -= speed;
                stamina -= 1;
            }
       } else if(keyHandler.down){
            animation = crouchAnimation;
            animation.start();
            SoundEffects.sniffle.play();
       }

       if(keyHandler.up && isGrounded && !collideTop){
            animation = jumpAnimation;
            animation.start();
            jumping.get(random.nextInt(jumping.size())).play();
            dy -= jumpHeight;
            isGrounded = false;
            keyHandler.up = false;
       }
        
        if(!keyHandler.up && !keyHandler.down && !keyHandler.left && !keyHandler.right && !keyHandler.J){
            animation = idleAnimation;
            animation.start();
        } 
        

       if(!isGrounded){
            animation = jumpAnimation;
            y += dy;
            dy += gravity;
            if(dy > 0) fallHeight += 1;
            takeFallDamage();
        } else {
            dy = 0;
            fallHeight = 0;
        }
        
        super.updateCollission();
        animation.update();
    }

    private void takeFallDamage(){
        if(fallHeight > 28){
            health -= 5;
            fallHeight = 0;
            injuries.get(random.nextInt(injuries.size())).play();

        }
    }

    /**
     * Renders the player's sprite and walking animation on the graphics context.
     *
     * @param g The Graphics object to draw the player on.
     */
    public void draw(Graphics g){    
        g.drawImage(animation.getSprite(), x-direction/2, y, direction, size, null);

    }
}