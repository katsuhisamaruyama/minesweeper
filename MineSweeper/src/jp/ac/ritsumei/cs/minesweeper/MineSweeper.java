
package jp.ac.ritsumei.cs.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MineSweeper extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
    
    private static final long serialVersionUID = 4461919141743196800L;
    private Land land;
    private JButton startButton;
    private GameInfo info;
    public static final int COLUMNS = 16;
    
    public static void main(String argv[]) {
        MineSweeper mineSweeper = new MineSweeper();
        mineSweeper.init();
    }
    
    public void init() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        startButton = new JButton("Start");
        info = new GameInfo();
        panel.add(startButton);
        panel.add(info);
        
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout(5, 0));
        land = new Land(info, COLUMNS, COLUMNS);
        cp.add("North", panel);
        cp.add("Center", land);
        pack();
        setVisible(true);
        
        startButton.addActionListener(this);
        land.addMouseListener(this);
        land.addMouseMotionListener(this);
        
        land.gameStart();
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            land.gameStart();
        }
    }
    
    public void mousePressed(MouseEvent e) {
        if (!land.isGameOver()) {
            int mx = e.getX();
            int my = e.getY();
            
            e.consume();
            if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
                land.leftPressed(mx, my);
            else if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
                land.rightPressed(mx, my);
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        if (!land.isGameOver()) {
            int mx = e.getX();
            int my = e.getY();
            
            e.consume();
            if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
                land.leftReleased(mx, my);
        }
    }
    
    public void mouseDragged(MouseEvent e) {
        if (!land.isGameOver()) {
            int mx = e.getX();
            int my = e.getY();
            e.consume();
            if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
                land.leftDragged(mx, my);
        }
    }
    
    public void mouseClicked(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
    
    public void mouseMoved(MouseEvent e) {
    }
}
