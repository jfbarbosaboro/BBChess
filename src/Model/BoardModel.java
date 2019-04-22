package Model;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import Controller.BoardController;

public class BoardModel implements Observer{

    public ArrayList<Piece> whitePieces;
    public ArrayList<Piece> blackPieces;
    public Piece[][] piecesOnTheBoard;
    private Piece.Color turn = Piece.Color.WHITE;
    protected ArrayList<Piece> lastPiecesMoved = new ArrayList<Piece>();
    protected Piece whiteKing = new King(Piece.Color.WHITE, 4, 0); // Pointer to White King.
    protected Piece blackKing = new King(Piece.Color.BLACK, 4, 7); // Pointer to Black King.
    protected Piece leftWhiteRook = new Rook(Piece.Color.WHITE, 0, 0, false);
    protected Piece rightWhiteRook = new Rook(Piece.Color.WHITE, 7, 0, false);
    protected Piece leftBlackRook = new Rook(Piece.Color.BLACK, 0, 7, false);
    protected Piece rightBlackRook = new Rook(Piece.Color.WHITE, 7, 7, false);
    
    protected ArrayList<Move> listOfPossibleMoves = new ArrayList<Move>();
    protected BoardController controller;

    public BoardModel()  {
        this.whitePieces = new ArrayList<Piece>();
        this.blackPieces = new ArrayList<Piece>();
        this.piecesOnTheBoard = new Piece[8][8];
        init();
    }
    
    public void addController(BoardController controller){
        this.controller = controller;
    }
    
