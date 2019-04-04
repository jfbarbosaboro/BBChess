package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Knight extends Piece{
    
    protected final static String wKnightImgPath = "img/pieces/w-knight.png";
    protected static BufferedImage wKnightImg = null;
    protected final static String bKnightImgPath = "img/pieces/b-knight.png";
    protected static BufferedImage bKnightImg = null; 

    public Knight(Piece.Color color, int x, int y)  {
        super(color, x, y);
        if(this.color == Piece.Color.BLACK){
            try{
                bKnightImg = ImageIO.read(new File(bKnightImgPath));
            }
            catch(IOException e){}
        } else {
            try{
                wKnightImg = ImageIO.read(new File(wKnightImgPath));
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
            g.drawImage(bKnightImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        } else {
            g.drawImage(wKnightImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        }
    }
    
    @Override
    public String toString() {
        if(this.color == Piece.Color.BLACK){
            return "Black Knight";
        } else {
            return "White Knight";
        }
    }
}