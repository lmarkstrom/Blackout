import java.awt.Color;
import java.awt.Graphics;

public class Player  {
    
    private int x;
    private int y;
    public Color red = Color.RED;
    KeyHandler keyHandler;

    public Player(KeyHandler keyHandler){
        this.keyHandler = keyHandler;
        x = 100;
        y = 100;
    }

    public void update(){
       if (keyHandler.right == true){
            x += 1;
            keyHandler.right = false;
       } else if(keyHandler.left == true){
            x -= 1;
            keyHandler.left = false;
       }
    }

    public void draw(Graphics g){

        g.setColor(red);
        g.drawRect(x, y, 30, 30);

    }
}
