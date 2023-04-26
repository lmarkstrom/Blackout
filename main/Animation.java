package main;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;


public class Animation {

    private int ticks;                 
    private int frameDelay;
    private int currentFrame;
    private int totalFrames;              
    private BufferedImage[] walkingFrames;
    private List<Frame> frames = new ArrayList<Frame>();  
    private int col, row, height, width;



    public Animation(BufferedImage sprite, int frameDelay, int row, int col) {
        this.frameDelay = frameDelay;
        this.col = col;
        this.row = row;
        height = sprite.getHeight();
        width = sprite.getWidth();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
            }
        }
        //TODO skapa inte detta manuellt utan i for-looparna på något sätt 
        walkingFrames = new BufferedImage[] {getSprite(sprite, 0, 0), getSprite(sprite, 0, 1)};


        for (int i = 0; i < walkingFrames.length; i++) {
            addFrame(walkingFrames[i], frameDelay);
        }

        this.ticks = 0;
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        // this.animationDirection = 1;
        this.totalFrames = this.frames.size();
    }

    private BufferedImage getSprite(BufferedImage sprite, int xGrid, int yGrid){
        return sprite.getSubimage(xGrid*width/col, yGrid*height/row, width/col, height/row);
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
