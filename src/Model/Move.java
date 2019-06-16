package Model;

import java.io.Serializable;

public class Move implements Serializable{
    
    protected Position ini;
    protected Position end;
    protected boolean isShortCastling = false;
    protected boolean isLongCastling = false;
    protected int promotionOption;
    
    public Move(Position ini, Position end){
        this.ini = new Position(ini.x, ini.y);
        this.end = new Position(end.x, end.y);
    }
    
    public void setShorCastling(boolean isShortCastling){
        this.isShortCastling = isShortCastling;
    }
    
    public void setLongCastling(boolean isLongCastling){
        this.isLongCastling = isLongCastling;
    }
    
    public void setPromotionOption(int promotionOption){
        this.promotionOption = promotionOption;
    }
    
    public Position getIni(){
        return ini;
    }
    
    public Position getEnd(){
        return end;
    }
    
    public boolean isShortCastling(){
        return isShortCastling;
    }
    
    public boolean isLongCastling(){
        return isLongCastling;
    }
    
    public int getPromotionOption(){
        return promotionOption;
    }
}
