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
import Model.Position;

/**
 *
 * @author jbatista
 */
public class King extends Piece{
    
    protected final static String wKingImgPath = "img/pieces/w-king.png";
    protected static BufferedImage wKingImg = null;
    protected final static String bKingImgPath = "img/pieces/b-king.png";
    protected static BufferedImage bKingImg = null; 

    public King(Piece.Color color, int x, int y, BoardModel model)  {
        super(color, x, y, false, model);
        setImages();
    }
    
    public void setImages(){
        if(this.color == Piece.Color.BLACK){
            try{
                bKingImg = ImageIO.read(new File(bKingImgPath));
            }
            catch(IOException e){}
        } else {
            try{
                wKingImg = ImageIO.read(new File(wKingImgPath));
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
            g.drawImage(bKingImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        } else {
            g.drawImage(wKingImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        }
    }
    
    @Override
    public String toString() {
        if(this.color == Piece.Color.BLACK){
            return "Black King";
        } else {
            return "White King";
        }
    }
    
    @Override
    public void createListOfCandidateMoves(){
        
        this.listOfCandidateMoves.clear();
        
        if (square.x + 1 < 8 && model.piecesOnTheBoard[square.x+1][square.y].getColor() != this.color){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x + 1, square.y)));
        }
        if (square.y + 1 < 8 && model.piecesOnTheBoard[square.x][square.y+1].getColor() != this.color){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x, square.y + 1)));
        }
        if (square.x - 1 > -1 && model.piecesOnTheBoard[square.x-1][square.y].getColor() != this.color){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x - 1, square.y)));
        }
        if (square.y - 1 > -1 && model.piecesOnTheBoard[square.x][square.y-1].getColor() != this.color){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x, square.y - 1)));
        }
        if (square.x + 1 < 8 && square.y + 1 < 8 && model.piecesOnTheBoard[square.x+1][square.y+1].getColor() != this.color){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x + 1, square.y + 1)));
        }
        if (square.x + 1 < 8 && square.y - 1 > -1 && model.piecesOnTheBoard[square.x+1][square.y-1].getColor() != this.color){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x + 1, square.y - 1)));
        }
        if (square.x - 1 > -1 && square.y + 1 < 8 && model.piecesOnTheBoard[square.x-1][square.y+1].getColor() != this.color){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x - 1, square.y + 1)));
        }
        if (square.x - 1 > -1 && square.y - 1 > -1 && model.piecesOnTheBoard[square.x-1][square.y-1].getColor() != this.color){
            this.listOfCandidateMoves.add(new Move(square, new Position(square.x - 1, square.y - 1)));
        }
        
        if (numberOfMoves == 0){
            if (color == Color.WHITE && model.leftWhiteRook.numberOfMoves == 0
                    && model.piecesOnTheBoard[square.x-1][square.y].getColor() == Color.EMPTY
                    && model.piecesOnTheBoard[square.x-2][square.y].getColor() == Color.EMPTY
                    && model.piecesOnTheBoard[square.x-3][square.y].getColor() == Color.EMPTY){
                if (!model.isKingOfColorChecked(Color.WHITE) && !model.isSquareAttackedByColor(square.x-1, square.y, Color.BLACK) && !model.isSquareAttackedByColor(square.x-2, square.y, Color.BLACK)){
                    this.listOfCandidateMoves.add(new Move(square, new Position(square.x - 2, square.y)));
                }
            }
            if (color == Color.WHITE && model.rightWhiteRook.numberOfMoves == 0
                    && model.piecesOnTheBoard[square.x+1][square.y].getColor() == Color.EMPTY
                    && model.piecesOnTheBoard[square.x+2][square.y].getColor() == Color.EMPTY){
                if (!model.isKingOfColorChecked(Color.WHITE) && !model.isSquareAttackedByColor(square.x+1, square.y, Color.BLACK) && !model.isSquareAttackedByColor(square.x+2, square.y, Color.BLACK)){
                    this.listOfCandidateMoves.add(new Move(square, new Position(square.x + 2, square.y)));
                }
            }
            if (color == Color.BLACK && model.leftBlackRook.numberOfMoves == 0
                    && model.piecesOnTheBoard[square.x-1][square.y].getColor() == Color.EMPTY
                    && model.piecesOnTheBoard[square.x-2][square.y].getColor() == Color.EMPTY
                    && model.piecesOnTheBoard[square.x-3][square.y].getColor() == Color.EMPTY){
                if (!model.isKingOfColorChecked(Color.BLACK) && !model.isSquareAttackedByColor(square.x-1, square.y, Color.WHITE) && !model.isSquareAttackedByColor(square.x-2, square.y, Color.WHITE)){
                    this.listOfCandidateMoves.add(new Move(square, new Position(square.x - 2, square.y)));
                }
                
            }
            if (color == Color.BLACK && model.rightBlackRook.numberOfMoves == 0
                    && model.piecesOnTheBoard[square.x+1][square.y].getColor() == Color.EMPTY
                    && model.piecesOnTheBoard[square.x+2][square.y].getColor() == Color.EMPTY){
                if (!model.isKingOfColorChecked(Color.BLACK) && !model.isSquareAttackedByColor(square.x+1, square.y, Color.WHITE) && !model.isSquareAttackedByColor(square.x+2, square.y, Color.WHITE)){
                    this.listOfCandidateMoves.add(new Move(square, new Position(square.x + 2, square.y)));
                }
            }
            
        }
    }
}