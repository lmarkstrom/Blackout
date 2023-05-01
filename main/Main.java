package main;

import javax.swing.JFrame;

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

        // panel object
        Panel panel = new Panel();
        
        // window setUp
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D PLATFORM GAME"); // Window title
        window.add(panel); // Adds panel to the window object
        window.pack();
        window.setLocationRelativeTo(null); // Places the window in the middle of the screen
        window.setVisible(true);

        // start mainthread
        panel.mainThread();
    }
}