    private void init() {
        // initialize white pieces
        whitePieces.add(new Pawn(Piece.Color.WHITE,0,1));
        whitePieces.add(new Pawn(Piece.Color.WHITE,1,1));
        whitePieces.add(new Pawn(Piece.Color.WHITE,2,1));
        whitePieces.add(new Pawn(Piece.Color.WHITE,3,1));
        whitePieces.add(new Pawn(Piece.Color.WHITE,4,1));
        whitePieces.add(new Pawn(Piece.Color.WHITE,5,1));
        whitePieces.add(new Pawn(Piece.Color.WHITE,6,1));
        whitePieces.add(new Pawn(Piece.Color.WHITE,7,1));
        whitePieces.add(new Queen(Piece.Color.WHITE, 3, 0, false));
        whitePieces.add(whiteKing);
        whitePieces.add(new Bishop(Piece.Color.WHITE, 5, 0, false));
        whitePieces.add(new Bishop(Piece.Color.WHITE, 2, 0, false));
        whitePieces.add(new Knight(Piece.Color.WHITE, 6, 0, false));
        whitePieces.add(new Knight(Piece.Color.WHITE, 1, 0, false));
        whitePieces.add(leftWhiteRook);
        whitePieces.add(rightWhiteRook);
        
        // inicialize black pieces
        blackPieces.add(new Pawn(Piece.Color.BLACK,0,6));
        blackPieces.add(new Pawn(Piece.Color.BLACK,1,6));
        blackPieces.add(new Pawn(Piece.Color.BLACK,2,6));
        blackPieces.add(new Pawn(Piece.Color.BLACK,3,6));
        blackPieces.add(new Pawn(Piece.Color.BLACK,4,6));
        blackPieces.add(new Pawn(Piece.Color.BLACK,5,6));
        blackPieces.add(new Pawn(Piece.Color.BLACK,6,6));
        blackPieces.add(new Pawn(Piece.Color.BLACK,7,6));
        blackPieces.add(new Queen(Piece.Color.BLACK, 3, 7, false));
        blackPieces.add(blackKing);
        blackPieces.add(new Bishop(Piece.Color.BLACK, 5, 7, false));
        blackPieces.add(new Bishop(Piece.Color.BLACK, 2, 7, false));
        blackPieces.add(new Knight(Piece.Color.BLACK, 6, 7, false));
        blackPieces.add(new Knight(Piece.Color.BLACK, 1, 7, false));
        blackPieces.add(leftBlackRook);
        blackPieces.add(rightBlackRook);
        
        for (int i = 0; i < 8; i++){
            for(int j = 2; j < 6; j++){
                piecesOnTheBoard[i][j] = new Empty(Piece.Color.EMPTY, i, j);
            }
        }
        
        for (Piece p : whitePieces){
            p.addModel(this);
            piecesOnTheBoard[p.square.x][p.square.y] = p;
        }
        
        for (Piece p : blackPieces){
            p.addModel(this);
            piecesOnTheBoard[p.square.x][p.square.y] = p;
        }
        
        this.createListOfPossibleMoves();
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
        ArrayList<Move> moves = new ArrayList<Move>(); // Queue of moves to me made at this turn.
        
        // En passant verification.
        // This works only for legal moves.
        if (piecesOnTheBoard[m.getIni().x][m.getIni().y] instanceof Pawn && piecesOnTheBoard[m.getIni().x][m.getIni().y].getColor() == Piece.Color.WHITE && Math.abs(m.getEnd().x - m.getIni().x) == 1 &&  piecesOnTheBoard[m.getEnd().x][m.getEnd().y].getColor() == Piece.Color.EMPTY && m.getIni().y == 4){
            moves.add(new Move(m.getEnd(), new Position(m.getEnd().x, m.getEnd().y - 1)));
        }
        if (piecesOnTheBoard[m.getIni().x][m.getIni().y] instanceof Pawn && piecesOnTheBoard[m.getIni().x][m.getIni().y].getColor() == Piece.Color.BLACK && Math.abs(m.getEnd().x - m.getIni().x) == 1 &&  piecesOnTheBoard[m.getEnd().x][m.getEnd().y].getColor() == Piece.Color.EMPTY && m.getIni().y == 3){
            moves.add(new Move(m.getEnd(), new Position(m.getEnd().x, m.getEnd().y+1)));
        }
        
        // Add the move to the queue of moves to me made.
        moves.add(m);
        
        // Castling verification.
        // This works only for legal moves.
        if (piecesOnTheBoard[m.getIni().x][m.getIni().y] instanceof King){
            if (m.getEnd().x - m.getIni().x > 1){
                moves.add(new Move(new Position(7, m.getIni().y), new Position(5, m.getIni().y)));
            }
            if (m.getEnd().x - m.getIni().x < -1){
                moves.add(new Move(new Position(0, m.getIni().y), new Position(3, m.getIni().y)));
            }
        }
        
        //  Realization of the moves at this turn.
        for (Move M : moves){
            Piece aux = piecesOnTheBoard[M.getIni().x][M.getIni().y];
            piecesOnTheBoard[M.getIni().x][M.getIni().y] = new Empty(Piece.Color.EMPTY, M.getIni().x, M.getIni().y);
            /*if(piecesOnTheBoard[M.getEnd().x][M.getEnd().y].getColor() == Piece.Color.WHITE){
                whitePieces.remove(piecesOnTheBoard[M.getEnd().x][M.getEnd().y]);
            } else if (piecesOnTheBoard[M.getEnd().x][M.getEnd().y].getColor() == Piece.Color.BLACK) {
                blackPieces.remove(piecesOnTheBoard[M.getEnd().x][M.getEnd().y]);
            }*/
            aux.listOfPiecesTaken.add(piecesOnTheBoard[M.getEnd().x][M.getEnd().y]);
            piecesOnTheBoard[M.getEnd().x][M.getEnd().y] = aux;
            aux.makeMove(M);
            lastPiecesMoved.add(aux);
        }
        
        // Turn changes.
        if (turn == Piece.Color.WHITE){
            turn = Piece.Color.BLACK;
        } else {
            turn = Piece.Color.WHITE;
        }
        
    }
    
    public Boolean isMovePossible(Move m){
        for(Move M : listOfPossibleMoves){
            if (m.ini.x == M.ini.x && m.ini.y == M.ini.y && m.end.x == M.end.x && m.end.y == M.end.y){
                return true;
            }
        }
        //return false; //change commented line to work properly.
        return true;
    }
    
    public Piece.Color getTurn(){
        return turn;
    }
    
