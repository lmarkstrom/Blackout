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
    public JPanel menuPanel = new JPanel();
    public JPanel pausePanel = new JPanel();
    private JFrame window;
    private Panel panel;
    public JComponent startPage;
    public JComponent loadPage;

    private int width, height;

    private BufferedImage background;
    
    public Menu(Panel panel, JFrame window, JLayeredPane pane){
        this.panel = panel;
        this.window = window;
        width = 32*3*12;
        height = 32*3*8;
        loadImage();
        
        // main menu
        menuPanel.setPreferredSize(new Dimension(width, height));
        menuPanel.setFocusable(true);
        startPage = drawMain();
        loadPage = new JLayeredPane();
        menuPanel.add(startPage);
        menuPanel.add(loadPage);
        loadPage.setVisible(false);
        menuPanel.setBounds(0, 0, width, height);

        // pause menu
        pausePanel.setPreferredSize(new Dimension(width, height));
        pausePanel.setFocusable(true);
        pausePanel.add(drawPause());
        pausePanel.setBounds(0, 0, width, height);
        pausePanel.setOpaque(false); // so that the game still shows behind
        pausePanel.setVisible(false);
        

        // Add menu to window
        pane.add(menuPanel, JLayeredPane.POPUP_LAYER);
        pane.add(pausePanel, JLayeredPane.POPUP_LAYER);
    }

    public void openMainMenu(){
        System.out.println("exitToMain");
        loadPage.setVisible(false);
        startPage.setVisible(true);
        menuPanel.setVisible(true);
    }

    private void loadImage(){
        try{
            background = ImageIO.read(getClass().getResourceAsStream("/tex/bg/menuBg.png"));
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
        JButton startNewGame = new JButton("START GAME");
        startNewGame.setActionCommand("start");
        startNewGame.addActionListener(this);
        startNewGame.setBounds(width/2 - 100, 300, 200, 50);

        // Load saved game
        JButton loadGame = new JButton("LOAD GAME");
        loadGame.setActionCommand("load");
        loadGame.addActionListener(this);
        loadGame.setBounds(width/2 - 100, 350, 200, 50);

        // Exit
        JButton exit = new JButton("EXIT");
        exit.setActionCommand("exit");
        exit.addActionListener(this);
        exit.setBounds(width/2 - 100, 400, 200, 50);
        
        layers.add(bg, JLayeredPane.DEFAULT_LAYER);
        layers.add(title, JLayeredPane.POPUP_LAYER);
        layers.add(startNewGame, JLayeredPane.POPUP_LAYER);
        layers.add(loadGame, JLayeredPane.POPUP_LAYER);
        layers.add(exit, JLayeredPane.POPUP_LAYER);
        layers.setVisible(true);
        
        return layers;
    }

    private JComponent drawPause(){
        // Layers
        JLayeredPane layers = new JLayeredPane();
        layers.setPreferredSize(new Dimension(width, height));
        layers.setOpaque(true); // make background visable

        // Set background
        Color bgColor = new Color(0, 0, 0, 128); // opacity to 50%
        layers.setBackground(bgColor);

        // Main title
        JLabel title = new JLabel("GAME PAUSED", SwingConstants.CENTER);
        Font titleFont = new Font("Arial", Font.BOLD, 80);
        title.setForeground(Color.WHITE);
        title.setFont(titleFont);
        title.setBounds(width/2 - 300, 50, 600, 150);
        
        // Start game button
        JButton continueGame = new JButton("CONTINUE");
        continueGame.setActionCommand("continue");
        continueGame.addActionListener(this);
        continueGame.setBounds(width/2 - 100, 300, 200, 50);

        // Exit to main menu
        JButton exit = new JButton("MAIN MENU");
        exit.setActionCommand("exitGame");
        exit.addActionListener(this);
        exit.setBounds(width/2 - 100, 350, 200, 50);
        
        layers.add(title, JLayeredPane.POPUP_LAYER);
        layers.add(continueGame, JLayeredPane.POPUP_LAYER);
        layers.add(exit, JLayeredPane.POPUP_LAYER);
        layers.setVisible(true);
        
        return layers;
    }

    private JComponent drawLoad(){
        // Layers
        JLayeredPane layers = new JLayeredPane();
        layers.setPreferredSize(new Dimension(width, height));

        // Background image
        Image dimg = background.getScaledInstance(background.getWidth()*3, background.getHeight()*3, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(dimg);
        JLabel bg = new JLabel(icon);
        bg.setBounds(0, 0, width, height);

        // Load title
        JLabel title = new JLabel("CHOOSE LOAD", SwingConstants.CENTER);
        Font titleFont = new Font("Arial", Font.BOLD, 80);
        title.setForeground(Color.WHITE);
        title.setFont(titleFont);
        title.setBounds(width/2 - 300, 50, 600, 150);
        
        for (int i = 0; i < panel.playerData.loadCount; i++){
            JButton newBt = new JButton("LOAD: " + (i + 1));
            newBt.setActionCommand("loadSave");
            newBt.addActionListener(this);
            newBt.setBounds(width/2 - 100, (300 + 50*i), 200, 50);
            layers.add(newBt, JLayeredPane.POPUP_LAYER);
        }

        // Exit
        JButton exit = new JButton("BACK");
        exit.setActionCommand("exitToMain");
        exit.addActionListener(this);
        exit.setBounds(width/2 - 100, 700, 200, 50);
        
        layers.add(bg, JLayeredPane.DEFAULT_LAYER);
        layers.add(title, JLayeredPane.POPUP_LAYER);
        layers.add(exit, JLayeredPane.POPUP_LAYER);
        layers.setVisible(true);
        
        return layers;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionStr = e.getActionCommand();
        if(actionStr.equals("start")){
            System.out.println("start");
            panel.playerData.saveNum = panel.playerData.loadCount + 1;
            panel.playerData.loadCount++;
            menuPanel.setVisible(false);
            panel.startNewGame();
        } else if(actionStr.equals("continue")){
            System.out.println("continue");
            pausePanel.setVisible(false);
            panel.startGame();
        } else if(actionStr.equals("load")){
            System.out.println("load");
            //panel.playerData.loadSaves();
            loadPage = drawLoad();
            menuPanel.add(loadPage);
            startPage.setVisible(false);
            loadPage.setVisible(true);
        } else if(actionStr.equals("exit")){
            System.out.println("exit");
            panel.playerData.saveGame();
            window.dispose();
            window.setVisible(false);
            System.exit(0);
        } else if(actionStr.equals("exitGame")){
            System.out.println("exitGame");
            panel.stopGame();
            panel.playerData.setSave();
            menuPanel.setVisible(true);
            pausePanel.setVisible(false);
        }else if(actionStr.equals("exitToMain")){
            openMainMenu();
        } else if(actionStr.equals("loadSave")){
            System.out.println("loadSave");
            JButton button = (JButton) e.getSource();
            panel.playerData.saveNum = Integer.parseInt(String.valueOf(button.getText().charAt(6)));
            panel.playerData.setLoad();
            menuPanel.setVisible(false);
            loadPage.setVisible(false);
            startPage.setVisible(true);
            panel.startGame();
        }
    }
}
