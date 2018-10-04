/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.data.Cell;
import model.data.Celltype;
import model.data.Coordinate;
import model.Difficulty;
import model.Game;
import model.data.Tile;
import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import static javafx.scene.media.AudioClip.INDEFINITE;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author donatdeva
 */
public class Run extends Application {
    static Stage stageLink;
    
    static Properties properties = new Properties();
    
    static Pane field;
    static Pane next;
    static Pane hold;
    static Label currentScore;
    
    
//    static TickThread tickThread;
    static DrawerThread drawerThread;
//    static MusicThread musicThread;
    static AudioClip audio;
//    static String originalMusic = "file:src/View/sound/original.wav";
//    static String remixMusic = "file:src/View/sound/remix.wav";
//    static String click = "file:src/View/sound/click.mp3";
    static String originalMusic = "resources/sound/original.wav";
    static String remixMusic = "resources/sound/remix.wav";
    static String click = "resources/sound/click.mp3";
    static boolean original = true; //original music or not is meant

    //main or pause menu
    static PreviousScene prevScene;

    //initialize game
    static Game game = new Game();
    
    static Rectangle[] current;
    static Rectangle[] locked;
    static Random random = new Random();
    
    public static void main(String[] args) {
        Application.launch();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("TETRIS");
        
        stageLink = stage;
        
        Scene scene = mainMenu();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {stopThreads(); Platform.exit();});
        stage.setResizable(false);
    }
    
    static void newGame(){
        game.newGame();
//        toggleGame();
        unpauseGame();
    }
    
    static void draw(){ //spaeter Objektspezifisch?
        clean();
        drawField();
        drawNext(); //spaeter verschieben zur Optimierung
        drawHold(); //spaeter verschieben zur Optimierung
    }
    
    static void clean(){
        hold.getChildren().clear();
        next.getChildren().clear();
        field.getChildren().clear();
    }
    
    static void drawHold(){
        Tile holdTile = game.getHold();
        if (holdTile != null) {
            Coordinate[] c = holdTile.getCoordinates();
            for (int i=0; i<c.length; i++) {
                drawRectangle(c[i], holdTile.getType().getType(), hold);
            }
        }
    }
    
    static void drawNext(){
        Tile nextTile = game.getNext();
        Coordinate[] c = nextTile.getCoordinates();
        for (int i=0; i<c.length; i++){
            drawRectangle(c[i], nextTile.getType().getType(), next);
        }
    }
    
    /**
     * Method to update the drawn rectangles from the field
     */
    static void drawField(){
        Cell[][] gameField = game.getSetMatrix();
        
        for (int i=0; i<gameField.length; i++){
            for (int j=0; j<gameField[i].length; j++) {
                if (gameField[i][j].isSet()){
                    //System.out.println("Rectangle at x:" + j + " y:" + i + " as " + gameField[i][j].getType());
                    drawRectangle(new Coordinate(j,i), gameField[i][j].getType(), field);
                }
            }
        }
    }
    
    static void drawRectangle(Coordinate c, Celltype t, Pane target){
        //System.out.println("got draw command ");
        Rectangle r = new Rectangle(c.x*properties.blocksize, c.y*properties.blocksize, properties.blocksize, properties.blocksize);
        r.setStrokeWidth(properties.rectangleStrokeWidth);
        r.setStroke(properties.rectangleStrokeColor);
        switch (t){
            case L:
//                r.setFill(new Color(0.1, 0.9, 0.2, 1));
//                r.setFill(LCOLOR);
                r.setStyle(properties.lstyle);
                break;
            case J:
//                r.setFill(new Color(0.9, 0.1, 0.2, 1));
//                r.setFill(JCOLOR);
                r.setStyle(properties.jstyle);
                break;
            case I:
//                r.setFill(new Color(0.1, 0.3, 0.9, 1));
//                r.setFill(ICOLOR);
                r.setStyle(properties.istyle);
                break;
            case O:
//                r.setFill(new Color(0.4, 0.3, 0.8, 1));
//                r.setFill(OCOLOR);
                r.setStyle(properties.ostyle);
                break;
            case S:
//                r.setFill(new Color(0.9, 0.1, 0.8, 1));
//                r.setFill(SCOLOR);
                r.setStyle(properties.sstyle);
                break;
            case Z:
//                r.setFill(new Color(0.0, 0.8, 0.9, 1));
//                r.setFill(ZCOLOR);
                r.setStyle(properties.zstyle);
                break;
            case T:
                r.setStyle(properties.tstyle);
                break;
            case GL:
//                r.setFill(new Color(0.1, 0.9, 0.2, 1));
//                r.setFill(LCOLOR);
                r.setStyle(properties.lstyle + properties.ghostopacity);
                break;
            case GJ:
//                r.setFill(new Color(0.9, 0.1, 0.2, 1));
//                r.setFill(JCOLOR);
                r.setStyle(properties.jstyle + properties.ghostopacity);
                break;
            case GI:
//                r.setFill(new Color(0.1, 0.3, 0.9, 1));
//                r.setFill(ICOLOR);
                r.setStyle(properties.istyle + properties.ghostopacity);
                break;
            case GO:
//                r.setFill(new Color(0.4, 0.3, 0.8, 1));
//                r.setFill(OCOLOR);
                r.setStyle(properties.ostyle + properties.ghostopacity);
                break;
            case GS:
//                r.setFill(new Color(0.9, 0.1, 0.8, 1));
//                r.setFill(SCOLOR);
                r.setStyle(properties.sstyle + properties.ghostopacity);
                break;
            case GZ:
//                r.setFill(new Color(0.0, 0.8, 0.9, 1));
//                r.setFill(ZCOLOR);
                r.setStyle(properties.zstyle + properties.ghostopacity);
                break;
            case GT:
//                r.setFill(new Color(0.7, 0.1, 0.2, 1));
//                r.setFill(TCOLOR);
                r.setStyle(properties.tstyle + properties.ghostopacity);
                break;
//            case G:
//                r.setStyle(gstyle);
//                break;
        }
        target.getChildren().add(r);
    }
    
    public static Scene mainMenu(){
        DesignedVBox designedVBox = new DesignedVBox(properties.textColor, properties.font, properties.menuStyle, properties.vboxSpacing, properties.vboxInsets, properties.vboxAlignment);
        
        TextFlow titleLabel = new TextFlow();
        Text t = new Text("T");
        t.setStyle(properties.lstyle);
//        t.setFont(properties.titleFontStyle);
        t.setFont(properties.titleFont);
        Text e = new Text("E");
        e.setStyle(properties.tstyle);
//        e.setStroke(Color.WHITE);
        e.setFont(properties.titleFont);
        Text t2 = new Text("T");
        t2.setStyle(properties.sstyle);
//        t2.setFont(properties.titleFontStyle);
        t2.setFont(properties.titleFont);
        Text r = new Text("R");
        r.setStyle(properties.ostyle);
//        r.setFont(properties.titleFontStyle);
        r.setFont(properties.titleFont);
        Text i = new Text("I");
        i.setStyle(properties.istyle);
//        i.setFont(properties.titleFontStyle);
        i.setFont(properties.titleFont);
        Text s = new Text("S");
        s.setStyle(properties.jstyle);
//        s.setFont(properties.titleFontStyle);
        s.setFont(properties.titleFont);
        titleLabel.getChildren().addAll(t,e,t2,r,i,s);
        
        Label newGame = new Label("New game");
        Label settings = new Label("Settings");
        Label controls = new Label("Controls");
        Label exit = new Label("Exit");
        
        designedVBox.getChildren().add(titleLabel);
        designedVBox.addAllLabel(newGame, settings, controls, exit);
        
        EventHandler<MouseEvent> newGameClick = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                Scene scene = ingameGUI();
        
                EventHandler keyListener = new KeyEventHandler();
                scene.setOnKeyPressed(keyListener);
                
                newGame();
//                stageLink.setScene(scene);
                setScene(scene);
                
            }
        };
        newGame.setOnMouseClicked(newGameClick);
        
        SettingsEventHandler settingsClick = new SettingsEventHandler();
        settings.setOnMouseClicked(settingsClick);
        
        ControlsEventHandler controlsClick = new ControlsEventHandler();
        controls.setOnMouseClicked(controlsClick);
        
        EventHandler<MouseEvent> exitClick = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                System.exit(0);
            }
        };
        exit.setOnMouseClicked(exitClick);
        prevScene = PreviousScene.MAIN;
        return new Scene(designedVBox);
    }
    
    public static Scene settingsMenu(){
        GridPane gridPane = new GridPane();
        String style = "-fx-text-fill: white; -fx-font: " + new Double(properties.textscale/2).intValue() + "px " + properties.fonttype + ";";
        gridPane.setVgap(10);
        gridPane.setHgap(20);
        
        Label windowSizeLabel = new Label("Window size:");
        windowSizeLabel.setStyle(style);
        Slider sizeSlider = new Slider();
        sizeSlider.setStyle("-fx-tick-label-fill: white;");
        sizeSlider.setMin(10);
        sizeSlider.setMax(150);
        sizeSlider.setValue(properties.windowSize);
        sizeSlider.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                Slider source = (Slider) t.getSource();
                properties.setWindowSize(source.getValue());
                setScene(settingsMenu());            
            }
        });
        
        
        Label musicLabel = new Label("Music:");
        musicLabel.setStyle(style);
        HBox musicToggleBox = new HBox();
        ToggleGroup musicToggleGroup = new ToggleGroup();
        ToggleButton onToggleButton = new ToggleButton("ON");
        onToggleButton.setSelected(properties.musicOn);
        onToggleButton.setToggleGroup(musicToggleGroup);
        onToggleButton.setId("on");
        ToggleButton offToggleButton = new ToggleButton("OFF");
        offToggleButton.setSelected(!properties.musicOn);
        offToggleButton.setToggleGroup(musicToggleGroup);
        offToggleButton.setId("off");
        musicToggleBox.getChildren().addAll(onToggleButton,offToggleButton);
        musicToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                ToggleButton newToggle = (ToggleButton) t1;
