/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Gustavo
 */
public class Cell {
    //1 wall
    //0 path
    
    private int type;
    private boolean visited;
    private boolean start;
    private boolean end;
    
    public Cell(){
        type = 1;
        visited = false;
        start = false;
        end = false;
    }
    public void setWall(){
        this.type = 1;
    }
    public void setUnvisited(){
        this.visited = false;
    }
    public void setVisited(){
        this.visited = true;
    }
    public void setPath(){
        this.type = 0;
    }
    public boolean is_visited(){
        return visited;
    }
    public void showCell(){
        System.out.print(type);
    }
   
    public boolean is_wall(){
        boolean result;
        result = (type==1) ? true : false;
        return result;
    }
    
    public boolean is_path(){
        boolean result;
        result = (type==0) ? true : false;
        return result;
    }
    public boolean is_start() {
        return start;
    }

    public void set_start() {
        this.start = true;
    }

    public boolean is_end() {
        return end;
    }

    public void set_end() {
        this.end = true;
    }
}
