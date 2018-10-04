/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.data.Cell;
import model.data.Celltype;
import model.data.Coordinate;
import model.Game;
import model.data.Tile;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import static javafx.scene.media.AudioClip.INDEFINITE;

import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author donatdeva
 */
public class Run extends Application {
    public static Stage stageLink;
    
    static Pane field;
    static Pane next;
    static Pane hold;
    static Label currentScore;
    

    static DrawerThread drawerThread;
    static AudioClip audio;
//    static String originalMusic = "file:src/View/sound/original.wav";
//    static String remixMusic = "file:src/View/sound/remix.wav";
//    static String click = "file:src/View/sound/click.mp3";
    static String originalMusic = "resources/sound/original.wav";
    static String remixMusic = "resources/sound/remix.wav";
    static String click = "resources/sound/click.mp3";
    static boolean original = true; //original music or not is meant

    /**
     * keeps main or pause menu as values for menu navigation (take a look at BackFromSettingsOrControlsEventHandler)
     */
    public static PreviousScene prevScene;

    /**
     * initialize game
     */
    public static Game game = new Game();

    //start JavaFX App
    public static void main(String[] args) {
        Application.launch();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("TETRIS");

        //make stage accessible to other classes
        stageLink = stage;

        //set scene to main menu and show it
        Scene scene = Scenes.getMainMenu();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {stopThreads(); Platform.exit();});
        stage.setResizable(false);
    }

    /**
     * creates new game and starts it
     */
    public static void newGame(){
        game.newGame();
        unpauseGame();
    }

    /**
     * clears tile fields and draws "main field", "next field" and "hold field"
     */
    static void draw(){ //maybe later objectspecific?
        clean();
        drawField();
        drawNext(); //relocate later for optimization
        drawHold(); //relocate later for optimization
    }

    /**
     * clears view
     */
    static void clean(){
        hold.getChildren().clear();
        next.getChildren().clear();
        field.getChildren().clear();
    }

    /**
     * draws content of the "hold field" if there is a tile on hold
     */
    static void drawHold(){
        Tile holdTile = game.getHold();
        if (holdTile != null) {
            Coordinate[] c = holdTile.getCoordinates();
            for (int i=0; i<c.length; i++) {
                drawRectangle(c[i], holdTile.getType().getType(), hold);
            }
        }
    }

    /**
     * draws content of the "next field"
     */
    static void drawNext(){
        Tile nextTile = game.getNext();
        Coordinate[] c = nextTile.getCoordinates();
        for (int i=0; i<c.length; i++){
            drawRectangle(c[i], nextTile.getType().getType(), next);
        }
    }
    
    /**
     * draws content of the "main field"
     */
    static void drawField(){
        Cell[][] gameField = game.getSetMatrix();
        
        for (int i=0; i<gameField.length; i++){
            for (int j=0; j<gameField[i].length; j++) {
                //check if the respective cell is set and when yes, draw it
                if (gameField[i][j].isSet()){
                    //System.out.println("Rectangle at x:" + j + " y:" + i + " as " + gameField[i][j].getType());
                    drawRectangle(new Coordinate(j,i), gameField[i][j].getType(), field);
                }
            }
        }
    }

    /**
     * draws a tile block
     * @param c Coordinate at which the block should be drawn
     * @param t Celltype the block has
     * @param target View container target
     */
    static void drawRectangle(Coordinate c, Celltype t, Pane target){
        //System.out.println("got draw command ");
        Rectangle rectangle = new Rectangle(c.x*Properties.blocksize, c.y*Properties.blocksize, Properties.blocksize, Properties.blocksize);
        rectangle.setStrokeWidth(Properties.rectangleStrokeWidth);
        rectangle.setStroke(Properties.rectangleStrokeColor);
        rectangle.setStyle(Properties.getStyleForCelltype(t));
        target.getChildren().add(rectangle);
    }
    
    public static void pauseGame(){
        game.pause();
        stopThreads();
    }
    
    static void unpauseGame(){
        game.unpause();
        startThreads();
    }
    
    static void startThreads(){
        drawerThread = new DrawerThread(game);
        drawerThread.start();
        startMusic(originalMusic);
    }
    
    static void stopThreads(){
        if (drawerThread != null){
            drawerThread.stopExecuting();
            stopMusic();
        }
    }
    
    public static void setScene(Scene scene){
        stageLink.setScene(scene);
    }
    
    static void startMusic(String path){
        if (Properties.musicOn){
            int s = INDEFINITE;
    //        audio = new AudioClip(path);
            audio = new AudioClip(Run.class.getResource(path).toExternalForm());
            audio.setVolume(0.5f);
            audio.setCycleCount(s);
            audio.play();
        }
    }
    
    static void stopMusic(){
        if(Properties.musicOn){
            audio.stop();   
        }
    }
    
    static void toggleMusic(){
        if (Properties.musicOn){
            audio.stop();
            if (original){
                startMusic(remixMusic);
                original = false;
//                System.out.println("Now playing remix");
            } else {
                startMusic(originalMusic);
                original = true;
//                System.out.println("Now playing original");
            }
        }
    }
    
    static void clickSound(){
        int s = 1;
        audio = new AudioClip(Run.class.getResource(click).toExternalForm());
        audio.setVolume(0.5f);
        audio.setCycleCount(s);
        audio.play();
    }
    
}