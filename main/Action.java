package main;

import java.util.ArrayList;
import java.util.Random;

public class Action {

    // private int anger;
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

    }


    public void update(){
        if(keyHandler.F){
            SoundEffects.fAina.play();
            player.anger += 20;
            keyHandler.F = false;
        }
        if(keyHandler.H){
            songs.get(random.nextInt(songs.size())).play();
            keyHandler.H = false;
            //TODO ex. spela låten och gör en dansanimation så länge klippet håller på
        }

        if(keyHandler.J){
            //TODO spela animationen en stund inte bara när man håller in
            //TODO och under tiden ska man inte kunna trycka på nåot annat ex gå 
            
            // player.animation = dances.get(random.nextInt(dances.size()));
            // TODO vill växla mellan dem men i nuläget blir det lite knäppt
            player.animation = player.danceAnimation1;
            player.animation.start();  
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
