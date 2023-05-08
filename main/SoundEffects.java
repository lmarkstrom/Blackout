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

    //songs 
    bananMelon("sounds/songs/BananMelon.wav"),
    girlInWorld("sounds/songs/girlInWorld.wav"),
    kattjaKatt("sounds/songs/katjaKatt.wav"),
    beatbox("sounds/songs/epicBeatbox.wav"),
    theNights("sounds/songs/theNights.wav"),
    studenten("sounds/songs/studenten.wav"),
    summer("sounds/songs/summer.wav"),

    //movement
    hopp("sounds/movement/Hopp1.wav"),
    hopp2("sounds/movement/Hopp2.wav"),
    hopp3("sounds/movement/Hopp3.wav"),
    hopp4("sounds/movement/Hopp4.wav"), 
    ramlar("sounds/movement/ramlar.wav"),
    ramlar2("sounds/movement/ramlar2.wav"),
    ramlar3("sounds/movement/ramlar3.wav"), 

    //noise
    harklar("sounds/noise/harklar.wav"),//används ej i nuläget
    rap("sounds/noise/rap.wav"),//används ej i nuläget
    sniffle("sounds/noise/Sniffle.wav"),
    snark("sounds/noise/snark1.wav"),
    snark2("sounds/noise/snark2.wav"),
    snark3("sounds/noise/snark3.wav"), 

    //voicelines
    hanSomBörja("sounds/voicelines/hanSomBorja.wav"),//används ej i nuläget
    fAina("sounds/voicelines/fAina.wav"),
    chilla("sounds/voicelines/chilla.wav"), 
    marInteBra("sounds/voicelines/marInteBra.wav"),
    kollaInte("sounds/voicelines/kollaInte.wav"),
    hemmaAntligen("sounds/voicelines/hemmaAntligen.wav"),//används ej i nuläget
    hemmaAntligen2("sounds/voicelines/hemmaAntligen2.wav");//används ej i nuläget

    



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
