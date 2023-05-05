package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

    /**
     * KeyHandler implements KeyListener
     * @author Elsa
     */
public class KeyHandler implements KeyListener {
    boolean left, right, up, down, pause, anyKeyPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        anyKeyPressed = true;

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            left = true;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            right = true;
        }
        if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W){
            up = true;
        }
        if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S){
            down = true;
        }
        // pause game
        if(key == KeyEvent.VK_ESCAPE){
            pause = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
     
        int key = e.getKeyCode();

        anyKeyPressed = false;

        if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
            left = false;
        }

        if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
            right = false;
        }

        if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W){
            up = false;
        }

        if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S){
            down = false;
        }

        if(key == KeyEvent.VK_ESCAPE){
            pause = false;
        }

        //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }


}
