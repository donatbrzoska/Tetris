package view;

import controller.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Difficulty;

/**
 * creates menus and stores them for later use
 */
public class Scenes {

    private static Scene mainMenu;
    private static Scene settingsMenu;
    private static Scene controlsMenu;
    private static Scene pauseMenu;
    private static Scene gameOverMenu;
    private static Scene ingameGUI;

    public static Scene getMainMenu(){
        if (mainMenu==null){
            mainMenu = createMainMenu();
        }
        return mainMenu;
    }

    public static Scene getSettingsMenu(){
        if (settingsMenu==null){
            settingsMenu = createSettingsMenu();
        }
        return settingsMenu;
    }

    public static Scene getControlsMenu(){
        if (controlsMenu==null){
            controlsMenu = createControlsMenu();
        }
        return controlsMenu;
    }

    public static Scene getPauseMenu(){
        if (pauseMenu==null){
            pauseMenu = createPauseMenu();
        }
        return pauseMenu;
    }

    public static Scene getGameOverMenu(){
        if (gameOverMenu==null){
            gameOverMenu = createGameOverMenu();
        }
        return gameOverMenu;
    }

    public static Scene getIngameGUI(){
        if (ingameGUI==null){
            ingameGUI = createIngameGUI();
        }
        return ingameGUI;
    }


    /**
     * builds the main menu GUI and assigns controllers to the elements
     * @return main menu
     */
    private static Scene createMainMenu(){
        DesignedVBox designedVBox = new DesignedVBox(Properties.textColor, Properties.font, Properties.menuStyle, Properties.vboxSpacing, Properties.vboxInsets, Properties.vboxAlignment);

        //TETRIS TITLE
        TextFlow titleLabel = new TextFlow();

        Text t = new Text("T");
        t.setStyle(Properties.lstyle);
//        t.setFont(properties.titleFontStyle);
        t.setFont(Properties.titleFont);

        Text e = new Text("E");
        e.setStyle(Properties.tstyle);
//        e.setStroke(Color.WHITE);
        e.setFont(Properties.titleFont);

        Text t2 = new Text("T");
        t2.setStyle(Properties.sstyle);
//        t2.setFont(properties.titleFontStyle);
        t2.setFont(Properties.titleFont);

        Text r = new Text("R");
        r.setStyle(Properties.ostyle);
//        r.setFont(properties.titleFontStyle);
        r.setFont(Properties.titleFont);

        Text i = new Text("I");
        i.setStyle(Properties.istyle);
//        i.setFont(properties.titleFontStyle);
        i.setFont(Properties.titleFont);

        Text s = new Text("S");
        s.setStyle(Properties.jstyle);
//        s.setFont(properties.titleFontStyle);
        s.setFont(Properties.titleFont);

        titleLabel.getChildren().addAll(t,e,t2,r,i,s);



        Label newGameLabel = new Label("New game");
        Label settingsLabel = new Label("Settings");
        Label controlsLabel = new Label("Controls");
        Label exitLabel = new Label("Exit");



        //ADD EVERYTHING TO CUSTOM VBOX
        designedVBox.getChildren().add(titleLabel);
        designedVBox.addAllLabel(newGameLabel, settingsLabel, controlsLabel, exitLabel);



        //when newGameLabel gets clicked
        EventHandler<MouseEvent> newGameClick = new NewGameEventHandler();
        newGameLabel.setOnMouseClicked(newGameClick);



        //when settingsLabel gets clicked
        SettingsEventHandler settingsClick = new SettingsEventHandler();
        settingsLabel.setOnMouseClicked(settingsClick);



        //when controlsLabel gets clicked
        ControlsEventHandler controlsClick = new ControlsEventHandler();
        controlsLabel.setOnMouseClicked(controlsClick);



        //when exitLabel gets clicked
        EventHandler<MouseEvent> exitClick = new ExitEventHandler();
        exitLabel.setOnMouseClicked(exitClick);



        Run.prevScene = PreviousScene.MAIN;
        return new Scene(designedVBox);
    }

