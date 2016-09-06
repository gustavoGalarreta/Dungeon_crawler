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
public class Armor extends Artefact {
    private int protection;
    
    public Armor(int protection){
        this.protection = protection;
    }

    /**
     * @return the protection
     */
    public int getProtection() {
        return protection;
    }

    /**
     * @param protection the protection to set
     */
    public void setProtection(int protection) {
        this.protection = protection;
    }
    
}
