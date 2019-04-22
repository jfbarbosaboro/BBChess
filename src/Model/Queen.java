/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jbatista
 */
public class Queen  extends Piece{
    
    protected final static String wQueenImgPath = "img/pieces/w-queen.png";
    protected static BufferedImage wQueenImg = null;
    protected final static String bQueenImgPath = "img/pieces/b-queen.png";
    protected static BufferedImage bQueenImg = null; 

    public Queen(Piece.Color color, int x, int y, boolean p)  {
        super(color, x, y, p);
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