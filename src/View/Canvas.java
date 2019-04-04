package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Canvas extends JPanel{
    
    private ArrayList<Observer> observers;
    protected final static String imgPath = "img/board-for-white.png";
    protected static BufferedImage TabuImg = null;

    public Canvas() {
        super();
        observers = new ArrayList<Observer>();
    }
    
    public void registerObserver(Observer ob){
        this.observers.add(ob);
    }
    
    private void drawBoard(Graphics2D g){
        
    Graphics2D g2d = (Graphics2D) g;
        try{
            TabuImg = ImageIO.read(new File(imgPath));
        }
        catch(IOException e){}
        g.drawImage(TabuImg, 0, 0, 720, 720, 0, 0, 720, 720, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        this.drawBoard(g2);
        
        for(Observer ob : observers){
            ob.update(null, g2);
        }
    }
    
}
