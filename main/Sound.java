package main;
import java.io.*;
import javax.sound.sampled.*;

public class Sound {

    File file;
    AudioInputStream audioStream;
    Clip clip;

  

    public void loadSounds()throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        file = new File("sounds/Vomiting-Close.wav"); 
        audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);       
    }

    public void play(){
        clip.setMicrosecondPosition(0);
        clip.start();

    }

    
}