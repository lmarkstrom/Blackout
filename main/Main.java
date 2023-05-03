package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

/**
 * Main class that uses main function to start the mainthread and sets up the gamewindow.
 * 
 * @author Linus Markström
 * @version 2023-04-21
 * 
 */
class Main{ 
    public static void main(String[] args) {
        // window object
        JFrame window = new JFrame();
        JLayeredPane pane = new JLayeredPane();
        Panel panel = new Panel();

        // panel object
        pane.setPreferredSize(new Dimension(panel.width, panel.height));
        panel.setBounds(0, 0, panel.width, panel.height);
        panel.mainThread();
        Menu menu = new Menu(panel, window, pane);
        panel.addPause(menu);
        pane.add(panel, JLayeredPane.DEFAULT_LAYER); // Adds panel to the window object
        
        
        // window setUp
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D PLATFORM GAME"); // Window title
        window.add(pane);
        window.pack();
        window.setLocationRelativeTo(null); // Places the window in the middle of the screen
        window.setVisible(true);

        // start mainthread
        
    }
}