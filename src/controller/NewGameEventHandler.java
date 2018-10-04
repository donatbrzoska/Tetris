package controller;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import view.Run;
import view.Scenes;

public class NewGameEventHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent t) {
        //build Scene
        Scene scene = Scenes.getIngameGUI();

        //start new game
        Run.newGame();
        Run.setScene(scene);
    }
}