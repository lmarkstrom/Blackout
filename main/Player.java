package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.Panel.STATE;

/**
 * The Player class represents a character in the game.
 * It handles player movement, animation, and rendering.
 *
 * @author Elsa
 */
public class Player extends Entity {
    
    private int fallHeight = 0;
    public boolean won = false;
    public KeyHandler keyHandler;
    private BufferedImage idle, walk, jump, crouch;
    public Animation walkAnimation, idleAnimation, jumpAnimation, crouchAnimation;
    public Animation animation;
    private BufferedImage dance1, dance2, dance3;
    public Animation danceAnimation1, danceAnimation2, danceAnimation3;
    private BufferedImage standup, puke;
    public Animation standupAnimation, pukeAnimation;
    public int anger;
    public boolean isBusted;
    private ArrayList<SoundEffects> snoring = new ArrayList<>();
    private ArrayList<SoundEffects> injuries = new ArrayList<>();
    private ArrayList<SoundEffects> jumping = new ArrayList<>();
    private Random random = new Random();
    private boolean isStanding;
    private boolean isPuking; 
    private int tick;
    private int tick2;
    public boolean done, done2, done3, done4;


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
        y = panel.height-panel.tileSize*3;
        direction = size;
        injuries.add(SoundEffects.ramlar);
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

        isStanding = false;
        tick = 0;
        tick2 = 0;

        animation = standupAnimation;
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

            dance1 = ImageIO.read(getClass().getResourceAsStream("/tex/anim/dance1.png"));
            dance2 = ImageIO.read(getClass().getResourceAsStream("/tex/anim/dance2.png"));
            dance3 = ImageIO.read(getClass().getResourceAsStream("/tex/anim/dance3.png"));

            danceAnimation1 = new Animation(dance1, 10, 1, 2);
            danceAnimation2 = new Animation(dance2, 10, 1, 2);
            danceAnimation3 = new Animation(dance3, 10, 1, 2);

            standup = ImageIO.read(getClass().getResourceAsStream("/tex/anim/standup.png"));
            puke = ImageIO.read(getClass().getResourceAsStream("/tex/anim/puke.png"));

            standupAnimation = new Animation(standup, 20, 1, 7);
            pukeAnimation = new Animation(puke, 10, 1, 6);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Updates the animation and handles dance state logic.
     * N.B. this method will be updated regardless of the panel.state
     */
    public void updateAnimation(){
        animation.update();

        if (panel.state == STATE.DANCE){

            if(!isStanding) tick++;
            if(tick > 60) { 
                panel.stopDanceState(); 
                isStanding = true; 
                tick = 0;
            }

            if(isPuking) tick2 ++;
            if(tick2 > 60){
                panel.stopDanceState(); 
                animation = idleAnimation; 
                isPuking = false;
                tick2 = 0;
            }
     
        }
    }

    /**
     * Triggers puking animation and sound effect.
     */
    private void puke(){
        panel.startDanceState();
        SoundEffects.vomiting.play();
        animation = pukeAnimation;
        direction = -direction;
        animation.start();
        isPuking = true;
    }

    /**
     * Checks if the given integer is within the given range.
     * 
     * @param x     the integer to be checked
     * @param lower the lower bound of the range (inclusive)
     * @param upper the upper bound of the range (inclusive)
     * @return      true if x is within the range, false otherwise
     */
    private boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    /**
     * Updates the player's position and movement based on keyboard input.
     * Handles player jumping and gravity.
     * Updates the walking animation if the player is moving.
     * N.B. This update method will only run if panel.state == STATE.GAME
     * @throws InterruptedException
     */
    public void update(){
        if(!isPuking){
            if (keyHandler.right){
                animation = walkAnimation;
                animation.start();
                direction = size;
                if(collideX == false){  
                    cam += speed;
                    stamina -= 5;
                }
           } else if(keyHandler.left){
                animation = walkAnimation;
                animation.start();
                direction = -size;
                if(collideX == false){
                    cam -= speed;
                    stamina -= 5;
                }
           } else if(keyHandler.down){
                animation = crouchAnimation;
                animation.start();
                SoundEffects.sniffle.play();
           }
            var ran = random.nextInt(100, 500);
            if (!done && isBetween(stamina, 3000+ran , 3000+ran+50) && isGrounded){
                puke();
                done = true;
            } 
            if (!done2 && isBetween(stamina, 3000, 3050) && isGrounded){
                puke();
                done2 = true;
            } 
            if (!done3 && isBetween(stamina, 2000, 2050) && isGrounded){ 
                puke();
                done3 = true;
            }
            if (!done4 && isBetween(stamina, 1000, 1050) && isGrounded){ 
                puke();
                done4 = true;
            }

            if(stamina == 4000) SoundEffects.marInteBra.play();

        }      

       if(keyHandler.up && isGrounded && !collideTop){
            animation = jumpAnimation;
            animation.start();
            jumping.get(random.nextInt(jumping.size())).play();
            dy -= jumpHeight;
            isGrounded = false;
            keyHandler.up = false;
       }
        
        if(!keyHandler.up && !keyHandler.down && !keyHandler.left && !keyHandler.right && !keyHandler.Q && animation != pukeAnimation){
            animation = idleAnimation;
            animation.start();
        } 

        if(!isStanding){
            direction = -direction;
            animation = standupAnimation; 
            animation.start();
            panel.startDanceState();
            tick = 0;
        } else if(!isGrounded){
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
    }

    /**
    * This method calculates and applies fall damage to the player if the fall height is greater than 28.
    * If fall damage is applied, a random injury sound effect is played.
    */
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