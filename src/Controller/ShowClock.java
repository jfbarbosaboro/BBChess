/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BoardModel;
import View.BoardView;

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
            this.controller.view.getTimeLabel().setText(String.format("%02d : %02d' %02d.%03d''", this.controller.model.elapsedTime/3600000,
                                                                                                  (this.controller.model.elapsedTime/60000)%60,
                                                                                                  (this.controller.model.elapsedTime/1000)%60,
                                                                                                  this.controller.model.elapsedTime%1000));
        }
    }
}
