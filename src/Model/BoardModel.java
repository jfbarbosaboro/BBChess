package Model;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.awt.Point;

public class BoardModel implements Observer{

    public ArrayList<Piece> pieces;
    public Piece[][] piecesOnTheBoard;
    private Piece.Color turn = Piece.Color.WHITE;

    public BoardModel()  {
        this.pieces = new ArrayList<Piece>();
        this.piecesOnTheBoard = new Piece[8][8];
        
        init();
    }
    
    private void init() {
        
        // initialize white pieces
        
        pieces.add(new Pawn(Piece.Color.WHITE,0,6));
        pieces.add(new Pawn(Piece.Color.WHITE,1,6));
        pieces.add(new Pawn(Piece.Color.WHITE,2,6));
        pieces.add(new Pawn(Piece.Color.WHITE,3,6));
        pieces.add(new Pawn(Piece.Color.WHITE,4,6));
        pieces.add(new Pawn(Piece.Color.WHITE,5,6));
        pieces.add(new Pawn(Piece.Color.WHITE,6,6));
        pieces.add(new Pawn(Piece.Color.WHITE,7,6));
        pieces.add(new Queen(Piece.Color.WHITE, 3, 7));
        pieces.add(new King(Piece.Color.WHITE, 4, 7));
        pieces.add(new Bishop(Piece.Color.WHITE, 5, 7));
        pieces.add(new Bishop(Piece.Color.WHITE, 2, 7));
        pieces.add(new Knight(Piece.Color.WHITE, 6, 7));
        pieces.add(new Knight(Piece.Color.WHITE, 1, 7));
        pieces.add(new Rook(Piece.Color.WHITE, 0, 7));
        pieces.add(new Rook(Piece.Color.WHITE, 7, 7));
        
        // inicialize black pieces
        
        pieces.add(new Pawn(Piece.Color.BLACK,0,1));
        pieces.add(new Pawn(Piece.Color.BLACK,1,1));
        pieces.add(new Pawn(Piece.Color.BLACK,2,1));
        pieces.add(new Pawn(Piece.Color.BLACK,3,1));
        pieces.add(new Pawn(Piece.Color.BLACK,4,1));
        pieces.add(new Pawn(Piece.Color.BLACK,5,1));
        pieces.add(new Pawn(Piece.Color.BLACK,6,1));
        pieces.add(new Pawn(Piece.Color.BLACK,7,1));
        pieces.add(new Queen(Piece.Color.BLACK, 3, 0));
        pieces.add(new King(Piece.Color.BLACK, 4, 0));
        pieces.add(new Bishop(Piece.Color.BLACK, 5, 0));
        pieces.add(new Bishop(Piece.Color.BLACK, 2, 0));
        pieces.add(new Knight(Piece.Color.BLACK, 6, 0));
        pieces.add(new Knight(Piece.Color.BLACK, 1, 0));
        pieces.add(new Rook(Piece.Color.BLACK, 0, 0));
        pieces.add(new Rook(Piece.Color.BLACK, 7, 0));
        
        
        for (int i = 0; i < 8; i++){
            for(int j = 2; j < 6; j++){
                piecesOnTheBoard[i][j] = new Empty(Piece.Color.EMPTY, i, j);
            }
        }
        
        for (Piece p : pieces){
            piecesOnTheBoard[p.square.x][p.square.y] = p;
        }
    }
    
    public Piece findPiece(int x, int y) {
        return piecesOnTheBoard[x][y];
    }
    
    public void draw(Graphics2D g){
        for(Piece[] r : piecesOnTheBoard){
            for(Piece p : r){
                if (p != null) p.draw(g);
            }
        }
    }
    
    public void makeMove(Move m){
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(m);
        
        // Castling verification.
        // This works only for legal moves.
        if (piecesOnTheBoard[m.ini.x][m.ini.y] instanceof King){
            if (m.end.x - m.ini.x > 1){
                moves.add(new Move(new Point(7, m.ini.y), new Point(5, m.ini.y)));
            }
            if (m.end.x - m.ini.x < -1){
                moves.add(new Move(new Point(0, m.ini.y), new Point(3, m.ini.y)));
            }
        }
        
        for (Move M : moves){
            Piece aux = piecesOnTheBoard[M.ini.x][M.ini.y];
            piecesOnTheBoard[M.ini.x][M.ini.y] = new Empty(Piece.Color.EMPTY, M.ini.x, M.ini.y);
            piecesOnTheBoard[M.end.x][M.end.y] = aux;
            aux.makeMove(M);
        }
        
        if (turn == Piece.Color.WHITE){
            turn = Piece.Color.BLACK;
        } else {
            turn = Piece.Color.WHITE;
        }
        
    }
    
    public Boolean isMovePossible(Move m){
        if (this.piecesOnTheBoard[m.ini.x][m.ini.y].color == this.turn){
            return true;
        } else {
            return false;
        }
    }
    
    public Piece.Color getTurn(){
        return this.turn;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        draw((Graphics2D) arg);
    }
    
    public String getStringOfPieceAt(int x, int y){
        return piecesOnTheBoard[x][y].toString();
    }
    
}
