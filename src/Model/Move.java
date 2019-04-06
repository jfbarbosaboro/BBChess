package Model;
import java.awt.Point;
import Model.BoardModel;

public class Move {
    
    public Point ini;
    public Point end;
    public boolean isShortCastling = false;
    public boolean isLongCastling = false;
    
    public Move(Point ini, Point end){
        this.ini = ini;
        this.end = end;
        
    }
}
