/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Avatar;
import Model.Drawer;
import Model.Enemy;
import Model.Maze;
import Model.Maze_manager;
import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.IOException;
import java.util.List;

public class Game {

    private Maze current_maze;
    private int amount_mazes;
    private ArrayList<Maze> mazes = new ArrayList<Maze>();
    private int current_maze_in_mazes;
    private Avatar avatar;
    private Drawer drawer = new Drawer(11, 11);

    public Game(){
        
    }
    public void game_init(){
        int movement_arrows, state, move;
        String action, movement_keyboard, straux;
        int interact_with_artefact;
        this.init();
        Scanner sc = new Scanner(System.in);
        Scanner scint = new Scanner(System.in);
        while (true) {
            System.out.println("");
            System.out.print("Fila del avatar: ");
            System.out.println(avatar.getRow());
            System.out.print("Columna del avatar: ");
            System.out.println(avatar.getCol());

            System.out.print("Inicio: ");
            System.out.print(current_maze.getStart_row());
            System.out.print("   ");
            System.out.print(current_maze.getStart_col());
            System.out.print("    Final: ");
            System.out.print(current_maze.getEnd_row());
            System.out.print("   ");
            System.out.println(current_maze.getEnd_col());

            System.out.println("Enter the command to use");
            System.out.println("up    -->  w");
            System.out.println("down  -->  s");
            System.out.println("right -->  d");
            System.out.println("left  -->  a");
            movement_keyboard = sc.nextLine();
            move = 0;
            if (movement_keyboard.equals("w")) {
                move = 1;
            }
            if (movement_keyboard.equals("s")) {
                move = 2;
            }
            if (movement_keyboard.equals("d")) {
                move = 3;
            }
            if (movement_keyboard.equals("a")) {
                move = 4;
            }
            if ((5 - move) == 5) {
                System.out.println("invalid move");
            } else {
                state = play(move);
                drawer.drawView(current_maze, avatar.getCol(), avatar.getRow());
                if (state == 0){
                    //clean
                    System.out.println("You Lose");
                    break;
                } else //Runtime.getRuntime().exec("clear");
                {
                    if (current_maze.is_prev(avatar.getRow(), avatar.getCol())) {
                        System.out.println("You Win");
                        break;
                    } else {
                        System.out.print("\u001b[2J");
                        System.out.flush();
                        if (state != -1) {
                            current_maze.showMaze();
                            //if there is an enemy variable = 0 if there is an artefact =1
                            int which_element = current_maze.who_in_cell(avatar.getRow(), avatar.getCol());
                            System.out.println(current_maze.getMaze()[avatar.getRow()][avatar.getCol()].isEnemy_exits());
                            if (which_element == 0) {
                                //fight(); //if avatar encounters a enemy it gotta fight
                                System.out.println("FIGHT!");
                                
                                //Falta completar la pelea
                                
                                
                            } else if (which_element == 1) {
                                System.out.print("\u001b[2J");
                                System.out.flush();
                                System.out.println("Do you want to get the artefact?");
                                System.out.println("");
                                System.out.println("Type:    [1]YES       or     [0]NO");
                                interact_with_artefact = scint.nextInt();
                                if (interact_with_artefact == 1) {
                                    //linea de abajo estaa maaaaaal
                                    
                                    //avatar.new_artefact(current_maze.getMaze()[avatar.getRow()][avatar.getCol()].getArtefact());
                                    System.out.println("Artefact added to bag successfully!");
                                    System.out.println("Press Enter to Continue ...");
                                    straux=sc.nextLine();

                                }
                                System.out.print("\u001b[2J");
                                System.out.flush();
                                current_maze.showMaze();
                            }
                        } else {
                            current_maze.showMaze();
                            System.out.println("");
                            System.out.println("Is a wall. Choose another move");

                        }

                    }
                }
            }
        }
    }

    public int play(int movement) {
        int avatar_row = this.avatar.getRow();
        int avatar_col = this.avatar.getCol();
        int new_row = 0, new_col = 0;
        if (movement == 1) {
            new_row = avatar_row - 1;
            new_col = avatar_col;
        }
        if (movement == 2) {
            new_row = avatar_row + 1;
            new_col = avatar_col;
        }
        if (movement == 3) {
            new_col = avatar_col + 1;
            new_row = avatar_row;
        }
        if (movement == 4) {
            new_col = avatar_col - 1;
            new_row = avatar_row;
        }

        if (current_maze.is_free(new_row, new_col)) {
            avatar.move(new_row, new_col);
            return 2;
        } else if (current_maze.is_next(new_row, new_col)) {
            if ((current_maze_in_mazes + 1) != amount_mazes) {
                //load monsters
                setCurrent_maze_in_mazes(current_maze_in_mazes + 1);
                current_maze = mazes.get(current_maze_in_mazes);
                avatar_row = current_maze.getStart_row();
                avatar_col = current_maze.getStart_col();
                avatar.move(avatar_row, avatar_col);
            } else {
                return 1;
            }
        } else if (current_maze.is_prev(new_row, new_col)) {
            //Todo
            if ((current_maze_in_mazes - 1) != 0) {
                //load monsters
                setCurrent_maze_in_mazes(current_maze_in_mazes + 1);
                current_maze = mazes.get(current_maze_in_mazes);
                avatar_row = current_maze.getEnd_row();
                avatar_col = current_maze.getEnd_col();
                avatar.move(avatar_row, avatar_col);
            } else {
                System.out.println("It's imposible to go back");
            }
        } else {
            System.out.println("Is a wall");
            return -1;
        }
        return 2;
    }

    public void init() {
        int amount = 3, maze_fetcher = 0, avatar_row, avatar_col;
        int health_points = 0;
        String name = "";
        setAmount_mazes(amount);
        while(maze_fetcher <= amount_mazes){
            Maze new_maze;
            //int size = Maze.randomNumberGenerator(15);
            int size =7;
            Maze_manager maze_manager = new Maze_manager();
            new_maze = maze_manager.create_maze(size);
            mazes.add(new_maze);
            maze_fetcher++;
        }
        //choose one random maze, maybe the first
        int random_index = 1;   //we can call here a randomize function 
        current_maze = mazes.get(random_index);
        avatar_row = current_maze.getStart_row();
        avatar_col = current_maze.getStart_col();
        avatar = new Avatar(name, avatar_row, avatar_col, health_points);
        //load_monsters();
        //dibujador
        drawer.drawView(current_maze, avatar_col, avatar_row);
    }

    public void load_monsters() {
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

    public void set_current_maze(int current_maze_in_mazes) {
        current_maze = mazes.get(current_maze_in_mazes);
    }

    public Maze get_current_maze() {
        return this.current_maze;
    }
}
