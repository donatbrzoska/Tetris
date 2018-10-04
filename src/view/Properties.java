/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author donatdeva
 */
public class Properties {
    double windowSize = 80;
    double blocksize = 0.4*windowSize;
    double textscale = 1.5*blocksize;
    
//    double fieldGap = 1d/30d * blocksize;
    double fieldGap = 1;
    Insets fieldInsets = new Insets(0,fieldGap,0,fieldGap);
    String fieldStyle = "-fx-background-color: black;";
    
    String borderSectionStyle = "-fx-background-color: black;";
    String borderPaneStyle = "-fx-background-color: GhostWhite;";

    Color rectangleStrokeColor = Color.WHITE;
    int rectangleStrokeWidth = 2;
    
    String lstyle = "-fx-fill: darkorange;";
    String jstyle = "-fx-fill: orangered;";
    String ostyle = "-fx-fill: limegreen;";
    String tstyle = "-fx-fill: deepskyblue;";
    String zstyle = "-fx-fill: crimson;";
    String sstyle = "-fx-fill: slateblue;";
    String istyle = "-fx-fill: gold;";
    String ghostopacity = " -fx-opacity: 0.4";
    String gstyle = "-fx-fill: ghostwhite; -fx-opacity: 0.95";

    Color textColor = Color.WHITE;
    Color gameOverScoreColor = Color.DEEPSKYBLUE;
    String fonttype = "Courier";
    Font font = Font.font(fonttype, textscale);
//    String titleFontSrc = Run.class.getResource("../resources/font/TETRIS.TTF").toExternalForm();
//    String titleFontStyle = " -fx-font: bold "+((int)(textscale*1.5))+"pt Arial Black;";
//    Font titleFontStyle = Font.font(titleFont, FontWeight.BOLD, textscale*1.5);
    Font titleFont = getTitleFont();
    String menuStyle = "-fx-background-color: black;";
    int vboxSpacing = (int) (0.5714d*blocksize);
    Insets vboxInsets = new Insets(0.625*windowSize,0.625*windowSize,0.625*windowSize,0.625*windowSize);
    Pos vboxAlignment = Pos.CENTER;
    boolean musicOn = true;
    
    void setWindowSize(double windowSize){
        this.windowSize = windowSize;
        blocksize = 0.4*windowSize;
        textscale = 1.5*blocksize;
        fieldGap = 1;
        fieldInsets = new Insets(0,fieldGap,0,fieldGap);
        font = Font.font(fonttype, textscale);
        vboxSpacing = (int) (0.5714d*blocksize);
//         vboxInsets = new Insets(50,50,50,50);
//         vboxAlignment = Pos.CENTER;
    }
    
    Font getTitleFont(){
//        try {
//            System.out.println("CHECK " + textscale*1.5);
//            return Font.loadFont(new FileInputStream(new File(titleFontSrc)), textscale*10);
//        } catch (Exception e) {};
//        return null;
        return Font.font("Block Stock", FontWeight.BOLD, textscale*1.5);
    }
}
