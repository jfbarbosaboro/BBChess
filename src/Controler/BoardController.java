package Controler;

import Model.BoardModel;
import Model.Move;
import View.BoardView;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Point;
import Model.Piece;

public class BoardController implements  MouseListener, MouseMotionListener{

  private BoardView view;
  private BoardModel model;
  private static int moveState = 0;
  private Point ini;
  private Point end;
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
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = Quad(e.getX(),e.getY());
        if (e.getButton() == 1){
            view.getPieceNameLabel().setText(model.getStringOfPieceAt(p.x, p.y));
            if (moveState == 0){
                System.out.println("Entrou no caso 0.");
                ini = p;
                if (clickedOnPiece(e)){
                    moveState = 1;
                }
            }
            else {
                System.out.println("Entrou no caso 1.");
                end = p;
                m = new Move(ini, end);
                System.out.println("ini.x = "+ini.x+", ini.y = "+ini.y+", end.x = "+end.x+", end.y = "+end.y);
                if (model.piecesOnTheBoard[m.ini.x][m.ini.y].isMovePossible(m)){
                    model.makeMove(m);
                }
                view.repaint();
                moveState = 0;
            }
        }
        if (e.getButton() == 3){
            System.out.println("Entrou no caso 2.");
            moveState = 0;
        }
        view.getClickLabel().setText("x:"+p.x+"  y:"+p.y+"   -   Square: ["+p.x+","+p.y+"]");
    }
    
    public boolean clickedOnPiece(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        Point p = Quad(x,y);
        if (model.piecesOnTheBoard[p.x][p.y].getCor() != Piece.Color.EMPTY){
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
        Point p = Quad(x,y);
        view.getCoordinateLabel().setText("x:"+x+"  y:"+y+"   -   Square: ["+p.x+","+p.y+"]");
        view.getMouseCoord().setLocation(x, y);
        view.repaint();
    }
    
    private Point Quad(int x, int y){
        return new Point((x-56)/76, (y-56)/76);
    }

}
