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
public class Bag {
    protected List<Artefact> artefacts;
    private int max_capacity;
    
    public Bag(int max_capacity){
        artefacts = new ArrayList<Artefact>();
        this.max_capacity = max_capacity;
    }
    
    public void add_artefact(Artefact artefact){
        if(artefacts.size() < getMax_capacity())
            artefacts.add(artefact);
    }

    public void remove_artefact(int i){
        if(i < artefacts.size())
            artefacts.remove(i);
    }
    /**
     * @return the max_capacity
     */
    public int getMax_capacity() {
        return max_capacity;
    }

    /**
     * @param max_capacity the max_capacity to set
     */
    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }
    
}
