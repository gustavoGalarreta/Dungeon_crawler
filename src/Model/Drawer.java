/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Console;

/**
 *
 * @author Diego Monge <diegofmp>
 */
public class Drawer {
    private int distance_a;
    private int distance_b;
    
    public Drawer(int a, int b){
        this.distance_a=a;
        this.distance_b=b;
    }
    
    public void drawStory(){
        System.out.println("historia");
    }
    
    public void clear(){
        for(int i=0; i<distance_b*2;i++)
            System.out.println("");
    }
            
    public void drawCell(Maze gameMaze, int col, int row){
        Cell currentCell= gameMaze.getMaze()[row][col];
        if(gameMaze.has_potion(row, col))
            System.out.print('P');
        else if(gameMaze.has_artefact(row, col))
            System.out.print('A');
        else if(gameMaze.has_monster(row, col))
            System.out.print('E');        
        else if(gameMaze.is_next(row, col))
            System.out.print('+');
        else if(gameMaze.is_prev(row, col))
            System.out.print('-');
        else if(gameMaze.is_path(row, col)){
            System.out.print(' ');
        }
        else if(gameMaze.is_wall(row, col))
            System.out.print('#');
    }
    
    //--funcion dibujar vista actual
    public void drawView(Maze gameMaze, int x_ini, int y_ini){    //x es col y es row
        int row, col;
        for(row=y_ini-distance_b; row<=y_ini+distance_b; row++){
            for(col=x_ini-distance_a; col<=x_ini+distance_a; col++){
                if(row==0&& col==0){
                    System.err.print("");
                }
                if(row==y_ini && col==x_ini)
                    System.out.print('X');
                else{
                    if(gameMaze.these_coordinates_are_in_maze(row, col))
                        drawCell(gameMaze, col, row);//ojo con el orden de params
                    else
                        System.out.print('?');
                }
            }
            System.out.println();
        }        
        System.out.println();
    }
            
    
}