//                ToggleButton oldToggle = (ToggleButton) t;
//                if (!(oldToggle.getId().equals(newToggle.getId()))) {
                if (newToggle != null){ 
                    if (newToggle.getId().equals("on")){
                        properties.musicOn=true;
                    } else {
                        properties.musicOn=false;
                    }
                }
//                System.out.println("Music is now: " + ((properties.musicOn) ? "on" : "off"));
            }
        });
        
        
        
       
        Label difficultyLabel = new Label("Difficulty:");
        difficultyLabel.setStyle(style);
        VBox difficultyBox = new VBox();
        difficultyBox.setSpacing(0.1*properties.blocksize);
        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        RadioButton easyRadioButton = new RadioButton("Easy");
        easyRadioButton.setTextFill(Color.WHITE);
        easyRadioButton.setFont(new Font(properties.fonttype, properties.textscale/3));
        easyRadioButton.setToggleGroup(difficultyToggleGroup);
        easyRadioButton.setSelected(game.getDifficulty() == Difficulty.EASY);
        easyRadioButton.setId("easy");
        RadioButton mediumRadioButton = new RadioButton("Medium");
        mediumRadioButton.setTextFill(Color.WHITE);
        mediumRadioButton.setFont(new Font(properties.fonttype, properties.textscale/3));
        mediumRadioButton.setToggleGroup(difficultyToggleGroup);
        mediumRadioButton.setSelected(game.getDifficulty() == Difficulty.MEDIUM);
        mediumRadioButton.setId("medium");
        RadioButton hardRadioButton = new RadioButton("Hard");
        hardRadioButton.setTextFill(Color.WHITE);
        hardRadioButton.setFont(new Font(properties.fonttype, properties.textscale/3));
        hardRadioButton.setToggleGroup(difficultyToggleGroup);
        hardRadioButton.setSelected(game.getDifficulty() == Difficulty.HARD);
        hardRadioButton.setId("hard");
        difficultyBox.getChildren().addAll(easyRadioButton,mediumRadioButton,hardRadioButton);
        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                ToggleButton newDiff = (ToggleButton) t1;
                if (newDiff!=null){
                    if (newDiff.getId().equals("easy")){
                        game.setDifficulty(Difficulty.EASY);
                    }
                    if (newDiff.getId().equals("medium")){
                        game.setDifficulty(Difficulty.MEDIUM);
                    }
                    if (newDiff.getId().equals("hard")){
                        game.setDifficulty(Difficulty.HARD);
                    }
                }
            }
        });
        
        
        
        gridPane.add(windowSizeLabel, 0, 0);
        gridPane.add(sizeSlider, 1, 0);
        
        gridPane.add(musicLabel, 0, 1);
        gridPane.add(musicToggleBox, 1, 1);
        
        gridPane.add(difficultyLabel, 0, 2);
        gridPane.add(difficultyBox, 1, 2);
        
        
        
        
        Label backLabel = new Label("Back");
        EventHandler<MouseEvent> okClick = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                if (prevScene == PreviousScene.PAUSE){
//                    stageLink.setScene(pauseMenu());
                    setScene(pauseMenu());
                } else {
//                    stageLink.setScene(mainMenu());
                    setScene(mainMenu());
                }
            }
        };
        backLabel.setOnMouseClicked(okClick);
        
        DesignedVBox designedVBox = new DesignedVBox(properties.textColor, properties.font, properties.lstyle, properties.vboxSpacing, properties.vboxInsets, properties.vboxAlignment);
        designedVBox.setStyle("-fx-background-color: black");
        designedVBox.getChildren().add(gridPane);
        designedVBox.addAllLabel(backLabel);
        Scene scene = new Scene(designedVBox);
        return scene;
    }
    
    public static Scene controlsMenu(){
        GridPane gridPane = new GridPane();
        String keyLabelStyle = "-fx-text-fill: white; -fx-font: " + new Double(properties.textscale/2).intValue() + "px " + properties.fonttype + ";";
        gridPane.setVgap(10);
        gridPane.setHgap(20);
        
        Label moveLeft = new Label("Move left:");
        moveLeft.setStyle(keyLabelStyle);
        Label moveRightLabel = new Label("Move right:");
        moveRightLabel.setStyle(keyLabelStyle);
        Label moveDownLabel = new Label("Move down:");
        moveDownLabel.setStyle(keyLabelStyle);
        Label rotateLabel = new Label("Rotate:");
        rotateLabel.setStyle(keyLabelStyle);
        Label holdLabel = new Label("Hold:");
        holdLabel.setStyle(keyLabelStyle);
        Label instantlockLabel = new Label("Instantlock:");
        instantlockLabel.setStyle(keyLabelStyle);
        Label pauseLabel = new Label("Pause:");
        pauseLabel.setStyle(keyLabelStyle);
        
        Pane left = new Pane();
        left.setMinSize(41,21);
        left.setMaxSize(41,21);
        String leftImage = Run.class.getResource("resources/img/left.png").toExternalForm();
        left.setStyle("-fx-background-image: url('"+leftImage+"'); -fx-background-size: cover; -fx-background-repeat: stretch;");
        GridPane.setHalignment(left, HPos.CENTER);
        
        Pane right = new Pane();
        right.setMinSize(41,21);
        right.setMaxSize(41,21);
        String rightImage = Run.class.getResource("resources/img/right.png").toExternalForm();
        right.setStyle("-fx-background-image: url('"+rightImage+"'); -fx-background-size: cover; -fx-background-repeat: stretch;");
        GridPane.setHalignment(right, HPos.CENTER);
        
        Pane down = new Pane();
        down.setMinSize(41,21);
        down.setMaxSize(41,21);
        String downImage = Run.class.getResource("resources/img/down.png").toExternalForm();
        down.setStyle("-fx-background-image: url('"+downImage+"'); -fx-background-size: cover; -fx-background-repeat: stretch;");
        GridPane.setHalignment(down, HPos.CENTER);
        
        Pane up = new Pane();
        up.setMinSize(41,21);
        up.setMaxSize(41,21);
        String upImage = Run.class.getResource("resources/img/up.png").toExternalForm();
        up.setStyle("-fx-background-image: url('"+upImage+"'); -fx-background-size: cover; -fx-background-repeat: stretch;");
        GridPane.setHalignment(up, HPos.CENTER);
        
        Pane shift = new Pane();
        shift.setMinSize(76,21);
        shift.setMaxSize(76,21);
        String shiftImage = Run.class.getResource("resources/img/shift.png").toExternalForm();
        shift.setStyle("-fx-background-image: url('"+shiftImage+"'); -fx-background-size: cover; -fx-background-repeat: stretch;");
        GridPane.setHalignment(shift, HPos.CENTER);
        
        Pane space = new Pane();
        space.setMinSize(134,21);
        space.setMaxSize(134,21);
        String spaceImage = Run.class.getResource("resources/img/space.png").toExternalForm();
        space.setStyle("-fx-background-image: url('"+spaceImage+"'); -fx-background-size: cover;");
        GridPane.setHalignment(space, HPos.CENTER);
        
        Pane esc = new Pane();
        esc.setMinSize(41,21);
        esc.setMaxSize(41,21);
        String escImage = Run.class.getResource("resources/img/esc.png").toExternalForm();
        esc.setStyle("-fx-background-image: url('"+escImage+"'); -fx-background-size: cover; -fx-background-repeat: stretch;");
        GridPane.setHalignment(esc, HPos.CENTER);
        
        gridPane.add(moveLeft, 0, 0);
        gridPane.add(left, 1, 0);
         
        gridPane.add(moveRightLabel, 0, 1);
        gridPane.add(right, 1, 1);
        
        gridPane.add(moveDownLabel, 0, 2);
        gridPane.add(down, 1, 2);
        
        gridPane.add(rotateLabel, 0, 3);
        gridPane.add(up, 1, 3);
        
        gridPane.add(holdLabel, 0, 4);
        gridPane.add(shift, 1, 4);
        
        gridPane.add(instantlockLabel, 0, 5);
        gridPane.add(space, 1, 5);
        
        gridPane.add(pauseLabel, 0, 6);
        gridPane.add(esc, 1, 6);
        
        
        Label backLabel = new Label("Back");
        EventHandler<MouseEvent> okClick = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                if (prevScene == PreviousScene.PAUSE){
//                    stageLink.setScene(pauseMenu());
                    setScene(pauseMenu());
                } else {
//                    stageLink.setScene(mainMenu());
                    setScene(mainMenu());
                }
            }
        };
        backLabel.setOnMouseClicked(okClick);
        
        DesignedVBox designedVBox = new DesignedVBox(properties.textColor, properties.font, properties.lstyle, properties.vboxSpacing, properties.vboxInsets, properties.vboxAlignment);
        designedVBox.setStyle("-fx-background-color: black");
        designedVBox.getChildren().add(gridPane);
        designedVBox.addLabel(backLabel);
        
        return new Scene(designedVBox);
    }
    
    public static Scene pauseMenu(){
        DesignedVBox designedVBox = new DesignedVBox(properties.textColor, properties.font, properties.menuStyle, properties.vboxSpacing, properties.vboxInsets, properties.vboxAlignment);
        Label continueLabel = new Label("Continue");
        Label settingsLabel = new Label("Settings");
        Label controlsLabel = new Label("Controls");
        Label exitLabel = new Label("Back to main menu");
        
        designedVBox.addAllLabel(continueLabel, settingsLabel, controlsLabel, exitLabel);
                
        EventHandler<MouseEvent> continueClick = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                Scene scene = ingameGUI();
        
                KeyEventHandler keyListener = new KeyEventHandler();
                scene.setOnKeyPressed(keyListener);
                
                draw();
//                toggleGame();
                unpauseGame();
//                stageLink.setScene(scene);
                setScene(scene);
            }
        };
        continueLabel.setOnMouseClicked(continueClick);
        
        SettingsEventHandler settingsClick = new SettingsEventHandler();
        settingsLabel.setOnMouseClicked(settingsClick);
        
        ControlsEventHandler controlsClick = new ControlsEventHandler();
        controlsLabel.setOnMouseClicked(controlsClick);
        
        EventHandler<MouseEvent> exitClick = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                Scene scene = mainMenu();
