package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Enemy extends Entity {
    
    private BufferedImage walk;
    private Animation animation;
    private int walkDirection = 1;
    private Player player;

    public Enemy(Panel panel, int x, int y, Player player, int speed){
        super();
        this.player = player;
        this.panel = panel;
        this.isInMenu = false;
        size = panel.tileSize;
        this.cam = x - panel.width/2;
        x = panel.width/2;
        this.y = y;
        this.speed = speed;
        direction = size;
        loadTextures();
        animation.start();
    }

    private void loadTextures(){
        try{
            walk = ImageIO.read(getClass().getResourceAsStream("/tex/policeWalk.png"));

            animation = new Animation(walk, 10, 1, 4);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private void updateAI() {
        if(!collideX && walkDirection > 0){
            cam += speed;
            direction = size;
        } else if(!collideX && walkDirection < 0){
            direction = -size;
            cam -= speed;
        } else {
            walkDirection = -walkDirection;
            collideX = false;
            cam += walkDirection*speed;
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
    }

    public void draw(Graphics g){
        g.drawImage(animation.getSprite(), (cam - player.cam + panel.width/2 - direction/2), y, direction, size, null);
    
    }
}