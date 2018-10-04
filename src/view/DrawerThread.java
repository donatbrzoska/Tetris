/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Game;
import javafx.application.Platform;

/**
 *
 * @author donatdeva
 * constantly checks, whether the game is over
 *  if yes -> pause game and change to game over menu
 *  if no -> sleep 40ms (->25fps) and draw the game state
 */
public class DrawerThread extends Thread{
    private Game game;
    private boolean execute;
    
    DrawerThread(Game game){
        super();
        this.game = game;
        execute = true;
    }
    
    void stopExecuting(){
        execute = false;
    }
    
    @Override
    public void run(){
        while(execute){
            if (game.getGameOver()){
//                System.out.println("GAME OVER DETECTED BY DRAWER THREAD");
                Run.pauseGame();
                Platform.runLater(() -> {
                    Run.stageLink.setScene(Scenes.getGameOverMenu());
                });
//                Run.toggleGame();
            } else {
                try {
                    Thread.sleep(40);
                } catch (InterruptedException ex) {}
                Platform.runLater(() -> {
                    Run.draw();
                    Run.currentScore.setText(game.getScore() + "");
                });
            }
        }
    }
}