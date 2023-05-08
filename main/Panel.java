package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
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
    public KeyHandler keyHandler = new KeyHandler();
    public ArrayList<Enemy> enemies;
    public Player player;
    private TileManager tileManager;
    public CollisionHandler collisionHandler;
    private Menu menu;
    public PlayerData playerData;
    public CutScene cutScene;
    private Action action;

    public enum STATE{
        MENU,
        GAME,
        CUTSCENE
    }

    public STATE state;
    private Image backgroundImage;

    private String[] level;
    public int levelIndex;

    public Panel(){
        state = STATE.MENU;
        // setUp game panel
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Buffer to the panel, so it starts painting before the next drawtime
        this.setFocusable(true);
        this.addKeyListener(keyHandler);
        

        this.level = new String[] {"/data/map.txt", "/data/map2.txt"};
        this.levelIndex = 0;
        this.enemies = new ArrayList<>();
        this.player = new Player(keyHandler, this);
        this.tileManager = new TileManager(this, player, level[0], "/tex/bg/gameBg.png");
        this.collisionHandler = new CollisionHandler(this, tileManager, keyHandler);
        this.playerData = new PlayerData(this);
        this.cutScene = new CutScene(this, keyHandler);
        this.action = new Action(keyHandler, player, this);
        try {
            this.backgroundImage = ImageIO.read(getClass().getResourceAsStream("/tex/bg/gameBg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        playerData.loadSaves();
        SoundEffects.init();
    }

    public void addPause(Menu menu){
        this.menu = menu;
    }


    
    public void deleteEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            enemies.remove(i);
        }
    }

    /*
     * Sets up the thread that runs the game
     */
    public void mainThread(){
        thread = new Thread(this);
        
        thread.start();
    }

    public void startGame(){
        state = STATE.GAME;
        cutScene.cutSceneDone = true;
        System.out.println(state);
    }

    public void stopCutScene(){
        state = STATE.GAME;
    }
  
    public void startCutScene(String path, int dur){
        state = STATE.CUTSCENE;
        cutScene.cutSceneDone = false;
        cutScene.count = 0;
        cutScene.frameCount = 0;
        cutScene.getFrames(path, dur);
    }
    public void startCutScene(){
        state = STATE.CUTSCENE;
    }

    public void startNewGame(){
        state = STATE.CUTSCENE;
        cutScene.cutSceneDone = false;
        cutScene.count = 0;
        cutScene.frameCount = 0;
        player.cam = 0;
        player.y = height/2;
        cutScene.getFrames("cutscenes/introScene.gif", 29);
        System.out.println(state);
    }

    public void stopGame(){
        state = STATE.MENU;
        System.out.println(state);
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
        while(thread != null){
            // update for position and physics
            update();
            // update for painting graphics
            repaint();
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
        }
    }

    /*
     * Update method that updates data outside the graphics like physics
     */
    public void update() {
        // call method that should be updated here:
        playerData.update();
        if (state == STATE.GAME){
            if(keyHandler.pause) {
                state = STATE.MENU;
                menu.pausePanel.setVisible(true);
            }else{
                tileManager.update();
                
                if (state != STATE.CUTSCENE) player.update();
            }

            // LEVEL TEST (tryck "L" för att få ny bana)
            if (keyHandler.L) {
                this.levelIndex = (this.levelIndex+1)%level.length;
                updateLevel();
                keyHandler.L = false;
            }
        }    
        for (Enemy enemy : enemies) {
            enemy.update();
        }
        action.update();
        player.updateAnimation();
    }

    private void updateLevel() {
        deleteEnemies();
        this.tileManager = new TileManager(this, player, level[levelIndex], "/tex/bg/gameBg.png");
        this.collisionHandler = new CollisionHandler(this, tileManager, keyHandler);
        // player.x = width/2;
        // player.y = height/2;
    }

    /*
     * PaintComponent method thats calls super method paintComponent, draws all graphic
     */
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        // create graphic object
        Graphics2D g = (Graphics2D) graphics;

        
        
        if(!cutScene.cutSceneDone && state == STATE.CUTSCENE){
            cutScene.draw(g);
        } else if (cutScene.cutSceneDone){
            // Draw background
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

            // call method to paint player and map here:
            tileManager.draw(g);
            for (Enemy enemy : enemies) {
                enemy.draw(g);
            }
            player.draw(g);
            playerData.draw(g);
        }
        
        // dispose graphic
        g.dispose();
    }
}
