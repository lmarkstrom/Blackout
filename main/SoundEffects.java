package main;

import java.io.*;
import javax.sound.sampled.*;
import java.net.URL;

/**
 * An enumeration of sound effects, each containing a Clip object that can be played.
 */
public enum SoundEffects{

    one("sounds/Vomit-in-Bathroom.wav"),
    two("sounds/Vomiting-Close.wav"),
    sniffle("sounds/Sniffle.wav");

    private Clip clip;
 
    /**
     * Constructs a new SoundEffect object with the specified sound file name.
     * @param soundFileName the name of the sound file
     */
    SoundEffects(String soundFileName){
        try {
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (LineUnavailableException e){
            e.printStackTrace();
        }
    }

    /**
     * Plays the sound effect from the beginning.
     */
    public void play(){
        if (clip.isRunning()) clip.stop();   
        clip.setFramePosition(0); 
        clip.start();    
    }
     
    /**
     * Initializes all SoundEffects objects.
     */
    static void init(){
        values(); 
    }

}
