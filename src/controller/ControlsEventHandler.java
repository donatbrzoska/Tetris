/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import view.Run;
import view.Scenes;

/**
 *
 * @author donatdeva
 */
public class ControlsEventHandler implements EventHandler<MouseEvent>{

    @Override
    public void handle(MouseEvent t) {
        Run.stageLink.setScene(Scenes.getControlsMenu());
    }
    
}
