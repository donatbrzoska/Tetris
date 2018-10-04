/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author donatdeva
 */
public class ControlsEventHandler implements EventHandler<MouseEvent>{

    @Override
    public void handle(MouseEvent t) {
        Run.stageLink.setScene(Run.controlsMenu());
    }
    
}
