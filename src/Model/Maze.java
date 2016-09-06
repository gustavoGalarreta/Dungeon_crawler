/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.Random;
import java.util.*;
import Util.Utilitarios;
import java.util.concurrent.ThreadLocalRandom;
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
    private boolean already_visited;
    private ArrayList<Enemy> enemies= new ArrayList<Enemy>();
    
    protected Maze(int size){
        height = width = size * 2 + 1;
        maze = new Cell[width][width];
        int x =1;
        for(int row = 0; row < height; row++)
            for(int col = 0; col < width; col++){
                maze[row][col] = new Cell();
                maze[row][col].setWall();
            }
        for(int row = 0; row < height; row++)
            for(int col = 0; col < width; col++){
                if (col % 2 != 0 && row % 2 != 0 
                        && row != 0 && row != height -1 
                        && col != 0 && col != width -1)
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
        if ( (row >= 0 && row < height) && (col >= 0 && col < width) )
            return true;
        else
            return false;
    }
    public int[] getUnvisitedCell(int[] initCell){
        //Integer directions[] = Maze.randomDirection(4);
        int[] directions = {1, 2, 3, 4};
        Random rnd = ThreadLocalRandom.current();
        for (int i = directions.length - 1; i > 0; i--)
        {
          int index = rnd.nextInt(i + 1);
          int a = directions[index];
          directions[index] = directions[i];
          directions[i] = a;
        }
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
                getMaze()[row][col].showCell();
        }
    }
    
    public void destroy_wall_and_continue_path(int[] newValidCell){
        int row = newValidCell[0];
        int col = newValidCell[1];
        int direction = newValidCell[2];
        switch (direction){
            case 1: //up
                getMaze()[row + 1][col].setPath();
                break;
            case 2: //down
                getMaze()[row - 1][col].setPath();
                break;
            case 3: //right
                getMaze()[row][col - 1].setPath();
                break;
            case 4:  //left
                getMaze()[row][col + 1].setPath();
                break;
            default:
                break;
        }
    }
    
    public boolean can_be_visit_this_cell(int row, int col){
        boolean result = true;
        if (these_coordinates_are_in_maze(row, col)) 
            return !maze[row][col].is_visited() && getMaze()[row][col].is_path();
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
        getMaze()[validCell[0]][validCell[1]].setVisited();
    }
    public void set_start_maze(){
        int start_row ,start_col;
        Cell current_cell;
        while(true){
            start_row = Maze.randomNumberGenerator(height);
            start_col= Maze.randomNumberGenerator(width);
            current_cell = getMaze()[start_row][start_col];
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
            current_cell = getMaze()[end_row][end_col];
            if (current_cell.is_path() && !current_cell.is_start() && !current_cell.is_end()){
                current_cell.set_end();
                setEnd_row(end_row);
                setEnd_col(end_col);
                break;
            }
        }
    }
    
    public static Maze generateMaze(int size){
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
        current_maze.set_end_maze();
        current_maze.load_enemies();
        //current_maze.load_artefacts();
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
        return maze[row][col].is_path();
    }
    public boolean is_wall(int row, int col){
        return maze[row][col].is_wall();
    }
    public boolean has_monster(int row, int col){
        return maze[row][col].isEnemy_exits();
    }
    public boolean has_potion(int row, int col){
        return false;
    }        
    public boolean has_artefact(int row, int col){
        return maze[row][col].isArtefact_exists();
    }        
    public boolean is_free(int row, int col){
        boolean aux=is_path(row, col);
        System.out.println(aux);
        return is_path(row, col) && !is_next(row, col) && !is_prev(row, col) && !is_wall(row, col);
    }
    public boolean is_next(int row, int col){
        return maze[row][col].is_start();
    }
    public boolean is_prev(int row, int col){
        return maze[row][col].is_end();
    }

    /**
     * @return the maze
     */
    public Cell[][] getMaze() {
        return maze;
    }

    /**
     * @param maze the maze to set
     */
    public void setMaze(Cell[][] maze) {
        this.maze = maze;
    }
    
    
    public void load_enemies(){
        int MAXNUM_ENEMIES=5; //maximun number of enemies added in a maze , eg. 5
        int i=0;
        for(int row = 0; row < height; row++)
            for(int col = 0; col < width; col++){
                if( Utilitarios.getRandomBoolean() && !maze[row][col].is_wall() && !maze[row][col].is_end() && !maze[row][col].is_start() && i<=MAXNUM_ENEMIES){
                    Enemy enemy_to_be_added= new Enemy("Enemigo", row, col, Maze.randomNumberGenerator(700), Maze.randomNumberGenerator(500));   //cambiar estos datos
                    enemies.add(enemy_to_be_added);
                    maze[row][col].setEnemy_exits(true);
                    maze[row][col].setEnemy(enemy_to_be_added);
                    i++;
                }
                    
            }
    }
    
    public void load_artefacts(){
        int MAXNUM_ARTEFACTS=5; //maximun number of artefacts added in a maze , eg. 5
        int i=0;
        for(int row = 0; row < height; row++)
            for(int col = 0; col < width; col++){
                if( Utilitarios.getRandomBoolean() && !maze[row][col].is_wall() &&  i<=MAXNUM_ARTEFACTS && !maze[row][col].isEnemy_exits()){
                    Artefact artefact_to_be_added;
                    if(Utilitarios.getRandomBoolean()) {
                        artefact_to_be_added=new Armor(Maze.randomNumberGenerator(400));
                        maze[row][col].setArtefact_exists(true);
                        maze[row][col].setArtefact(artefact_to_be_added);
                        i++;
                    }
                    else{
                        if(Utilitarios.getRandomBoolean()) {
                            artefact_to_be_added=new Weapon(Maze.randomNumberGenerator(500));
                            maze[row][col].setArtefact_exists(true);
                            maze[row][col].setArtefact(artefact_to_be_added);
                            i++;
                        }
                        else {
                            artefact_to_be_added=new HealingPotion(Maze.randomNumberGenerator(100));
                            maze[row][col].setArtefact_exists(true);
                            maze[row][col].setArtefact(artefact_to_be_added);
                            i++;
                        }
                    }
                }
                    
            }
    }
    
    
    public int who_in_cell(int row, int col){
        if (maze[row][col].isArtefact_exists()) return 1;
        else if(maze[row][col].isEnemy_exits()) return 0;
        else return 2;
    }
    
}
