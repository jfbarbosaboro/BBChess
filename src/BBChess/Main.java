package BBChess;

import Controller.BoardController;
import Model.BoardModel;
import View.BoardView;

public class Main {
    
  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {
            BoardModel model = new BoardModel();
            BoardView view = new BoardView(model);
            BoardController controller = new BoardController(); 
        
            view.addController(controller);
        
            controller.addView(view);
            controller.addModel(model);
            
            model.addController(controller);
        
            controller.runBoard();
        }
    });
  }
}
