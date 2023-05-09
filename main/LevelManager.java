package main;

import java.awt.Graphics;

public class LevelManager {
    
    TileManager tileManager;
    Player player;
    Panel panel;

    public int levelIndex;
    private String[] levelPath = new String[] {"/data/map.txt", "/data/map2.txt"};
    private String[] bgPath = new String[] {"/tex/bg/gameBg.png", "/tex/bg/gameBg.png"};

    public int maxLevel = levelPath.length - 1;


    public LevelManager(Panel panel, Player player){
        this.panel = panel;
        this.player = player;
        this.tileManager = new TileManager(panel, player, levelPath[levelIndex], bgPath[levelIndex]);
    }

    public void setLevel(){
        levelIndex = panel.levelIndex;
        panel.deleteEnemies();
        tileManager = new TileManager(panel, player, levelPath[levelIndex], bgPath[levelIndex]);
        panel.collisionHandler = new CollisionHandler(panel, this, panel.keyHandler);
    }

    public void resetGame(){
        tileManager = new TileManager(panel, player, levelPath[levelIndex], bgPath[levelIndex]);
    }

    public void update(){
        if(panel.levelIndex != levelIndex){
            setLevel();
        }
        tileManager.update();
    }

    public void draw(Graphics g){
        tileManager.draw(g);
    }
}
