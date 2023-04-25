package main;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;


public class Animation {

    private int ticks;                 
    private int frameDelay;
    private int currentFrame;
    private int totalFrames;              
    // private List<BufferedImage> frames = new ArrayList<BufferedImage>();
    // private BufferedImage[] frames;
    private List<Frame> frames = new ArrayList<Frame>();  


    public Animation(BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;
        // this.stopped = true;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }
        // this.frames = frames;

        this.ticks = 0;
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        // this.animationDirection = 1;
        this.totalFrames = this.frames.size();
    }

    private void addFrame(BufferedImage frame, int duration) {
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
        // return frames.get(currentFrame);
    }

    public void update() {
        // if (!stopped) {
            ticks++;

            if (ticks > frameDelay) {
                ticks = 0;
                // currentFrame += animationDirection;
                currentFrame ++;

                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                }
                else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
            }
        // }
    }
    
}
