package main;

import java.io.*;
import javax.sound.sampled.*;
import java.net.URL;

/**
 * An enumeration of sound effects, each containing a Clip object that can be played.
 */
public enum SoundEffects{

    sniffle("sounds/Sniffle.wav"),
    bananMelon("sounds/BananMelon.wav"),
    girlInWorld("sounds/GirlInWorld.wav"),
    summer("sounds/summer.wav"),
    ramlar("sounds/ramlar.wav"),
    ramlar2("sounds/ramlar2.wav"),
    ramlar3("sounds/ramlar3.wav"), 
    harklar("sounds/harklar.wav"),
    rap("sounds/rap.wav"),
    studenten("sounds/studenten.wav"),
    beatbox("sounds/epicBeatbox.wav"),
    theNights("sounds/theNights.wav"),
    fAina("sounds/fAina.wav"),
    kattjaKatt("sounds/katjaKatt.wav"),
    chilla("sounds/chilla.wav");

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
