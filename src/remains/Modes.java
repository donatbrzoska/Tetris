/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remains;

import java.util.LinkedList;
import java.util.List;
import model.data.Cell;

/**
 *
 * @author donatdeva
 */
public class Modes {
    private int current;
    private List<Cell[][]> modes;
    
    Modes(){
        current = 0;
        modes = new LinkedList<>();
    }
    
    void addMode(Cell[][] mode){
        modes.add(mode);
    }
    
    void nextMode(){
        if (current+1 < modes.size())
            current++;
        else 
            current = 0;
    }
    
    @Override
    public String toString(){
        String s = "";
        for (int i=0; i<modes.size(); i++){
            Cell[][] mode = modes.get(i);
            for (int j=0; j<mode.length; j++){
                for (int k=0; k<mode[j].length; k++){
                    //System.out.println(modes.get(i)==mode);
                    //System.out.println(s==null);
                    //System.out.println(mode[j][k].toString());
                    s = s+mode[j][k].toString() + " ";
                }
                s = s + "\n";
            }
            s = s + "\n\n";
        }
        return s;
    }
}
