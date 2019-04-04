package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Rook extends Piece{
    
    protected final static String wRookImgPath = "img/pieces/w-rook.png";
    protected static BufferedImage wRookImg = null;
    protected final static String bRookImgPath = "img/pieces/b-rook.png";
    protected static BufferedImage bRookImg = null; 

    public Rook(Piece.Color color, int x, int y)  {
        super(color, x, y);
        if(this.color == Piece.Color.BLACK){
            try{
                bRookImg = ImageIO.read(new File(bRookImgPath));
            }
            catch(IOException e){}
        } else {
            try{
                wRookImg = ImageIO.read(new File(wRookImgPath));
            }
            catch(IOException e){}
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int squareWidth = 76;
        int squareHeight = 76;
        
        int x0 = square.x * squareWidth + 56;
        int y0 = square.y * squareHeight + 56;
        int x1 = x0 + squareWidth;
        int y1 = y0 + squareHeight;
        
        if(this.color == Piece.Color.BLACK){
            g.drawImage(bRookImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        } else {
            g.drawImage(wRookImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        }
    }
    
    @Override
    public String toString() {
        if(this.color == Piece.Color.BLACK){
            return "Black Rook";
        } else {
            return "White Rook";
        }
    }
}