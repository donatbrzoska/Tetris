/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

/**
 *
 * @author donatdeva
 */
public class Cell {
    private boolean set;
    private Celltype type;
    //private Coordinate coordinates;
    
    public Cell(){
        set = false;
        type = Celltype.X;
    }
    
    public Cell(Celltype type){
        set = true;
        this.type = type;
    }
    
    public boolean isSet(){
        return set;
    }
    
    public Celltype getType(){
        return type;
    }
    
    public void setType(Celltype type){
        set = (type != Celltype.X);
        this.type = type;
    }
    
    public String toString(){
        return type.toString();
    }
}