    public void undo(){
        Piece lastMoved;
        Position currentSquare;
        
        if (thereIsNothingToUndo()){
            return;
        }
        
        while(this.lastPiecesMoved.get(this.lastPiecesMoved.size() - 1).color != this.turn){
            lastMoved = this.lastPiecesMoved.remove(this.lastPiecesMoved.size() - 1);
            currentSquare = lastMoved.getSquare();
            if (this.piecesOnTheBoard[currentSquare.x][currentSquare.y].lastMoves.isEmpty() && this.piecesOnTheBoard[currentSquare.x][currentSquare.y].isPromotedPiece){
                this.piecesOnTheBoard[currentSquare.x][currentSquare.y] = this.piecesOnTheBoard[currentSquare.x][currentSquare.y].ancientPiece;
            }
            this.piecesOnTheBoard[currentSquare.x][currentSquare.y] = this.piecesOnTheBoard[currentSquare.x][currentSquare.y].listOfPiecesTaken.remove(this.piecesOnTheBoard[currentSquare.x][currentSquare.y].listOfPiecesTaken.size() - 1);
            /*if(this.piecesOnTheBoard[currentSquare.x][currentSquare.y].getColor() == Piece.Color.WHITE){
                whitePieces.add(this.piecesOnTheBoard[currentSquare.x][currentSquare.y]);
            } else if (this.piecesOnTheBoard[currentSquare.x][currentSquare.y].getColor() == Piece.Color.BLACK) {
                blackPieces.add(this.piecesOnTheBoard[currentSquare.x][currentSquare.y]);
            }*/
            lastMoved.undo();
            this.piecesOnTheBoard[lastMoved.getSquare().x][lastMoved.getSquare().y] = lastMoved;
            if (this.lastPiecesMoved.isEmpty()){
                break;
            }
        }
        
        if (this.turn == Piece.Color.WHITE){
            this.turn = Piece.Color.BLACK;
        } else {
            this.turn = Piece.Color.WHITE;
        }
    }
    
    public boolean thereIsNothingToUndo(){
        return this.lastPiecesMoved.isEmpty();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        draw((Graphics2D) arg);
    }
    
    public String getStringOfPieceAt(int x, int y){
        return piecesOnTheBoard[x][y].toString();
    }
    
    public boolean isKingOfColorChecked(Piece.Color c){
        if (c == Piece.Color.WHITE){
            return isSquareAttackedByColor(whiteKing.square, Piece.Color.BLACK);
        } else {
            return isSquareAttackedByColor(blackKing.square, Piece.Color.WHITE);
        }
    }
    
    public void createListOfPossibleMoves(){
        
        listOfPossibleMoves.clear();
        
        for (Piece p : (turn == Piece.Color.WHITE ? whitePieces : blackPieces)){
            p.createListOfCandidateMoves();
            for(Move m : p.listOfCandidateMoves){
                makeMove(m);
                if (!isKingOfColorChecked(p.getColor())){
                    listOfPossibleMoves.add(new Move(m.ini, m.end));
                }
                undo();
            }
        }
    }
    
    public void updateListOfPossibleMoves(){
        
    }
    
