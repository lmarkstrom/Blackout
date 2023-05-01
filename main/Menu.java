package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel{
    private ActionListener actionListener;
    private Panel panel;
    private Font titleFont;
    private String title = "DRUNK & DISORDERED";

    private JButton button1 = new JButton("Start Game");
    private BufferedImage background;
    
    public Menu(Panel panel){
        this.panel = panel;
        panel.add(button1);
        titleFont = new Font("Arial", Font.BOLD, 50);
        try{
            background = ImageIO.read(getClass().getResourceAsStream("/tex/menuBg.png"));
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }

    private ActionListener action(){
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String actionStr = event.getActionCommand();
                if(actionStr.equals("start")){
                    
                }
            }
        };
        return actionListener;
    }

    public void drawMain(Graphics g){
        g.drawImage(background, 0, 0, panel.width, panel.height, null);
        g.setFont(titleFont);
        g.setColor(Color.WHITE);
        g.drawString(title, (panel.width - g.getFontMetrics().stringWidth(title))/2, 100);        
    }
}
