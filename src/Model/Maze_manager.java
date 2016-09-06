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
public class Maze_manager {
    public Maze_manager(){
        
    }
    public Maze create_maze(int size){
        Maze maze = Maze.generateMaze(size);
        return maze;
    }
}
