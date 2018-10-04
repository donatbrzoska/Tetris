/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remains;

import model.Game;
//import static View.View.game;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author donatdeva
 */
public class ListenerThread extends Thread {
    
    private Game game;
    private EventHandler<KeyEvent> keyListener;
    
    ListenerThread(Game gmae){
        super();
        this.game = game;
    }
    
    @Override
    public void run(){
        keyListener = new EventHandler<KeyEvent>() {
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
                //if ()
                //draw();
                event.consume();
            }
        };
    }
    
    EventHandler<KeyEvent> getListener(){
        return keyListener;
    }
}
