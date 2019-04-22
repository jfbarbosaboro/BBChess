/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author jose_fernando
 */
public class Position {
    
    protected int x;
    protected int y;
    
    public Position(){
        this.x = 0;
        this.y = 0;
    }
    
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int x(){
        return this.x;
    }
    
    public int y(){
        return this.y;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    
    
}
