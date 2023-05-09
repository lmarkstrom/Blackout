package main;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Action class handles player actions such as singing a song and starting dance animations.
 * @author Elsa
 */
public class Action {

    private int minAnger;
    private KeyHandler keyHandler;
    private Player player;
    private Panel panel;
    private boolean busted;
    private ArrayList<SoundEffects> songs = new ArrayList<>();
    private ArrayList<SoundEffects> noise = new ArrayList<>();
    private Random random = new Random();
    // private int ticks;                 
    private ArrayList<Animation> dances = new ArrayList<>();

    private long duration;
    private boolean danceing;
    private int dur = 0;

    /**
     * Constructs an Action object with the specified KeyHandler, Player, and Panel.
     * @param keyHandler the KeyHandler object to handle keyboard input
     * @param player the Player object to control the game's main character
     * @param panel the Panel object that displays the game
     */
    public Action(KeyHandler keyHandler, Player player, Panel panel){
        this.keyHandler = keyHandler;
        this.player = player;
        this.panel = panel;
        minAnger = 0;
        player.anger = minAnger;
        busted = false;
        // ticks = 0;
        songs.add(SoundEffects.bananMelon);
        songs.add(SoundEffects.girlInWorld);
        songs.add(SoundEffects.summer);
        songs.add(SoundEffects.beatbox);
        songs.add(SoundEffects.theNights);
        songs.add(SoundEffects.studenten);
        songs.add(SoundEffects.kattjaKatt);

        noise.add(SoundEffects.rap);
        noise.add(SoundEffects.harklar);

        dances.add(player.danceAnimation1);
        dances.add(player.danceAnimation2);
        dances.add(player.danceAnimation3);

        danceing = false;
    }

    /**
     * Returns a random song from the list of available songs.
     * @return a SoundEffects object representing the chosen song
     */
    public SoundEffects changeSong(){
        return songs.get(random.nextInt(songs.size()));
    }

    /**
     * Updates actions based on the current keyboard input
     */
    public void update(){
        if(keyHandler.F){
            SoundEffects.fAina.play();
            player.anger += 20;
            keyHandler.F = false;
        }

        if(keyHandler.H && !danceing ){
            var song = changeSong();
            duration = song.duration;
            danceing = true;
            panel.startCutScene();
            song.play();
            player.animation = dances.get(random.nextInt(dances.size()));
            player.animation.start();      
            keyHandler.H = false;
        }
        if (danceing){
            dur ++;
        } 
        if (dur > duration-60 && danceing){
            dur = 0;
            panel.stopCutScene();
            danceing = false;
        }

        if(player.anger >= 100 && !busted){
            busted = true;
            panel.enemies.add(new Enemy(panel, 0, panel.height/2, player, 5, true));
        }


        //TODO dethär vill bara göra  om ingget annat ljud spelas upp samtidigt, hur?
        // ticks++;
        // if(ticks >= 60*7){
        //     noise.get(random.nextInt(noise.size())).play();
        //     ticks = 0;
        // } 


    }


}