    /**
     * builds the settings menu GUI and assigns controllers to the elements
     * @return settings menu
     */
    private static Scene createSettingsMenu(){
        GridPane gridPane = new GridPane();
        String style = "-fx-text-fill: white; -fx-font: " + new Double(Properties.textscale/2).intValue() + "px " + Properties.fonttype + ";";
        gridPane.setVgap(10);
        gridPane.setHgap(20);



        //WINDOW SIZE SETTING
        Label windowSizeLabel = new Label("Window size:");
        windowSizeLabel.setStyle(style);
        Slider sizeSlider = new Slider();
        sizeSlider.setStyle("-fx-tick-label-fill: white;");
        sizeSlider.setMin(10);
        sizeSlider.setMax(150);
        sizeSlider.setValue(Properties.windowSize);
        sizeSlider.setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                Slider source = (Slider) t.getSource();
                Properties.setWindowSize(source.getValue());
                resetMenus();
                Run.setScene(getSettingsMenu());
            }
        });



        //TOGGLE MUSIC SETTING
        Label musicLabel = new Label("Music:");
        musicLabel.setStyle(style);

        HBox musicToggleBox = new HBox();
        ToggleGroup musicToggleGroup = new ToggleGroup();

        ToggleButton onToggleButton = new ToggleButton("ON");
        onToggleButton.setSelected(Properties.musicOn);
        onToggleButton.setToggleGroup(musicToggleGroup);
        onToggleButton.setId("on");

        ToggleButton offToggleButton = new ToggleButton("OFF");
        offToggleButton.setSelected(!Properties.musicOn);
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
                        Properties.musicOn=true;
                    } else {
                        Properties.musicOn=false;
                    }
                }
