/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remains;
/**
 *
 * @author donatdeva
 */
public class Test {
    
    static void setFalse(boolean[] b){
        b[0] = false;
    }
    
    public static void main(String[] args){
//        Tile i = new Tile(Tiletype.L, Place.FIELD);
//        System.out.println(i.getMode());
//        
//        i.rotate();
//        System.out.println(i.getMode() + " (ROTATED)");
//        i.rotate();
//        System.out.println(i.getMode() + " (ROTATED)");
//        
//        System.out.println("Previous Mode should: " + i.getPrevious().getMode());
//        i.returnToPrevious();
//        System.out.println("Previous Mode is: " + i.getMode());
//        System.out.println("Previous Mode should: " + i.getPrevious().getMode());
//        i.returnToPrevious();
//        System.out.println("Previous Mode is: " + i.getMode());
        boolean[] b = new boolean[1];
        b[0] = true;
        setFalse(b);
        System.out.println(b[0]);
        
        System.out.println(0.03333 * 30);
        System.out.println(1d/30d * 30);
        System.out.println(1.5 * 30);
        System.out.printf("%.15f",new Double(1/30));
    }
}
