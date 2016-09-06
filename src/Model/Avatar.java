/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.*;
/**
 *
 * @author Marco
 */
public class Avatar extends Entity {
    private String name;
    
    private Bag bag;
    
    public Avatar(String name,int row, int col, int max_life){
        super(row, col, max_life);
        this.name = name;
    }
    
    public void new_artefact(Artefact artefact){
        bag.add_artefact(artefact);
    }
    
    public void remove_artefact(int i){
        bag.remove_artefact(i);
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }



    /**
     * @return the bag
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * @param bag the bag to set
     */
    public void setBag(Bag bag) {
        this.bag = bag;
    }
    
}
