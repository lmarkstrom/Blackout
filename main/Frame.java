package main;

import java.awt.image.BufferedImage;

 /**
 * A single frame of an animation.
 * @author Elsa
 */
public class Frame {

    private BufferedImage frame;

    /**
     * Constructs a new Frame with the given image.
     *
     * @param frame the image for this frame
     */
    public Frame(BufferedImage frame) {

        this.frame = frame;
    }

       /**
     * Returns the image for this frame.
     *
     * @return the image for this frame
     */
    public BufferedImage getFrame() {
        return frame;
    }

     /**
     * Sets the image for this frame.
     *
     * @param frame the image for this frame
     */
    public void setFrame(BufferedImage frame) {
        this.frame = frame;
    }

}