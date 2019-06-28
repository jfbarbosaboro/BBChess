package BBChess;

import Controller.BoardController;
import Model.BoardModel;
import View.BoardView;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
        @Override
            public void run() {
                boolean existsSavedGame = true;
                BoardModel model = null;
                // Trying to deserialize the game:
                FileInputStream fileInput;
                BufferedInputStream buffer;
                ObjectInputStream objectInput;
                Object lastBoard = new Object();
                try {
                    fileInput = new FileInputStream("LastGame.bin");
                    buffer = new BufferedInputStream(fileInput);
                    objectInput = new ObjectInputStream(buffer);
                    lastBoard = objectInput.readObject();
                    model = (BoardModel)lastBoard;
                } catch (FileNotFoundException ex) {
                    existsSavedGame = false;
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
                BoardController controller;
                if (existsSavedGame){
                    controller = model.controller;
                } else {
                    controller = new BoardController();
                }

                view.addController(controller);

                controller.addView(view);
                controller.addModel(model);
                
                if (!existsSavedGame){
                    model.addController(controller);
                }

                controller.runBoard(existsSavedGame);
                
                final BoardModel finalModel = model;
                
                view.addWindowListener(new WindowAdapter(){
                    @Override
                    public void windowClosing(WindowEvent e){
                        
                        String[] closeOption = {"Save game and quit", "Quit without saving game"};
                        int opt = JOptionPane.showOptionDialog(null, "Do you want to save the game before quit?", "BBChess", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, closeOption, closeOption[0]);
                        if(opt == 0) { // Serialize the game.
                            System.out.println("Serialization started...");
                            FileOutputStream fileOutput;
                            ObjectOutputStream objectOutput;
                            try {
                                fileOutput = new FileOutputStream("LastGame.bin");
                                objectOutput = new ObjectOutputStream(fileOutput);
                                objectOutput.writeObject(finalModel);
                                objectOutput.close();
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            finalModel.controller.autoSave.interrupt();
                            File file = new File(".\\LastGame.bin");
                            file.delete();
                            System.exit(0);
                        }
                        
                        
                        
                    }
                });
            }
        });
    }
}
