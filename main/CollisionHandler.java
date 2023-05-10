package main;

import main.Panel.STATE;

public class CollisionHandler{

    // private Entity player;
    private Panel panel;
    private TileManager tileManager;
    private LevelManager levelManager;
    private KeyHandler keyHandler;
    private boolean damageTaken;

    public int map[][];
    public int mapObj[][];

    public CollisionHandler(Panel panel, LevelManager levelManager, KeyHandler keyHandler){
        this.panel = panel;
        this.levelManager = levelManager;
        this.tileManager = levelManager.tileManager;
        this.keyHandler = keyHandler;
        this.map = tileManager.map;
        this.mapObj = tileManager.mapObj;
    }

    private void controllGround(Entity player){
        // setting up values of the player pos
        double playerPosYBottom = player.y + panel.tileSize; 
        int playerPosX = panel.width/2 + player.cam;
        int playerCol = playerPosX/panel.tileSize;
        int bottomRow = (int) playerPosYBottom/panel.tileSize;
        // controll if the player is below or above screen
        if(playerCol < tileManager.mapSizeX && playerCol >= 0 && bottomRow < tileManager.mapSizeY && bottomRow >= 0 ){
            if (playerPosYBottom > panel.height) player.collideY = true;
            if (player.dy >= 0){
                int tileVal = map[playerCol][bottomRow];
                int objVal = mapObj[playerCol][bottomRow];
                if (tileManager.tiles[tileVal].collision == true ) {
                    player.isGrounded = true;
                    if (player.dy >= 0) player.y = (player.y/panel.tileSize)*panel.tileSize;
                } else if(objVal != 0 && tileManager.objects[objVal].collision == true){
                    player.isGrounded = true;
                    if (player.dy >= 0) player.y = (player.y/panel.tileSize)*panel.tileSize;
                } else {
                    player.isGrounded = false;
                }
            }else {
                player.isGrounded = false;
            }
        } else {
            player.isGrounded = false;
        }
    }

    private void controllside(int direction, Entity player){
        // setting up values of the player pos
        double playerPosYTop = player.y + player.gravity;
        double playerPosYBottom = player.y + 50;
        int playerPosX;
        if (direction > 0) playerPosX = panel.width/2 + player.cam + panel.tileSize/2 - 10;
        else playerPosX = panel.width/2 + player.cam - panel.tileSize/2 + 10;
        int playerCol = playerPosX/panel.tileSize;
        int playerRow1 = (int) playerPosYTop/panel.tileSize;
        int playerRow2 = (int) playerPosYBottom/panel.tileSize;
        // controll if the player is below or above screen
        if(playerCol < tileManager.mapSizeX && playerCol >= 0 && playerRow1 < tileManager.mapSizeY && playerRow1 >= 0 && playerRow2 < tileManager.mapSizeY && playerRow2 >= 0){
            if (true){
                int tileVal1 = map[playerCol][playerRow1]; 
                int tileVal2 = map[playerCol][playerRow2];
                int objVal1 = mapObj[playerCol][playerRow1];
                int objVal2 = mapObj[playerCol][playerRow2];
                if (tileManager.tiles[tileVal1].collision == true || tileManager.tiles[tileVal2].collision == true) {
                    player.collideX = true;
                } else if((objVal1 != 0 && tileManager.objects[objVal1].collision == true) || (objVal2 != 0 && tileManager.objects[objVal2].collision == true)){
                    player.collideX = true;
                }else {
                    player.collideX = false;
                }
                if ((playerCol - 1) >= 0) objVal1 = mapObj[playerCol - 1][playerRow2]; // check wider area for collisoon
                if(player.isPlayer && ((objVal1 != 0 && !tileManager.objects[objVal1].actionString.equals("")) || (objVal2 != 0 && !tileManager.objects[objVal2].actionString.equals("")))){
                    checkObjAction(objVal2, player);
                }
            }
        } else {
            player.collideX = false;
        }
    }

    private void controllTop(Entity player){
        // setting up values of the player pos
        double playerPosYTop = player.y - player.jumpHeight; 
        int playerPosX = panel.width/2 + player.cam;
        int playerCol = playerPosX/panel.tileSize;
        int topRow = (int) playerPosYTop/panel.tileSize; 
        // controll if the player is below or above screen
        if(playerCol < tileManager.mapSizeX && playerCol >= 0 && topRow < tileManager.mapSizeY && topRow >= 0 ){
            if (player.dy >= 0){
                int tileVal = map[playerCol][topRow];
                if (tileManager.tiles[tileVal].collision == true) {
                    player.collideTop = true;
                    if (player.dy >= 0) player.y = (player.y/panel.tileSize)*panel.tileSize;
                } else {
                    player.collideTop = false;
                }
            }else player.collideTop = false;
        } else player.collideTop = false;
    }

    public void update(Entity player){
        controllTop(player);
        if(player.isPlayer){
            if (keyHandler.right || keyHandler.left || keyHandler.E){
                controllside(player.direction, player);
            }
        } else{
            controllside(player.direction, player); 
        }
        controllGround(player);
    }

    public void checkObjAction(int objVal, Entity player){
        String action = tileManager.objects[objVal].actionString;
        switch (action){
            case "donken":
                panel.cutScene.startDonken();
                break;
            case "next":
                levelManager.nextLevel();
            case "carCrash":
                if(panel.state == STATE.GAME &&levelManager.tileManager.objects[4].actionString == "redLight"){
                    levelManager.tileManager.objects[5].isVisible = true;
                    player.health -= 100;
                    SoundEffects.crach.play();
                }
            default:
                break;
        }
    }
}
