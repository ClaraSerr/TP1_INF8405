package com.example.tp1inf8405;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
/*** This grid class links the gridlayout to the entire logic of the game
 *
 */
public class Grid {
    ArrayList<Bloc> blocs;
    int width;
    int height;
    int[][] grid;

    boolean game_started = false;
    boolean reseting = false ;
    ArrayList<Grid> states ; // list to hold the states of the game HEY we could track this if we want to know the number of moves
    ArrayList<Integer> win_zone ;
    public Grid(int width, int height) {
        blocs = new ArrayList<>();
        states = new ArrayList<>();
        win_zone = new ArrayList<>();
        this.width=width;
        this.height = height;
        this.grid = new int[width][height];
        win_zone.add(2) ;
        win_zone.add(6); //la zone de victoire est ligne 2 colonne 6
    }
    /*** Class used for deep copies
     *
     */
    public Grid(Grid grid){
        //Faut une deepcopy de la Array list... bon
        this.blocs = new ArrayList<>();
        for (int k=0; k<grid.blocs.size(); k++){
            this.blocs.add(new Bloc(grid.blocs.get(k)));
            Log.d("STATE_CREATION",grid.blocs.get(k).toString());
        }
        ; // I hope this is a deepcopy orr I might have prroblems
        this.width = grid.width;
        this.height = grid.height;
        this.grid = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.grid[i][j] = grid.grid[i][j];
            }
        }
        this.win_zone = new ArrayList<>(grid.win_zone);
    }
    /**
     * When the setup of the blocs inside the grid is completed this method is used to add the very first state
     * also it changes the "state" of the game
     */
    public void game_ready(){
        game_started = true;
        states.add(new Grid(this)); // adding the initial state
    }
    /**
     * Method used to update the state Array List and add the previous grids to the list
     * */
    public void updateState() {
        if ((game_started) && (!reseting)) {
            states.add(new Grid(this)); //used to add another state of the grid to the list
        }
    }
    /**
     * Method to add a bloc only to the grid
     * */
    public void addBlocToGrid(Bloc b){
        for (int i = b.row; i < b.row + b.row_span; i++) {
            for (int j = b.col; j < b.col + b.column_span; j++) {
                grid[i][j] = 1;
                //Log.d("grid",grid.toString());
            }
        }
    }
    /**
     * Method to remove a bloc only to the grid
     * */
    public void removeBlockFromGrid(Bloc b){
        for (int i = b.row; i < b.row + b.row_span; i++) {
            for (int j = b.col; j < b.col + b.column_span; j++) {
                grid[i][j] = 0;
            }
        }
    }
    /**
     * Method to complitely clear the collision grid of "1", used when loading a state.
     * For some reason removeBlockFromGrid couldn't do it
     * */
    public void clear_grid(){
        for (int i = 0; i< width;i++){
            for(int j = 0; j< height;j++){
                grid[i][j] = 0;
            }
        }
    }
    /**
     * Method used at the beginning to add blocs to the list of the grid. So the grid knows which blocs are in the game
     * */

    public void addBloc(Bloc b) {
        blocs.add(b);
        for (int i = b.row; i < b.row + b.row_span; i++) {
            for (int j = b.col; j < b.col + b.column_span; j++) {
                grid[i][j] = 1;
                //Log.d("grid",grid.toString());
            }
        }
    }

    public void removeBloc(Bloc b) {
        blocs.remove(b);
        for (int i = b.row; i < b.row + b.row_span; i++) {
            for (int j = b.col; j < b.col + b.column_span; j++) {
                grid[i][j] = 0;
            }
        }
    }
    /** Verify if said cell is occupied
     * */
    public boolean isOccupied(int row, int col) {
        return grid[row][col] == 1;
    }
    /** Check if it is possible to move to the left.
     **/
    public boolean canMoveLeft(Bloc b) {
        for (int i = b.row; i < b.row + b.row_span; i++) {
            if (b.col - 1 < 0 || isOccupied(i, b.col - 1)) {
                return false;
            }
        }
        return true;
    }
    /** Check if it is possible to move to the right.
     **/
    public boolean canMoveRight(Bloc b) {
        for (int i = b.row; i < b.row + b.row_span; i++) {
            if (b.col + b.column_span >= this.width || isOccupied(i, b.col + b.column_span)) {
                return false;
            }
        }
        return true;
    }
    /** Check if it is possible to move to the up.
     * */
    public boolean canMoveUp(Bloc b) {
        for (int j = b.col; j < b.col + b.column_span; j++) {
            if (b.row - 1 < 0 || isOccupied(b.row - 1, j)) {
                return false;
            }
        }
        return true;
    }
    /** Check if it is possible to move to the down.
     * */
    public boolean canMoveDown(Bloc b) {
        //Here we test occupency and boundaries differently because it is tricky. row_span 0 and 11 basically have the same effect so we have to be smart
        for (int j = b.col; j < b.col + b.column_span; j++) {
            if ((b.row + (b.row_span) >= (this.height -1)) || isOccupied(b.row + b.row_span, j)) {
                return false;
            }
        }


        return true;
    }
    /** Used to verify if the target is at the place of the win
     * */
    public boolean checkWin(Bloc b){
        if (b.isTarget) {
            if ((b.col + b.column_span - 1) == (this.width - 1)) {
                return true;
            }
        }
        return false;
    }
    /** Used to reload the game to a previous state using the states Array List
     * */
    public void reload(ArrayList<Bloc> b_list){
        //now we must replace our bloc list with this one and carefully remove and readd every bloc...
        this.reseting = true;
        int nb_bloc = b_list.size();
        clear_grid();
        /*(int k=0; k< nb_bloc; k++){
            this.removeBlockFromGrid(this.blocs.get(0)); // If you use k on a list that dynamically change size you will break everything
        }*/
        blocs.clear();
        for(int k=0; k< nb_bloc; k++){
            Log.d("LOGGING_blocs",Integer.toString(k));
            this.addBloc(b_list.get(k)); // Look, we add it to the colection AND to the grid, this might work
        }
        this.reseting = false;
    }

    @NonNull
    @Override
    public String toString(){
        String aString = "\n";
        for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid[row].length; col++) {
                aString += " " + grid[row][col];
            }
            aString += "\r\n";
        }
        return aString;
    }
    /** Used to moove a bloc that belongs to the grid up
     * */
    public void mooveUp(Bloc b){
        this.removeBlockFromGrid(b);
        b.decRow(); // Normalement par la magie de spointeurs d'ID la liste update automatiquement la position de l'object actuel. En priant pour que ce soit bien le même
        // Au pire il faut reconstruire la liste à chaque fois ( c'est usant)
        this.addBlocToGrid(b);
    }
    /** Used to moove a bloc that belongs to the grid down
     * */
    public void mooveDown(Bloc b){
        this.removeBlockFromGrid(b);
        b.incRow(); // Normalement par la magie de spointeurs d'ID la liste update automatiquement la position de l'object actuel. En priant pour que ce soit bien le même
        // Au pire il faut reconstruire la liste à chaque fois ( c'est usant)
        this.addBlocToGrid(b);
    }
    /** Used to moove a bloc that belongs to the grid left
     * */
    public void mooveLeft(Bloc b){
        this.removeBlockFromGrid(b);
        b.decCol(); // Normalement par la magie de spointeurs d'ID la liste update automatiquement la position de l'object actuel. En priant pour que ce soit bien le même
        // Au pire il faut reconstruire la liste à chaque fois ( c'est usant)
        this.addBlocToGrid(b);
    }
    /** Used to moove a bloc that belongs to the grid right
     * */
    public void mooveRight(Bloc b){
        this.removeBlockFromGrid(b);
        b.incCol(); // Normalement par la magie de spointeurs d'ID la liste update automatiquement la position de l'object actuel. En priant pour que ce soit bien le même
        // Au pire il faut reconstruire la liste à chaque fois ( c'est usant)
        this.addBlocToGrid(b);
    }


}