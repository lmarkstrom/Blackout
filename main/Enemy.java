package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Enemy extends Entity {
    
    private int size;
    private BufferedImage idle, walk;
    private Animation animation;
    private Animation idleAnimation;
    private int walkDirection = 1;
    private Player player;

    public Enemy(Panel panel, int x, int y, Player player, int speed){
        super();
        this.player = player;
        this.panel = panel;
        this.isInMenu = false;
        size = panel.tileSize;
        this.x = x - panel.width/2;
        xx = panel.width/2;
        this.y = y;
        this.speed = speed;
        gravity = 1;
        direction = size;
        loadTextures();
        animation.start();
    }

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

    private void updateAI() {
        System.out.println(collideX + " " + isGrounded);
        if(!collideX && walkDirection > 0){
            System.out.println("walk");
            x += speed;
            direction = size;
        } else if(!collideX && walkDirection < 0){
            System.out.println("walk");
            direction = -size;
            x -= speed;
        } else {
            walkDirection = -walkDirection;
            collideX = false;
            x += walkDirection*speed;
        } 
    }

    public void update(){ 
        //xx = x - player.x;
        //x = player.xx;
        if(isGrounded) updateAI();
       if(!isGrounded){
            y += dy;
            dy += gravity;
       } else {

            dy = 0;
        }
        super.updateCollission();
        animation.update();
        idleAnimation.update();
    }

    public void draw(Graphics g){
        g.drawImage(animation.getSprite(), (x - player.x + panel.width/2 - direction/2), y, direction, size, null);
    
    }
}