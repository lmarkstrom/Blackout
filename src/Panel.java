import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Class that extends panel that contains the game graphic and runs the main thread.
 * 
 * @author Linus Markström
 * @version 2023-04-21
 */
public class Panel extends JPanel implements Runnable{
    // fields
    private final int WIDTH = 400;
    private final int HEIGHT = 400;

    // thread that runs the game update and drawing
    public Thread thread;

    public Panel(){
        // setUp game panel
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Buffer to the panel, so it starts painting before the next drawtime
        this.setFocusable(true);
    }

    /*
     * Sets up the thread that runs the game
     */
    public void mainThread(){
        thread = new Thread(this);
        thread.start();
    }

    /*
     * Run method, part of runnable, contains the gameloop and calls update and repaint
     */
    @Override
    public void run() {
        // game loop
        while(thread != null){
            // update for position and physics
            update();
            // update for painting graphics
            repaint();
        }
    }

    /*
     * Update method that updates data outside the graphics like physics
     */
    public void update() {
        // call method that should be updated here:
    }

    /*
     * PaintComponent method thats calls super method paintComponent, draws all graphic
     */
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        // create graphic object
        Graphics2D g = (Graphics2D) graphics;

        // draw test
        g.setColor(Color.WHITE);
        g.fillRect(100, 100, 200, 200);
        g.drawString("String", 10, 14);

        // call method to paint player and map here:

        // dispose graphic
        g.dispose();
    }
}
