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
    private LevelManager levelManager;
    public CollisionHandler collisionHandler;
    private Menu menu;
    public PlayerData playerData;
    public CutScene cutScene;
    private Action action;

    public enum STATE{
        MENU,
        GAME,
        CUTSCENE, 
        DANCE
    }

    public STATE state;
    private Image backgroundImage;

    public Panel(){
        state = STATE.MENU;
        // setUp game panel
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Buffer to the panel, so it starts painting before the next drawtime
        this.setFocusable(true);
        this.addKeyListener(keyHandler);
        this.enemies = new ArrayList<>();
        this.player = new Player(keyHandler, this);
        this.levelManager = new LevelManager(this, player);
        this.collisionHandler = new CollisionHandler(this, levelManager, keyHandler);
        this.playerData = new PlayerData(this, levelManager);
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

    public void resetGame(){
        state = STATE.GAME;
        cutScene.cutSceneDone = true;
        player.isBusted = false;
        player.anger = 0;
        for (Enemy enemy : enemies) enemy.isChasing = false;
        deleteEnemies();
        levelManager.resetGame();
        player.cam = 0;
        player.y = height/2;
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
        cutScene.cutSceneDone = false;
        cutScene.count = 0;
        cutScene.frameCount = 0;
    }

    public void startDanceState(){
        state = STATE.DANCE;
    }

    public void stopDanceState(){
        state = STATE.GAME;
    }

    public void startNewGame(){
        state = STATE.CUTSCENE;
        cutScene.cutSceneDone = false;
        cutScene.count = 0;
        cutScene.frameCount = 0;
        player.cam = 0;
        player.y = height/2;
        levelManager.levelIndex = 0;
        cutScene.getFrames("cutscenes/introScene.gif", 29);
    }

    public void stopGame(){
        state = STATE.MENU;
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
                levelManager.update();
                for (Enemy enemy : enemies) {
                    if(enemy.isChasing){
                        enemy.update();
                    } 
                }
                player.update();
            }

            // LEVEL TEST (tryck "L" för att få ny bana)
            if (keyHandler.L) {
                this.levelManager.levelIndex = 1;
                if(levelManager.levelIndex > levelManager.maxLevel) this.levelManager.levelIndex--;
                levelManager.setLevel(); 
                keyHandler.L = false;
            }
        }    
        for (Enemy enemy : enemies) {
            if(!enemy.isChasing)
            {
                enemy.update();
            } 
        }
        
        action.update();
        player.updateAnimation();
    }

    /*
     * PaintComponent method thats calls super method paintComponent, draws all graphic
     */
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);

        // create graphic object
        Graphics2D g = (Graphics2D) graphics;

        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

        // call method to paint player and map here:
        levelManager.draw(g);
        //tileManager.draw(g);
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        player.draw(g);
        playerData.draw(g);
        
        if(!cutScene.cutSceneDone && state == STATE.CUTSCENE){
            cutScene.draw(g);
        }
        
        // dispose graphic
        g.dispose();
    }
}
