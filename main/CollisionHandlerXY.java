package main;

public class CollisionHandlerXY {
    private Player player;
    private TileManager tileManager;
    private Panel panel;
    private int groundY;

    public CollisionHandlerXY(Player player, Panel panel, TileManager tileManager) {
        this.player = player;
        this.panel = panel;
        this.tileManager = tileManager;
    }

    private void collisionY() {
        player.collideY = false;

    }

    public void update() {

    }
}