//                stageLink.setScene(scene);
                setScene(scene);
            }
        };
        exitLabel.setOnMouseClicked(exitClick);
        
        prevScene = PreviousScene.PAUSE;
        return new Scene(designedVBox);
    }
    
    public static Scene gameOverMenu(){
        DesignedVBox designedVBox = new DesignedVBox(properties.textColor, properties.font, properties.menuStyle, properties.vboxSpacing, properties.vboxInsets, properties.vboxAlignment);
        
        Label gameOverLabel = new Label("Game over!");
        Label scoreLabel = new Label("Score: " + game.getScore());
        Label gapLabel = new Label(" ");
        Label newGameLabel = new Label("New game");
        Label mainLabel = new Label("Back to main menu");
        Label exitLabel = new Label("Exit");
//        dv.addNonEffectLabel(gameOver);
        designedVBox.getChildren().addAll(gameOverLabel, scoreLabel);
        designedVBox.addAllLabel(gapLabel, newGameLabel, mainLabel, exitLabel);
        
        gapLabel.setFont(new Font(properties.fonttype, properties.textscale * 0.5));
        
        gameOverLabel.setTextFill(Color.ORANGERED);
        gameOverLabel.setFont(new Font(properties.fonttype, properties.textscale * 1.1));
        
        scoreLabel.setTextFill(properties.gameOverScoreColor);
        scoreLabel.setFont(properties.font);
        
        
        
        EventHandler<MouseEvent> newGameClick = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                Scene scene = ingameGUI();
        
                EventHandler keyListener = new KeyEventHandler();
                scene.setOnKeyPressed(keyListener);
                
                newGame();
                //draw();
//                stageLink.setScene(scene);
                setScene(scene);
                
            }
        };
        newGameLabel.setOnMouseClicked(newGameClick);
        
        EventHandler<MouseEvent> mainClick = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                Scene scene = mainMenu();
