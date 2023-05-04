package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class PlayerData {
    // fields
    private int maxHealth;
    private int maxStamina;

    private int healthX;
    private int staminaX;
    private int posX; 
    private Color healthCol = new Color(172, 43, 43);
    private Color staminaCol = new Color(40, 185, 50);

    private BufferedImage healthBar;
    private BufferedImage staminaBar;

    // objects
    private Player player;
    private Panel panel;

    public PlayerData(Panel panel){
        this.player = panel.player;
        this.panel = panel;
        this.maxHealth = player.maxHealth;
        this.maxStamina = player.maxStamina;
        posX = panel.width/2 - maxHealth*2;
        loadTex();
    }

    public void update(){
        healthX = player.health*4;
        staminaX = (player.stamina/100)*4;
    }

    public void draw(Graphics g){
        // healthbar
        g.drawImage(healthBar, posX - 10, 5, 461, 32, null);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(posX, 18, maxHealth*4 , 6);
        g.setColor(healthCol);
        g.fillRect(posX, 18, healthX , 6);

        // staminabar
        g.drawImage(staminaBar, posX - 10, 40, 461, 32, null);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(posX, 54, (player.maxStamina/(player.maxStamina/100))*4 , 6);
        g.setColor(staminaCol);
        g.fillRect(posX, 54, staminaX , 5);
    }

    private void loadTex(){
        try {
            healthBar = ImageIO.read(getClass().getResourceAsStream("/tex/bar.png"));
            staminaBar = ImageIO.read(getClass().getResourceAsStream("/tex/bar2.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
