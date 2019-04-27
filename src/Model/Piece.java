package Model;

import java.util.ArrayList;
import java.awt.Graphics2D;
import Model.Position;

public abstract class Piece implements IMovable{
    
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
    
    public Piece(Color color, int x, int y, boolean isPromotedPiece)  {
        this.color = color;
        this.square = new Position(x,y);
        this.isPromotedPiece = isPromotedPiece;
        this.isOnTheBoard = true;
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
