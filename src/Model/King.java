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

    public King(Piece.Color color, int x, int y)  {
        super(color, x, y, false);
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
    
    public void createListOfCandidateMoves(){
        Move m[] = {
            new Move(new Position(this.square.x, this.square.y), new Position(this.square.x+1, this.square.y+1)),
            new Move(new Position(this.square.x, this.square.y), new Position(this.square.x+1, this.square.y-1)),
            new Move(new Position(this.square.x, this.square.y), new Position(this.square.x-1, this.square.y-1)),
            new Move(new Position(this.square.x, this.square.y), new Position(this.square.x-1, this.square.y-1)),
            new Move(new Position(this.square.x, this.square.y), new Position(this.square.x, this.square.y+1)),
            new Move(new Position(this.square.x, this.square.y), new Position(this.square.x, this.square.y-1)),
            new Move(new Position(this.square.x, this.square.y), new Position(this.square.x+1, this.square.y)),
            new Move(new Position(this.square.x, this.square.y), new Position(this.square.x-1, this.square.y))
        };
        
        for (Move M : m){
            if (M.getEnd().x < 8 && M.getEnd().y < 8 && M.getEnd().x > -1 && M.getEnd().y > -1){
                if (this.model.piecesOnTheBoard[M.getEnd().x][M.getEnd().y].getColor() != this.color){
                    this.listOfCandidateMoves.add(M);
                }
            }
        }
        
        if (this.numberOfMoves == 0){
            if ((this.color == Color.WHITE && model.leftWhiteRook.numberOfMoves == 0) || (this.color == Color.BLACK && model.leftBlackRook.numberOfMoves == 0)){
                if (model.piecesOnTheBoard[this.square.x - 1][this.square.y].getColor() == Piece.Color.EMPTY
                        && model.piecesOnTheBoard[this.square.x - 2][this.square.y].getColor() == Piece.Color.EMPTY
                        && model.piecesOnTheBoard[this.square.x - 3][this.square.y].getColor() == Piece.Color.EMPTY){
                    if (color == Piece.Color.WHITE){
                        if (!model.isSquareAttackedByColor(square, Piece.Color.BLACK) && !model.isSquareAttackedByColor(new Position(square.x-1, square.y), Piece.Color.BLACK) && !model.isSquareAttackedByColor(new Position(square.x-2, square.y), Piece.Color.BLACK)){
                            this.listOfCandidateMoves.add(new Move(this.square, new Position(this.square.x-2, this.square.y)));
                        }
                    } else if (color == Piece.Color.BLACK){
                        if (!model.isSquareAttackedByColor(square, Piece.Color.WHITE) && !model.isSquareAttackedByColor(new Position(square.x-1, square.y), Piece.Color.WHITE) && !model.isSquareAttackedByColor(new Position(square.x-2, square.y), Piece.Color.WHITE)){
                            this.listOfCandidateMoves.add(new Move(this.square, new Position(this.square.x-2, this.square.y)));
                        }
                    }
                }
            }
            
            if ((this.color == Color.WHITE && model.rightWhiteRook.numberOfMoves == 0) || (this.color == Color.BLACK && model.rightBlackRook.numberOfMoves == 0)){
                if (model.piecesOnTheBoard[this.square.x + 1][this.square.y].getColor() == Piece.Color.EMPTY
                        && model.piecesOnTheBoard[this.square.x + 2][this.square.y].getColor() == Piece.Color.EMPTY){
                    if (color == Piece.Color.WHITE){
                        if (!model.isSquareAttackedByColor(square, Piece.Color.BLACK) && !model.isSquareAttackedByColor(new Position(square.x+1, square.y), Piece.Color.BLACK) && !model.isSquareAttackedByColor(new Position(square.x+2, square.y), Piece.Color.BLACK)){
                            this.listOfCandidateMoves.add(new Move(this.square, new Position(this.square.x+2, this.square.y)));
                        }
                    } else if (color == Piece.Color.BLACK){
                        if (!model.isSquareAttackedByColor(square, Piece.Color.WHITE) && !model.isSquareAttackedByColor(new Position(square.x+1, square.y), Piece.Color.WHITE) && !model.isSquareAttackedByColor(new Position(square.x+2, square.y), Piece.Color.WHITE)){
                            this.listOfCandidateMoves.add(new Move(this.square, new Position(this.square.x+2, this.square.y)));
                        }
                    }
                }
            }
        }
    }
}