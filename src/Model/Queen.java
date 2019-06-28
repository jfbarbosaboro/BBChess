package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;

public class Queen  extends Piece{
    
    protected final String wQueenImgPath = "img/pieces/w-queen.png";
    protected static BufferedImage wQueenImg = null;
    protected final String bQueenImgPath = "img/pieces/b-queen.png";
    protected static BufferedImage bQueenImg = null; 

    public Queen(Piece.Color color, int x, int y, boolean p, BoardModel model)  {
        super(color, x, y, p, model);
        setImages();
    }
    
    @Override
    public void setImages(){
        if(this.color == Piece.Color.BLACK){
            try{
                bQueenImg = ImageIO.read(new File(bQueenImgPath));
            }
            catch(IOException e){}
        } else {
            try{
                wQueenImg = ImageIO.read(new File(wQueenImgPath));
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
            g.drawImage(bQueenImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        } else {
            g.drawImage(wQueenImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        }
    }
    
    @Override
    public String toString() {
        if(this.color == Piece.Color.BLACK){
            return "Black Queen";
        } else {
            return "White Queen";
        }
    }
    
    @Override
    public void createListOfCandidateMoves(){
        
        this.listOfCandidateMoves.clear();
        
        for (int i = 1; square.x + i < 8; i++){
            if (model.piecesOnTheBoard[square.x+i][square.y].getColor() != this.color){
                this.listOfCandidateMoves.add(new Move(square, new Position(square.x+i, square.y)));
                if (model.piecesOnTheBoard[square.x+i][square.y].getColor() != Color.EMPTY){
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = 1; square.y + i < 8; i++){
            if (model.piecesOnTheBoard[square.x][square.y+i].getColor() != this.color){
                this.listOfCandidateMoves.add(new Move(square, new Position(square.x, square.y+i)));
                if (model.piecesOnTheBoard[square.x][square.y+i].getColor() != Color.EMPTY){
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = 1; square.x - i > -1; i++){
            if (model.piecesOnTheBoard[square.x-i][square.y].getColor() != this.color){
                this.listOfCandidateMoves.add(new Move(square, new Position(square.x-i, square.y)));
                if (model.piecesOnTheBoard[square.x-i][square.y].getColor() != Color.EMPTY){
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = 1; square.y - i > -1; i++){
            if (model.piecesOnTheBoard[square.x][square.y-i].getColor() != this.color){
                this.listOfCandidateMoves.add(new Move(square, new Position(square.x, square.y-i)));
                if (model.piecesOnTheBoard[square.x][square.y-i].getColor() != Color.EMPTY){
                    break;
                }
            } else {
                break;
            }
        }
        
        for (int i = 1; square.x + i < 8 && square.y + i < 8; i++){
            if (model.piecesOnTheBoard[square.x+i][square.y+i].getColor() != this.color){
                this.listOfCandidateMoves.add(new Move(square, new Position(square.x+i, square.y+i)));
                if (model.piecesOnTheBoard[square.x+i][square.y+i].getColor() != Color.EMPTY){
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = 1; square.x + i < 8 && square.y - i > -1; i++){
            if (model.piecesOnTheBoard[square.x+i][square.y-i].getColor() != this.color){
                this.listOfCandidateMoves.add(new Move(square, new Position(square.x+i, square.y-i)));
                if (model.piecesOnTheBoard[square.x+i][square.y-i].getColor() != Color.EMPTY){
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = 1; square.x - i > -1 && square.y - i > -1; i++){
            if (model.piecesOnTheBoard[square.x-i][square.y-i].getColor() != this.color){
                this.listOfCandidateMoves.add(new Move(square, new Position(square.x-i, square.y-i)));
                if (model.piecesOnTheBoard[square.x-i][square.y-i].getColor() != Color.EMPTY){
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = 1; square.x - i > -1 && square.y + i < 8; i++){
            if (model.piecesOnTheBoard[square.x-i][square.y+i].getColor() != this.color){
                this.listOfCandidateMoves.add(new Move(square, new Position(square.x-i, square.y+i)));
                if (model.piecesOnTheBoard[square.x-i][square.y+i].getColor() != Color.EMPTY){
                    break;
                }
            } else {
                break;
            }
        }
        
    }
}