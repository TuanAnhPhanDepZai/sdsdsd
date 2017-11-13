/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author phant
 */
public class PanelImage extends JPanel{
    private JLabel label = new JLabel();
    public PanelImage(){
        
        try {
            
            BufferedImage icon = ImageIO.read(new File("E:\\icon.PNG")) ;
            ImageIcon imageIcon  = new ImageIcon(icon.getScaledInstance(250, 250, icon.SCALE_SMOOTH)) ;
            label.setIcon(imageIcon);
            this.add(label);
            this.setSize(250,250);
            this.setVisible(true);
            
        } catch (IOException ex) {
            Logger.getLogger(PanelImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
        new PanelImage();
    }
}
