package Model;

import java.awt.Graphics2D;

public class Empty extends Piece{

    public Empty(Color color, int x, int y, BoardModel model)  {
        super(color, x, y, false, model);
        this.isOnTheBoard = false;
    }
    
    @Override
    public void setImages(){
        
    }
    
    @Override
    public void draw(Graphics2D g) {}
    
    @Override
    public String toString() {
        return "Empty";
    }
    
    @Override
    public void createListOfCandidateMoves(){
        
    }
}