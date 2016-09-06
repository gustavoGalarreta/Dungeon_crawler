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
public class HealingPotion extends Artefact {
    private int life_points;
    
    public HealingPotion(int life_points){
        this.life_points = life_points;
    }

    /**
     * @return the life_points
     */
    public int getLife_points() {
        return life_points;
    }

    /**
     * @param life_points the life_points to set
     */
    public void setLife_points(int life_points) {
        this.life_points = life_points;
    }
    
}
