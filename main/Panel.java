package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Class that extends panel that contains the game graphic and runs the main thread.
 * 
 * @author Linus Markström
 * @version 2023-04-21
 */
public class Panel extends JPanel implements Runnable{
    // fields
    private final int tilePixels = 32;
    public final int scale = 3;
    public final int tileSize = tilePixels*scale;
    public final int maxCol = 12;
    public final int maxRows = 8;
    public final int width = tileSize*maxCol;
    public final int height = tileSize*maxRows;
    public final int FPS = 60;

    // thread that runs the game update and drawing
    public Thread thread;

    private KeyHandler keyHandler = new KeyHandler();

    private Player player = new Player(keyHandler, this);

    private TileManager tileManager = new TileManager(this, player);

    private CollisionHandler collisionHandler = new CollisionHandler(player, this, tileManager);

    private Image backgroundImage;
    
    public Panel(){
        // setUp game panel
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Buffer to the panel, so it starts painting before the next drawtime
        this.setFocusable(true);
        this.addKeyListener(keyHandler);
        try { 
            backgroundImage = ImageIO.read(new File("tex/newBGImage.jpg"));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
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
        // FPS setup
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        double timePassed = System.nanoTime();
        // game loop
        while(thread != null && !player.isInMenu){
            // FPS calculator
            if(System.nanoTime() - timePassed > 1000000000) {
                timePassed = System.nanoTime();
            }
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            nextDrawTime += drawInterval;
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
        tileManager.update();
        collisionHandler.update();
        player.update();
      
    }

    /*
     * PaintComponent method thats calls super method paintComponent, draws all graphic
     */
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        // create graphic object
        Graphics2D g = (Graphics2D) graphics;
        
        // Draw background
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

        // draw test
        g.setColor(Color.WHITE);
        g.fillRect(100, 100, 200, 200);
        g.drawString("String", 10, 14);

        // call method to paint player and map here:
        tileManager.draw(g);
        player.draw(g);

        // dispose graphic
        g.dispose();
    }
}
