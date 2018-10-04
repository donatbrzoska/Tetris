package controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import view.Run;
import view.Scenes;

public class BackToMainMenuEventHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent t) {
        Scene scene = Scenes.getMainMenu();
//                stageLink.setScene(scene);
        Run.setScene(scene);
    }
}
