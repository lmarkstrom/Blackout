package main;

import main.Panel.STATE;

/**
 * Class that handles backgroundSounds
 * 
 * @author Elsa
 */
public class BackgroundSound {
    
    private SoundEffects sound;
    private Panel panel;

    /**
     * Constructs a new BackgroundSound object 
     * @param panel the Panel object in which the background music is played
     */
    public BackgroundSound(Panel panel){
        this.panel = panel;
    }

    /**
     * Sets the sound based on the current background index of the level manager.
     * If the background index is 0, the sound effect is set to "forest", 
     * otherwise it is set to "morningHighwayInDistance". 
     * The sound effect is played after it is set.
     */
    public void setSound(){
        if(panel.levelManager.tileManager.bgIndex == 0){
            sound = SoundEffects.forest;
        }else{
            sound = SoundEffects.morningHighwayInDistance;
        }

        sound.play();
    }

    /**
     * Updates the sound effect based on the current state of the game.
     * If the sound clip is not running, it sets the frame position to 0 and starts the clip. (restart)
     * If the game state is not GAME, it stops the sound clip.
     */
    public void update(){
        if(!sound.clip.isRunning()){
            sound.clip.setFramePosition(0);
            sound.clip.start();
        }
        if(panel.state != STATE.GAME) sound.clip.stop();
        
    }
}
