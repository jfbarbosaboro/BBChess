package Model;

import java.awt.Graphics2D;

public class Empty extends Piece{

    public Empty(Color color, int x, int y)  {
        super(color, x, y);
    }
    
    @Override
    public void draw(Graphics2D g) {
        
    }
    
    @Override
    public String toString() {
        return "Empty";
    }
}