package BBChess;

import Controller.BoardController;
import Model.BoardModel;
import View.BoardView;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    
  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {
            BoardModel model = null;
            FileInputStream fileInput;
            BufferedInputStream buffer;
            ObjectInputStream objectInput;
            Object lastBoard = new Object();
            try {
                fileInput = new FileInputStream("LastGame.data");
                buffer = new BufferedInputStream(fileInput);
                objectInput = new ObjectInputStream(buffer);
                lastBoard = objectInput.readObject();
                model = (BoardModel)lastBoard;
            } catch (FileNotFoundException ex) {
                model = new BoardModel();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
            
            BoardView view = null;
            try {
                view = new BoardView(model);
            } catch (NullPointerException exception){
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, exception);
                System.exit(1);
            }
            BoardController controller = new BoardController(); 
        
            view.addController(controller);
        
            controller.addView(view);
            controller.addModel(model);
            
            model.addController(controller);
        
            controller.runBoard();
            
            view.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e){
                    System.out.println("Serialization started...");
                    FileOutputStream fileOutput;
                    BufferedOutputStream buffer;
                    ObjectOutputStream objectOutput;
                    try {
                        fileOutput = new FileOutputStream("LastGame.data");
                        buffer = new BufferedOutputStream(fileOutput);
                        objectOutput = new ObjectOutputStream(buffer);
                        objectOutput.writeObject(model);
                        objectOutput.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
        }
    });
  }
}
