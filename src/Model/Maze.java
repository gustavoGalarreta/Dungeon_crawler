/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.Random;
import java.util.*;

/**
 *
 * @author Gustavo
 */
    //contructor
    // -----------
    //|11111111111|
    //|10000000001|
    //|10000000001|
    //|11111111111|
    // -----------

public class Maze {
    //attr
    private int height;
    private int width;
    private Cell maze[][];
    private int start_row;
    private int start_col;
    private int end_row;
    private int end_col;
    //TBD start, next
    
    protected Maze(int size){
        height = width = size * 2 + 1;
        maze = new Cell[width][width];
        for(int row = 0; row < height; row++)
            for(int col = 0; col < width; col++){
                maze[row][col] = new Cell();
                if (row == 0 || row == height-1)
                    maze[row][col].setWall();
                else
                    if (col == 0 || col == width-1)
                        maze[row][col].setWall();
                    else
                        if ( (col % 2 == 0 || row % 2== 0) && !(col % 2 == 0 && row % 2 == 0))
                            maze[row][col].setWall();
                        else
                            maze[row][col].setPath();
                    }
    }    
//-----------------------------------------------------------------------------//    
    //GETS
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
//-----------------------------------------------------------------------------//    
    //CLASS METHODS
    
    private static int randomNumberGenerator(int max){
        Random randomGenerator = new Random();
        int randomValue = randomGenerator.nextInt(max);
        return randomValue;
    }

    private static int [] getRandomCellInMaze(){
        int myCell[] = new int [2];
        //this will be random
        myCell[0] = 1;//row
        myCell[1] = 1;//col
        return myCell;
    }
    
//----------------------------------------------------------------------------//
    // INSTANCE METHODS        

    public boolean these_coordinates_are_in_maze(int row, int col){
        if ( (row > 0 && row < height) && (col > 0 && col < width) )
            return true;
        else
            return false;
    }
    public int[] getUnvisitedCell(int[] initCell){
        //Integer directions[] = Maze.randomDirection(4);
        int[] directions = {1, 2, 3, 4};
        int validCell[] = new int[3];
        int direction, direction_fetcher = 0;
        boolean cell_is_selected = false;
        //direction_fetcher < 4, i dk if this is good
        while (!cell_is_selected && direction_fetcher < 4){
            direction = directions[direction_fetcher];
            switch (direction){
                case 1: //up
                    if (can_be_visit_this_cell(initCell[0] - 2, initCell[1])){
                        validCell[0] = initCell[0] - 2;
                        validCell[1] = initCell[1];
                        validCell[2] = direction;
                        cell_is_selected = true;
                    }
                    break;
                case 2: //down
                    if (can_be_visit_this_cell(initCell[0] + 2, initCell[1])){
                        validCell[0] = initCell[0] + 2;
                        validCell[1] = initCell[1];                        
                        validCell[2] = direction;
                        cell_is_selected = true;
                    }
                    break;
                case 3: //right
                    if (can_be_visit_this_cell(initCell[0], initCell[1] + 2)){
                        validCell[0] = initCell[0];
                        validCell[1] = initCell[1] + 2;                        
                        validCell[2] = direction;
                        cell_is_selected = true;
                    }
                    break;                    
                case 4:  //left
                    if (can_be_visit_this_cell(initCell[0], initCell[1] - 2)){
                        validCell[0] = initCell[0];
                        validCell[1] = initCell[1] - 2;                        
                        validCell[2] = direction;
                    }
                    break;                    
                default:
                    break;
            }
            direction_fetcher++;
        }
        return validCell;
    }

    public void showMaze(){
        for(int row = 0; row < getHeight(); row++){
            System.out.println("");
            for(int col = 0; col < getWidth(); col++)
                maze[row][col].showCell();
        }
    }
    
    public void destroy_wall_and_continue_path(int[] newValidCell){
        int row = newValidCell[0];
        int col = newValidCell[1];
        int direction = newValidCell[2];
        switch (direction){
            case 1: //up
                maze[row + 1][col].setPath();
                break;
            case 2: //down
                maze[row - 1][col].setPath();
                break;
            case 3: //right
                maze[row][col - 1].setPath();
                break;
            case 4:  //left
                maze[row][col + 1].setPath();
                break;
            default:
                break;
        }
    }
    
