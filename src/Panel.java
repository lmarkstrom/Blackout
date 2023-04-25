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
    private final int tileSize = 32;
    private final int scale = 3;
    private final int maxCol = 12;
    private final int maxRows = 8;
    private final int width = tileSize*scale*maxCol;
    private final int height = tileSize*scale*maxRows;
    private final int FPS = 60; // frames per second

    // thread that runs the game update and drawing
    public Thread thread;

    
    KeyHandler keyHandler = new KeyHandler();
    Player player = new Player(keyHandler);


    public Panel(){
        // setUp game panel
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Buffer to the panel, so it starts painting before the next drawtime
        this.setFocusable(true);
        this.addKeyListener(keyHandler);


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
        // set up time controll for FPS limitation
        double drawInterval = 1000000000/FPS; // devides the the frames from nanosecouns
        double timeToDraw = System.nanoTime() + drawInterval;
        double timePassed = System.nanoTime();

        // game loop
        while(thread != null){
            // update limitation to FPS so it only updates FPS times/secound
            if(System.nanoTime() - timePassed > 1000000000) {
                timePassed = System.nanoTime();
            }
            try {
                double remainingTime = timeToDraw - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if(remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeToDraw += drawInterval;

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
        player.update();
      
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

        player.draw(g);

        // dispose graphic
        g.dispose();
    }
}
