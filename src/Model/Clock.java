/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.BoardController;
import View.BoardView;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael Baquero
 */
public class Clock extends Thread {
    private long msec;
    private BoardView view;
    private BoardController controller;
    private BoardModel model;
    
    public Clock(BoardView view, BoardModel model, BoardController controller, long alreadyElapsedTime) {
        this.view = view;
        this.controller = controller;
        this.model = model;
        this.msec = alreadyElapsedTime;
    }
    
    @Override
    public void run() {
        String printTime;
        while(controller.hasNotAlreadyFinished) {
            model.elapsedTime = msec;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
            }
            msec++;
        }
        return;
    }
    
}
