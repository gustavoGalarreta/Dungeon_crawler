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
import java.awt.Toolkit; 

public class Game {

    private Maze current_maze;
    private int amount_mazes;
    private ArrayList<Maze> mazes = new ArrayList<Maze>();
    private int current_maze_in_mazes;
    private Avatar avatar;
    private Drawer drawer = new Drawer(10, 10);

    public Game(){
        
    }
   
    public void game_info() {
        System.out.println("-------------------------------------------------");
        System.out.println("Posicion Avatar: [" + avatar.getRow() + " ; " + avatar.getCol() + "]");

        System.out.println("Inicio: [" + current_maze.getStart_row() + " ; " + current_maze.getStart_col() + "]");
        System.out.println("Final: [" + current_maze.getEnd_row() + " ; " + current_maze.getEnd_col() + "]");

        System.out.println("-------------------------------------------------");
        System.out.println("Enter the command to use:");
        System.out.println("up    -->  w");
        System.out.println("down  -->  s");
        System.out.println("right -->  d");
        System.out.println("left  -->  a");
        System.out.println("-------------------------------------------------");
    }

    public void game_init() {
        int movement_arrows, state, move;
        String action, movement_keyboard, straux;
        String interact_with_artefact;
        this.init();
        Scanner sc = new Scanner(System.in);
        Scanner scint = new Scanner(System.in);        
        while (true) {
            game_info(); //print the movements and indications            
            movement_keyboard = sc.nextLine();
            move = 0;
            if (movement_keyboard.equalsIgnoreCase("w")) {
                move = 1;
            }
            if (movement_keyboard.equalsIgnoreCase("s")) {
                move = 2;
            }
            if (movement_keyboard.equalsIgnoreCase("d")) {
                move = 3;
            }
            if (movement_keyboard.equalsIgnoreCase("a")) {
                move = 4;
            }
            if (movement_keyboard.equalsIgnoreCase("salir"))
                break;
            if ((5 - move) == 5) {
                drawer.drawView(current_maze, avatar.getCol(), avatar.getRow());
                System.out.println(">>>>>>>invalid move");
            } else {
                state = play(move);
                //drawer.drawView(current_maze, avatar.getCol(), avatar.getRow());
                if (state == 0){
                    //clean
                    System.out.println("You Lose");
                    break;
                } else //Runtime.getRuntime().exec("clear");
                {
//                    if (current_maze.is_prev(avatar.getRow(), avatar.getCol())) {
                    //si no acabamos de llegar de otro maze, sino que estamos en el ultimo next-> ganaste
                    if (state!=10 && state!=-10 && current_maze.is_next(avatar.getRow(), avatar.getCol()) ) {
                        drawer.clear();
                        Toolkit.getDefaultToolkit().beep();
                        System.out.println("------------------------------------------------------");
                        System.out.println("------------------------------------------------------");
                        System.out.println("----------------      You Win       ------------------");
                        System.out.println("------------------------------------------------------");
                        System.out.println("------------------------------------------------------");
                        drawer.clear();
                        break;
                    } else {
                        System.out.print("\u001b[2J");
                        System.out.flush();
                        if (state != -1) {
                            drawer.drawView(current_maze, avatar.getCol(), avatar.getRow());
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
                                interact_with_artefact = scint.nextLine();
                                if(!(interact_with_artefact.equals("1")) && !(interact_with_artefact.equals("0"))){
                                    while(true){ //verifica que no pongan lo que sea
                                        System.out.println("Invalido");
                                        System.out.println("Type:    [1]YES       or     [0]NO");
                                        interact_with_artefact = scint.nextLine();
                                        if((interact_with_artefact.equals("1")) || (interact_with_artefact.equals("0"))) 
                                            break;
                                    }
                                }
                                if (interact_with_artefact.equals("1")) {
                                    //linea de abajo estaa maaaaaal
                                    
                                    //avatar.new_artefact(current_maze.getMaze()[avatar.getRow()][avatar.getCol()].getArtefact());
                                    avatar.new_artefact(current_maze.getMaze()[avatar.getRow()][avatar.getCol()].getArtefact());
                                    //delete artefact
                                    current_maze.deleteArtefacts(avatar.getRow(), avatar.getCol());
                                    System.out.println("Artefact added to bag successfully!");
                                    System.out.println("Press Enter to Continue ...");
                                    straux=sc.nextLine();
                                }
                                System.out.print("\u001b[2J");
                                System.out.flush();
                                drawer.drawView(current_maze, avatar.getCol(), avatar.getRow());
                            }
                        } else {
                            //current_maze.showMaze();
                            //System.out.println("");
                            //drawer.clear();
                            drawer.drawView(current_maze, avatar.getCol(), avatar.getRow());
                            System.out.println(">>>>Is a wall. Choose another move");
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
                return 10; //flag significaa: se cambio al siguiente maze
            } else {
                //si no puede cambiar de maze, es el ultimo->>> mover (no tiene mucho sentido lo que sigue al regresar)
                avatar.move(new_row, new_col);
                return 1;
            }
        } else if (current_maze.is_prev(new_row, new_col)) {
            //Todo
            if (current_maze_in_mazes != 0) { //si no es el primero -> load previous maze
                //load monsters
                setCurrent_maze_in_mazes(current_maze_in_mazes - 1);
                current_maze = mazes.get(current_maze_in_mazes); //el avatar aparece en el fin del maze previo.
                avatar_row = current_maze.getEnd_row();
                avatar_col = current_maze.getEnd_col();
                avatar.move(avatar_row, avatar_col);
                return -10;//flag significa: se cambio al previo
            } else {
                //moverlo a la posicion y no cambiar de maze
                avatar.move(new_row, new_col);
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
        //choose one random maze, maybe the first <<<<???? 
        //int random_index = 1;   //we can call here a randomize function 
        //siempre mostrar desde el primero
        int random_index = 0;  
        
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
    
    public void game_intro(){
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
 
        System.out.println("-----------------------------DUNGEON CRAWLER----------------------------");
 
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        System.out.println("Press Enter to Continue ...");
        String straux = sc.nextLine();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
                                                                                                                                                   
        System.out.println("Hill giants are stealing all the grain and livestock they can while stone\ngiants have been scouring settlements that have been around forever.       \n "
                        + "Fire giants are press-ganging the smallfolk into the desert, while frost  \ngiant longships have been pillaging along the Sword Coast. Even the elusive\n "
                        + "cloud giants have been witnessed, their wondrous floating cities appearing\n above Waterdeep and Baldur’s Gate. Where is the storm giant King Hekaton,  \n "
                        + "who is tasked with keeping order among the giants?\n");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Press Enter to Continue ...");
        String straux2 = sc.nextLine();
        System.out.print("\u001b[2J");
        System.out.flush();
       
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("The humans, dwarves, elves, and other small folk of the Sword    \nCoast will be crushed underfoot from the onslaught of these giant foes.     \n "
                        + "The only chance at survival is for the small folk to work together\nto investigate this invasion and harness the power of rune magic, the giants’\n"
                        + "weapon against their ancient enemy the dragons. The only way the  \npeople of Faerun can restore order is to use the giants’ own power against them.");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Press Enter to Continue ...");
        String straux3 = sc.nextLine();
        System.out.print("\u001b[2J");
        System.out.flush();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("                          LET'S START PLAYING...                 ");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        String straux4 = sc.nextLine();
        System.out.print("\u001b[2J");
        System.out.flush();
    }
}
