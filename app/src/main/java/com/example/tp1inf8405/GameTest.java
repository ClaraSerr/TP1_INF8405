package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameTest extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Highscores";
    int current_puzzle = 1;
    float xDown = 0, yDown = 0;
    int total_moves = 0;

    /**
     * This part initializes the target block and the 7 blocs as Block objects for each one of the three puzzles.
     */

    //The target block
    View target;
    Bloc target_bloc1 = new Bloc(2,1,1,2, target, "target_puzzle1",true);
    Bloc target_bloc2 = new Bloc(2,1,1,2, target, "target_puzzle2",true);
    Bloc target_bloc3 = new Bloc(2, 1, 1, 2, target, "target_puzzle3", true);

    //Regular blocks
    View car1 ;
    Bloc car1_bloc1 = new Bloc(0,1,1,3, car1, "car1_puzzle1",false);
    Bloc car1_bloc2 = new Bloc(1,3,2,1, car1, "car1_puzzle2",false);
    Bloc car1_bloc3 = new Bloc(0, 1, 2, 1, car1, "car1_puzzle3", false);

    View car2 ;
    Bloc car2_bloc1 = new Bloc(1,3,3,1, car2,"car2_puzzle1",false);
    Bloc car2_bloc2 = new Bloc(1,4,3,1, car2, "car2_puzzle2",false);
    Bloc car2_bloc3 = new Bloc(4, 1, 1, 3, car2, "car2_puzzle3", false);

    View car3 ;
    Bloc car3_bloc1 = new Bloc(3,1,2,1, car3,"car3_puzzle1",false);
    Bloc car3_bloc2 = new Bloc(1,5,3,1, car3, "car3_puzzle2",false);
    Bloc car3_bloc3 = new Bloc(0, 2, 1, 2, car3, "car3_puzzle3", false);

    View car4 ;
    Bloc car4_bloc1 = new Bloc(5,1,1,3, car4,"car4_puzzle1",false);
    Bloc car4_bloc2 = new Bloc(3,1,1,2, car4, "car4_puzzle2",false);
    Bloc car4_bloc3 = new Bloc(1, 3, 2, 1, car4, "car4_puzzle3", false);

    View car5 ;
    Bloc car5_bloc1 = new Bloc(4,5,2,1, car5,"car5_puzzle1",false);
    Bloc car5_bloc2 = new Bloc(3,3,2,1, car5, "car5_puzzle2",false);
    Bloc car5_bloc3 = new Bloc(0, 4, 1, 2, car5, "car5_puzzle3", false);

    View car6 ;
    Bloc car6_bloc1 = new Bloc(3,5,1,2, car6,"car6_puzzle1",false);
    Bloc car6_bloc2 = new Bloc(4,2,2,1, car6, "car6_puzzle2",false);
    Bloc car6_bloc3 = new Bloc(2, 4, 3, 1, car6, "car6_puzzle3", false);

    View car7 ;
    Bloc car7_bloc1 = new Bloc(0,6,3,1,car7,"car7_puzzle1",false);
    Bloc car7_bloc2 = new Bloc(5,3,1,2, car7, "car7_puzzle2",false);
    Bloc car7_bloc3 = new Bloc(2, 5, 3, 1, car7, "car7_puzzle3", false);

    /**
     * This part initializes the 3 grids for the 3 puzzles.
     */
    //The grid
    Grid game1 = new Grid(7,7);
    Grid game2 = new Grid(7,7);
    Grid game3 = new Grid(7,7);
    Grid game = game1;


    public Map<String, Integer> MaxHighscores = new HashMap<String, Integer>() {{
        put("1", 15);
        put("2", 17);
        put("3", 15);
    }};

    public void updateHighScore(String puzzleNumber) {
        /**
         * Updating the highest score displayed in the TextView above the puzzle.
         *
         * Called :
         * - every time we finish a puzzle.
         */
        TextView v = findViewById(R.id.record3);
        String score = sharedpreferences.getString(puzzleNumber, "--");
        v.setText(score + "/" +  MaxHighscores.get(puzzleNumber));
    };

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    public void update_mooves(){
        /**
         * Updating the number of moves displayed in the TextView above the puzzle.
         *
         * Called :
         * - every time we need to increment the number of moves : when moving a block.
         * - every time we need to decrement the number of moves : when deleting the previous move.
         */
        TextView v = findViewById(R.id.nb_moves);
        v.setText(Integer.toString(total_moves));
    }

    public void reset_mooves() {
        /**
         * Resetting the number of moves displayed in the TextView above the puzzle.
         *
         * Called :
         * - every time we need to reset the number of moves (when starting a new puzzle or resetting the current one).
         */
        total_moves = 0;
        TextView v = findViewById(R.id.nb_moves);
        v.setText(Integer.toString(total_moves));
    }

    public void createNewVictoryDialog(){
        /**
         * Creates and displays the victory bubble on top of the current puzzle for a limited duration of time.
         * Duration : 3s (3000ms)
         *
         * Called :
         * - every time there is a win.
         */
        dialogBuilder = new AlertDialog.Builder(this);
        final View victoryPopupView = getLayoutInflater().inflate(R.layout.activity_pop_up_victory, null);
        dialogBuilder.setView(victoryPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
        String HighestScoreSoFar = sharedpreferences.getString (Integer.toString(current_puzzle), "--");
        if ((HighestScoreSoFar == "--") || (Integer.parseInt(HighestScoreSoFar) > total_moves)){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Integer.toString(current_puzzle), Integer.toString(total_moves));
            editor.commit();
        }
        switch (current_puzzle){
            case 1:
                current_puzzle++;
                setPreviousButtonState(true);
                break;
            case 2:
                current_puzzle++;
                setNextButtonState(false);
                break;
        }

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
                display_Puzzle(current_puzzle);
            }
        });
        anim.start();
    }

    public void setResetButtonState(boolean bool){
        /**
         * Sets the "reset" button's "enabled" mode.
         *
         * Called :
         * - every time it must be disabled (false) (when game already been reset or just started)
         * - every time it must be enabled (true) (when game can be reset)
         *
         */
        Button reset = (Button) findViewById(R.id.reset);
        reset.setEnabled(bool);
    }

    public void setPreviousButtonState(boolean bool){
        /**
         * Sets the "previous" button's "enabled" mode and color.
         *
         * Background colors :
         * - "#e0e0e0" : disabled button
         * - "#886b4a" : enabled button
         *
         * Text colors :
         * - "#959595" : disabled button
         * - "#FFFEFEFE" : enabled button
         *
         * Called :
         * - every time it must be disabled (false) (when current puzzle is puzzle 1)
         * - every time it must be enabled (true) (when current puzzle is puzzle 2 or 3)
         */
        ImageButton previous = (ImageButton) findViewById(R.id.previous);
        previous.setEnabled(bool);

        if (bool){
            previous.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#886b4a")));
            previous.setColorFilter(Color.parseColor("#FEFEFE"));
            previous.setElevation(10);
        }
        else{
            previous.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e0e0e0")));
            previous.setColorFilter(Color.parseColor("#959595"));
            previous.setElevation(0);
        }

    }

    public void setNextButtonState(boolean bool) {
        /**
         * Sets the "next" button's "enabled" mode and color.
         *
         * Background colors :
         * - "#e0e0e0" : disabled button
         * - "#886b4a" : enabled button
         *
         * Text colors :
         * - "#959595" : disabled button
         * - "#FFFEFEFE" : enabled button
         *
         *
         * Called :
         * - every time it must be disabled (false) (when current puzzle is puzzle 1)
         * - every time it must be enabled (true) (when current puzzle is puzzle 2 or 3)
         */
        ImageButton next = (ImageButton) findViewById(R.id.next);
        next.setEnabled(bool);

        if (bool) {
            next.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#886b4a")));
            next.setColorFilter(Color.parseColor("#FEFEFE"));
            next.setElevation(10);
        } else {
            next.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#e0e0e0")));
            next.setColorFilter(Color.parseColor("#959595"));
            next.setElevation(0);
        }

    }

    public void setCancelButtonState(boolean bool){
        /**
         * Sets the "cancel" button's "enabled" mode.
         *
         * Called :
         * - every time it must be disabled (false) (when no move made)
         * - every time it must be enabled (true) (when one or more moves made)
         *
         */
        Button cancel = (Button) findViewById(R.id.cancel_move);
        cancel.setEnabled(bool);
    }

    /*This function loads the initial state of a Game using what is stored in the Grid */
    public void loadInitialState(Grid grid){
        /**
         *
         * Resets the states of the grid and the number of moves displays to come back to the original state on the desired grid.
         *
         * Called :
         * - everytime we start the game.
         * - everytime we start a new puzzle.
         *
         */
        reset_mooves();

        //getting the desired state
        Grid initialState = grid.states.get(0);

        //réinitialiser le boutton reset et le score
        setResetButtonState(false);
        setCancelButtonState(false);
        total_moves = 0;
        update_mooves();

        //On met à jour le higher score avec ce qui est stocké dans les préférences
        updateHighScore(Integer.toString(current_puzzle));

        ArrayList<Bloc> new_blocs = new ArrayList<>() ;
        Log.d("Initial state",initialState.toString());
        int nb_bloc = grid.blocs.size();
        for(int k=0 ;k< nb_bloc; k++){

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
        grid.reload(new_blocs);
        for(int k=0 ;k< nb_bloc; k++){
            Bloc b = grid.blocs.get(k);
            GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) b.view.getLayoutParams();
            layoutParams.columnSpec = GridLayout.spec(b.col,b.column_span);
            layoutParams.rowSpec =  GridLayout.spec(b.row,b.row_span);
            b.view.setLayoutParams(layoutParams);
            b.update_original_pos();
        }
    }

    public void loadPreviousState(Grid grid){
        /**
         * Loads the previous state of the selected grid and decrements the number of moves display.
         *
         * Called :
         * - every time we click the "cancel" button
         *
         */
        int size = grid.states.size();

        Log.d("currrent", Integer.toString(current_puzzle));
        total_moves -= 1;
        update_mooves();
        if (total_moves==0){
            setResetButtonState(false);
            setCancelButtonState(false);
        }

        //getting the desired state
        Grid initialState = grid.states.get(size-2); //on va à l'état -1 puis on supprimer l'état actuel de la liste des states


        ArrayList<Bloc> new_blocs = new ArrayList<>() ;
        Log.d("Initial state",initialState.toString());
        int nb_bloc = grid.blocs.size();
        for(int k=0 ;k< nb_bloc; k++){

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
        grid.loadPrevious(new_blocs);
        for(int k=0 ;k< nb_bloc; k++){
            Bloc b = grid.blocs.get(k);
            GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) b.view.getLayoutParams();
            layoutParams.columnSpec = GridLayout.spec(b.col,b.column_span);
            layoutParams.rowSpec =  GridLayout.spec(b.row,b.row_span);
            b.view.setLayoutParams(layoutParams);
            b.update_original_pos();
        }
    }

    public void display_Puzzle(int k){
        /**
         * Displays the current puzzle by :
         * - changing the puzzle number displayed above the puzzle
         * - resetting the number of move displayed above the puzzle
         * - reinitializing the other puzzles
         * - preparing for the use of the grid of the new puzzle
         */
        TextView number = findViewById(R.id.puzzle_number);
        number.setText(Integer.toString(k));

        updateHighScore(Integer.toString(current_puzzle));
        reset_mooves();

        switch(k) {
            case 1:
                game = game1;
                loadInitialState(game2); //loading initial states is essential as it basically reset the state of the previous game
                loadInitialState(game3);
                break;

            case 2:
                game = game2;
                loadInitialState(game1);
                loadInitialState(game3);
                break;
            case 3:
                game = game3;
                loadInitialState(game1);
                loadInitialState(game2);
                break;
        }
        for (Bloc x : game.blocs){
            x.update_view();
            x.view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            /**
                             * The finger touches the screen. Starts calculating the movement.
                             */
                            xDown = event.getX();
                            yDown = event.getY();
                            Log.d("DOWN", x.name + " was touched");
                            Log.d("grid",game.toString());
                            Log.d("BLOC", x.toString());
                            break;

                        case MotionEvent.ACTION_MOVE:
                            /**
                             * The finger moves on the screen.
                             */
                            float movedX, movedY;
                            boolean wasmoved = false;
                            movedX = event.getX();
                            movedY = event.getY();
                            // calculate how much the user moved his finger
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

                            /**
                             * Movement of the bloc depending on the movement of the finger :
                             * - dpDeltaX : the number of dp horizontally
                             * - dpDeltaY : the number of dp vertically
                             *
                             * Movement of blocks is initiate only if the finger moves more than one block (100dp).
                             */
                            if (dpDeltaX > 100) {
                                if ((game.canMoveRight(x)) && (x.isHorizontal)){
                                    game.mooveRight(x);
                                    wasmoved = true;
                                }
                            }
                            if (dpDeltaX < -100) {
                                if ((game.canMoveLeft(x)) && (x.isHorizontal)){
                                    game.mooveLeft(x);
                                    wasmoved = true;
                                }
                            }
                            if (dpDeltaY < -100) {
                                Log.d("UP","mooving up");
                                if ((game.canMoveUp(x)) && (!x.isHorizontal)){
                                    game.mooveUp(x);
                                    Log.d("UP","nice you moved Up");
                                    wasmoved = true;
                                }
                            }
                            if (dpDeltaY > 100) {
                                Log.d("down","mooving down");
                                if ((game.canMoveDown(x)) && (!x.isHorizontal)){
                                    game.mooveDown(x);
                                    wasmoved = true;
                                    Log.d("down","nice you moved down");
                                }
                            }
                            layoutParams.columnSpec = GridLayout.spec(x.col,x.column_span);
                            layoutParams.rowSpec =  GridLayout.spec(x.row,x.row_span);
                            Log.d("Result",x.toString());
                            if (wasmoved == true){
                                x.view.setLayoutParams(layoutParams);
                                Log.d("grid",game.toString());
                                wasmoved = false;
                            }
                            break;

                        case MotionEvent.ACTION_UP:
                            /**
                             * The finger releases the screen.
                             *
                             * Launch the update of :
                             * - grid state
                             * - number of moves
                             * - "reset" and "cancel" buttons displays if need be
                             */
                            Log.d("Original col", Integer.toString(x.original_col));
                            Log.d("Current col", Integer.toString(x.col));
                            Log.d("Original row", Integer.toString(x.original_row));
                            Log.d("Current row", Integer.toString(x.row));
                            if ((x.original_col != x.col) || (x.original_row != x.row)){
                                // on update le nombre de moves et on met à jour les position original des blocs
                                total_moves += 1;
                                setResetButtonState(true);
                                setCancelButtonState(true);
                                x.update_original_pos();
                                game.updateState();

                            }
                            update_mooves();
                            Log.d("MOOVES",Integer.toString(total_moves));
                            if (game.checkWin(x)){
                                createNewVictoryDialog();
                                loadInitialState(game);
                            }
                            break;

                    }

                    return true;
                }
        });
    }
    }

    /** function used to load the previous puzzle
     * */
    public void loading_previousPuzzle(View v){
        current_puzzle--;
        if(current_puzzle==1){
            setPreviousButtonState(false);
        }
        if(current_puzzle==2){
            setNextButtonState(true);
        }
        display_Puzzle(current_puzzle);

    }
    /**Function used to load the next puzzle
     * */
    public void loading_nextPuzzle(View v){
        current_puzzle++;
        if(current_puzzle==3){
            setNextButtonState(false);
        }
        if(current_puzzle==2){
            setPreviousButtonState(true);
        }
        display_Puzzle(current_puzzle);
    }

    public void load_Puzzle(){
        /**
         * Creates the puzzle by adding the blocks one by one to the puzzles' grids (for puzzles 1, 2 and 3).
         */
        game1.addBloc(target_bloc1);
        game1.addBloc(car1_bloc1);
        game1.addBloc(car2_bloc1);
        game1.addBloc(car3_bloc1);
        game1.addBloc(car4_bloc1);
        game1.addBloc(car5_bloc1);
        game1.addBloc(car6_bloc1);
        game1.addBloc(car7_bloc1);
        game1.game_ready();

        game2.addBloc(target_bloc2);
        game2.addBloc(car1_bloc2);
        game2.addBloc(car2_bloc2);
        game2.addBloc(car3_bloc2);
        game2.addBloc(car4_bloc2);
        game2.addBloc(car5_bloc2);
        game2.addBloc(car6_bloc2);
        game2.addBloc(car7_bloc2);
        game2.game_ready();

        game3.addBloc(target_bloc3);
        game3.addBloc(car1_bloc3);
        game3.addBloc(car2_bloc3);
        game3.addBloc(car3_bloc3);
        game3.addBloc(car4_bloc3);
        game3.addBloc(car5_bloc3);
        game3.addBloc(car6_bloc3);
        game3.addBloc(car7_bloc3);
        game3.game_ready();

    }
    public void back_menu(View v){
        //test from Studio 3
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);

        /**
         * This part fetch the block's TextViews in the xml activity_game_test.xml using findViewById function.
         */
        target = findViewById(R.id.target);
        car1 = findViewById(R.id.car1);
        car2 = findViewById(R.id.car2);
        car3 = findViewById(R.id.car3);
        car4 = findViewById(R.id.car4);
        car5 = findViewById(R.id.car5);
        car6 = findViewById(R.id.car6);
        car7 = findViewById(R.id.car7);

        /**
         * This part associates the block's TextViews with the Block objects. Respectively for Puzzle 1, 2 and 3.
         */
        target_bloc1.set_view(target);
        car1_bloc1.set_view(car1);
        car2_bloc1.set_view(car2);
        car3_bloc1.set_view(car3);
        car4_bloc1.set_view(car4);
        car5_bloc1.set_view(car5);
        car6_bloc1.set_view(car6);
        car7_bloc1.set_view(car7);

        target_bloc2.set_view(target);
        car1_bloc2.set_view(car1);
        car2_bloc2.set_view(car2);
        car3_bloc2.set_view(car3);
        car4_bloc2.set_view(car4);
        car5_bloc2.set_view(car5);
        car6_bloc2.set_view(car6);
        car7_bloc2.set_view(car7);

        target_bloc3.set_view(target);
        car1_bloc3.set_view(car1);
        car2_bloc3.set_view(car2);
        car3_bloc3.set_view(car3);
        car4_bloc3.set_view(car4);
        car5_bloc3.set_view(car5);
        car6_bloc3.set_view(car6);
        car7_bloc3.set_view(car7);

        //setting preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        load_Puzzle();
        display_Puzzle(current_puzzle);
        update_mooves();

        setResetButtonState(false);
        setCancelButtonState(false);
        setPreviousButtonState(false);

        updateHighScore("1");

        Log.d("grid",game1.toString());
        Log.d("car1",car1_bloc1.toString());
        Log.d("car7",car7_bloc1.toString());
        ArrayList<Bloc> allBlocs = (ArrayList<Bloc>) game1.blocs.clone(); // This will contain all the blocs available
        allBlocs.addAll(game2.blocs);
        allBlocs.addAll(game3.blocs);

        /**
         * This following part initializes the reset button.
         */
        Button res_button = (Button) findViewById(R.id.reset);
        res_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("RESET_START","you are trying to reset");
                loadInitialState(game);
                Log.d("RESET_END","you reseted I hope");
                // Do something in response to button click
            }
        });

        /**
         * This following part initializes the cancel button.
         */
        Button cancel_button = (Button) findViewById(R.id.cancel_move);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("CANCEL_START","you are trying to cancel");
                loadPreviousState(game);
                Log.d("CANCEL_END","you canceled I hope");
                // Do something in response to button click
            }
        });



    }
}