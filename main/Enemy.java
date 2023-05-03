package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Enemy extends Entity {
    
    private int size;
    // private KeyHandler keyHandler;
    private BufferedImage idle, walk;
    private Animation animation;
    private Animation idleAnimation;

    public Enemy(Panel panel){
        super();
        // this.keyHandler = keyHandler;
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

    public void update(){ 

       if(!isGrounded){
            y += dy;
            dy += gravity;
        } else {
            dy = 0;
        }
        animation.update();
        idleAnimation.update();
    }

    public void draw(Graphics g){

        //g.drawImage(image, xx, y, size, size, null);
        if (direction == 0){
            idleAnimation.start();
            g.drawImage(idleAnimation.getSprite(), xx-size/2, y, size, size, null);
        }else{
            idleAnimation.stop();
            g.drawImage(animation.getSprite(), xx-direction/2, y, direction, size, null);
        }
    }
}