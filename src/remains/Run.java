/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remains;

import javafx.application.Application;
import javafx.stage.Stage;
import view.Scenes;

/**
 * 
 * 
 * 
 * @author donatdeva
 */
public class Run extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("TETRIS");
        stage.setScene(Scenes.getIngameGUI());
        stage.show();
    }
    
}
