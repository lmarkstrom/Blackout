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
    public int count;
    public int frameCount = 0;

    public CutScene(Panel panel){
        this.panel = panel;
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

    public void startDonken(){
        System.out.println("enter donken");
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

