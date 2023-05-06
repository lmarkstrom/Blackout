package main;

public class Entity {
    
    public int cam;
    public int x;
    public int y;
    public int dy;
    public int size;
    public int speed = 10;
    public int jumpHeight = 15;
    public int gravity = 1;
    public boolean collideX;
    public boolean collideY;
    public boolean collideTop;
    public int direction;
    public boolean isGrounded = false;
    public Panel panel;
    public boolean isInMenu;
    public boolean isPlayer;
    public int health;
    public int maxHealth;
    public int stamina;
    public int maxStamina;

    public void updateCollission() {
        panel.collisionHandler.update(this);
    }
}