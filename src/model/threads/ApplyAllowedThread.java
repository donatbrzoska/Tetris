/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.threads;

import model.Game;

/**
 *
 * @author donatdeva
 */
public class ApplyAllowedThread extends Thread {
    private Game game;
    
    public ApplyAllowedThread(Game game){
        super();
        this.game = game;
    }
    
    @Override
    public void run(){
        game.setApplyAllowed(false);
        try {
//            Thread.sleep((long) (1000/game.getTicksPerSecond()));
            Thread.sleep(800);
        } catch (InterruptedException e){}
        game.setApplyAllowed(true);
    }
}
