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
public class TickThread extends Thread{
    private Game game;
    private boolean execute;
    
    private boolean tick = false;
    private int tickCounter = 0;
    private int tickStep = 20;
    private int tickAt = 40*tickStep;
    
    
    public TickThread(Game game){
        super();
        this.game = game;
        execute = true;
    }
    
    public void stopExecuting(){
        execute = false;
    }
    
    @Override
    public void run(){
        while(execute){
            try {
                Thread.sleep((long) (1000/game.getTicksPerSecond()));
            } catch (InterruptedException ex) {}
//            if (game.applyIsAllowed()) {
                game.tick();
//            }
        }
    }
}
