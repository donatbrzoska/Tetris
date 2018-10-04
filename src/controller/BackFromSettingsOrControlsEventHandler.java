package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import view.PreviousScene;
import view.Run;
import view.Scenes;

public class BackFromSettingsOrControlsEventHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent t) {
        if (Run.prevScene == PreviousScene.PAUSE){
            Run.setScene(Scenes.getPauseMenu());
        } else {
            Run.setScene(Scenes.getMainMenu());
        }
    }
}
