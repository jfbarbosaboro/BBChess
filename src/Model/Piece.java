package Model;

import java.util.ArrayList;
import java.awt.Graphics2D;
import Model.Position;
import java.io.Serializable;

public abstract class Piece implements IMovable, Serializable{
    
    protected int numberOfMoves = 0;
    protected Color color;
    protected Position square;
    protected boolean isPromotedPiece;
    protected BoardModel model;
    public ArrayList<Move> listOfCandidateMoves = new ArrayList<Move>();
    public ArrayList<Move> lastMoves = new ArrayList<Move>();
    public ArrayList<Piece> listOfTakenPieces = new ArrayList<Piece>();
    public Piece ancientPiece;
    public boolean isOnTheBoard;
    
    public enum Color{
        BLACK,
        WHITE,
        EMPTY
    }
    
    public abstract void setImages();
    
    public Piece(Color color, int x, int y, boolean isPromotedPiece, BoardModel model)  {
        this.color = color;
        this.square = new Position(x,y);
        this.isPromotedPiece = isPromotedPiece;
        this.isOnTheBoard = true;
        this.model = model;
    }
    
    public abstract void draw(Graphics2D g);
    //public abstract void createListOfCandidateMoves();
    
    public void addModel(BoardModel model){
        this.model = model;
    }
    
    public boolean inSquare(Position p){
        return (p.x == this.square.x && p.y == this.square.y);
    }
    
    public void setSquare(Position p){
        this.square.setPosition(p.x, p.y);
    }
    
    public Position getSquare(){
        return square;
    }
    
    @Override
    public void makeMove(Move m){
        this.numberOfMoves++;
        this.lastMoves.add(m);
        this.square.setPosition(m.end.x, m.end.y);
    }
    
    public void undo(){
        Move lastMove = this.lastMoves.remove(--this.numberOfMoves);
        this.square.setPosition(lastMove.getIni().x, lastMove.getIni().y);
    }
    
    public abstract void createListOfCandidateMoves();
    
    public Color getColor(){
        return color;
    }
}
