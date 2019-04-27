package Controller;

import Model.BoardModel;
import Model.Move;
import View.BoardView;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import Model.Position;
import Model.Piece;
import Model.Pawn;
import java.util.ArrayList;

public class BoardController implements MouseListener, MouseMotionListener{

  protected BoardView view;
  protected BoardModel model;
  private static int moveState = 0;
  private Position ini;
  private Position end;
  private Move m;
  
  
    public void addView (BoardView view){
        this.view = view;
    }
    
    public void addModel (BoardModel model){
        this.model = model;
    }
    
    public void runBoard() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - view.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - view.getHeight()) / 2);
        view.setLocation(x, y);
        
        view.setVisible(true);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        view.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        String wK, bK;
        
        Position p = Square(e.getX(),e.getY());
        if (e.getButton() == 1){
            view.getPieceNameLabel().setText(model.getStringOfPieceAt(p.x(), p.y()));
            if (moveState == 0){
                ini = p;
                if (clickedOnPiece(e) && model.piecesOnTheBoard[ini.x()][ini.y()].getColor() == model.getTurn()){
                    moveState = 1;
                }
            }
            else {
                end = p;
                m = new Move(ini, end);
                
                if ((model.piecesOnTheBoard[ini.x()][ini.y()] instanceof Pawn && model.piecesOnTheBoard[ini.x()][ini.y()].getColor() == Piece.Color.WHITE && end.y() == 7)
                    || (model.piecesOnTheBoard[ini.x()][ini.y()] instanceof Pawn && model.piecesOnTheBoard[ini.x()][ini.y()].getColor() == Piece.Color.BLACK && end.y() == 0)){
                
                    m.setPromotionOption(getPromotionType());
                    
                }
                
                if (model.isMovePossible(m)){
                    model.makeMove(m);
                }
                
                wK = model.isKingOfColorChecked(Piece.Color.WHITE) ? "True" : "False";
                bK = model.isKingOfColorChecked(Piece.Color.BLACK) ? "True" : "False";
                
                System.out.println("White King is in check: " + wK);
                System.out.println("Black King is in check: " + bK);
                
                model.createListOfPossibleMoves();
                
                view.repaint();
                moveState = 0;
            }
        }
        if (e.getButton() == 3){
            moveState = 0;
        }
        view.getClickLabel().setText("x:"+p.x()+"  y:"+p.y()+"   -   Square: ["+p.x()+","+p.y()+"]");
    }
    
    public boolean clickedOnPiece(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        Position p = Square(x,y);
        if (model.piecesOnTheBoard[p.x()][p.y()].getColor() != Piece.Color.EMPTY){
            return true;
        } else {
            return false;
        }
    }
    
    public int getMoveState(){
        return moveState;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Position p = Square(x,y);
        view.getCoordinateLabel().setText("x:"+x+"  y:"+y+"   -   Square: ["+p.x()+","+p.y()+"]");
        view.getMouseCoord(x, y);
        view.repaint();
    }
    
    private Position Square(int x, int y){
        return new Position(Math.min(Math.max((x-56)/76, 0), 7), Math.min(Math.max(7 - (y-56)/76, 0), 7));
    }
    
    public void undo(){
        model.undo();
        model.createListOfPossibleMoves();
        view.repaint();
    }
    
    public boolean thereIsNothingToUndo(){
        return model.thereIsNothingToUndo();
    }
    
    public int getPromotionType(){
        return 0;
    }
    
    public Position getIni() {
        return ini;
    }

    public ArrayList<Position> getListOfPossibleMovesForPieceIn(Position p) {
        return model.getListOfPossibleMovesForPieceIn(p);
     }
    
    public ArrayList<Position> getListOfCapturableEnemiesForPieceIn(Position p) { 
        return model.getListOfCapturableEnemiesForPieceIn(p);
    }

}
