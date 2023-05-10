package main;

public class BackgroundSound {
    
    private SoundEffects sound;
    private Panel panel;

    public BackgroundSound(Panel panel){
        this.panel = panel;
    }

    public void setSound(){
        if(panel.levelManager.tileManager.bgIndex == 0){
            sound = SoundEffects.forest;
        }else{
            sound = SoundEffects.morningHighwayInDistance;
        }

        sound.play();
    }

    public void update(){
        if(!sound.clip.isRunning()){
            sound.clip.setFramePosition(0);
            sound.clip.start();
        }
        
    }
}
