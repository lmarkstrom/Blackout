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
    harklar("sounds/noise/harklar.wav"),
    rap("sounds/noise/rap.wav"),
    sniffle("sounds/noise/Sniffle.wav"),
    snark("sounds/noise/snark1.wav"),//används ej i nuläget
    snark2("sounds/noise/snark2.wav"),//används ej i nuläget
    snark3("sounds/noise/snark3.wav"), //används ej i nuläget
    crach("sounds/noise/crash.wav"),
    vomiting("sounds/noise/vomiting.wav"),

    //voicelines
    hanSomBörja("sounds/voicelines/hanSomBorja.wav"),//används ej i nuläget
    fAina("sounds/voicelines/fAina.wav"),
    chilla("sounds/voicelines/chilla.wav"), 
    marInteBra("sounds/voicelines/marInteBra.wav"),//används ej i nuläget
    kollaInte("sounds/voicelines/kollaInte.wav"),// används ej i nuläget
    hemmaAntligen("sounds/voicelines/hemmaAntligen.wav"),//används ej i nuläget
    hemmaAntligen2("sounds/voicelines/hemmaAntligen2.wav"),

    //backgrounds
    music("sounds/background/music.wav"),
    forest("sounds/background/forest.wav"),
    morningHighwayInDistance("sounds/background/morningHighwayInDistance.wav");



    public Clip clip;
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
                duration = Objects.requireNonNull(fileInputStream).getChannel().size() / (128*12);
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
