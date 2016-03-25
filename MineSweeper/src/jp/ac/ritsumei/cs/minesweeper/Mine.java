
package jp.ac.ritsumei.cs.minesweeper;

import java.awt.*;

public class Mine {
    
    private static Mine mine = new Mine();
    
    private Mine() {
    }
    
    public static Mine getInstance() {
        return mine;
    }
    
    public void paint(Graphics g, int posX, int posY) {
        int xCenter = posX * Lot.size + Lot.size / 2 + Lot.offsetX;
        int yCenter = posY * Lot.size + Lot.size / 2 + Lot.offsetY;
        
        g.setColor(Color.black);
        g.drawLine(xCenter, yCenter, xCenter, yCenter - 6);
        g.drawLine(xCenter, yCenter, xCenter + 6, yCenter);
        g.drawLine(xCenter, yCenter, xCenter, yCenter + 6);
        g.drawLine(xCenter, yCenter, xCenter - 6, yCenter);
        
        g.fillOval(xCenter - 4, yCenter - 4, 8, 8);
    }
}
