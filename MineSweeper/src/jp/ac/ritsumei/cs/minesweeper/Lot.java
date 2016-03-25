
package jp.ac.ritsumei.cs.minesweeper;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Lot {
    
    private int posX, posY;
    private boolean isOpen;
    private Mine mine;
    private Flag flag;
    private List<Lot> neighbors = new ArrayList<Lot>();
    private int neighborMines;
    
    private boolean isSelected;
    private boolean isExploded;
    private boolean isChanged;
    
    public static final int size = 20;
    public static final int offsetX = 2;
    public static final int offsetY = 2;
    private Font font = new Font("TimesRoman", Font.BOLD, 14);
    
    Lot(int x, int y) {
        posX = x;
        posY = y;
        isChanged = false;
    }
    
    public void addNeighbor(Lot n) {
        neighbors.add(n);
    }
    
    public void select() {
        if (!isOpen && !isFlag()) {
            isSelected = true;
            isChanged = true;
        }
    }
    
    public void unselect() {
        if (!isOpen && !isFlag()) {
            isSelected = false;
            isChanged = true;
        }
    }
    
    public boolean open() {
        if (isSelected && !isOpen) {
            if (isMine()) {
                return false;
            }
            
            if (!isFlag()) {
                openNeighbor();
            }
        }
        return true;
    }
    
    public void openNeighbor() {
        if (!isOpen && !isFlag()) {
            isOpen = true;
            isChanged = true;
            
            if (neighborMines == 0) {
                for (Lot lot : neighbors) {
                    lot.openNeighbor();
                }
            }
        }
    }
    
    public void close() {
        isOpen = false;
        mine = null;
        flag = null;
        neighborMines = 0;
        
        isSelected = false;
        isExploded = false;
        isChanged = true;
    }
    
    public int switchFlag() {
        if (!isOpen) {
            isChanged = true;
            
            if (isFlag()) {
                flag = null;
                return -1;
            } else {
                flag = Flag.getInstance();
                return 1;
            }
        }
        return 0;
    }
    
    public void explosion() {
        isExploded = true;
        isChanged = true;
    }
    
    public boolean isMine() {
        if (mine != null)
            return true;
        
        return false;
    }
    
    private boolean isFlag() {
        if (flag != null)
            return true;
        
        return false;
    }
    
    public void layMine() {
        mine = Mine.getInstance();
        
        for (Lot lot : neighbors) {
            lot.addNeighborMines();
        }
    }
    
    public void addNeighborMines() {
        neighborMines++;
    }
    
    public void paint(Graphics g) {
        isChanged = true;
        update(g);
    }
    
    public void update (Graphics g) {
        if (isChanged) {
            int left = posX * size + offsetX;
            int right = (posX + 1) * size - 1 + offsetX;
            int top = posY * size + offsetY;
            int bottom = (posY + 1) * size - 1 + offsetY;
            int border = 2;
            
            g.setColor(new Color(204, 204, 204));
            g.fillRect(left, top, size - 1, size - 1);
            
            if (isSelected || isOpen) {
                g.setColor(new Color(180, 180, 180));
                g.drawRect(left, top, size - 1 , size - 1);
                
            } else {
                g.setColor(new Color(255, 255, 255));
                for (int i = 0; i < border; i++) {
                    g.drawLine(left + i, top + i, right - i, top + i);
                    g.drawLine(left + i, top + i, left + i, bottom - i);
                }
                
                g.setColor(new Color(120, 120, 120));
                for (int i = 0; i < border; i++) {
                    g.drawLine(left + i, bottom - i, right - i, bottom - i);
                    g.drawLine(right - i, top + i, right - i, bottom - i);
                }
            }
            
            if (isOpen && neighborMines != 0) {
                String number = String.valueOf(neighborMines);
                g.setFont(font);
                g.setColor(Color.blue);
                g.drawString(number, left + 5, bottom - 3);
            }
            
            if (isFlag()) {
                flag.paint(g, posX, posY);
            }
            
            if (isExploded) {
                if (isMine()) {
                    if (isSelected) {
                        g.setColor(Color.red);
                        g.fillRect(left, top, size - 1, size - 1);
                    } else {
                        g.setColor(new Color(204, 204, 204));
                        g.fillRect(left, top, size - 1, size - 1);
                    }
                    mine.paint(g, posX, posY);
                }
                
                if (isFlag() && !isMine()) {
                    g.setColor(Color.black);
                    g.drawLine(left + 4, top + 4, right - 4, bottom - 4);
                    g.drawLine(left + 3, top + 4, right - 5, bottom - 4);
                    g.drawLine(left + 4, bottom - 4, right - 4, top + 4);
                    g.drawLine(left + 3, bottom - 4, right - 5, top + 4);
                }
            }
            
            isChanged = false;
        }
    }
}
