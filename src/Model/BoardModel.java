package Model;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class BoardModel implements Observer{

    public ArrayList<Piece> blackPieces;
    public ArrayList<Piece> whitePieces;
    public Piece[][] piecesOnTheBoard;

    public BoardModel()  {
        this.blackPieces = new ArrayList<Piece>();
        this.whitePieces  = new ArrayList<Piece>();
        this.piecesOnTheBoard = new Piece[8][8];
        
        init();
    }
    
    private void init() {
        
        whitePieces.add(new Pawn(Piece.Color.WHITE,0,6));
        whitePieces.add(new Pawn(Piece.Color.WHITE,1,6));
        whitePieces.add(new Pawn(Piece.Color.WHITE,2,6));
        whitePieces.add(new Pawn(Piece.Color.WHITE,3,6));
        whitePieces.add(new Pawn(Piece.Color.WHITE,4,6));
        whitePieces.add(new Pawn(Piece.Color.WHITE,5,6));
        whitePieces.add(new Pawn(Piece.Color.WHITE,6,6));
        whitePieces.add(new Pawn(Piece.Color.WHITE,7,6));
        whitePieces.add(new Queen(Piece.Color.WHITE, 3, 7));
        whitePieces.add(new King(Piece.Color.WHITE, 4, 7));
        whitePieces.add(new Bishop(Piece.Color.WHITE, 5, 7));
        whitePieces.add(new Bishop(Piece.Color.WHITE, 2, 7));
        whitePieces.add(new Knight(Piece.Color.WHITE, 6, 7));
        whitePieces.add(new Knight(Piece.Color.WHITE, 1, 7));
        whitePieces.add(new Rook(Piece.Color.WHITE, 0, 7));
        whitePieces.add(new Rook(Piece.Color.WHITE, 7, 7));
        
        //inicializa time preto
        
        blackPieces.add(new Pawn(Piece.Color.BLACK,0,1));
        blackPieces.add(new Pawn(Piece.Color.BLACK,1,1));
        blackPieces.add(new Pawn(Piece.Color.BLACK,2,1));
        blackPieces.add(new Pawn(Piece.Color.BLACK,3,1));
        blackPieces.add(new Pawn(Piece.Color.BLACK,4,1));
        blackPieces.add(new Pawn(Piece.Color.BLACK,5,1));
        blackPieces.add(new Pawn(Piece.Color.BLACK,6,1));
        blackPieces.add(new Pawn(Piece.Color.BLACK,7,1));
        blackPieces.add(new Queen(Piece.Color.BLACK, 3, 0));
        blackPieces.add(new King(Piece.Color.BLACK, 4, 0));
        blackPieces.add(new Bishop(Piece.Color.BLACK, 5, 0));
        blackPieces.add(new Bishop(Piece.Color.BLACK, 2, 0));
        blackPieces.add(new Knight(Piece.Color.BLACK, 6, 0));
        blackPieces.add(new Knight(Piece.Color.BLACK, 1, 0));
        blackPieces.add(new Rook(Piece.Color.BLACK, 0, 0));
        blackPieces.add(new Rook(Piece.Color.BLACK, 7, 0));
        
        
        for (int i = 0; i < 8; i++){
            for(int j = 2; j < 6; j++){
                piecesOnTheBoard[i][j] = new Empty(Piece.Color.EMPTY, i, j);
            }
        }
        
        for (Piece p : whitePieces){
            piecesOnTheBoard[p.square.x][p.square.y] = p;
        }
        for (Piece p : blackPieces){
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
        Piece aux = piecesOnTheBoard[m.ini.x][m.ini.y];
        piecesOnTheBoard[m.ini.x][m.ini.y] = new Empty(Piece.Color.EMPTY, m.ini.x, m.ini.y);
        piecesOnTheBoard[m.end.x][m.end.y] = aux;
        aux.makeMove(m);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        draw((Graphics2D) arg);
    }
    
    public String getStringOfPieceAt(int x, int y){
        return piecesOnTheBoard[x][y].toString();
    }
    
}
