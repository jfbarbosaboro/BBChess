/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BoardModel;
import View.BoardView;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIOS+6804647
 */
public class ShowClock extends Thread{
    public BoardController controller;
    
    public ShowClock(BoardController controller){
        this.controller = controller;
        
    }
    
    public void run(){
        while(true){
            this.controller.view.getTimeLabel().setText(String.format("%02d : %02d' %02d.%01d''", this.controller.model.elapsedTime/36000,
                                                                                                  (this.controller.model.elapsedTime/600)%60,
                                                                                                  (this.controller.model.elapsedTime/10)%60,
                                                                                                  this.controller.model.elapsedTime%10));
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ShowClock.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
