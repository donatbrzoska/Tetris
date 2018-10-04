/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static view.Run.game;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author donatdeva
 */
public class KeyEventHandler implements EventHandler<KeyEvent>{
    
    @Override
    public void handle(KeyEvent event) {
        if(event.getCode() == KeyCode.DOWN) { 
            System.out.println("Down key pressed");
            game.moveDown();
        }
        if(event.getCode() == KeyCode.RIGHT) {
            System.out.println("Right key pressed");
            game.moveRight();
        }
        if(event.getCode() == KeyCode.LEFT) {
            System.out.println("Left key pressed");
            game.moveLeft();
        }
        if(event.getCode() == KeyCode.UP) {
            System.out.println("Up key pressed");
            game.rotate();
        }
        if(event.getCode() == KeyCode.SHIFT) {
            System.out.println("Shift key pressed");
            game.hold();
        }
        if(event.getCode() == KeyCode.SPACE) {
            System.out.println("Spacebar pressed");
            game.instantLock();
        }
        if(event.getCode() == KeyCode.ESCAPE) {
            System.out.println("Escape key pressed");
//            Run.toggleGame();
            Run.pauseGame();
//            Run.stageLink.setScene(Run.pauseMenu());
            Run.setScene(Run.pauseMenu());
        }
        if(event.getCode() == KeyCode.Q) {
            System.out.println("Cheating");
            Run.game.incScore();
            Run.game.updateTicksPerSecond();
        }
//        if(event.getCode() == KeyCode.D) {
//            System.out.println("Toggling music");
//            Run.toggleMusic();
//        }

//        Run.draw();

        event.consume();
    }   
}
