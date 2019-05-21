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
    private int msec;
    private BoardView view;
    private BoardController controller;
    
    public Clock(BoardView view, BoardController controller) {
        this.view = view;
        this.controller = controller;
    }
    
    @Override
    public void run() {
        msec = 0;
        String printTime;
        while(controller.hasNotAlreadyFinished) {
            printTime = String.format("%02d : %02d' %02d.%03d''", msec/3600000, (msec/60000)%60, (msec/1000)%60, msec%1000);
            view.getTimeLabel().setText(printTime);
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
            }
            msec++;
        }
        return;
    }
    
}
