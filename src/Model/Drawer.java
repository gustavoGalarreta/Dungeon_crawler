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
    public void drawCell(Maze gameMaze, int col, int fil){
        Cell currentCell= gameMaze.getMaze()[fil][col];
        if(currentCell.is_path()){
            System.out.print(' ');
        }
        else if(currentCell.is_wall())
            System.out.print('#');
        else if(currentCell.is_start())
            System.out.print('+');
        else if(currentCell.is_end())
            System.out.print('-');
        //faltan dibujar resto de posibilidades....        
    }
    
    //--funcion dibujar vista actual
    public void drawView(Maze gameMaze, int x_ini, int y_ini){    //x es col y es row
        int row, col;
        for(row=y_ini-distance_b; row<=y_ini+distance_b; row++){
            for(col=x_ini-distance_a; col<=x_ini+distance_a; col++){
                if(row==y_ini && col==x_ini)
                    System.out.print('X');
                else{
                    if(gameMaze.these_coordinates_are_in_maze(row, col))
                        drawCell(gameMaze, row, col);                
                    else
                        System.out.print('?');
                }
            }
            System.out.println();
        }        
        System.out.println();
    }
            
    
}
