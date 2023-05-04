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

    private BufferedImage healthBar;

    // objects
    private Player player;
    private Panel panel;

    public PlayerData(Panel panel){
        this.player = panel.player;
        this.panel = panel;
        this.maxHealth = player.maxHealth;
        this.maxStamina = player.maxStamina;
        loadTex();
    }

    public void update(){
        healthX = player.health*4;
    }

    public void draw(Graphics g){
        int posX = panel.width/2 - maxHealth*2;
        g.drawImage(healthBar, posX - 10, 5, 420, 30, null);
        
        g.setColor(Color.DARK_GRAY);
        g.fillRect(posX, 10, maxHealth*4 , 20);
        //g.setColor(Color.RED);
        //g.fillRect(posX, 10, healthX , 20);
        
    }

    private void loadTex(){
        try {
            healthBar = ImageIO.read(getClass().getResourceAsStream("/tex/healthBar.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
