/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Model.Avatar;
import Model.Maze;
import Model.Maze_manager;
import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.IOException;

public class Game {
    private Maze current_maze;
    private int amount_mazes;
    private ArrayList<Maze> mazes;
    private int current_maze_in_mazes;
    private  Avatar avatar;
    //private Dibujador dibujador
   
    public void Game(){
        int movement_arrows, state, move;
        String action, movement_keyboard;
        init();
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Enter the command to use");
            System.out.println("up    -->  w");
            System.out.println("down  -->  s");
            System.out.println("right -->  d");
            System.out.println("left  -->  a");
            movement_keyboard = sc.nextLine();
            move = 0;
            if (movement_keyboard.equals("w")) move = 1;
            if (movement_keyboard.equals("s")) move = 2;
            if (movement_keyboard.equals("d")) move = 3;
            if (movement_keyboard.equals("a")) move = 4;
            if ((5-move) == 5) System.out.println("invalid move");
            else {
                state = play(move);
                //dibujador
                if (state == 0){
                    //clean
                    System.out.println("You Lose");
                    break;
                }
                else{
                    //Runtime.getRuntime().exec("clear");
                    System.out.println("You Win");
                    break;
                }
            }
        }     
    }
    public int play(int movement){
        int avatar_row = avatar.getRow();
        int avatar_col = avatar.getCol();
        int new_row = 0, new_col = 0;
        if (movement == 1) new_row = avatar_row - 1;
        if (movement == 2) new_row = avatar_row + 1;
        if (movement == 3) new_col = avatar_col + 1;
        if (movement == 4) new_col = avatar_col - 1;
        if (current_maze.is_free(new_row, new_col)){
            avatar.move(new_row, new_col);
            return 2;
        }
        else
            if(current_maze.is_next(new_row, new_col)){
                if ((current_maze_in_mazes + 1) != amount_mazes){
                    //load monsters
                    setCurrent_maze_in_mazes(current_maze_in_mazes + 1);
                    current_maze = mazes.get(current_maze_in_mazes);
                    avatar_row = current_maze.getStart_row();
                    avatar_col = current_maze.getStart_col();
                    avatar.move(avatar_row, avatar_col);
                }
                else return 1;
            }
            else
                if (current_maze.is_prev(new_row, new_col)){
                    //Todo
                    if ((current_maze_in_mazes - 1) != 0){
                        //load monsters
                        setCurrent_maze_in_mazes(current_maze_in_mazes + 1);
                        current_maze = mazes.get(current_maze_in_mazes);
                        avatar_row = current_maze.getEnd_row();
                        avatar_col = current_maze.getEnd_col();
                        avatar.move(avatar_row, avatar_col);
                    }
                    else System.out.println("It's imposible to go back");
                }
                else{
                    System.out.println("Is a wall");
                }
        
        return 2;
    }

    public void init(){
        int amount = 3, maze_fetcher = 1, avatar_row, avatar_col;
        int health_points = 0;
        String name = "";
        setAmount_mazes(amount);
        Maze_manager maze_manager = new Maze_manager();
        while(maze_fetcher <= amount_mazes){
            Maze new_maze;
            new_maze = maze_manager.create_maze();
            mazes.add(new_maze);
            maze_fetcher++;
        }
        //choose one random maze, maybe the first
        Maze current_maze = mazes.get(1);
        avatar_row = current_maze.getStart_row();
        avatar_col = current_maze.getStart_col();
        Avatar avatar = new Avatar(name, avatar_row, avatar_col, health_points);
        //load_monsters();
        //dibujador
    }
    public void load_monsters(){
        //TBD
    }
    public int getAmount_mazes() {
        return amount_mazes;
    }
    public void setAmount_mazes(int amount_mazes) {
        this.amount_mazes = amount_mazes;
    }
    public int getCurrent_maze_in_mazes() {
        return current_maze_in_mazes;
    }
    public void setCurrent_maze_in_mazes(int current_maze_in_mazes) {
        this.current_maze_in_mazes = current_maze_in_mazes;
    }
    public void set_current_maze(int current_maze_in_mazes){
        current_maze = mazes.get(current_maze_in_mazes);
    }
    public Maze get_current_maze(){
        return this.current_maze;
    }
}
