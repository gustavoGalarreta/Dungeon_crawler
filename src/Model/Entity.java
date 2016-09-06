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
public class Entity {
    private int max_life;
    private int actual_life;
    private int row;
    private int col;
    
    public Entity(int row, int col, int max_life){
        this.max_life = max_life;
        this.row = row;
        this.col = col;
    }
    
    public void receive_damage(int damage){
        actual_life -= damage;
        if(actual_life < 0) actual_life = 0;
    }
    
    public void healing(int life_points){
        actual_life += life_points;
        if(actual_life > max_life) actual_life = max_life;
    }
    
    public void move(int x, int y){
        setRow(x);
        setCol(y);
    }

    /**
     * @return the x
     */
    public int getRow() {
        return row;
    }

    /**
     * @paramrowtherowto set
     */
    public void setRow(int x) {
        this.row = x;
    }

    /**
     * @return the y
     */
    public int getCol() {
        return col;
    }

    /**
     * @param y the y to set
     */
    public void setCol(int y) {
        this.col = y;
    }
    
    /**
     * @return the max_life
     */
    public int getMax_life() {
        return max_life;
    }

    /**
     * @param max_life the max_life to set
     */
    public void setMax_life(int max_life) {
        this.max_life = max_life;
    }

    /**
     * @return the actual_life
     */
    public int getActual_life() {
        return actual_life;
    }

    /**
     * @param actual_life the actual_life to set
     */
    public void setActual_life(int actual_life) {
        this.actual_life = actual_life;
    }  
}
