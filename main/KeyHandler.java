package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

    /**
     * KeyHandler implements KeyListener
     * @author Elsa
     */
public class KeyHandler implements KeyListener {
    boolean left, right, up;

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            left = true;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            right = true;
        }
        if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W){
            up = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
     
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
            left = false;
        }

        if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
            right = false;
        }

        if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W){
            up = false;
        }

        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }


}
