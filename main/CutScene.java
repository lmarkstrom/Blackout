package main;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CutScene{
    public int n;
    public boolean cutSceneDone = true;
    private Panel panel;
    private ArrayList<BufferedImage> frames;
    private KeyHandler keyHandler;
    public int count;
    private int frameDur = 29;
    public int frameCount = 0;

    public CutScene(Panel panel, KeyHandler keyHandler){
        this.keyHandler = keyHandler;
        this.panel = panel;
    }

    public void getFrames(String fileName, int dur){
        this.frameDur = dur;
        ArrayList<BufferedImage> frames = new ArrayList<>();
        try {
            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            File input = new File(fileName);
            ImageInputStream stream = ImageIO.createImageInputStream(input);
            reader.setInput(stream);
            int count = reader.getNumImages(true);
            for (int index = 0; index < count; index++) {
                BufferedImage frame = reader.read(index);
                frames.add(frame);
            }
        } catch (IOException e) {
            System.out.println("File not found for cutscne: " + e);
        }
        this.frames = frames;
    }

    public void startDonken(){
        System.out.println("enter donken");
        if(keyHandler.E){
            panel.startCutScene("cutscenes/donkenScene.gif", 10);
        } 
    }

    public void draw (Graphics g){
        g.drawImage(frames.get(frameCount), 0, 0, panel.width, panel.height, null);
        if (count > frameDur) {
            frameCount++;
            count = 0;
        }
        if (frameCount >= frames.size()) {
            cutSceneDone = true;
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
            panel.startGame();
        }
        count++;
    }     
}

