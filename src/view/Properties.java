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
import model.data.Celltype;

/**
 *
 * @author donatdeva
 */
public class Properties {
    static double windowSize = 80;
    static double blocksize = 0.4*windowSize;
    static double textscale = 1.5*blocksize;
    
//    double fieldGap = 1d/30d * blocksize;
    static double fieldGap = 1;
    static Insets fieldInsets = new Insets(0,fieldGap,0,fieldGap);
    static String fieldStyle = "-fx-background-color: black;";
    
    static String borderSectionStyle = "-fx-background-color: black;";
    static String borderPaneStyle = "-fx-background-color: GhostWhite;";

    static Color rectangleStrokeColor = Color.WHITE;
    static int rectangleStrokeWidth = 2;

    static String lstyle = "-fx-fill: darkorange;";
    static String jstyle = "-fx-fill: orangered;";
    static String ostyle = "-fx-fill: limegreen;";
    static String tstyle = "-fx-fill: deepskyblue;";
    static String zstyle = "-fx-fill: crimson;";
    static String sstyle = "-fx-fill: slateblue;";
    static String istyle = "-fx-fill: gold;";
    static String ghostopacity = " -fx-opacity: 0.4";
    static String gstyle = "-fx-fill: ghostwhite; -fx-opacity: 0.95";

    static Color textColor = Color.WHITE;
    static Color gameOverScoreColor = Color.DEEPSKYBLUE;
    static String fonttype = "Courier";
    static Font font = Font.font(fonttype, textscale);
//    String titleFontSrc = Run.class.getResource("../resources/font/TETRIS.TTF").toExternalForm();
//    String titleFontStyle = " -fx-font: bold "+((int)(textscale*1.5))+"pt Arial Black;";
//    Font titleFontStyle = Font.font(titleFont, FontWeight.BOLD, textscale*1.5);
    static Font titleFont = getTitleFont();
    static String menuStyle = "-fx-background-color: black;";
    static int vboxSpacing = (int) (0.5714d*blocksize);
    static Insets vboxInsets = new Insets(0.625*windowSize,0.625*windowSize,0.625*windowSize,0.625*windowSize);
    static Pos vboxAlignment = Pos.CENTER;
    static boolean musicOn = true;

    static void setWindowSize(double windowSize){
        Properties.windowSize = windowSize;
        blocksize = 0.4*windowSize;
        textscale = 1.5*blocksize;
        fieldGap = 1;
        fieldInsets = new Insets(0,fieldGap,0,fieldGap);
        font = Font.font(fonttype, textscale);
        vboxSpacing = (int) (0.5714d*blocksize);
//         vboxInsets = new Insets(50,50,50,50);
//         vboxAlignment = Pos.CENTER;
    }

    static Font getTitleFont(){
//        try {
//            System.out.println("CHECK " + textscale*1.5);
//            return Font.loadFont(new FileInputStream(new File(titleFontSrc)), textscale*10);
//        } catch (Exception e) {};
//        return null;
        return Font.font("Block Stock", FontWeight.BOLD, textscale*1.5);
    }

    public static String getStyleForCelltype(Celltype t) {
        switch (t) {
            case L:
                return lstyle;
            case J:
                return jstyle;
            case I:
                return istyle;
            case O:
                return ostyle;
            case S:
                return sstyle;
            case Z:
                return zstyle;
            case T:
                return tstyle;
            case GL:
                return lstyle+ghostopacity;
            case GJ:
                return jstyle+ghostopacity;
            case GI:
                return istyle+ghostopacity;
            case GO:
                return ostyle+ghostopacity;
            case GS:
                return sstyle+ghostopacity;
            case GZ:
                return zstyle+ghostopacity;
            case GT:
                return tstyle+ghostopacity;
//            case G:
//                r.setStyle(gstyle);
//                break;
            default:
                return null;
        }
    }
}
