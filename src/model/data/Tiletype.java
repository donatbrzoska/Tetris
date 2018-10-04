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
public enum Tiletype {
    L(new Mode[]{new Mode(new Cell[][]{{new Cell(Celltype.L),new Cell(),          new Cell()},
                                       {new Cell(Celltype.L),new Cell(),          new Cell()},
                                       {new Cell(Celltype.L),new Cell(Celltype.L),new Cell()}}),
                 new Mode(new Cell[][]{{new Cell(),          new Cell(),          new Cell()},
                                       {new Cell(Celltype.L),new Cell(Celltype.L),new Cell(Celltype.L)},
                                       {new Cell(Celltype.L),new Cell(),          new Cell()}}),
                 new Mode(new Cell[][]{{new Cell(Celltype.L),new Cell(Celltype.L),new Cell()},
                                       {new Cell(),          new Cell(Celltype.L),new Cell()},
                                       {new Cell(),          new Cell(Celltype.L),new Cell()}}),
                 new Mode(new Cell[][]{{new Cell(),          new Cell(),          new Cell()},
                                       {new Cell(),          new Cell(),          new Cell(Celltype.L)},
                                       {new Cell(Celltype.L),new Cell(Celltype.L),new Cell(Celltype.L)}})
                 
                }, Celltype.L),
    J(new Mode[]{new Mode(new Cell[][]{{new Cell(),          new Cell(Celltype.J),new Cell()},
                                       {new Cell(),          new Cell(Celltype.J),new Cell()},
                                       {new Cell(Celltype.J),new Cell(Celltype.J),new Cell()}}),
                 new Mode(new Cell[][]{{new Cell(),          new Cell(),          new Cell()},
                                       {new Cell(Celltype.J),new Cell(),          new Cell()},
                                       {new Cell(Celltype.J),new Cell(Celltype.J),new Cell(Celltype.J)}}),
                 new Mode(new Cell[][]{{new Cell(Celltype.J),new Cell(Celltype.J),new Cell()},
                                       {new Cell(Celltype.J),new Cell(),          new Cell()},
                                       {new Cell(Celltype.J),new Cell(),          new Cell()}}),
                 new Mode(new Cell[][]{{new Cell(),          new Cell(),          new Cell()},
                                       {new Cell(Celltype.J),new Cell(Celltype.J),new Cell(Celltype.J)},
                                       {new Cell()          ,new Cell(),          new Cell(Celltype.J)}})
                }, Celltype.J),
    I(new Mode[]{new Mode(new Cell[][]{{new Cell(),new Cell(),new Cell(Celltype.I),new Cell()},
                                       {new Cell(),new Cell(),new Cell(Celltype.I),new Cell()},
                                       {new Cell(),new Cell(),new Cell(Celltype.I),new Cell()},
                                       {new Cell(),new Cell(),new Cell(Celltype.I),new Cell()}}),
                 new Mode(new Cell[][]{{new Cell(),new Cell(),new Cell(),new Cell()},
                                       {new Cell(),new Cell(),new Cell(),new Cell()},
                                       {new Cell(Celltype.I),new Cell(Celltype.I),new Cell(Celltype.I),new Cell(Celltype.I)},
                                       {new Cell(),new Cell(),new Cell(),new Cell()}})
                }, Celltype.I), 
    S(new Mode[]{new Mode(new Cell[][]{{new Cell(),          new Cell(),          new Cell()},
                                       {new Cell(),          new Cell(Celltype.S),new Cell(Celltype.S)},
                                       {new Cell(Celltype.S),new Cell(Celltype.S),new Cell()}}),
                 new Mode(new Cell[][]{{new Cell(Celltype.S),new Cell(),          new Cell()},
                                       {new Cell(Celltype.S),new Cell(Celltype.S),new Cell()},
                                       {new Cell(),          new Cell(Celltype.S),new Cell()}})
                }, Celltype.S),
    Z(new Mode[]{new Mode(new Cell[][]{{new Cell(),          new Cell(),          new Cell()},
                                       {new Cell(Celltype.Z),new Cell(Celltype.Z),new Cell()},
                                       {new Cell(          ),new Cell(Celltype.Z),new Cell(Celltype.Z)}}),
                 new Mode(new Cell[][]{{new Cell(),          new Cell(Celltype.Z),new Cell()},
                                       {new Cell(Celltype.Z),new Cell(Celltype.Z),new Cell()},
                                       {new Cell(Celltype.Z),new Cell(),          new Cell()}})
                }, Celltype.Z),
    O(new Mode[]{new Mode(new Cell[][]{{new Cell(Celltype.O),new Cell(Celltype.O)},
                                       {new Cell(Celltype.O),new Cell(Celltype.O)}})
                }, Celltype.O),
    T(new Mode[]{new Mode(new Cell[][]{{new Cell(),          new Cell(Celltype.T),new Cell()},
                                       {new Cell(Celltype.T),new Cell(Celltype.T),new Cell(Celltype.T)},
                                       {new Cell(),          new Cell(),          new Cell()}}),
                 new Mode(new Cell[][]{{new Cell(),          new Cell(Celltype.T),new Cell()},
                                       {new Cell(),          new Cell(Celltype.T),new Cell(Celltype.T)},
                                       {new Cell(),          new Cell(Celltype.T),new Cell()}}),
                 new Mode(new Cell[][]{{new Cell(),          new Cell(),          new Cell()},
                                       {new Cell(Celltype.T),new Cell(Celltype.T),new Cell(Celltype.T)},
                                       {new Cell(),          new Cell(Celltype.T),          new Cell()}}),
                 new Mode(new Cell[][]{{new Cell(),          new Cell(Celltype.T),new Cell()},
                                       {new Cell(Celltype.T),new Cell(Celltype.T),new Cell()},
                                       {new Cell(),          new Cell(Celltype.T),new Cell()}})
                }, Celltype.T);
    
    Mode[] modes;
    Celltype type;
    
    Tiletype(Mode[] modes, Celltype type){
        this.modes = modes;
        this.type = type;
    }
    
    Mode[] getModes(){
        return modes;
    }
    
    public Celltype getType(){
        return type;
    }
    
    @Override
    public String toString(){
        String s = "";
        for (int i=0; i<modes.length; i++){
            Mode mode = modes[i];
            for (int j=0; j<mode.structure.length; j++){
                for (int k=0; k<mode.structure[j].length; k++){
                    //System.out.println(modes.get(i)==mode);
                    //System.out.println(s==null);
                    //System.out.println(mode[j][k].toString());
                    s = s+mode.structure[j][k].toString() + " ";
                }
                s = s + "\n";
            }
            s = s + "\n\n";
        }
        return s;
    }
}