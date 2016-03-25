
package jp.ac.ritsumei.cs.minesweeper;

import java.awt.*;

/**
 * Constructs the land on which flags and mines are laid.
 */
public class Land extends Canvas {
    
    private static final long serialVersionUID = 1750073108216164964L;
    
    private GameInfo info;
    private int columns;
    private int rows;
    private int mines;
    private int flags;
    private int lots;
    private Lot lot[][];
    
    private boolean isGameOver;
    private int width, height;
    private int prevX, prevY;
    
    Land(GameInfo i, int c, int r) {
        info = i;
        columns = c;
        rows = r;
        mines = (columns * rows * 10 / 6) / 10;
        
        lot = new Lot[rows][columns];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                setLot(x, y, new Lot(x, y));
            }
        }
        
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                for (int dy = y - 1; dy <= y + 1; dy++) {
                    for (int dx = x - 1; dx <= x + 1; dx++) {
                        if (dx >= 0 && dx < columns && dy >= 0 && dy < rows) {
                            getLot(x, y).addNeighbor(getLot(dx, dy));
                        }
                    }
                }
            }
        }
        
        width = Lot.size * columns + Lot.offsetX * 2;
        height = Lot.size * rows + Lot.offsetY * 2;
        setSize(width, height);
    }
    
    public void gameStart() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                getLot(x, y).close();
            }
        }
        
        lots = 0;
        flags = 0;
        info.setMines(mines - flags);
        layMines(mines);
        info.init();
        isGameOver = false;
        
        repaint();
    }
    
    private void layMines(int m) {
        while (m > 0) {
            int x = (int)(Math.random() * columns);
            int y = (int)(Math.random() * rows);
            if (!getLot(x, y).isMine()) {
                getLot(x, y).layMine();
                m--;
            }
        }
    }
    
    public void leftPressed(int mx, int my) {
        int posX = tansX(mx);
        int posY = tansY(my);
        
        if (prevX != -1 && prevY != -1) {
            getLot(posX, posY).select();
            repaint();
        }
        
        prevX = posX;
        prevY = posY;
    }
    
    public void leftDragged(int mx, int my) {
        int posX = tansX(mx);
        int posY = tansY(my);
        
        if(posX != prevX || posY != prevY) {
            if (prevX != -1 && prevY != -1)
                getLot(prevX, prevY).unselect();
            if (posX != -1 && posY != -1)
                getLot(posX, posY).select();
            
            repaint();
            
            prevX = posX;
            prevY = posY;
        }
    }
    
    public void leftReleased(int mx, int my) {
        int posX = tansX(mx);
        int posY = tansY(my);
        boolean safe;
        
        if (posX != -1 && posY != -1) {
            safe = getLot(posX, posY).open();
            
            lots++;
            if (lots == 1)
                info.startTimer();
            
            if (lots == columns * rows)
                info.stopTimer();
            
            if (!safe) {
                info.stopTimer();
                for (int y = 0; y < rows; y++) {
                    for (int x = 0; x < columns; x++) {
                        getLot(x, y).explosion();
                    }
                }
                isGameOver = true;
            }
            
            repaint();
        }
    }
    
    public void rightPressed(int mx, int my) {
        int posX = tansX(mx);
        int posY = tansY(my);
        
        if (posX != -1 && posY != -1) {
            flags = flags + getLot(posX, posY).switchFlag();
            info.setMines(mines - flags);
            repaint();
        }
    }
    
    private int tansX(int mx) {
        int lotX = (mx - Lot.offsetX) / Lot.size;
        if (lotX >=0 && lotX < columns)
            return lotX; 
        
        return -1;
    }
    
    private int tansY(int my) {
        int lotY = (my - Lot.offsetY) / Lot.size;
        if (lotY >=0 && lotY < rows)
            return lotY;
        
        return -1;
    }
    
    private Lot getLot(int x, int y) {
        return lot[y][x];
    }
    
    private void setLot(int x, int y, Lot l) {
        lot[y][x] = l;
    }
    
    public boolean isGameOver() {
        return isGameOver;
    }
    
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                getLot(x, y).paint(g);
            }
        }
    }
    
    public void update(Graphics g) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                getLot(x, y).update(g);
            }
        }
    }
}
