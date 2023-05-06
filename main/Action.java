package main;

public class Action {

    private int anger;
    private int minAnger;
    private KeyHandler keyHandler;
    private Player player;
    private Panel panel;
    private boolean busted;

    public Action(KeyHandler keyHandler, Player player, Panel panel){
        this.keyHandler = keyHandler;
        this.player = player;
        this.panel = panel;
        minAnger = 0;
        anger = minAnger;
        busted = false;

    }

    public void update(){
        if(keyHandler.F){
            anger += 20;
            keyHandler.F = false;
        }

        if(anger >= 100 && !busted){
            busted = true;
            panel.enemies.add(new Enemy(panel, 0, panel.height/2, player, 1));
        }
    }
}