//                stageLink.setScene(scene);
                setScene(scene);
            }
        };
        mainLabel.setOnMouseClicked(mainClick);
        
        EventHandler<MouseEvent> exitClick = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                System.exit(0);
            }
        };
        exitLabel.setOnMouseClicked(exitClick);
        
        return new Scene(designedVBox);
    }
    
    public static Scene ingameGUI(){
        
        //field.setPrefSize(properties.blocksize*10, properties.blocksize*18);
        field = new Pane();
//        field.setPrefSize(properties.blocksize*10, properties.blocksize*18);
        field.setMinSize(properties.blocksize*10, properties.blocksize*18);
        field.setMaxSize(properties.blocksize*10, properties.blocksize*18);
        field.setStyle(properties.fieldStyle);
//        field.setPadding(properties.fieldInsets);
        
        
        VBox nextArea = new VBox();
        next = new Pane();
//        next.setPrefSize(properties.blocksize*4, properties.blocksize*4);
        next.setMinSize(properties.blocksize*4, properties.blocksize*4);
        next.setMaxSize(properties.blocksize*4, properties.blocksize*4);
        Label nextLabel = new Label("Next");
        nextLabel.setTextFill(properties.textColor);
        nextLabel.setFont(properties.font);
        Label gapLabel = new Label(" ");
        gapLabel.setFont(properties.font);
        Label scoreLabel = new Label("Score:");
        scoreLabel.setTextFill(properties.textColor);
        scoreLabel.setFont(new Font(properties.fonttype, properties.textscale*0.6));
        currentScore = new Label();
        currentScore.setTextFill(properties.textColor);
        currentScore.setFont(new Font(properties.fonttype, properties.textscale*0.6));
        nextArea.getChildren().addAll(next, nextLabel, gapLabel, scoreLabel, currentScore);
        nextArea.setStyle(properties.borderSectionStyle);
        nextArea.setAlignment(Pos.TOP_CENTER);
        
        
        
        VBox holdArea = new VBox();
        hold = new Pane();
//        hold.setPrefSize(properties.blocksize*4, properties.blocksize*4);
        hold.setMinSize(properties.blocksize*4, properties.blocksize*4);
        hold.setMaxSize(properties.blocksize*4, properties.blocksize*4);
        Label holdLabel = new Label("Hold");
        holdLabel.setTextFill(properties.textColor);
        holdLabel.setFont(properties.font);
        holdArea.getChildren().addAll(hold, holdLabel);
        holdArea.setStyle(properties.borderSectionStyle);
        holdArea.setAlignment(Pos.TOP_CENTER);
        

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle(properties.borderPaneStyle);
        borderPane.setLeft(holdArea);
        borderPane.setCenter(field);
        borderPane.setRight(nextArea);
        BorderPane.setMargin(field,properties.fieldInsets);
        
        Scene scene = new Scene(borderPane,field.getMinWidth() + next.getMinWidth() + hold.getMinWidth() + 2f*properties.fieldGap, field.getMinHeight());

//        scene.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
////                System.out.println("Width: " + newSceneWidth);
//                properties.blocksize = scene.getWidth() / (18f+(1f/30f));
////                updateIngameGUI();
//            }
//        });
//        
//        scene.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
////                System.out.println("Height: " + newSceneHeight);
//                BLOCKSIZEY = scene.getHeight() / 18f;
////                updateIngameGUI();
//            }
//        });
        
        return scene;
    }
    
