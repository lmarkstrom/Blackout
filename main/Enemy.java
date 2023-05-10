package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.Panel.STATE;

public class Enemy extends Entity {
    
    private BufferedImage walk;
    private Animation animation;
    private int walkDirection = 1;
    private Player player;
    public boolean isChasing;
    private boolean copVisible = false;

    private boolean collidePlayer = false;

    public Enemy(Panel panel, int x, int y, Player player, int speed, boolean isChasing){
        super();
        this.player = player;
        this.panel = panel;
        this.isInMenu = false;
        size = panel.tileSize;
        this.cam = x - panel.width/2;
        x = panel.width/2;
        this.y = y;
        this.speed = speed;
        this.isChasing = isChasing;
        direction = size;
        loadTextures();
        animation.start();

    }

    private void loadTextures(){
        try{
            walk = ImageIO.read(getClass().getResourceAsStream("/tex/anim/policeWalk.png"));

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

        int width = animation.getSprite().getWidth();
        if(cam > player.cam - width && cam < player.cam + width && player.y == y && !collidePlayer){
            player.anger +=20;
            collidePlayer = true;
        } 
        
        if(cam < player.cam - width || cam > player.cam + width && collidePlayer){
            collidePlayer = false;
        }

        // if(cam > (player.cam - 6*panel.tileSize) && cam < (player.cam + 6*panel.tileSize) && !copVisible && panel.state == STATE.GAME){
        //     copVisible = true;
        //     SoundEffects.kollaInte.play();
        // }

    }

    private void updateChase(){
        if(!collideX && walkDirection > 0){
            cam += speed;
            direction = size;
        } else if(collideX && walkDirection > 0 && isGrounded){
            dy -= jumpHeight+2;
            isGrounded = false;
        }
        if(!isGrounded){
            y += dy;
            dy += gravity;
        } else {
            dy = 0;
        }

        if ( player.cam <= cam){
            SoundEffects.chilla.play();
            panel.startCutScene();
            panel.cutScene.getFrameBusted();
            player.isBusted = true;
            panel.startCutScene();
        } 
    }

    public void update(){ 
        if(isChasing) updateChase();
        else if (isGrounded) updateAI();
        else if(!isGrounded){
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