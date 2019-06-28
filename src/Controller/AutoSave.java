/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import BBChess.Main;
import Model.BoardModel;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Fernando
 */
public class AutoSave extends Thread{
    private BoardController controller;
    private BoardModel model;
    
    public AutoSave(BoardModel model, BoardController controller){
        this.controller = controller;
        this.model = model;
    }
    
    public void run(){
        while(controller.hasNotFinishedYet){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                System.out.println("AutoSave was interrupted...");
                break;
            }
            
            System.out.println("Serialization started...");
            FileOutputStream fileOutput;
            ObjectOutputStream objectOutput;
            try {
                fileOutput = new FileOutputStream("LastGame.bin");
                objectOutput = new ObjectOutputStream(fileOutput);
                objectOutput.writeObject(model);
                objectOutput.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
