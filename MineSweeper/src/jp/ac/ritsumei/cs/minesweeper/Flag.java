
package jp.ac.ritsumei.cs.minesweeper;

import java.awt.*;

/**
 * Represents a flag on the land.
 */
public class Flag {
    
    private static Flag flag = new Flag();
    
    /**
     * 
     */
    private Flag() {
    }
    
    public static Flag getInstance() {
        return flag;
    }
    
    public void paint(Graphics g, int posX, int posY) {
        int xCenter = posX * Lot.size + Lot.size / 2 + Lot.offsetX;
        int yCenter = posY * Lot.size + Lot.size / 2 + Lot.offsetY;
        
        g.setColor(Color.magenta);
        int xPoints[] = new int[3];
        int yPoints[] = new int[3];
        xPoints[0] = xCenter - 3;  yPoints[0] = yCenter - 6;
        xPoints[1] = xCenter - 3;  yPoints[1] = yCenter + 2;
        xPoints[2] = xCenter + 3;  yPoints[2] =  yCenter - 2;
        g.fillPolygon(xPoints, yPoints, 3);
        
        g.drawLine(xPoints[0], yPoints[0], xPoints[1], yPoints[1] + 3);
        g.drawLine(xPoints[0] - 1, yPoints[0], xPoints[1] - 1, yPoints[1] + 3);
        g.drawLine(xPoints[1] - 2, yPoints[1] + 3, xPoints[1] + 2, yPoints[1] + 3);
    }
}
