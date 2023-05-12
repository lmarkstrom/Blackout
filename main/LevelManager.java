package main;

import java.awt.Graphics;

public class LevelManager {
    
    TileManager tileManager;
    Player player;
    Panel panel;

    public int levelIndex;
    public int currentIndex;
    private String[] levelPath = new String[] {"/data/map.txt", "/data/map2.txt", "/data/map3.txt"};
    private int[] bgIndex = new int[] {0, 0, 1};

    public int maxLevel = levelPath.length - 1;


    public LevelManager(Panel panel, Player player){
        this.panel = panel;
        this.player = player;
        this.currentIndex = levelIndex;
        this.tileManager = new TileManager(panel, player, levelPath[levelIndex], bgIndex[levelIndex]);
    }

    public void nextLevel(){
        if((levelIndex + 1) > maxLevel){
            panel.winGame();
        } else{
            if(levelIndex < maxLevel) levelIndex++;
            panel.deleteEnemies();
            player.cam = 0;
            player.y = panel.height/2;
            this.currentIndex = levelIndex;
            tileManager = new TileManager(panel, player, levelPath[levelIndex], bgIndex[levelIndex]);
            panel.collisionHandler = new CollisionHandler(panel, this, panel.keyHandler);
            panel.startCutScene("cutscenes/nextLvl.gif", 5);
        }
    }

    public void setLevel(){
        panel.deleteEnemies();
        this.currentIndex = levelIndex;
        tileManager = new TileManager(panel, player, levelPath[levelIndex], bgIndex[levelIndex]);
        panel.collisionHandler = new CollisionHandler(panel, this, panel.keyHandler);
    }

    public void resetGame(){
        this.currentIndex = levelIndex;
        tileManager = new TileManager(panel, player, levelPath[levelIndex], bgIndex[levelIndex]);
    }

    public void update(){
        if(levelIndex != currentIndex){
            setLevel();
        }
        tileManager.update();
    }

    public void draw(Graphics g){
        tileManager.draw(g);
    }
}
