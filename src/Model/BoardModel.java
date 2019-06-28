package Model;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import Controller.BoardController;
import java.io.Serializable;
import java.util.Random;


public class BoardModel implements Observer, Serializable{

    public ArrayList<Piece> whitePieces = new ArrayList<Piece>();
    public ArrayList<Piece> blackPieces = new ArrayList<Piece>();
    public Piece[][] piecesOnTheBoard;
    public ArrayList<Move> listOfPossibleMoves = new ArrayList<Move>();
    private Piece.Color turn = Piece.Color.WHITE;
    public ArrayList<Piece> lastPiecesMoved = new ArrayList<Piece>();
    protected Piece whiteKing;// Pointer to White King.
    protected Piece blackKing; // Pointer to Black King.
    protected Piece leftWhiteRook;
    protected Piece rightWhiteRook;
    protected Piece leftBlackRook;
    protected Piece rightBlackRook;
    public long elapsedTime;
    public BoardController controller;

    public BoardModel()  {
        this.piecesOnTheBoard = new Piece[8][8];
    }
    
    public void addController(BoardController controller){
        this.controller = controller;
    }
    
    public void init() {
        
        this.controller.hasNotFinishedYet = true;
        
        whitePieces.clear();
        blackPieces.clear();
        lastPiecesMoved.clear();
        
        this.whiteKing = new King(Piece.Color.WHITE, 4, 0, this);
        this.blackKing = new King(Piece.Color.BLACK, 4, 7, this);
        this.leftWhiteRook = new Rook(Piece.Color.WHITE, 0, 0, false, this);
        this.rightWhiteRook = new Rook(Piece.Color.WHITE, 7, 0, false, this);
        this.leftBlackRook = new Rook(Piece.Color.BLACK, 0, 7, false, this);
        this.rightBlackRook = new Rook(Piece.Color.BLACK, 7, 7, false, this);
        
        // initialize white pieces
        whitePieces.add(new Pawn(Piece.Color.WHITE,0,1, this));
        whitePieces.add(new Pawn(Piece.Color.WHITE,1,1, this));
        whitePieces.add(new Pawn(Piece.Color.WHITE,2,1, this));
        whitePieces.add(new Pawn(Piece.Color.WHITE,3,1, this));
        whitePieces.add(new Pawn(Piece.Color.WHITE,4,1, this));
        whitePieces.add(new Pawn(Piece.Color.WHITE,5,1, this));
        whitePieces.add(new Pawn(Piece.Color.WHITE,6,1, this));
        whitePieces.add(new Pawn(Piece.Color.WHITE,7,1, this));
        whitePieces.add(new Queen(Piece.Color.WHITE, 3, 0, false, this));
        whitePieces.add(this.whiteKing);
        whitePieces.add(new Bishop(Piece.Color.WHITE, 5, 0, false, this));
        whitePieces.add(new Bishop(Piece.Color.WHITE, 2, 0, false, this));
        whitePieces.add(new Knight(Piece.Color.WHITE, 6, 0, false, this));
        whitePieces.add(new Knight(Piece.Color.WHITE, 1, 0, false, this));
        whitePieces.add(this.leftWhiteRook);
        whitePieces.add(this.rightWhiteRook);
        
        // inicialize black pieces
        blackPieces.add(new Pawn(Piece.Color.BLACK,0,6, this));
        blackPieces.add(new Pawn(Piece.Color.BLACK,1,6, this));
        blackPieces.add(new Pawn(Piece.Color.BLACK,2,6, this));
        blackPieces.add(new Pawn(Piece.Color.BLACK,3,6, this));
        blackPieces.add(new Pawn(Piece.Color.BLACK,4,6, this));
        blackPieces.add(new Pawn(Piece.Color.BLACK,5,6, this));
        blackPieces.add(new Pawn(Piece.Color.BLACK,6,6, this));
        blackPieces.add(new Pawn(Piece.Color.BLACK,7,6, this));
        blackPieces.add(new Queen(Piece.Color.BLACK, 3, 7, false, this));
        blackPieces.add(this.blackKing);
        blackPieces.add(new Bishop(Piece.Color.BLACK, 5, 7, false, this));
        blackPieces.add(new Bishop(Piece.Color.BLACK, 2, 7, false, this));
        blackPieces.add(new Knight(Piece.Color.BLACK, 6, 7, false, this));
        blackPieces.add(new Knight(Piece.Color.BLACK, 1, 7, false, this));
        blackPieces.add(this.leftBlackRook);
        blackPieces.add(this.rightBlackRook);
        
        for (int i = 0; i < 8; i++){
            for(int j = 0; j < 6; j++){
                    piecesOnTheBoard[i][j] = new Empty(Piece.Color.EMPTY, i, j, this);
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
        
        this.turn = Piece.Color.WHITE;
        
        this.elapsedTime = 0;
        this.controller.startClock(0);
        
        this.createListOfPossibleMoves();
        
        this.controller.startAutoSave();
    }
    
    public void initSavedGame(){
        this.controller.startClock(elapsedTime);
        this.controller.startAutoSave();
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
            piecesOnTheBoard[M.getIni().x][M.getIni().y] = new Empty(Piece.Color.EMPTY, M.getIni().x, M.getIni().y, this);
            piecesOnTheBoard[M.getEnd().x][M.getEnd().y].isOnTheBoard = false;
            aux.listOfTakenPieces.add(piecesOnTheBoard[M.getEnd().x][M.getEnd().y]);
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
        for (Move M : this.listOfPossibleMoves){
            if (M.ini.x() == m.ini.x() && M.ini.y() == m.ini.y() && M.end.x() == m.end.x() && M.end.y() == m.end.y()){
                return true;
            }
        }
        return false;
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
            this.piecesOnTheBoard[currentSquare.x][currentSquare.y] = this.piecesOnTheBoard[currentSquare.x][currentSquare.y].listOfTakenPieces.remove(this.piecesOnTheBoard[currentSquare.x][currentSquare.y].listOfTakenPieces.size() - 1);
            this.piecesOnTheBoard[currentSquare.x][currentSquare.y].isOnTheBoard = true;
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
    
    public void createListOfPossibleMoves(){
        
        listOfPossibleMoves.clear();
        
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (this.piecesOnTheBoard[i][j].getColor() == this.turn){
                    this.piecesOnTheBoard[i][j].createListOfCandidateMoves();
                    for (Move m : this.piecesOnTheBoard[i][j].listOfCandidateMoves){
                        Piece.Color aux = this.piecesOnTheBoard[i][j].getColor();
                        this.makeMove(m);
                        if (!this.isKingOfColorChecked(aux)){
                            this.listOfPossibleMoves.add(m);
                        }
                        this.undo();
                    }
                }
            }
        }
    }
    
    public void setTheGameUp(){
        this.controller.setTheGameUp(false);
    }
    
    public Move getRandomPossibleMove(){
        if (this.listOfPossibleMoves.isEmpty()){
            return null;
        } else {
            Random rand = new Random();
            return this.listOfPossibleMoves.get(rand.nextInt(this.listOfPossibleMoves.size()));
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
            return isSquareAttackedByColor(whiteKing.square.x, whiteKing.square.y, Piece.Color.BLACK);
        } else {
            return isSquareAttackedByColor(blackKing.square.x, blackKing.square.y, Piece.Color.WHITE);
        }
    }
    
    public ArrayList<Position> getListOfPossibleMovesForPieceIn(Position p) {
        ArrayList<Position> PossibleMovesForPiece = new ArrayList<Position>(); 
        for(Move m : listOfPossibleMoves) {
            if((p.x == m.ini.x)&&(p.y == m.ini.y)) {
                PossibleMovesForPiece.add(m.end); 
            }
        }
        return PossibleMovesForPiece;
    }
    
    public boolean isSquareAttackedByColor(int x, int y, Piece.Color c){
        for (int i = 1; 
             (x + i < 8 && y + i < 8); 
             i++){
            
            if (this.piecesOnTheBoard[x+i][y+i].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (i == 1 && c == Piece.Color.BLACK && this.piecesOnTheBoard[x+i][y+i].getColor() == Piece.Color.BLACK && this.piecesOnTheBoard[x+i][y+i] instanceof Pawn){
                return true;
            }
            
            if (this.piecesOnTheBoard[x+i][y+i].getColor() == c && (this.piecesOnTheBoard[x+i][y+i] instanceof Bishop || this.piecesOnTheBoard[x+i][y+i] instanceof Queen)){
                return true;
            } else {
                break;
            }
        }
        
        for (int i = 1; 
             (x + i < 8 && y - i > -1); 
             i++){
            
            if (this.piecesOnTheBoard[x+i][y-i].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (i == 1 && c == Piece.Color.WHITE && this.piecesOnTheBoard[x+i][y-i].getColor() == Piece.Color.WHITE && this.piecesOnTheBoard[x+i][y-i] instanceof Pawn){
                return true;
            }
            
            if (this.piecesOnTheBoard[x+i][y-i].getColor() == c && (this.piecesOnTheBoard[x+i][y-i] instanceof Bishop || this.piecesOnTheBoard[x+i][y-i] instanceof Queen)){
                return true;
            } else {
                break;
            }
        }
        
        for (int i = 1; 
             (x - i > -1 && y + i < 8); 
             i++){
            
            if (this.piecesOnTheBoard[x-i][y+i].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (i == 1 && c == Piece.Color.BLACK && this.piecesOnTheBoard[x-i][y+i].getColor() == Piece.Color.BLACK && this.piecesOnTheBoard[x-i][y+i] instanceof Pawn){
                return true;
            }
            
            if (this.piecesOnTheBoard[x-i][y+i].getColor() == c && (this.piecesOnTheBoard[x-i][y+i] instanceof Bishop || this.piecesOnTheBoard[x-i][y+i] instanceof Queen)){
                return true;
            } else {
                break;
            }
        }
        
        for (int i = 1; 
             (x - i > -1 && y - i > -1); 
             i++){
            
            if (this.piecesOnTheBoard[x-i][y-i].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (i == 1 && c == Piece.Color.WHITE && this.piecesOnTheBoard[x-i][y-i].getColor() == Piece.Color.WHITE && this.piecesOnTheBoard[x-i][y-i] instanceof Pawn){
                return true;
            }
            
            if (this.piecesOnTheBoard[x-i][y-i].getColor() == c && (this.piecesOnTheBoard[x-i][y-i] instanceof Bishop || this.piecesOnTheBoard[x-i][y-i] instanceof Queen)){
                return true;
            } else {
                break;
            }
        }
        
        for (int i = 1; 
             (x + i < 8); 
             i++){
            
            if (this.piecesOnTheBoard[x+i][y].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[x+i][y].getColor() == c && (this.piecesOnTheBoard[x+i][y] instanceof Rook || this.piecesOnTheBoard[x+i][y] instanceof Queen)){
                return true;
            } else {
                break;
            }
        }
        
        for (int i = -1; 
             (x + i > -1); 
             i--){
            
            if (this.piecesOnTheBoard[x+i][y].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[x+i][y].getColor() == c && (this.piecesOnTheBoard[x+i][y] instanceof Rook || this.piecesOnTheBoard[x+i][y] instanceof Queen)){
                return true;
            } else {
                break;
            }
        }
        
        for (int j = 1; 
             (y + j < 8); 
             j++){
            
            if (this.piecesOnTheBoard[x][y+j].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[x][y+j].getColor() == c && (this.piecesOnTheBoard[x][y+j] instanceof Rook || this.piecesOnTheBoard[x][y+j] instanceof Queen)){
                return true;
            } else {
                break;
            }
        }
        
        for (int j = -1; 
             (y + j > -1); 
             j--){
            
            if (this.piecesOnTheBoard[x][y+j].getColor() == Piece.Color.EMPTY){
                continue;
            }
            
            if (this.piecesOnTheBoard[x][y+j].getColor() == c && (this.piecesOnTheBoard[x][y+j] instanceof Rook || this.piecesOnTheBoard[x][y+j] instanceof Queen)){
                return true;
            } else {
                break;
            }
        }
        
        if (x + 1 < 8 && y + 2 < 8){
            if (this.piecesOnTheBoard[x+1][y+2].getColor() == c && this.piecesOnTheBoard[x+1][y+2] instanceof Knight){
                return true;
            }
        }
        if (x + 2 < 8 && y + 1 < 8){
            if (this.piecesOnTheBoard[x+2][y+1].getColor() == c && this.piecesOnTheBoard[x+2][y+1] instanceof Knight){
                return true;
            }
        }
        
        if (x - 1 > -1 && y + 2 < 8){
            if (this.piecesOnTheBoard[x-1][y+2].getColor() == c && this.piecesOnTheBoard[x-1][y+2] instanceof Knight){
                return true;
            }
        }
        
        if (x - 2 > -1 && y + 1 < 8){
            if (this.piecesOnTheBoard[x-2][y+1].getColor() == c && this.piecesOnTheBoard[x-2][y+1] instanceof Knight){
                return true;
            }
        }
        
        if (x + 1 < 8 && y - 2 > -1){
            if (this.piecesOnTheBoard[x+1][y-2].getColor() == c && this.piecesOnTheBoard[x+1][y-2] instanceof Knight){
                return true;
            }
        }
        
        if (x + 2 < 8 && y - 1 > -1){
            if (this.piecesOnTheBoard[x+2][y-1].getColor() == c && this.piecesOnTheBoard[x+2][y-1] instanceof Knight){
                return true;
            }
        }
        
        if (x - 1 > -1 && y - 2 > -1){
            if (this.piecesOnTheBoard[x-1][y-2].getColor() == c && this.piecesOnTheBoard[x-1][y-2] instanceof Knight){
                return true;
            }
        }
        
        if (x - 2 > -1 && y - 1 > -1){
            if (this.piecesOnTheBoard[x-2][y-1].getColor() == c && this.piecesOnTheBoard[x-2][y-1] instanceof Knight){
                return true;
            }
        }
      

        
        if (x + 1 < 8 && y + 1 < 8){
            if (this.piecesOnTheBoard[x+1][y+1].getColor() == c && this.piecesOnTheBoard[x+1][y+1] instanceof King){
                return true;
            }
        }
        
        if (x + 1 < 8 && y - 1 > -1){
            if (this.piecesOnTheBoard[x+1][y-1].getColor() == c && this.piecesOnTheBoard[x+1][y-1] instanceof King){
                return true;
            }
        }
        
        if (x - 1 > -1 && y + 1 < 8){
            if (this.piecesOnTheBoard[x-1][y+1].getColor() == c && this.piecesOnTheBoard[x-1][y+1] instanceof King){
                return true;
            }
        }
        
        if (x - 1 > -1 && y - 1 > -1){
            if (this.piecesOnTheBoard[x-1][y-1].getColor() == c && this.piecesOnTheBoard[x-1][y-1] instanceof King){
                return true;
            }
        }
        
        if (x + 1 < 8){
            if (this.piecesOnTheBoard[x+1][y].getColor() == c && this.piecesOnTheBoard[x+1][y] instanceof King){
                return true;
            }
        }
        
        if (x - 1 > -1){
            if (this.piecesOnTheBoard[x-1][y].getColor() == c && this.piecesOnTheBoard[x-1][y] instanceof King){
                return true;
            }
        }
        
        if (y + 1 < 8){
            if (this.piecesOnTheBoard[x][y+1].getColor() == c && this.piecesOnTheBoard[x][y+1] instanceof King){
                return true;
            }
        }
        
        if (y - 1 > -1){
            if (this.piecesOnTheBoard[x][y-1].getColor() == c && this.piecesOnTheBoard[x][y-1] instanceof King){
                return true;
            }
        }
        
        
        return false;
    }
    
    public ArrayList<Position> getListOfCapturableEnemiesForPieceIn(Position p) {
        ArrayList<Position> CapturableEnemiesForPiece = new ArrayList<Position>(); 
        for(Move m : listOfPossibleMoves) {
            if((p.x == m.ini.x)&&(p.y == m.ini.y)&&(!(findPiece(m.end.x, m.end.y) instanceof Empty))) {
                CapturableEnemiesForPiece.add(m.end);
            }
        }
        return CapturableEnemiesForPiece;
    }
}
