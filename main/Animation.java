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
    private boolean stopped;

    /**
     * Animation class handles the animation
     * @param sprite
     * @param frameDelay
     * @param row
     * @param col
     * @author Elsa
     */
    public Animation(BufferedImage sprite, int frameDelay, int row, int col) {
        this.frameDelay = frameDelay;
        this.col = col;
        this.row = row;
        stopped = true;
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
        this.totalFrames = this.frames.size();
    }

    /**
     * @param sprite
     * @param xGrid
     * @param yGrid
     * @return the sprite cutout from spritesheet 
     */
    private BufferedImage getSprite(BufferedImage sprite, int xGrid, int yGrid){
        return sprite.getSubimage(xGrid*width/col, yGrid*height/row, width/col, height/row);
    }

    /**
     * adds frame to frames list of frames
     * changes currentframe to 0
     * @param frame
     * @param duration
     */
    private void addFrame(BufferedImage frame, int duration) {
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    /**
     * @return current frame
     */
    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
    }

    /**
     * start the animation
     */
    public void start() {
        if (stopped) stopped = false;
    }

    /**
     * stop the animation
     */
    public void stop() {
        stopped = true;
    }

    /**
     * reset the animation
     * but not starting it
     */
    public void reset() {
        this.stopped = true;
        this.ticks = 0;
        this.currentFrame = 0;
    }

    /**
     * reset and restart the animation
     */
    public void restart() {
        this.ticks = 0;
        this.currentFrame = 0;
        this.stopped = false;
    }
    /**
     * runs the animation
     */
    public void update() {
        if (!stopped) {
            ticks++;

            if (ticks > frameDelay) {
                ticks = 0;
                currentFrame ++;

                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                }
                else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
            }
        }
    }
    
}
