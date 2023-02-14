package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameTest extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Highscores";
    int puzzle_number;
    float xDown = 0, yDown = 0;
    int total_moves =0;
    View target;
    Bloc target_bloc = new Bloc(2,1,1,2, target, "target",true);
    Bloc target_bloc2 = new Bloc(2,1,1,2, target, "target",true);

    View car1 ;
    Bloc car1_bloc = new Bloc(0,1,1,3, car1, "car1",false);
    Bloc car1_bloc2 = new Bloc(1,3,2,1, car1, "car1_puzzle2",false);


    View car2 ;
    Bloc car2_bloc = new Bloc(1,3,3,1, car2,"car2",false);
    Bloc car2_bloc2 = new Bloc(1,4,3,1, car2, "car2_puzzle2",false);

    View car3 ;
    Bloc car3_bloc = new Bloc(3,1,2,1, car3,"car3",false);
    Bloc car3_bloc2 = new Bloc(1,5,3,1, car3, "car3_puzzle2",false);

    View car4 ;
    Bloc car4_bloc = new Bloc(5,1,1,3, car4,"car4",false);
    Bloc car4_bloc2 = new Bloc(3,1,1,2, car4, "car4_puzzle2",false);
    View car5 ;
    Bloc car5_bloc = new Bloc(4,5,2,1, car5,"car5",false);
    Bloc car5_bloc2 = new Bloc(3,3,2,1, car5, "car5_puzzle2",false);

    View car6 ;
    Bloc car6_bloc = new Bloc(3,5,1,2, car6,"car6",false);
    Bloc car6_bloc2 = new Bloc(4,2,2,1, car6, "car6_puzzle2",false);

    View car7 ;
    Bloc car7_bloc = new Bloc(0,6,3,1,car7,"car7",false);
    Bloc car7_bloc2 = new Bloc(5,3,1,2, car7, "car7_puzzle2",false);

    Grid game1 = new Grid(7,7);
    Grid game2 = new Grid(7,7);
    Grid game3 = new Grid(7,7);
    Grid game = game1; // Normalement il suffit juste de changer vers quel grille game pointe. C'est pas une deep copy mais deux variables qui pointent aux même endroit pour l'instant


    public Map<String, Integer> MaxHighscores = new HashMap<String, Integer>() {{
        put("1", 15);
        put("2", 17);
        put("3", 15);
    }};

    public void updateHighScore(String puzzleNumber) {
        TextView v = findViewById(R.id.record3);
        String score = sharedpreferences.getString (puzzleNumber, "--");
        v.setText(score + "/" +  MaxHighscores.get(puzzleNumber));
    };

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    public void back_menu(View v){
        //test from Studio 3
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void update_mooves(){
        TextView v = findViewById(R.id.nb_moves);
        v.setText(Integer.toString(total_moves));
    }
    public void reset_mooves() {
        total_moves = 0;
        TextView v = findViewById(R.id.nb_moves);
        v.setText(Integer.toString(total_moves));
    }
        public void createNewVictoryDialog(Grid game){
        dialogBuilder = new AlertDialog.Builder(this);
        final View victoryPopupView = getLayoutInflater().inflate(R.layout.activity_pop_up_victory, null);
        dialogBuilder.setView(victoryPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.victory);
        mediaPlayer.start();
        final Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        ValueAnimator anim = ValueAnimator.ofFloat(1f, 0f);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();
                window.setDimAmount(1 - fraction);
            }
        });
        anim.setDuration(3000);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dialog.dismiss();
                loadInitialState(game);
            }
        });
        anim.start();
    }

    public void setResetButtonState(boolean bool){
        Button reset = (Button) findViewById(R.id.reset);
        reset.setEnabled(bool);
    }

    public void setCancelButtonState(boolean bool){
        Button reset = (Button) findViewById(R.id.cancel_move);
        reset.setEnabled(bool);
    }

    /*This function loads the initial state of a Game using what is stored in the Grid */
    public void loadInitialState(Grid grid){
        //remember to like, empty the grid.states an truly reinitialize
        reset_mooves();
        //ICI FAUT CLEAN LA GRID YA UN SOUCI DE COLLISION

        //getting the desired state
        Grid initialState = grid.states.get(0);

        //réinitialiser le boutton reset et le score
        setResetButtonState(false);
        setCancelButtonState(false);
        total_moves = 0;
        update_mooves();

        //On met à jour le higher score avec ce qui est stocké dans les préférences
        // 1 est hardcoded parce qu'on a pas encore codé les autres puzzles
        updateHighScore("1");

        ArrayList<Bloc> new_blocs = new ArrayList<>() ; // we will add this to the final grid and then litterally redraw everything as it is added
        //actually putting blocs back in there place we will use the fact that they are in the same order
        Log.d("Initial state",initialState.toString());
        int nb_bloc = grid.blocs.size();
        for(int k=0 ;k< nb_bloc; k++){
            //I mean, might as well load the entire initial state right ?

            Bloc b = grid.blocs.get(k);
            Log.d("LOGGING_blocs" + b.name,Integer.toString(k));
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
            GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) b.view.getLayoutParams();
            //okay now modify the view thingy with the actual parameters of your bloc
            layoutParams.columnSpec = GridLayout.spec(b.col,b.column_span);
            layoutParams.rowSpec =  GridLayout.spec(b.row,b.row_span);
            b.view.setLayoutParams(layoutParams);
            b.update_original_pos();
            //AYO THIS MIGHT DO IT
        }
    }

    public void loadPreviousState(Grid grid){
        int size = grid.states.size();
        if (size < 2) {
            //Je sais que le bouton doit être grisé si on a pas bougé donc bon peut être que c'est ici je me dis ?
        }
        else{
            //remember to like, empty the grid.states an truly reinitialize
            total_moves -= 1;
            update_mooves();
            if (total_moves==0){
                setResetButtonState(false);
                setCancelButtonState(false);
            }
            //ICI FAUT CLEAN LA GRID YA UN SOUCI DE COLLISION

            //getting the desired state

            Grid initialState = grid.states.get(size-2); //on va à l'état -1 puis on supprimer l'état actuel de la liste des states


            ArrayList<Bloc> new_blocs = new ArrayList<>() ; // we will add this to the final grid and then litterally redraw everything as it is added
            //actually putting blocs back in there place we will use the fact that they are in the same order
            Log.d("Initial state",initialState.toString());
            int nb_bloc = grid.blocs.size();
            for(int k=0 ;k< nb_bloc; k++){
                //I mean, might as well load the entire initial state right ?

                Bloc b = grid.blocs.get(k);
                Log.d("LOGGING_blocs" + b.name,Integer.toString(k));
                Log.d("Ancien_bloc",b.toString());
                Bloc init_b = initialState.blocs.get(k);
                Log.d("init_bloc",init_b.toString());
                b.col =  init_b.col;
                b.row = init_b.row;
                new_blocs.add(b);
                Log.d("Nouveau_blocs",b.toString());
            }
            //Here you call the function we will create. Its grid.reload.
            grid.loadPrevious(new_blocs);
            //Now that the virtual grid is reloaded, you need to update the ACTUAL view, goodluck with that my G
            //Maybe begin by browsing the blocs within ? They should be linked to the same views soits just a matter of updating the views within the blocs... I think
            for(int k=0 ;k< nb_bloc; k++){
                //I mean, might as well load the entire initial state right ?
                Bloc b = grid.blocs.get(k);
                GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) b.view.getLayoutParams();
                //okay now modify the view thingy with the actual parameters of your bloc
                layoutParams.columnSpec = GridLayout.spec(b.col,b.column_span);
                layoutParams.rowSpec =  GridLayout.spec(b.row,b.row_span);
                b.view.setLayoutParams(layoutParams);
                b.update_original_pos();
                //AYO THIS MIGHT DO IT
            }
        }
    }

    public void display_Puzzle(int k){
        switch(k) {
            case 1:
                game = game1;
                loadInitialState(game2); //loading initial states is essential as it basically reset the state of the previous game
                break;

            case 2:
                game = game2;
                loadInitialState(game1);
                break;
        }
        for (Bloc x : game.blocs){
            x.update_view();
        }
    }
    /** functions to be deleted, just for testing
     * */
    public void loading_Puzzle1(View v){
        display_Puzzle(1);
    }
    public void loading_Puzzle2(View v){
        display_Puzzle(2);
    }

    public void load_Puzzle(){
        game.addBloc(target_bloc);
        game.addBloc(car1_bloc);
        game.addBloc(car2_bloc);
        game.addBloc(car3_bloc);
        game.addBloc(car4_bloc);
        game.addBloc(car5_bloc);
        game.addBloc(car6_bloc);
        game.addBloc(car7_bloc);
        game.game_ready();

        game2.addBloc(target_bloc2);
        game2.addBloc(car1_bloc2);
        game2.addBloc(car2_bloc2);
        game2.addBloc(car3_bloc2);
        game2.addBloc(car4_bloc2);
        game2.addBloc(car5_bloc2);
        game2.addBloc(car6_bloc2);
        game2.addBloc(car7_bloc2);
        game2.game_ready();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);
        target = findViewById(R.id.target);
        car1 = findViewById(R.id.car1);
        car2 = findViewById(R.id.car2);
        car3 = findViewById(R.id.car3);
        car4 = findViewById(R.id.car4);
        car5 = findViewById(R.id.car5);
        car6 = findViewById(R.id.car6);
        car7 = findViewById(R.id.car7);

        target_bloc.set_view(target);
        car1_bloc.set_view(car1);
        car2_bloc.set_view(car2);
        car3_bloc.set_view(car3);
        car4_bloc.set_view(car4);
        car5_bloc.set_view(car5);
        car6_bloc.set_view(car6);
        car7_bloc.set_view(car7);

        target_bloc2.set_view(target);
        car1_bloc2.set_view(car1);
        car2_bloc2.set_view(car2);
        car3_bloc2.set_view(car3);
        car4_bloc2.set_view(car4);
        car5_bloc2.set_view(car5);
        car6_bloc2.set_view(car6);
        car7_bloc2.set_view(car7);

        //ImageView bloc = findViewById(R.id.bloc);
        //setting preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        load_Puzzle();
        update_mooves();



        setResetButtonState(false);
        setCancelButtonState(false);

        updateHighScore("1");



        Log.d("grid",game.toString());
        Log.d("car1",car1_bloc.toString());
        Log.d("car7",car7_bloc.toString());
        ArrayList<Bloc> allBlocs = (ArrayList<Bloc>) game1.blocs.clone(); // This will contain all the blocs available
        allBlocs.addAll(game2.blocs);

        Button res_button = (Button) findViewById(R.id.reset);
        res_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("RESET_START","you are trying to reset");
                loadInitialState(game);
                Log.d("RESET_END","you reseted I hope");
                // Do something in response to button click
            }
        });

        Button cancel_button = (Button) findViewById(R.id.cancel_move);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CANCEL_START","you are trying to cancel");
                loadPreviousState(game);
                Log.d("CANCEL_END","you canceled I hope");
                // Do something in response to button click
            }
        });


        for (Bloc x : allBlocs) {
            x.view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            xDown = event.getX(); // need to update this when you moove to correct jittering effect
                            yDown = event.getY();
                            Log.d("DOWN", x.name + " was touched");
                            Log.d("grid",game.toString());
                            Log.d("BLOC", x.toString());
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
                                    game.mooveRight(x);
                                    /*game.removeBloc(x); //SEIGNEUR, lorsque tu add et que tu remove le bloc, tu CHANGE sa posiution dans la grille
                                    x.incCol(); // d'ou l'importance d'avoir une fonction move bloc
                                    game.addBloc(x); //Et si on découplais ? on fait une fonction add et remove qui va juste pas affecter la liste initial quoi.*/
                                    wasmoved = true;
                                }
                            }
                            if (dpDeltaX < -100) {
                                if ((game.canMoveLeft(x)) && (x.isHorizontal)){
                                    game.mooveLeft(x);
                                    /*game.removeBloc(x);
                                    x.decCol();
                                    game.addBloc(x);*/
                                    wasmoved = true;
                                }
                            }
                            if (dpDeltaY < -100) {
                                Log.d("UP","mooving up");
                                if ((game.canMoveUp(x)) && (!x.isHorizontal)){
                                    game.mooveUp(x);
                                    /*game.removeBloc(x);
                                    x.decRow();
                                    game.addBloc(x);*/
                                    Log.d("UP","nice you moved Up");
                                    wasmoved = true;
                                }
                            //car1_bloc.decRow();
                            }
                            if (dpDeltaY > 100) {
                                Log.d("down","mooving down");
                                if ((game.canMoveDown(x)) && (!x.isHorizontal)){
                                    game.mooveDown(x);
                                    /*game.removeBloc(x);
                                    x.incRow();
                                    game.addBloc(x);*/
                                    wasmoved = true;
                                    Log.d("down","nice you moved down");
                                }
                            //car1_bloc.incRow();
                        }
                            layoutParams.columnSpec = GridLayout.spec(x.col,x.column_span);
                            layoutParams.rowSpec =  GridLayout.spec(x.row,x.row_span);
                            Log.d("Result",x.toString());
                            if (wasmoved == true){
                                x.view.setLayoutParams(layoutParams);
                                Log.d("grid",game.toString());
                                wasmoved = false;
                            }
                            //car1_bloc.view.setLayoutParams(layoutParams);
                            break;

                        case MotionEvent.ACTION_UP:
                            //Update le state uniquement quand on lache la pièce ! Faut le faire ici
                            Log.d("Original col", Integer.toString(x.original_col));
                            Log.d("Current col", Integer.toString(x.col));
                            Log.d("Original row", Integer.toString(x.original_row));
                            Log.d("Current row", Integer.toString(x.row));
                            //Rajouter une condition pour l'affichage, genre if la nouvelle position du bloc diffère de l'original
                            if ((x.original_col != x.col) || (x.original_row != x.row)){
                                // on update le nombre de moove et on met à jour les position original des blocs
                                total_moves += 1;
                                setResetButtonState(true);
                                setCancelButtonState(true);
                                x.update_original_pos();
                                game.updateState();

                            }
                            update_mooves();
                            Log.d("MOOVES",Integer.toString(total_moves));
                            if (game.checkWin(x)){
                                createNewVictoryDialog(game);
                                // TODO replace "1" by the String puzzleNumber
                                String HighestScoreSoFar = sharedpreferences.getString ("1", "--");
                                if ((HighestScoreSoFar == "--") || (Integer.parseInt(HighestScoreSoFar) > total_moves)){
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("1", Integer.toString(total_moves));
                                    editor.commit();
                                }

                            }
                            break;

                    }

                    return true;
                }
            });
        }

        /*bloc.setOnTouchListener(new View.OnTouchListener(){


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
                        //bloc.setX(bloc.getX()+distanceX);
                        //bloc.setY(bloc.getY()+distanceY);


                        break;
                }
                return true;
            }
        });*/


    }
}