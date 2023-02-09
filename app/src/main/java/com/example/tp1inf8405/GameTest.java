package com.example.tp1inf8405;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GameTest extends AppCompatActivity {
    float xDown = 0, yDown = 0;

    public class Bloc{
        String name;
        int row;
        int col;
        int row_span;
        int column_span;
        View view;
        boolean isHorizontal; //0 if vertical, 1 if horizontal

        boolean isTarget;
        Bloc (int row, int col, int row_span, int col_span, View v, String name, boolean isTarget){
            this.row = row;
            this.col = col;
            this.row_span = row_span;
            this.column_span = col_span;
            this.view = v;
            this.name = name;
            this.isTarget = isTarget;
            if (row_span > col_span) {
                this.isHorizontal = false;
            }
            else{
                this.isHorizontal = true;
            }
        }
        //Constructeur pour la deepcopy
        Bloc(Bloc bloc){
            this.row = bloc.row;
            this.col = bloc.col;
            this.row_span = bloc.row_span;
            this.column_span = bloc.column_span;
            this.view = bloc.view;
            this.name = bloc.name;
            this.isTarget = bloc.isTarget;
            if (bloc.row_span > bloc.column_span) {
                this.isHorizontal = false;
            }
            else{
                this.isHorizontal = true;
            }
        }

        @NonNull
        @Override
        public String toString() {
            String res = "for car " + this.name + " \n ";
            res = res + "row = " + Integer.toString(row) + " \n ";
            res += "row_span = " + Integer.toString(row_span) + " \n ";
            res += "col = " + Integer.toString(col) + " \n ";
            res += "column_span = " + Integer.toString(column_span) + " \n ";
            res += "horizontal = " + Boolean.toString(isHorizontal) + " \n";
            return res;
            //return super.toString();
        }

        public void incCol(){
            int test =  0;
            if (this.column_span>1) {
                test = this.col + 1 + this.column_span;
            }else{
                test = this.col + 1;
            }
            if (test < 8 ){
                this.col = this.col +1;
            }
        }
        public void decCol(){
            if (((this.col - 1) > 0)){
                this.col = this.col -1;
            }
        }

        public void incRow(){
            this.row = this.row +1;
        }
        public void decRow(){
            if ((this.row - 1) > -1 ){
            this.row = this.row -1;}
        }


    }


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

        public void game_ready(){
            game_started = true;
            states.add(new Grid(this)); // adding the initial state
        }

        public void addBloc(Bloc b) {
            blocs.add(b);
            for (int i = b.row; i < b.row + b.row_span; i++) {
                for (int j = b.col; j < b.col + b.column_span; j++) {
                    grid[i][j] = 1;
                    //Log.d("grid",grid.toString());
                    if ((game_started) && (!reseting)){
                        states.add(new Grid(this)); //used to add another state of the grid to the list
                    }
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

        public boolean isOccupied(int row, int col) {
            return grid[row][col] == 1;
        }

        public boolean canMoveLeft(Bloc b) {
            for (int i = b.row; i < b.row + b.row_span; i++) {
                if (b.col - 1 < 0 || isOccupied(i, b.col - 1)) {
                    return false;
                }
            }
            return true;
        }

        public boolean canMoveRight(Bloc b) {
            for (int i = b.row; i < b.row + b.row_span; i++) {
                if (b.col + b.column_span >= this.width || isOccupied(i, b.col + b.column_span)) {
                    return false;
                }
            }
            return true;
        }

        public boolean canMoveUp(Bloc b) {
            for (int j = b.col; j < b.col + b.column_span; j++) {
                if (b.row - 1 < 0 || isOccupied(b.row - 1, j)) {
                    return false;
                }
            }
            return true;
        }

        public boolean canMoveDown(Bloc b) {
            //Here we test occupency and boundaries differently because it is tricky. row_span 0 and 11 basically have the same effect so we have to be smart
            for (int j = b.col; j < b.col + b.column_span; j++) {
                if ((b.row + (b.row_span) >= (this.height -1)) || isOccupied(b.row + b.row_span, j)) {
                    return false;
                }
            }


            return true;
        }

        public boolean checkWin(Bloc b){
            if (b.isTarget) {
                if ((b.col + b.column_span - 1) == (this.width - 1)) {
                    return true;
                }
            }
            return false;
        }

        public void reload(ArrayList<Bloc> b_list){
            //now we must replace our bloc list with this one and carefully remove and readd every bloc...
            this.reseting = true;
            int nb_bloc = b_list.size()-1;
            for(int k=0; k< nb_bloc; k++){
                Log.d("LOGGING_blocs",Integer.toString(k));
                this.removeBloc(this.blocs.get(0)); // If you use k on a list that dynamically change size you will break everything
            }
            for(int k=0; k< nb_bloc; k++){
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

    }
    /*This function loads the initial state of a Game using what is stored in the Grid */
    protected void loadInitialState(Grid grid){
        //remember to like, empty the grid.states an truly reinitialize

        //getting the desired state
        Grid initialState = grid.states.get(0);
        ArrayList<Bloc> new_blocs = new ArrayList<>() ; // we will add this to the final grid and then litterally redraw everything as it is added
        //actually putting blocs back in there place we will use the fact that they are in the same order
        Log.d("Initial state",initialState.toString());
        int nb_bloc = grid.blocs.size();
        for(int k=0 ;k< nb_bloc; k++){
            //I mean, might as well load the entire initial state right ?
            Log.d("LOGGING_blocs",Integer.toString(k));
            Bloc b = grid.blocs.get(k);
            Log.d("Ancien_bloc",b.toString());
            Bloc init_b = initialState.blocs.get(k);
            Log.d("init_bloc",init_b.toString());
            b.col =  init_b.col;
            b.row = init_b.row;
            new_blocs.add(b);
            Log.d("Nouveau_blocs",b.toString());
        }
        //Here you call the function we will create. Its grid.reload.
        grid.reload(new_blocs);
        //Now that the virtual grid is reloaded, you need to update the ACTUAL view, goodluck with that my G
        //Maybe begin by browsing the blocs within ? They should be linked to the same views soits just a matter of updating the views within the blocs... I think
        for(int k=0 ;k< nb_bloc; k++){
            //I mean, might as well load the entire initial state right ?
            Bloc b = grid.blocs.get(k);
            View v = b.view;
            GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) v.getLayoutParams();
            //okay now modify the view thingy with the actual parameters of your bloc
            layoutParams.columnSpec = GridLayout.spec(b.col,b.column_span);
            layoutParams.rowSpec =  GridLayout.spec(b.row,b.row_span);
            b.view.setLayoutParams(layoutParams);
            //AYO THIS MIGHT DO IT
        }



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);
        ImageView bloc = findViewById(R.id.bloc);

        View target = findViewById(R.id.target);
        Bloc target_bloc = new Bloc(2,1,1,2, target, "target",true);

        View car1 = findViewById(R.id.car1);
        Bloc car1_bloc = new Bloc(0,1,1,3, car1, "car1",false);

        View car2 = findViewById(R.id.car2);
        Bloc car2_bloc = new Bloc(1,3,3,1, car2,"car2",false);

        View car3 = findViewById(R.id.car3);
        Bloc car3_bloc = new Bloc(3,1,2,1, car3,"car3",false);

        View car4 = findViewById(R.id.car4);
        Bloc car4_bloc = new Bloc(5,1,1,3, car4,"car4",false);

        View car5 = findViewById(R.id.car5);
        Bloc car5_bloc = new Bloc(4,5,2,1, car5,"car5",false);

        View car6 = findViewById(R.id.car6);
        Bloc car6_bloc = new Bloc(3,5,1,2, car6,"car6",false);

        View car7 = findViewById(R.id.car7);
        Bloc car7_bloc = new Bloc(0,6,3,1,car7,"car7",false);

        Grid game = new Grid(7,7);
        game.addBloc(target_bloc);
        game.addBloc(car1_bloc);
        game.addBloc(car2_bloc);
        game.addBloc(car3_bloc);
        game.addBloc(car4_bloc);
        game.addBloc(car5_bloc);
        game.addBloc(car6_bloc);
        game.addBloc(car7_bloc);
        game.game_ready();
        Log.d("grid",game.toString());
        Log.d("car1",car1_bloc.toString());
        Log.d("car7",car7_bloc.toString());
        ArrayList<Bloc> aList = game.blocs;

        Button res_button = (Button) findViewById(R.id.reset);
        res_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("RESET_START","you are trying to reset");
                loadInitialState(game);
                Log.d("RESET_END","you reseted I hope");
                // Do something in response to button click
            }
        });


        for (Bloc x : aList) {
            x.view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            xDown = event.getX(); // need to update this when you moove to correct jittering effect
                            yDown = event.getY();
                            Log.d("DOWN","Car1 was touched");
                            Log.d("grid",game.toString());
                            break;

                        case MotionEvent.ACTION_MOVE:
                            float movedX, movedY;
                            boolean wasmoved = false;
                            movedX = event.getX();
                            movedY = event.getY();
                            // calculate how much the user moviedhis finger
                            float distanceX = movedX - xDown;
                            float distanceY = movedY - yDown;

                            //now updated position
                            Resources resources = v.getContext().getResources();
                            float dpDeltaX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, distanceX, resources.getDisplayMetrics());
                            float dpDeltaY = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, distanceY, resources.getDisplayMetrics());
                            GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) v.getLayoutParams();
                            Log.d("MOVE","CAR1 was moved");
                            Log.d("PARAM",v.getLayoutParams().toString());
                            Log.d("column",layoutParams.columnSpec.toString());
                            if (dpDeltaX > 100) {
                                if ((game.canMoveRight(x)) && (x.isHorizontal)){
                                    game.removeBloc(x); //SEIGNEUR, lorsque tu add et que tu remove le bloc, tu CHANGE sa posiution dans la grille
                                    x.incCol(); // d'ou l'importance d'avoir une fonction move bloc
                                    game.addBloc(x); //Et si on découplais ? on fait une fonction add et remove qui va juste pas affecter la liste initial quoi.
                                    wasmoved = true;
                                }
                            }
                            if (dpDeltaX < -100) {
                                if ((game.canMoveLeft(x)) && (x.isHorizontal)){
                                    game.removeBloc(x);
                                    x.decCol();
                                    game.addBloc(x);
                                    wasmoved = true;
                                }
                            }
                            if (dpDeltaY < -100) {
                                Log.d("UP","mooving up");
                                if ((game.canMoveUp(x)) && (!x.isHorizontal)){
                                    game.removeBloc(x);
                                    x.decRow();
                                    game.addBloc(x);
                                    Log.d("UP","nice you moved Up");
                                        wasmoved = true;
                                }
                            //car1_bloc.decRow();
                            wasmoved = true;
                            }
                            if (dpDeltaY > 100) {
                                Log.d("down","mooving down");
                                if ((game.canMoveDown(x)) && (!x.isHorizontal)){
                                    game.removeBloc(x);
                                    x.incRow();
                                    game.addBloc(x);
                                    wasmoved = true;
                                    Log.d("down","nice you moved down");
                                }
                            //car1_bloc.incRow();
                            wasmoved = true;
                        }
                            layoutParams.columnSpec = GridLayout.spec(x.col,x.column_span);
                            layoutParams.rowSpec =  GridLayout.spec(x.row,x.row_span);
                            Log.d("Result",x.toString());
                            if (wasmoved = true){
                                x.view.setLayoutParams(layoutParams);
                                Log.d("grid",game.toString());
                                wasmoved = false;
                            }
                            //car1_bloc.view.setLayoutParams(layoutParams);
                            break;

                        case MotionEvent.ACTION_UP:
                            //Update le state uniquement quand on lache la pièce ! Faut le faire ici

                            if (game.checkWin(x)){
                                //Do a pop up notification or something
                            }
                            break;

                    }

                    return true;
                }
            });
        }

        bloc.setOnTouchListener(new View.OnTouchListener(){


            public boolean onTouch(View v, MotionEvent event){

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float movedX, movedY;
                        movedX = event.getX();
                        movedY = event.getY();
                        // calculate how much the user moviedhis finger
                        float distanceX = movedX - xDown;
                        float distanceY = movedY - yDown;

                        //now updated position
                        bloc.setX(bloc.getX()+distanceX);
                        bloc.setY(bloc.getY()+distanceY);


                        break;
                }
                return true;
            }
        });


    }
}