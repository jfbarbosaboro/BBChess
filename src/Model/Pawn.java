package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;

public class Pawn extends Piece{
    
    
    protected final static String wPawnImgPath = "img/pieces/w-pawn.png";
    protected static BufferedImage wPawnImg = null; 
    protected final static String bPawnImgPath = "img/pieces/b-pawn.png";
    protected static BufferedImage bPawnImg = null;
    protected ArrayList<Move> possibleMoves;

    public Pawn(Color color, int x, int y){
        super(color, x, y);
        if(this.color == Piece.Color.BLACK){
            try{
                bPawnImg = ImageIO.read(new File(bPawnImgPath));
            }
            catch(IOException e){}
        } else {
            try{
                wPawnImg = ImageIO.read(new File(wPawnImgPath));
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
            g.drawImage(bPawnImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        } else {
            g.drawImage(wPawnImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        }
    }
    
    @Override
    public String toString() {
        
        if(this.color == Piece.Color.BLACK){
            return "Black Pawn";
        } else {
            return "White Pawn";
        }
    }
}
