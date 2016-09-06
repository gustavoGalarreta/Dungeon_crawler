/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Marco
 */
public class Artefact {
    private boolean in_use;
    
    public Artefact(){
        this.in_use = false;
    }

    /**
     * @return the in_use
     */
    public boolean isIn_use() {
        return in_use;
    }

    /**
     * @param in_use the in_use to set
     */
    public void setIn_use(boolean in_use) {
        this.in_use = in_use;
    }
    
}
