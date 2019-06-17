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
                boolean existsSavedGame = true;
                BoardModel model = null;
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
                    System.out.println("Tá salvo sim...");
                } else {
                    controller = new BoardController();
                    System.out.println("Disse que num tem nada salvo...");
                }

                view.addController(controller);

                controller.addView(view);
                controller.addModel(model);
                
                if (!existsSavedGame){
                    model.addController(controller);
                    System.out.println("era pra não ter nada salvo...");
                }

                controller.runBoard(existsSavedGame);
                
                final BoardModel finalModel = model;
                
                view.addWindowListener(new WindowAdapter(){
                    @Override
                    public void windowClosing(WindowEvent e){
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
                    }
                });
            }
        });
    }
}
