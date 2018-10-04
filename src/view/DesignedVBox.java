/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author donatdeva
 */
public class DesignedVBox extends VBox {
    private Color textColor;
    private Font font;
    private final static Color SELECTED = Color.GOLDENROD;
//    private int spacing;
//    private Insets insets;
//    private String style;
    
    DesignedVBox(Color textColor, Font font, String style, int spacing, Insets insets, Pos alignment){
        this.textColor = textColor;
        this.font = font;
//        this.spacing = spacing;
        this.setSpacing(spacing);
//        this.insets = insets;
        this.setPadding(insets);
//        this.style = style;
        this.setStyle(style);
        this.setAlignment(alignment);
        
    }
    
    void addAllLabel(Label... l){
        for (int i=0; i<l.length; i++){
            l[i].setTextFill(textColor);
            l[i].setFont(font);
            l[i].setOnMouseEntered(e -> {
                Label source = (Label) e.getSource();
//                source.setFont(Font.font(Run.FONTTYPE, FontWeight.BOLD, Run.TEXTSCALE*1.1));
                source.setTextFill(SELECTED);
                Run.clickSound();
            });
            l[i].setOnMouseExited(e -> {
                Label source = (Label) e.getSource();
//                source.setFont(new Font(Run.FONTTYPE, Run.TEXTSCALE));
                source.setTextFill(textColor);
            });
            this.getChildren().add(l[i]);
        }
    }
    
//    void addNonEffectLabel(Label l){
//        l.setFont(font);
//        this.getChildren().add(l);
//    }
    
    void addLabel(Label l){
        addAllLabel(new Label[] {l});
    }
    
}