//    static void toggleGame(){
//        if (game.isPaused()) {
////            game.togglePause();
//            game.unpause();
//            startThreads();
//        } else {
////            game.togglePause();
//            game.pause();
//            stopThreads();
//        }
//    }
    
    static void pauseGame(){
        game.pause();
        stopThreads();
    }
    
    static void unpauseGame(){
        game.unpause();
        startThreads();
    }
    
    static void startThreads(){
//        tickThread = new TickThread(game);
//        tickThread.start();
        drawerThread = new DrawerThread(game);
        drawerThread.start();
        startMusic(originalMusic);
    }
    
    static void stopThreads(){
//        if (tickThread != null){
        if (drawerThread != null){
//            tickThread.stopExecuting();
            drawerThread.stopExecuting();
//            musicThread.stopExecuting();
            stopMusic();
        }
    }
    
    static Game getGame(){
        return game;
    }
    
    public static void setScene(Scene scene){
        stageLink.setScene(scene);
    }
    
    static void startMusic(String path){
        if (properties.musicOn){
            int s = INDEFINITE;
    //        audio = new AudioClip(path);
            audio = new AudioClip(Run.class.getResource(path).toExternalForm());
            audio.setVolume(0.5f);
            audio.setCycleCount(s);
            audio.play();
        }
    }
    
    static void stopMusic(){
        if(properties.musicOn){
            audio.stop();   
        }
    }
    
    static void toggleMusic(){
        if (properties.musicOn){
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