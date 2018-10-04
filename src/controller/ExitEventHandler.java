package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ExitEventHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent t) {
        System.exit(0);
    }
}