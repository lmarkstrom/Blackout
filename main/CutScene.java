package main;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CutScene{
    private Thread thread;
    private Graphics g;
    public int n;
    private boolean called;
    public boolean cutSceneDone = false;
    private Panel panel;
    private ArrayList<BufferedImage> frames;
    public int count;
    private int frameCount = 0;

    public CutScene(Panel panel){
        this.panel = panel;
    }
    
    public void introScene(){
        called = true;
        getFrames("cutscenes/introScene.gif");
        for (BufferedImage frame : frames){
            //g.drawImage(frame, 0, 0, panel.width, panel.height, null);
            System.out.println("1");
            
        }
        cutSceneDone = true;
    }

    public void getFrames(String fileName){
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

    public void draw (Graphics g){
        g.drawImage(frames.get(frameCount), 0, 0, panel.width, panel.height, null);
        if (count > 29) {
            frameCount++;
            count = 0;
        }
        if (frameCount >= frames.size()) {
            cutSceneDone = true;
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
        count++;
    }       
}

