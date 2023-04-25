

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    boolean left, right;

    Player player;
    public KeyHandler(){
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            //player.setDirection(-0.1);
            left = true;
            //player.red = Color.GREEN;
        }
        if (key == KeyEvent.VK_RIGHT) {
            //player.setDirection(0.1);
            right = true;
           //player.red = Color.BLUE;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
     
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }


}
