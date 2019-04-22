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

    public Rook(Piece.Color color, int x, int y, boolean p)  {
        super(color, x, y, p);
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
        
        int x0 = (square.x) * squareWidth + 56;
        int y0 = (7 - square.y) * squareHeight + 56;
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
    
    @Override
    public void createListOfCandidateMoves(){
        
        for (int i = 1, reachedEnd = 0; square.x-i>-1 && model.piecesOnTheBoard[square.x - i][square.y].getColor() != this.color && reachedEnd == 0; i++){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x-i, square.y)));
            if (model.piecesOnTheBoard[square.x + i][square.y].getColor() != Piece.Color.EMPTY) reachedEnd = 1;
        }
        for (int i = 1, reachedEnd = 0; square.x+i<8 && model.piecesOnTheBoard[square.x + i][square.y].getColor() != this.color && reachedEnd == 0; i++){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x+i, square.y)));
            if (model.piecesOnTheBoard[square.x + i][square.y].getColor() != Piece.Color.EMPTY) reachedEnd = 1;
        }
        for (int i = 1, reachedEnd = 0; square.y+i<8 && model.piecesOnTheBoard[square.x][square.y+i].getColor() != this.color && reachedEnd == 0; i++){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x, square.y+i)));
            if (model.piecesOnTheBoard[square.x][square.y+i].getColor() != Piece.Color.EMPTY) reachedEnd = 1;
        }
        for (int i = 1, reachedEnd = 0; square.y-i>-1 && model.piecesOnTheBoard[square.x][square.y-i].getColor() != this.color && reachedEnd == 0; i++){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x-i, square.y)));
            if (model.piecesOnTheBoard[square.x][square.y-i].getColor() != Piece.Color.EMPTY) reachedEnd = 1;
        }
    }
}