package BBChess;

import Controler.BoardController;
import Model.BoardModel;
import View.BoardView;

public class Main {
    
  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
          
        BoardModel model = new BoardModel();
        BoardView viewBoard = new BoardView(model);
        BoardController boardController = new BoardController(); 
        
        
        viewBoard.addController(boardController);
        viewBoard.addModel(model);
        boardController.addView(viewBoard);
        boardController.addModel(model);
        boardController.runBoard();
      }
    });
  }
}
