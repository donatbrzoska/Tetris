/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.data;

//import java.util.List;

import java.util.Stack;


/**
 *
 * @author donatdeva
 */
public class Tile {
    private Tiletype tiletype;
    private int mode;   //Rotationsmode
    private Coordinate position;    //obere linke Ecke des Matrix Objekts
    private Coordinate[] coordinates;   //Koordinaten der einzelnen Zellen des Tiles
    private Change change;  //letzte Veränderung
    //private GhostTile ghost;
    
    private Tile previous;  //Backup-Tile
    private Stack<Tile> history;
    
    public Tile(Tiletype tiletype, Place place){ 
        this.tiletype = tiletype;
        change = null;
        setPlace(place);
        history = new Stack<>();
    }
    
    private void nextMode(){
        if (mode+1 < tiletype.getModes().length)
            mode++;
        else 
            mode = 0;
    }
    
    private void updateCoordinates(){
        Coordinate[] modeCoords = tiletype.getModes()[mode].toCoordinates();
        for (int i = 0; i<modeCoords.length; i++){
            if (modeCoords[i]!=null){
                modeCoords[i].add(position);
            }
        }
        coordinates = modeCoords;
    }
    
    private void saveState(){
        Tile prev = new Tile(tiletype, Place.FIELD);
        prev.mode = mode;
        prev.position = new Coordinate(position.x, position.y);
        prev.updateCoordinates();
        prev.change = change;
        if (!history.isEmpty()){
            prev.previous = history.peek();
        }
        history.add(prev);
    }
    
    final void setPlace(Place p){
        switch(p) {
            case HOLD:
                switch(tiletype.name()){
                    case "I":
                        mode = 0;
                        position = new Coordinate(0,0);
                        break;
                    case "O":
                        mode = 0;
                        position = new Coordinate(1,1);
                        break;
                    case "L":
                        mode = 0;
                        position = new Coordinate(1,1);
                        break;
                    case "J":
                        mode = 0;
                        position = new Coordinate(1,1);
                        break;
                    case "T":
                        mode = 1;
                        position = new Coordinate(0,1);
                        break;
                    case "Z":
                        mode = 1;
                        position = new Coordinate(1,1);
                        break;
                    case "S":
                        mode = 1;
                        position = new Coordinate(1,1);
                        break;
                }
                //ghost = null;
                updateCoordinates();
                break;
            case FIELD:
                switch(tiletype.name()){
                    case "I":
                        mode = 1;
                        position = new Coordinate(3,-2);
                        break;
                    case "O":
                        mode = 0;
                        position = new Coordinate(4,0);
                        break;
                    case "L":
                        mode = 3;
                        position = new Coordinate(3,-1);
                        break;
                    case "J":
                        mode = 1;
                        position = new Coordinate(3,-1);
                        break;
                    case "T":
                        mode = 0;
                        position = new Coordinate(3,0);
                        break;
                    case "Z":
                        mode = 0;
                        position = new Coordinate(3,-1);
                        break;
                    case "S":
                        mode = 0;
                        position = new Coordinate(3,-1);
                        break;
                }
                updateCoordinates();
                break;
            case NEXT:
                switch(tiletype.name()){
                    case "I":
                        mode = 0;
                        position = new Coordinate(0,0);
                        break;
                    case "O":
                        mode = 0;
                        position = new Coordinate(1,1);
                        break;
                    case "L":
                        mode = 0;
                        position = new Coordinate(1,1);
                        break;
                    case "J":
                        mode = 0;
                        position = new Coordinate(1,1);
                        break;
                    case "T":
                        mode = 1;
                        position = new Coordinate(0,1);
                        break;
                    case "Z":
                        mode = 1;
                        position = new Coordinate(1,1);
                        break;
                    case "S":
                        mode = 1;
                        position = new Coordinate(1,1);
                        break;
                }
                //ghost = null;
                updateCoordinates();
                break;
        }
    }
    
    public void returnToPrevious(int steps){
        for (int i=0; i<steps; i++){
            Tile prev = history.pop();
            this.mode = prev.mode;
            this.position = prev.position;
            this.updateCoordinates();
            this.change = prev.change;
            this.previous = prev.previous;
        }
    }
    
    public void rotate(){
        saveState();
        nextMode();
        updateCoordinates();
        change = Change.ROTATED;
    }
    
    public void moveUp(){
        position.y--;
        updateCoordinates();
        //System.out.println("Tile moved up");
    }
    
    public void moveRight(){
        saveState();
        position.x++;
        updateCoordinates();
        change = Change.MOVEDRIGHT;
    }
    
    public void moveDown(){
        saveState();
        position.y++;
        updateCoordinates();
        change = Change.MOVEDDOWN;
    }
    
    public void moveLeft(){
        saveState();
        position.x--;
        updateCoordinates();
        change = Change.MOVEDLEFT;
    }
    
    public Change getLastChange(){
        return change;
    }
    
    public Coordinate[] getCoordinates(){
        return coordinates;
    }
    
    public int getMode(){
        return mode;
    }
    
    public Tiletype getType(){
        return tiletype;
    }
    
    @Override
    public String toString(){
        return tiletype.getModes()[mode].toString();
    }
    
    
    //DEBUG
    public Coordinate getPosition(){
        return position;
    }
    
    //DEBUG
    public Tile getPrevious(){
        return previous;
    }
}