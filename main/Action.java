package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import main.Panel.STATE;

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
    private int ticks;                 
    private ArrayList<Animation> dances = new ArrayList<>();

    private long duration;
    private boolean danceing;
    private int dur = 0;
    private boolean played;


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
        ticks = 0;
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
        played = false;
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

        if(keyHandler.Q && !danceing ){
            var song = changeSong();
            duration = song.duration;
            danceing = true;
            panel.startDanceState();
            song.play();
            player.animation = dances.get(random.nextInt(dances.size()));
            player.animation.start();      
            keyHandler.Q = false;
        }
        if (danceing){
            dur ++;
        } 
        if (dur > duration-60 && danceing){
            dur = 0;
            panel.stopDanceState();
            danceing = false;
        }

        if(player.anger >= 100 && !busted){
            busted = true;
            panel.enemies.add(new Enemy(panel, 0, panel.height/2, player, 5, true));
        }

        ticks ++;

        if(panel.state == STATE.GAME){
            var light = panel.levelManager.tileManager.objects[4];
            if ( ticks > 60*11){
                try {
                    light = new Object("/tex/obj/redLightYellow.png", 1, 2, "yellowLight");
                    ticks = 0;
                    played = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(ticks > 60*8 && player.animation == player.walkAnimation && !played){
                noise.get(random.nextInt(noise.size())).play();
                played = true;
            } else if(ticks > 60*6){
                try{
                    light = new Object("/tex/obj/redLightRed.png", 1, 2, "redLight");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(ticks > 60*5){
                try {
                    light = new Object("/tex/obj/redLightYellow.png", 1, 2, "yellowLight");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (ticks > 60){
                try{
                    light = new Object("/tex/obj/redLightGreen.png", 1, 2, "greenLight");
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
            panel.levelManager.tileManager.objects[4] = light;
        }   

    }


}
