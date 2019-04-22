package Model;

import java.awt.Graphics2D;
import Model.Position;
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
        super(color, x, y, false);
        if(color == Piece.Color.BLACK){
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
        
        int x0 = (square.x) * squareWidth + 56;
        int y0 = (7 - square.y) * squareHeight + 56;
        int x1 = x0 + squareWidth;
        int y1 = y0 + squareHeight;
        
        if(color == Piece.Color.BLACK){
            g.drawImage(bPawnImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        } else {
            g.drawImage(wPawnImg, x0, y0, x1, y1, 0, 0, 76, 76, null);
        }
    }
    
    @Override
    public String toString() {
        
        if(color == Piece.Color.BLACK){
            return "Black Pawn";
        } else {
            return "White Pawn";
        }
    }
    
    //@Override
    public void createListOfCandidateMoves(){
        if (color == Piece.Color.WHITE){
            if (square.y + 1 < 8){
                if (model.piecesOnTheBoard[square.x][square.y + 1].color == Piece.Color.EMPTY){
                    listOfCandidateMoves.add(new Move(square, new Position(square.x, square.y+1)));
                    if (square.y + 2 < 8 && numberOfMoves == 0){
                        if (model.piecesOnTheBoard[square.x][square.y + 2].color == Piece.Color.EMPTY){
                            listOfCandidateMoves.add(new Move(square, new Position(square.x, square.y+2)));
                        }
                    }
                }
                if (square.x + 1 < 8){
                    if (model.piecesOnTheBoard[square.x + 1][square.y + 1].color == Piece.Color.BLACK){
                        listOfCandidateMoves.add(new Move(square, new Position(square.x+1, square.y+1)));
                    }
                    if (square.y == 4 && model.piecesOnTheBoard[square.x+1][square.y].getColor() == Piece.Color.BLACK  && model.piecesOnTheBoard[square.x+1][square.y].numberOfMoves == 1 && model.piecesOnTheBoard[square.x+1][square.y] instanceof Pawn){
                        listOfCandidateMoves.add(new Move(square, new Position(square.x+1, square.y+1)));
                    }
                }
                if (square.x - 1 > -1){
                    if (model.piecesOnTheBoard[square.x - 1][square.y + 1].color == Piece.Color.BLACK){
                        listOfCandidateMoves.add(new Move(square, new Position(square.x-1, square.y+1)));
                    }   
                    if (square.y == 4 && model.piecesOnTheBoard[square.x-1][square.y].getColor() == Piece.Color.BLACK  && model.piecesOnTheBoard[square.x-1][square.y].numberOfMoves == 1 && model.piecesOnTheBoard[square.x-1][square.y] instanceof Pawn){
                        listOfCandidateMoves.add(new Move(square, new Position(square.x-1, square.y+1)));
                    }
                }   
            }
        }
        if (color == Piece.Color.BLACK){
            if (square.y - 1 > -1){
                if (model.piecesOnTheBoard[square.x][square.y - 1].color == Piece.Color.EMPTY){
                    listOfCandidateMoves.add(new Move(square, new Position(square.x, square.y-1)));
                    if (square.y - 2 > -1 && numberOfMoves == 0){
                        if (model.piecesOnTheBoard[square.x][square.y - 2].color == Piece.Color.EMPTY){
                            listOfCandidateMoves.add(new Move(square, new Position(square.x, square.y-2)));
                        }
                    }
                }
                if (square.x + 1 < 8){
                    if (model.piecesOnTheBoard[square.x + 1][square.y - 1].color == Piece.Color.WHITE){
                        listOfCandidateMoves.add(new Move(square, new Position(square.x+1, square.y+1)));
                    }
                    if (square.y == 3 && model.piecesOnTheBoard[square.x+1][square.y].getColor() == Piece.Color.WHITE  && model.piecesOnTheBoard[square.x+1][square.y].numberOfMoves == 1 && model.piecesOnTheBoard[square.x+1][square.y] instanceof Pawn){
                        listOfCandidateMoves.add(new Move(square, new Position(square.x+1, square.y+1)));
                    }
                }
                if (square.x - 1 > -1){
                    if (model.piecesOnTheBoard[square.x - 1][square.y - 1].color == Piece.Color.WHITE){
                        listOfCandidateMoves.add(new Move(square, new Position(square.x-1, square.y+1)));
                    }
                    if (square.y == 3 && model.piecesOnTheBoard[square.x-1][square.y].getColor() == Piece.Color.WHITE  && model.piecesOnTheBoard[square.x-1][square.y].numberOfMoves == 1 && model.piecesOnTheBoard[square.x-1][square.y] instanceof Pawn){
                        listOfCandidateMoves.add(new Move(square, new Position(square.x-1, square.y+1)));
                    }
                }   
            }   
        }
        
        for(Move m : listOfCandidateMoves){
            if ((color == Color.WHITE && m.getEnd().y == 7) || (color == Color.BLACK && m.getEnd().y == 0)){
                m.setPromotionOption(0);
                listOfCandidateMoves.add(new Move(m.getIni(), m.getEnd()));
                listOfCandidateMoves.get(listOfCandidateMoves.size()).setPromotionOption (1);
            }       
        }
        
    }
    
    @Override
    public void makeMove(Move m){
        numberOfMoves++;
        if ((color == Color.WHITE && m.getEnd().y == 7) || (color == Color.BLACK && m.getEnd().y == 0)){
            callPromotion(m);
        }
        lastMoves.add(m);
        setSquare(m.getEnd());
    }
 
    public void callPromotion(Move m){
        switch(m.getPromotionOption()){
            case 0:
                model.piecesOnTheBoard[m.getEnd().x][m.getEnd().y] = new Queen(color, m.getEnd().x, m.getEnd().y, true);
                break;
            case 3:
                model.piecesOnTheBoard[m.getEnd().x][m.getEnd().y] = new Rook(color, m.getEnd().x, m.getEnd().y, true);
                break;
            case 2:
                model.piecesOnTheBoard[m.getEnd().x][m.getEnd().y] = new Bishop(color, m.getEnd().x, m.getEnd().y, true);
                break;
            case 1:
                model.piecesOnTheBoard[m.getEnd().x][m.getEnd().y] = new Knight(color, m.getEnd().x, m.getEnd().y, true);
                break;
        }
        
        model.piecesOnTheBoard[m.getEnd().x][m.getEnd().y].ancientPiece = this;
        
        if (color == Color.WHITE){
            model.whitePieces.remove(this);
            model.whitePieces.add(model.piecesOnTheBoard[m.getEnd().x][m.getEnd().y]);
        } else {
            model.blackPieces.remove(this);
            model.blackPieces.add(model.piecesOnTheBoard[m.getEnd().x][m.getEnd().y]);
        }
        
    }
}