//                System.out.println("Music is now: " + ((properties.musicOn) ? "on" : "off"));
            }
        });



        //DIFFICULTY SETTING
        Label difficultyLabel = new Label("Difficulty:");
        difficultyLabel.setStyle(style);
        VBox difficultyBox = new VBox();
        difficultyBox.setSpacing(0.1*Properties.blocksize);

        ToggleGroup difficultyToggleGroup = new ToggleGroup();

        RadioButton easyRadioButton = new RadioButton("Easy");
        easyRadioButton.setTextFill(Color.WHITE);
        easyRadioButton.setFont(new Font(Properties.fonttype, Properties.textscale/3));
        easyRadioButton.setToggleGroup(difficultyToggleGroup);
        easyRadioButton.setSelected(Run.game.getDifficulty() == Difficulty.EASY);
        easyRadioButton.setId("easy");

        RadioButton mediumRadioButton = new RadioButton("Medium");
        mediumRadioButton.setTextFill(Color.WHITE);
        mediumRadioButton.setFont(new Font(Properties.fonttype, Properties.textscale/3));
        mediumRadioButton.setToggleGroup(difficultyToggleGroup);
        mediumRadioButton.setSelected(Run.game.getDifficulty() == Difficulty.MEDIUM);
        mediumRadioButton.setId("medium");

        RadioButton hardRadioButton = new RadioButton("Hard");
        hardRadioButton.setTextFill(Color.WHITE);
        hardRadioButton.setFont(new Font(Properties.fonttype, Properties.textscale/3));
        hardRadioButton.setToggleGroup(difficultyToggleGroup);
        hardRadioButton.setSelected(Run.game.getDifficulty() == Difficulty.HARD);
        hardRadioButton.setId("hard");

        difficultyBox.getChildren().addAll(easyRadioButton,mediumRadioButton,hardRadioButton);
        difficultyToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                ToggleButton newDiff = (ToggleButton) t1;
                if (newDiff!=null){
                    if (newDiff.getId().equals("easy")){
                        Run.game.setDifficulty(Difficulty.EASY);
                    }
                    if (newDiff.getId().equals("medium")){
                        Run.game.setDifficulty(Difficulty.MEDIUM);
                    }
                    if (newDiff.getId().equals("hard")){
                        Run.game.setDifficulty(Difficulty.HARD);
                    }
                }
            }
        });



        //ADD ELEMENTS TO GRID
        gridPane.add(windowSizeLabel, 0, 0);
        gridPane.add(sizeSlider, 1, 0);

        gridPane.add(musicLabel, 0, 1);
        gridPane.add(musicToggleBox, 1, 1);

        gridPane.add(difficultyLabel, 0, 2);
        gridPane.add(difficultyBox, 1, 2);



        //BACK "BUTTON"
        Label backLabel = new Label("Back");
        EventHandler<MouseEvent> backLabelClick = new BackFromSettingsOrControlsEventHandler();
        backLabel.setOnMouseClicked(backLabelClick);



        //ADD EVERYTHING TO CUSTOM VBOX
        DesignedVBox designedVBox = new DesignedVBox(Properties.textColor, Properties.font, Properties.lstyle, Properties.vboxSpacing, Properties.vboxInsets, Properties.vboxAlignment);
        designedVBox.setStyle("-fx-background-color: black");
        designedVBox.getChildren().add(gridPane);
        designedVBox.addAllLabel(backLabel);


        Scene scene = new Scene(designedVBox);
        return scene;
    }

    /**
     * builds the controls menu GUI and assigns controllers to the elements
     * @return controls menu
     */
    private static Scene createControlsMenu(){
        GridPane gridPane = new GridPane();
        String keyLabelStyle = "-fx-text-fill: white; -fx-font: " + new Double(Properties.textscale/2).intValue() + "px " + Properties.fonttype + ";";
        gridPane.setVgap(10);
        gridPane.setHgap(20);



        //CREATE LABELS WITH STYLES
        Label moveLeftLabel = new Label("Move left:");
        moveLeftLabel.setStyle(keyLabelStyle);

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



        //LOAD AND PREPARE IMAGES
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



        //ADD EVERYTHING TO GRID
        gridPane.add(moveLeftLabel, 0, 0);
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


        //ASSIGN "BACK" EVENT HANDLER
        Label backLabel = new Label("Back");
        EventHandler<MouseEvent> backLabelClick = new BackFromSettingsOrControlsEventHandler();
        backLabel.setOnMouseClicked(backLabelClick);



        //ADD EVERYTHING TO CUSTOM VBOX
        DesignedVBox designedVBox = new DesignedVBox(Properties.textColor, Properties.font, Properties.lstyle, Properties.vboxSpacing, Properties.vboxInsets, Properties.vboxAlignment);
        designedVBox.setStyle("-fx-background-color: black");
        designedVBox.getChildren().add(gridPane);
        designedVBox.addLabel(backLabel);


        return new Scene(designedVBox);
    }

    /**
     * builds the pause menu GUI and assigns controllers to the elements
     * @return pause menu
     */
    private static Scene createPauseMenu(){
        DesignedVBox designedVBox = new DesignedVBox(Properties.textColor, Properties.font, Properties.menuStyle, Properties.vboxSpacing, Properties.vboxInsets, Properties.vboxAlignment);

        Label continueLabel = new Label("Continue");
        Label settingsLabel = new Label("Settings");
        Label controlsLabel = new Label("Controls");
        Label backToMainMenuLabel = new Label("Back to main menu");

        //ADD EVERYTHING TO CUSTOM VBOX
        designedVBox.addAllLabel(continueLabel, settingsLabel, controlsLabel, backToMainMenuLabel);



        //"CONTINUE" EVENT HANDLER
        EventHandler<MouseEvent> continueClick = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                Scene scene = getIngameGUI();

                Run.unpauseGame();
                Run.setScene(scene);
            }
        };
        continueLabel.setOnMouseClicked(continueClick);



        //"SETTINGS" EVENT HANDLER
        SettingsEventHandler settingsClick = new SettingsEventHandler();
        settingsLabel.setOnMouseClicked(settingsClick);



        //"CONTTROLS" EVENT HANDLER
        ControlsEventHandler controlsClick = new ControlsEventHandler();
        controlsLabel.setOnMouseClicked(controlsClick);



        //"BACK TO MAIN MENU" EVENT HANDLER
        EventHandler<MouseEvent> backToMainMenuClick = new BackToMainMenuEventHandler();
        backToMainMenuLabel.setOnMouseClicked(backToMainMenuClick);


        Run.prevScene = PreviousScene.PAUSE;

        return new Scene(designedVBox);
    }

    /**
     * builds the game over GUI and assigns controllers to the elements
     * @return game over menu
     */
    private static Scene createGameOverMenu(){
        DesignedVBox designedVBox = new DesignedVBox(Properties.textColor, Properties.font, Properties.menuStyle, Properties.vboxSpacing, Properties.vboxInsets, Properties.vboxAlignment);

        Label gameOverLabel = new Label("Game over!");
        Label scoreLabel = new Label("Score: " + Run.game.getScore());
        Label gapLabel = new Label(" ");
        Label newGameLabel = new Label("New game");
        Label backToMainMenuLabel = new Label("Back to main menu");
        Label exitLabel = new Label("Exit");
//        dv.addNonEffectLabel(gameOver);

//        //ADD EVERYTHING TO CUSTOM VBOX
        designedVBox.getChildren().addAll(gameOverLabel, scoreLabel);
        designedVBox.addAllLabel(gapLabel, newGameLabel, backToMainMenuLabel, exitLabel);

        gapLabel.setFont(new Font(Properties.fonttype, Properties.textscale * 0.5));

        gameOverLabel.setTextFill(Color.ORANGERED);
        gameOverLabel.setFont(new Font(Properties.fonttype, Properties.textscale * 1.1));

        scoreLabel.setTextFill(Properties.gameOverScoreColor);
        scoreLabel.setFont(Properties.font);



        //"NEW GAME" EVENT HANDLER
        EventHandler<MouseEvent> newGameClick = new NewGameEventHandler();
        newGameLabel.setOnMouseClicked(newGameClick);



        //"BACK TO MAIN MENU" EVENT HANDLER
        EventHandler<MouseEvent> backToMainMenuClick = new BackToMainMenuEventHandler();
        backToMainMenuLabel.setOnMouseClicked(backToMainMenuClick);



        //"EXIT" EVENT HANDLER
        EventHandler<MouseEvent> exitClick = new ExitEventHandler();
        exitLabel.setOnMouseClicked(exitClick);

        return new Scene(designedVBox);
    }

    /**
     * builds the ingame GUI
     * @return ingame GUI
     */
    private static Scene createIngameGUI(){

        //main field
        Run.field = new Pane();
        Run.field.setMinSize(Properties.blocksize*10, Properties.blocksize*18);
        Run.field.setMaxSize(Properties.blocksize*10, Properties.blocksize*18);
        Run.field.setStyle(Properties.fieldStyle);



        //next tile field
        VBox nextArea = new VBox();
        Run.next = new Pane();
        Run.next.setMinSize(Properties.blocksize*4, Properties.blocksize*4);
        Run.next.setMaxSize(Properties.blocksize*4, Properties.blocksize*4);

        Label nextLabel = new Label("Next");
        nextLabel.setTextFill(Properties.textColor);
        nextLabel.setFont(Properties.font);

        Label gapLabel = new Label(" ");
        gapLabel.setFont(Properties.font);

        Label scoreLabel = new Label("Score:");
        scoreLabel.setTextFill(Properties.textColor);
        scoreLabel.setFont(new Font(Properties.fonttype, Properties.textscale*0.6));

        Run.currentScore = new Label();
        Run.currentScore.setTextFill(Properties.textColor);
        Run.currentScore.setFont(new Font(Properties.fonttype, Properties.textscale*0.6));

        nextArea.getChildren().addAll(Run.next, nextLabel, gapLabel, scoreLabel, Run.currentScore);
        nextArea.setStyle(Properties.borderSectionStyle);
        nextArea.setAlignment(Pos.TOP_CENTER);



        //hold tile field
        VBox holdArea = new VBox();
        Run.hold = new Pane();
        Run.hold.setMinSize(Properties.blocksize*4, Properties.blocksize*4);
        Run.hold.setMaxSize(Properties.blocksize*4, Properties.blocksize*4);

        Label holdLabel = new Label("Hold");
        holdLabel.setTextFill(Properties.textColor);
        holdLabel.setFont(Properties.font);

        holdArea.getChildren().addAll(Run.hold, holdLabel);
        holdArea.setStyle(Properties.borderSectionStyle);
        holdArea.setAlignment(Pos.TOP_CENTER);



        //parent element of game gui
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle(Properties.borderPaneStyle);
        borderPane.setLeft(holdArea);
        borderPane.setCenter(Run.field);
        borderPane.setRight(nextArea);
        BorderPane.setMargin(Run.field,Properties.fieldInsets);

        Scene scene = new Scene(borderPane,Run.field.getMinWidth() + Run.next.getMinWidth() + Run.hold.getMinWidth() + 2f*Properties.fieldGap, Run.field.getMinHeight());



        //ASSIGN KEY EVENT HANDLER
        KeyEventHandler keyListener = new KeyEventHandler();
        scene.setOnKeyPressed(keyListener);

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

    private static void resetMenus(){
        mainMenu=null;
        settingsMenu=null;
        controlsMenu=null;
        pauseMenu=null;
        gameOverMenu=null;
        ingameGUI=null;
    }
}
