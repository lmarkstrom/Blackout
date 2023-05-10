package main;

public class BackgroundSound {
    
    private SoundEffects sound;
    private Panel panel;

    public BackgroundSound(Panel panel){
        this.panel = panel;
    }

    public void setSound(){
        //TODO check level type: forrest / traffic
        
            sound = SoundEffects.morningHighwayInDistance;
        // else {
        //     sound = SoundEffects.forest;
        // }

        sound.play();
    }

    public void update(){
        if(!sound.clip.isRunning()){
            sound.clip.setFramePosition(0);
            sound.clip.start();
        }
        
    }
}