    public boolean can_be_visit_this_cell(int row, int col){
        boolean result = true;
        if (these_coordinates_are_in_maze(row, col)) 
            return !maze[row][col].is_visited() && maze[row][col].is_path();
        else
            return false;
    }
    public boolean current_cell_has_unvisited_neighbors(int [] initCell){
        boolean result = true, value = true;
        int row, col;
        row = initCell[0];
        col = initCell[1];
        result = can_be_visit_this_cell(row-2,col) || can_be_visit_this_cell(row+2,col) 
                || can_be_visit_this_cell(row,col+2) || can_be_visit_this_cell(row, col-2);
        return result;
    }
    public void set_cell_as_a_visited(int [] validCell){
        maze[validCell[0]][validCell[1]].setVisited();
    }
    public void set_start_maze(){
        int start_row ,start_col;
        Cell current_cell;
        while(true){
            start_row = Maze.randomNumberGenerator(height);
            start_col= Maze.randomNumberGenerator(width);
            current_cell = maze[start_row][start_col];
            if (current_cell.is_path() && !current_cell.is_start() && !current_cell.is_end()){
                current_cell.set_start();
                setStart_row(start_row);
                setStart_col(start_col);
                break;
            }
        }
    }
    public void set_end_maze(){
        int end_row, end_col;
        Cell current_cell;
        while(true){
            end_row = Maze.randomNumberGenerator(height);
            end_col= Maze.randomNumberGenerator(width);
            current_cell = maze[end_row][end_col];
            if (current_cell.is_path() && !current_cell.is_start() && !current_cell.is_end()){
                current_cell.set_end();
                setEnd_row(end_row);
                setEnd_col(end_col);
                break;
            }
        }
    }
    
    public static Maze generateMaze(){
        int size = Maze.randomNumberGenerator(15);
        //int size = 3; //test
        int initCell[] = getRandomCellInMaze(), validCell[];
        //initCell[0] row   initCell[1] col
        //creating a maze - encapsulation
        Maze current_maze = new Maze(size);
        Stack stackCells = new Stack();
        stackCells.push(initCell);
        while(!stackCells.isEmpty()){
            initCell = (int []) stackCells.pop();
            //directions (up 1, down 2, right 3, left 4)
            if (current_maze.current_cell_has_unvisited_neighbors(initCell)){
                //choosing a random unvisited neighbors
                validCell = current_maze.getUnvisitedCell(initCell);
                //complete the path
                current_maze.destroy_wall_and_continue_path(validCell);
                //mark neighbor as a visited
                current_maze.set_cell_as_a_visited(validCell);
                //push current cell on stack
                stackCells.push(initCell);
                //push neighbor cell on stack
                stackCells.push(validCell);
            }
        }
        current_maze.set_start_maze();
        current_maze.set_end_maze();;
        return current_maze;
    }

    public int getStart_row() {
        return start_row;
    }

    public void setStart_row(int start_row) {
        this.start_row = start_row;
    }

    public int getStart_col() {
        return start_col;
    }

    public void setStart_col(int start_col) {
        this.start_col = start_col;
    }

    public int getEnd_row() {
        return end_row;
    }

    public void setEnd_row(int end_row) {
        this.end_row = end_row;
    }

    public int getEnd_col() {
        return end_col;
    }

    public void setEnd_col(int end_col) {
        this.end_col = end_col;
    }
    
    public boolean is_path(int row, int col){
        return true;
    }
    public boolean is_wall(int row, int col){
        return true;
    }
    public boolean has_monster(int row, int col){
        return true;
    }
    public boolean has_potion(int row, int col){
        return true;
    }        
    public boolean is_free(int row, int col){
        return true;
    }
    public boolean is_next(int row, int col){
        return true;
    }
    public boolean is_prev(int row, int col){
        return true;
    }
}
