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

        if (key == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if(key == KeyEvent.VK_UP){
            up = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
     
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT){
            left = false;
        }

        if(key == KeyEvent.VK_RIGHT){
            right = false;
        }

        if(key == KeyEvent.VK_UP){
            up = false;
        }

        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }


}