    public boolean isSquareAttackedByColor(Position p, Piece.Color c){
        for (int i = 1; 
             (p.x + i < 8 && p.y + i < 8); 
             i++){
            
            if (this.piecesOnTheBoard[p.x+i][p.y+i].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[p.x+i][p.y+i].getColor() == Piece.Color.BLACK && i == 1 && this.piecesOnTheBoard[p.x+i][p.y+i] instanceof Pawn){
                return true;
            }
            
            if (this.piecesOnTheBoard[p.x+i][p.y+i].getColor() == c && (this.piecesOnTheBoard[p.x+i][p.y+i] instanceof Queen || this.piecesOnTheBoard[p.x+i][p.y+i] instanceof Bishop)){
                return true;
            } else {
                break;
            }
        }
        
        for (int i = 1; 
             (p.x + i < 8 && p.y - i > -1); 
             i++){
            
            if (this.piecesOnTheBoard[p.x+i][p.y-i].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[p.x+i][p.y-i].getColor() == Piece.Color.WHITE && i == 1 && this.piecesOnTheBoard[p.x+i][p.y-i] instanceof Pawn){
                return true;
            }
            
            if (this.piecesOnTheBoard[p.x+i][p.y-i].getColor() == c && (this.piecesOnTheBoard[p.x+i][p.y-i] instanceof Queen || this.piecesOnTheBoard[p.x+i][p.y-i] instanceof Bishop)){
                return true;
            } else {
                break;
            }
        }
        
        for (int i = 1; 
             (p.x - i > -1 && p.y + i < 8); 
             i++){
            
            if (this.piecesOnTheBoard[p.x-i][p.y+i].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[p.x-i][p.y+i].getColor() == Piece.Color.BLACK && i == 1 && this.piecesOnTheBoard[p.x-i][p.y+i] instanceof Pawn){
                return true;
            }
            
            if (this.piecesOnTheBoard[p.x-i][p.y+i].getColor() == c && (this.piecesOnTheBoard[p.x-i][p.y+i] instanceof Queen || this.piecesOnTheBoard[p.x-i][p.y+i] instanceof Bishop)){
                return true;
            } else {
                break;
            }
        }
        
        for (int i = 1; 
             (p.x - i > -1 && p.y - i > -1); 
             i++){
            
            if (this.piecesOnTheBoard[p.x-i][p.y-i].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[p.x-i][p.y-i].getColor() == Piece.Color.WHITE && i == 1 && this.piecesOnTheBoard[p.x-i][p.y-i] instanceof Pawn){
                return true;
            }
            
            if (this.piecesOnTheBoard[p.x-i][p.y-i].getColor() == c && (this.piecesOnTheBoard[p.x-i][p.y-i] instanceof Queen || this.piecesOnTheBoard[p.x-i][p.y-i] instanceof Bishop)){
                return true;
            } else {
                break;
            }
        }
        
        for (int i = 1; 
             (p.x + i < 8); 
             i++){
            
            if (this.piecesOnTheBoard[p.x+i][p.y].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[p.x+i][p.y].getColor() == c && (this.piecesOnTheBoard[p.x+i][p.y] instanceof Queen || this.piecesOnTheBoard[p.x+i][p.y] instanceof Rook)){
                return true;
            } else {
                break;
            }
        }
        
        for (int i = -1; 
             (p.x + i > -1); 
             i--){
            
            if (this.piecesOnTheBoard[p.x+i][p.y].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[p.x+i][p.y].getColor() == c && (this.piecesOnTheBoard[p.x+i][p.y] instanceof Queen || this.piecesOnTheBoard[p.x+i][p.y] instanceof Rook)){
                return true;
            } else {
                break;
            }
        }
        
        for (int j = 1; 
             (p.y + j < 8); 
             j++){
            
            if (this.piecesOnTheBoard[p.x][p.y+j].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[p.x][p.y+j].getColor() == c && (this.piecesOnTheBoard[p.x][p.y+j] instanceof Queen || this.piecesOnTheBoard[p.x][p.y+j] instanceof Rook)){
                return true;
            } else {
                break;
            }
        }
        
        for (int j = -1; 
             (p.y + j > -1); 
             j--){
            
            if (this.piecesOnTheBoard[p.x][p.y+j].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[p.x][p.y+j].getColor() == c && (this.piecesOnTheBoard[p.x][p.y+j] instanceof Queen || this.piecesOnTheBoard[p.x][p.y+j] instanceof Rook)){
                return true;
            } else {
                break;
            }
        }
        
        if (p.x + 1 < 8 && p.y + 2 < 8){
            if (this.piecesOnTheBoard[p.x+1][p.y+2].getColor() == c && this.piecesOnTheBoard[p.x+1][p.y+2] instanceof Knight){
                return true;
            }
        }
        if (p.x + 2 < 8 && p.y + 1 < 8){
            if (this.piecesOnTheBoard[p.x+2][p.y+1].getColor() == c && this.piecesOnTheBoard[p.x+2][p.y+1] instanceof Knight){
                return true;
            }
        }
        
        if (p.x - 1 > -1 && p.y + 2 < 8){
            if (this.piecesOnTheBoard[p.x-1][p.y+2].getColor() == c && this.piecesOnTheBoard[p.x-1][p.y+2] instanceof Knight){
                return true;
            }
        }
        
        if (p.x - 2 > -1 && p.y + 1 < 8){
            if (this.piecesOnTheBoard[p.x-2][p.y+1].getColor() == c && this.piecesOnTheBoard[p.x-2][p.y+1] instanceof Knight){
                return true;
            }
        }
        
        if (p.x + 1 < 8 && p.y - 2 > -1){
            if (this.piecesOnTheBoard[p.x+1][p.y-2].getColor() == c && this.piecesOnTheBoard[p.x+1][p.y-2] instanceof Knight){
                return true;
            }
        }
        
        if (p.x + 2 < 8 && p.y - 1 > -1){
            if (this.piecesOnTheBoard[p.x+2][p.y-1].getColor() == c && this.piecesOnTheBoard[p.x+2][p.y-1] instanceof Knight){
                return true;
            }
        }
        
        if (p.x - 1 > -1 && p.y - 2 > -1){
            if (this.piecesOnTheBoard[p.x-1][p.y-2].getColor() == c && this.piecesOnTheBoard[p.x-1][p.y-2] instanceof Knight){
                return true;
            }
        }
        
        if (p.x - 2 > -1 && p.y - 1 > -1){
            if (this.piecesOnTheBoard[p.x-2][p.y-1].getColor() == c && this.piecesOnTheBoard[p.x-2][p.y-1] instanceof Knight){
                return true;
            }
        }
        
        return false;
    }
}
