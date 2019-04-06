package Model;

import java.awt.Graphics2D;
import java.awt.Point;

public abstract class Piece implements IMovable{
    
    protected boolean hasBeenMoved = false;
    protected Color color;
    protected Point square;
    private BoardModel model;
    
    
    public enum Color{
        BLACK,
        WHITE,
        EMPTY
    }
    
    public Piece(Color color, int x, int y)  {
        this.color = color;
        this.square = new Point(x,y);
    }
    
    public boolean inSquare(Point p){
        if(p.x == this.square.x && p.y == this.square.y) return true;
        else return false;
    }
    
    public void setSquare(int x, int y){
        this.square.setLocation(x, y);
    }
    
    public abstract void draw(Graphics2D g);
    
    public Point getQuad(){
        return this.square;
    }
    
    public void makeMove(Move m){
        this.hasBeenMoved = true;
        this.square.x = m.end.x;
        this.square.y = m.end.y;
    }
    
    public Color getColor(){
        return this.color;
    }
}
