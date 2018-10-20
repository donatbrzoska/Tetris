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
public class Coordinate {
    public int x;
    public int y;
    
    public Coordinate(int x, int y){
        this.x=x;
        this.y=y;
    }
    
    public boolean equals(Coordinate coordinate){
        return (x==coordinate.x && y==coordinate.y);
    }
    
    public void add(Coordinate coordinate){
        x = x + coordinate.x;
        y = y + coordinate.y;
    }
    
    @Override
    public String toString(){
        return "x="+x+" y="+y;
    }
}
