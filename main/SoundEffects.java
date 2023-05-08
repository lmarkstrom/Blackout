package main;

import java.io.*;
import javax.sound.sampled.*;
import java.net.URL;
import java.util.Objects;

/**
 * An enumeration of sound effects, each containing a Clip object that can be played.
 * @author Elsa
 * 
 */
public enum SoundEffects{

    sniffle("sounds/Sniffle.wav"),
    bananMelon("sounds/BananMelon.wav"),
    girlInWorld("sounds/girlInWorld.wav"),
    summer("sounds/summer.wav"),
    ramlar("sounds/ramlar.wav"),
    ramlar2("sounds/ramlar2.wav"),
    ramlar3("sounds/ramlar3.wav"), 
    harklar("sounds/harklar.wav"),//används ej i nuläget
    rap("sounds/rap.wav"),//används ej i nuläget
    studenten("sounds/studenten.wav"),
    beatbox("sounds/epicBeatbox.wav"),
    theNights("sounds/theNights.wav"),
    fAina("sounds/fAina.wav"),
    kattjaKatt("sounds/katjaKatt.wav"),
    chilla("sounds/chilla.wav"), 
    kollaInte("sounds/kollaInte.wav"),
    snark("sounds/snark1.wav"),
    snark2("sounds/snark2.wav"),
    snark3("sounds/snark3.wav"), 
    hemmaAntligen("sounds/hemmaAntligen.wav"),
    hemmaAntligen2("sounds/hemmaAntligen2.wav"),
    hopp("sounds/Hopp1.wav"),
    hopp2("sounds/Hopp2.wav"),
    hopp3("sounds/Hopp3.wav"),
    hopp4("sounds/Hopp4.wav"), 
    marInteBra("sounds/marInteBra.wav"),
    hanSomBörja("sounds/hanSomBorja.wav");//används ej i nuläget


    private Clip clip;
    public FileInputStream fileInputStream = null;
    public long duration = 0;
 
    /**
     * Constructs a new SoundEffect object with the specified sound file name.
     * @param soundFileName the name of the sound file
     */
    SoundEffects(String soundFileName){
        try {
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

            try {
                fileInputStream = new FileInputStream(soundFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                duration = Objects.requireNonNull(fileInputStream).getChannel().size() / 128;
            } catch (IOException e) {
                e.printStackTrace();
            }
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
