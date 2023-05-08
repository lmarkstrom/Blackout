package main;

public class CollisionHandler{

    // private Entity player;
    private Panel panel;
    private TileManager tileManager;
    private KeyHandler keyHandler;
    private boolean damageTaken;

    public int map[][];
    public int mapObj[][];

    public CollisionHandler(Panel panel, TileManager tileManager, KeyHandler keyHandler){
        this.panel = panel;
        this.tileManager = tileManager;
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
                } else if(player.isPlayer && ((objVal1 != 0 && tileManager.objects[objVal1].donkeCutscene == true) || (objVal2 != 0 && tileManager.objects[objVal2].donkeCutscene == true))){
                    panel.cutScene.startDonken();
                }else {
                    player.collideX = false;
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
            if (keyHandler.right || keyHandler.left){
                controllside(player.direction, player);
            }
        } else{
            controllside(player.direction, player); 
        }
        controllGround(player);
    }
}
