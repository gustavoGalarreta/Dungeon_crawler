/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
/**
 *
 * @author jenisse
 */
public class Enemy extends Entity {
    private String name;
    
    private int level;
    
    private Weapon weapon_belonging;
    
    public static final int n = 2;  //constant which is going to be part of the definition of the function used as a weapon atribute
    
    public Enemy(String name,int row, int col, int max_life, int level){
        super(row, col, max_life);
        this.name = name;
        this.level=level;
        this.weapon_belonging=new Weapon(level/n);   //The level of damage of an enemy weapon is defined as the square of the level of the enemy
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Weapon getWeapon_belonging() {
        return weapon_belonging;
    }

    public void setWeapon_belonging(Weapon weapon_belonging) {
        this.weapon_belonging = weapon_belonging;
    }
    
    
}
