package main;

public class Entity {
    
    public int x;
    public int xx;
    public int y;
    public int dy;
    public int speed = 10;
    public int jumpHeight = 15;
    public int gravity;
    public boolean collideX;
    public boolean collideY;
    public boolean collideTop;
    public int direction;
    public boolean isGrounded = false;
    public Panel panel;
    public boolean isInMenu;
    public boolean isPlayer;

    public void updateCollission() {
        panel.collisionHandler.update(this);
    }
}