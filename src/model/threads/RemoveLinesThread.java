/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.threads;

import model.Game;
import java.util.LinkedList;

/**
 *
 * @author donatdeva
 */
public class RemoveLinesThread extends Thread{
    private Game game;
    private LinkedList<Integer> lines;
    
    public RemoveLinesThread(Game game, LinkedList<Integer> lines){
        super();
        this.game = game;
        this.lines = lines;
    }
    
    @Override
    public void run(){
        try {
            Thread.sleep(150);
            game.removeLines(lines);
        } catch (InterruptedException ex) {}
    }
}
