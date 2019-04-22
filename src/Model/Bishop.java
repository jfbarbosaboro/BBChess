package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Bishop extends Piece{
    
    protected final static String wBishopImgPath = "img/pieces/w-bishop.png";
    protected static BufferedImage wBishopImg = null;
    protected final static String bBishopImgPath = "img/pieces/b-bishop.png";
    protected static BufferedImage bBishopImg = null; 

    public Bishop(Piece.Color color, int x, int y, boolean p)  {
        super(color, x, y, p);
        if(this.color == Piece.Color.BLACK){
            try{
                bBishopImg = ImageIO.read(new File(bBishopImgPath));
            }
            catch(IOException e){}
        } else {
            try{
                wBishopImg = ImageIO.read(new File(wBishopImgPath));
            }
            catch(IOException e){}
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int squareWidth = 76;
        int squareHeight = 76;
        
        int x0 = (square.x) * squareWidth + 56;
        int y0 = (7 - square.y) * squareHeight + 56;
        int x1 = x0 + squareWidth;
        int y1 = y0 + squareHeight;
        
        if(this.color == Piece.Color.BLACK){
            g.drawImage(bBishopImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        } else {
            g.drawImage(wBishopImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        }
    }
    
    @Override
    public String toString() {
        if(this.color == Piece.Color.BLACK){
            return "Black Bishop";
        } else {
            return "White Bishop";
        }
    }
    
    @Override
    public void createListOfCandidateMoves(){
        
        for (int i = 1, reachedEnd = 0; square.x+i < 8 && square.y+i < 8 && model.piecesOnTheBoard[square.x + i][square.y + i].getColor() != this.color && reachedEnd == 0; i++){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x+i, square.y+i)));
            if (model.piecesOnTheBoard[square.x + i][square.y + i].getColor() != Piece.Color.EMPTY) reachedEnd = 1;
        }
        for (int i = 1, reachedEnd = 0; square.x+i < 8 && square.y-i > -1 && model.piecesOnTheBoard[square.x + i][square.y - i].getColor() != this.color && reachedEnd == 0; i++){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x+i, square.y-i)));
            if (model.piecesOnTheBoard[square.x + i][square.y - i].getColor() != Piece.Color.EMPTY) reachedEnd = 1;
        }
        for (int i = 1, reachedEnd = 0; square.x-i > -1 && square.y-i > -1 && model.piecesOnTheBoard[square.x - i][square.y - i].getColor() != this.color && reachedEnd == 0; i++){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x-i, square.y-i)));
            if (model.piecesOnTheBoard[square.x - i][square.y - i].getColor() != Piece.Color.EMPTY) reachedEnd = 1;
        }
        for (int i = 1, reachedEnd = 0; square.x-i>-1 && square.y+i < 8 && model.piecesOnTheBoard[square.x - i][square.y + i].getColor() != this.color && reachedEnd == 0; i++){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x-i, square.y+i)));
            if (model.piecesOnTheBoard[square.x - i][square.y + i].getColor() != Piece.Color.EMPTY) reachedEnd = 1;
        }
    }
}