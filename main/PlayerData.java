package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PlayerData {
    // fields

    private int healthX;
    private int staminaX;
    private int posX;
    public int saveNum;
    private Color healthCol = new Color(172, 43, 43);
    private Color staminaCol = new Color(40, 185, 50);
    public int loadCount = 0;
    public int maxLoads = 4;
    public ArrayList<String[]> loadList;

    private BufferedImage healthBar;
    private BufferedImage staminaBar;

    // objects
    private Player player;
    private Panel panel;

    public PlayerData(Panel panel){
        this.player = panel.player;
        this.panel = panel;
        posX = panel.width/2 - player.maxHealth*2;
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
        g.fillRect(posX, 18, player.maxHealth*4 , 6);
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
            healthBar = ImageIO.read(getClass().getResourceAsStream("/tex/ui/bar.png"));
            staminaBar = ImageIO.read(getClass().getResourceAsStream("/tex/ui/bar2.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // load player data for old gameSave to file
    public void loadSaves(){
        loadList = new ArrayList<>();
        try {
            InputStream stream = getClass().getResourceAsStream("/data/save.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String currentLine = reader.readLine();
            loadCount = 0;
            while (currentLine != null) {
                loadCount++;
                String[] loadLine = new String[8];
                loadLine = currentLine.split(" ");
                loadList.add(loadLine);
                currentLine = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("save.txt not found or possible to read!");
        }
    }

    public void setLoad(){
        String[] save = loadList.get(saveNum - 1);
        player.health = Integer.parseInt(save[1]);
        player.stamina = Integer.parseInt(save[2]);
        panel.levelIndex = Integer.parseInt(save[3]);
        player.cam = Integer.parseInt(save[4]);
        player.y = Integer.parseInt(save[5]);
    }

    public void setSave(){
        String[] save = new String[8];
        save[0] = Integer.toString(saveNum);
        save[1] = Integer.toString(player.health);
        save[2] = Integer.toString(player.stamina);
        save[3] = Integer.toString(panel.levelIndex);
        save[4] = Integer.toString(player.cam);
        save[5] = Integer.toString(player.y);
        if (loadList.size() > saveNum - 1) loadList.set(saveNum - 1, save);
        else loadList.add(saveNum - 1, save);
    }

    // save player data to file
    public void saveGame(){
        try {
            File file = new File("data/save.txt");
            FileWriter writer = new FileWriter(file, false);
            for (String [] saveLine : loadList){
                writer.write(saveLine[0] + " " + saveLine[1] + " " + saveLine[2] + " " + saveLine[3] + " " + saveLine[4] + " " + saveLine[5] + "\n");
            }

            writer.close();
            System.out.println("Game saved!");
          } catch (IOException e) {
            System.out.println("Game could not be saved!");
            e.printStackTrace();
          }
    }
}
