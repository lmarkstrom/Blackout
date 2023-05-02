package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Menu implements ActionListener{
    private JPanel menuPanel = new JPanel();
    private JFrame window;
    private Panel panel;
    private JLayeredPane pane;
    private Main main;

    private int width, height;

    private BufferedImage background;
    
    public Menu(Panel panel, JFrame window, JLayeredPane pane){
         
        this.pane = pane;
        this.panel = panel;
        this.window = window;
        width = 32*3*12;
        height = 32*3*8;
        loadImage();
        
        menuPanel.setPreferredSize(new Dimension(width, height));
        menuPanel.setFocusable(true);
        
        menuPanel.add(drawMain());
        menuPanel.setBounds(0, 0, width, height);

        // Add menu to window

        /* 
        JButton bt = new JButton("Start");
        bt.setBounds(100, 100, 100, 100);
        bt.setFocusable(false);
        bt.setActionCommand("start");
        bt.addActionListener(this);

        JButton bt2 = new JButton("Stop");
        bt2.setBounds(200, 100, 100, 100);
        bt2.setFocusable(false);
        bt2.setActionCommand("start1");
        bt2.addActionListener(this);

        pane.add(bt, JLayeredPane.POPUP_LAYER);
        pane.add(bt2, JLayeredPane.POPUP_LAYER);
        */

        pane.add(menuPanel, JLayeredPane.POPUP_LAYER);
    }

    private void loadImage(){
        try{
            background = ImageIO.read(getClass().getResourceAsStream("/tex/menuBg.png"));
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private JComponent drawMain(){
        // Layers
        JLayeredPane layers = new JLayeredPane();
        layers.setPreferredSize(new Dimension(width, height));

        // Background image
        Image dimg = background.getScaledInstance(background.getWidth()*3, background.getHeight()*3, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(dimg);
        JLabel bg = new JLabel(icon);
        bg.setBounds(0, 0, width, height);

        // Main title
        JLabel title = new JLabel("GAME TITLE", SwingConstants.CENTER);
        Font titleFont = new Font("Arial", Font.BOLD, 80);
        title.setForeground(Color.WHITE);
        title.setFont(titleFont);
        title.setBounds(width/2 - 300, 50, 600, 150);
        
        // Start game button
        JButton startNewButton = new JButton("START GAME");
        startNewButton.setActionCommand("start");
        startNewButton.addActionListener(this);
        startNewButton.setBounds(width/2 - 100, 300, 200, 50);

        // Load saved game
        JButton loadGameButton = new JButton("LOAD GAME");
        loadGameButton.setActionCommand("load");
        loadGameButton.addActionListener(this);
        loadGameButton.setBounds(width/2 - 100, 350, 200, 50);

        // Exit
        JButton exit = new JButton("EXIT");
        exit.setActionCommand("exit");
        exit.addActionListener(this);
        exit.setBounds(width/2 - 100, 400, 200, 50);
        
        layers.add(bg, JLayeredPane.DEFAULT_LAYER);
        layers.add(title, JLayeredPane.POPUP_LAYER);
        layers.add(startNewButton, JLayeredPane.POPUP_LAYER);
        layers.add(loadGameButton, JLayeredPane.POPUP_LAYER);
        layers.add(exit, JLayeredPane.POPUP_LAYER);
        layers.setVisible(true);
        
        return layers;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionStr = e.getActionCommand();
        if(actionStr.equals("start")){
            System.out.println("start");
            menuPanel.setVisible(false);
            panel.startGame();
        } else if(actionStr.equals("load")){
            System.out.println("load");
            
        } else if(actionStr.equals("exit")){
            System.out.println("exit");
            window.dispose();
            window.setVisible(false);
            System.exit(0);
        } else if(actionStr.equals("start1")){
            panel.stopGame();
        }
    }
}
