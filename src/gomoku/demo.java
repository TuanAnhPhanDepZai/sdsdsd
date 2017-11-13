/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

import javax.swing.JFrame;

/**
 *
 * @author beyeuphodau
 */
public class demo extends JFrame {
    
    private PanelImage panelImage ;
    
    public demo(){
        panelImage = new PanelImage();
        this.add(panelImage);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
    public static void main(String[] args) {
        new demo();
    }
}
