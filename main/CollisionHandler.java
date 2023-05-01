package main;

public class CollisionHandler{

    private Player player;
    private Panel panel;
    private TileManager tileManager;

    public int map[][];

    public CollisionHandler(Player player, Panel panel, TileManager tileManager){
        this.player = player;
        this.panel = panel;
        this.tileManager = tileManager;
        map = tileManager.map;
    }

    private void controllGround(){
        // setting up values of the player pos
        double playerPosYBottom = player.y + panel.tileSize + player.speed + player.gravity;
        int playerPosX = panel.width/2 + player.x + panel.tileSize/2;
        int playerCol = playerPosX/panel.tileSize;
        int bottomRow = (int) playerPosYBottom/panel.tileSize;
        player.collideY = false;
        // controll if the player is below or above screen
        if(playerCol < tileManager.mapSizeX && playerCol >= 0 && bottomRow < tileManager.mapSizeY && bottomRow >= 0 ){
            if (playerPosYBottom > panel.height) player.collideY = true;
            if (player.dy >= 0){
                int tileVal = map[playerCol][bottomRow];
                if (tileManager.tiles[tileVal].collision == true) player.isGrounded = true;
                else player.isGrounded = false;
            }else player.isGrounded = false;
        } else player.isGrounded = false;
    }

    private void controllside(){
        
    }

    public void update(){
        controllGround();
    }
}
