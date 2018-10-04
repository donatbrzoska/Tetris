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
public class Mode {
    Cell[][] structure;
    
    Mode(Cell[][] structure){
        this.structure=structure;
    }
    
    /**
     * creates coordinate array of the structure
     * @return Array of the coordinates the tile actually occupies
     */
    Coordinate[] toCoordinates(){
        Coordinate[] coordinates = new Coordinate[4];
        int count = 0;
        for (int i=0; i<structure.length; i++) {
            for (int j=0; j<structure[i].length; j++) {
                if (structure[i][j].isSet()) {
                    /*coordinates[count].setX(i);
                    coordinates[count].setY(j);*/
                    //System.out.println(count);
                    //System.out.println(coordinates[count]==null);
                    coordinates[count] = new Coordinate(j,i);
                    //coordinates[count].x = i;
                    //coordinates[count].y = j;
                    count++;
                }
            }
        }
        return coordinates;
    }
}