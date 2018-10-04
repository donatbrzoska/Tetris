/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remains;

import model.data.Cell;

/**
 *
 * @author donatdeva
 */
public class ITile {
    Cell[][][] cellmatrix;
    
    ITile(Cell[][][] cellmatrix){
        this.cellmatrix = cellmatrix;
    }
}
