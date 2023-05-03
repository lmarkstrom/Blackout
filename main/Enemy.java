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
    private int walkDirection = 1;
    private Player player;

    public Enemy(Panel panel, int x, int y, Player player){
        super();
        // this.keyHandler = keyHandler;
        this.player = player;
        this.panel = panel;
        this.isInMenu = false;
        size = panel.tileSize;
        this.x = x;
        xx = panel.width/2;
        this.y = y;
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

    private void updateAI() {
        if (collideX) {
            walkDirection = -walkDirection;
        }

    }

    public void update(){ 

       if(!isGrounded){
            y += dy;
            dy += gravity;
        } else {
            dy = 0;
        }
        updateAI();
        super.updateCollission();
        animation.update();
        idleAnimation.update();
    }

    public void draw(Graphics g){
        //g.drawRect(x, y, size, size);
        //System.out.println("x: " + x + " Y: " + y);

        //g.drawImage(image, xx, y, size, size, null);
        if (direction == 0){
            idleAnimation.start();
            g.drawImage(idleAnimation.getSprite(), (x - player.x), y, size, size, null);
        }else{
            idleAnimation.stop();
            g.drawImage(animation.getSprite(), (x - player.x), y, direction, size, null);
        }
        System.out.println("X: " + (x - player.x) + " Y: " + y);
    }
}