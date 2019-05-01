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
import javax.swing.JOptionPane;

public class BoardController implements MouseListener, MouseMotionListener{

  protected BoardView view;
  protected BoardModel model;
  private static int moveState = 0;
  private Position ini;
  private Position end;
  private Move m;
  private boolean isAgainstTheMachine;
  private Piece.Color machineColor;
  
  
    public void addView (BoardView view){
        this.view = view;
    }
    
    public void addModel (BoardModel model){
        this.model = model;
    }
    
    public void runBoard() {
        
        setTheGameUp();
        
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
                
                for (Move m : model.listOfPossibleMoves){
                    System.out.println("Ini: ("+(m.getIni().x())+", "+(m.getIni().y())+") - End: ("+(m.getEnd().x())+", "+(m.getEnd().y())+")");
                }
                
                if (hasNotAlreadyFinished() && isAgainstTheMachine && model.getTurn() == machineColor){
                    
                    model.makeMove(model.getRandomPossibleMove());
                    model.createListOfPossibleMoves();
                    view.repaint();
                    hasNotAlreadyFinished();
                    view.repaint();
                }
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
    
    public boolean hasNotAlreadyFinished(){
        if (model.listOfPossibleMoves.isEmpty()){
            if (model.getTurn() == Piece.Color.WHITE){
                if (model.isKingOfColorChecked(Piece.Color.WHITE)){
                    //White was checkmated;
                    System.out.println("White was checkmated.");
                    JOptionPane.showMessageDialog(null, "White was checkmated!", "BBChess", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    //It's a draw.
                    System.out.println("It's a draw.");
                    JOptionPane.showMessageDialog(null, "It's a  draw.", "BBChess", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                if (model.isKingOfColorChecked(Piece.Color.BLACK)){
                    //Black was checkmated;
                    System.out.println("Black was checkmated.");
                    JOptionPane.showMessageDialog(null, "Black was checkmated!", "BBChess", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    //It's a draw.
                    System.out.println("It's a draw.");
                    JOptionPane.showMessageDialog(null, "It's a draw.", "BBChess", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            String[] choices= {"Play again!", "Quit"};
            int response = JOptionPane.showOptionDialog(null, "Do you want to play again ar quit?", "BBChess", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
            if (response != 0 && response != 1){
                response = 0;
            }
            if (response == 0){
                this.setTheGameUp();
            } else {
                System.exit(0);
            }
            return false;
        }
        return true;
    }
    
    public void setTheGameUp(){
        String[] againstWho = {"Play against a friend", "Play against the machine"};
        int opt = JOptionPane.showOptionDialog(null, "Who do you want to play against?", "BBChess", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, againstWho, againstWho[0]);

        if (opt != 0 && opt != 1){
            opt = 1;
        }
        
        if (opt == 1){
            isAgainstTheMachine = true;
        } else {
            isAgainstTheMachine = false;
        }

        if (opt == 1){
            String[] colors = {"White", "Black"};
            int colorOfPlayer = JOptionPane.showOptionDialog(null, "Choose a color for you:", "BBChess", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, colors, colors[0]);
            if (colorOfPlayer != 0 && colorOfPlayer != 1){
                colorOfPlayer = 0;
            }
            model.init();
            if (colorOfPlayer == 1){
                machineColor = Piece.Color.WHITE;
                model.makeMove(model.getRandomPossibleMove());
                model.createListOfPossibleMoves();
                view.repaint();
            } else {
                machineColor = Piece.Color.BLACK;
            }
            return;
        }
        
        model.init();
        return;
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
        
        if (!isAgainstTheMachine){
            model.undo();
            model.createListOfPossibleMoves();
        } else {
            if (machineColor == Piece.Color.BLACK){
                for (int i = 0; i < 2; i++){
                    model.undo();
                    model.createListOfPossibleMoves();
                }
            } else {
                int count = 0;
                while (model.lastPiecesMoved.size() > 1 && count < 2){
                    model.undo();
                    model.createListOfPossibleMoves();
                    count++;
                }
            }
        }
        view.repaint();
    }
    
    public boolean thereIsNothingToUndo(){
        return model.thereIsNothingToUndo();
    }
    
    public int getPromotionType(){
        String[] choices= {"Queen","Rook", "Bishop", "Knight"};
        int response = JOptionPane.showOptionDialog(null, "Chose a promotion option:", "BBChess", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
        if (response != 0 && response != 1 && response != 2 && response != 3){
            return 0;
        } else {
            return response;
        }
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
