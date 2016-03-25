
package jp.ac.ritsumei.cs.minesweeper;

import java.awt.*;

public class GameInfo extends Canvas implements Runnable {
    
    private static final long serialVersionUID = 3841772258406410472L;
    
    private int mines;
    private int seconds;
    
    private Thread timer = null;
    
    private static final int width = 233;
    private static final int height = 25;
    
    private Font font = new Font("TimesRoman", Font.BOLD, 14);
    
    GameInfo() {
        setSize(width, height);
    }
    
    public void init() {
        stopTimer();
        seconds = 0;
    }
    
    public void setMines(int m) {
        mines = m;
        repaint();
    }
    
    public void startTimer() {
        timer = new Thread(this);
        repaint();
        timer.start();
    }
    
    public void stopTimer() {
        timer = null;
    }
    
    public void run() {
        seconds = -1;
        Thread thisThread = Thread.currentThread();
        while (timer == thisThread) {
            seconds++;
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }
        }
    }
    
    public void update(Graphics g) {
        paint(g);
    }
    
    public void paint(Graphics g) {
        g.setFont(font);
        g.setColor(Color.blue);
        g.drawString("Mines", 25, height - 5);
        g.drawString("Time", 145, height - 5);
        
        g.setColor(Color.black);
        g.fillRect(80, 0, 50, height);
        g.fillRect(190, 0, 50, height);
        
        g.setColor(Color.white);
        String str = String.valueOf(mines);
        g.drawString(str, 85, height - 5);
        
        str = String.valueOf(seconds);
        g.drawString(str, 195, height - 5);
    }
}
