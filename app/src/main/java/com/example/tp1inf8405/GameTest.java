package com.example.tp1inf8405;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;

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
        Bloc (int row, int col, int row_span, int col_span, View v, String name){
            this.row = row;
            this.col = col;
            this.row_span = row_span;
            this.column_span = col_span;
            this.view = v;
            this.name = name;
            if (row_span > col_span) {
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
            int test =  0;
            if (this.row_span>1) {
                test = this.row + 1 + this.row_span ;
            }else{
                test = this.row + 1;
            }
            if ((test) < 6 ){
                this.row = this.row +1;
            }
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

        public Grid(int width, int height) {
            blocs = new ArrayList<>();
            this.width=width;
            this.height = height;
            this.grid = new int[width][height];
        }

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
                if (b.row + b.row_span >= this.height || isOccupied(b.row + b.row_span, j)) {
                    return false;
                }
            }


            return true;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);
        ImageView bloc = findViewById(R.id.bloc);

        View car1 = findViewById(R.id.car1);
        Bloc car1_bloc = new Bloc(0,1,1,3, car1, "car1");

        View car2 = findViewById(R.id.car2);
        Bloc car2_bloc = new Bloc(1,3,3,1, car2,"car2");

        View car7 = findViewById(R.id.car7);
        Bloc car7_bloc = new Bloc(0,6,3,1,car7,"car7");

        Grid game = new Grid(7,7);
        game.addBloc(car1_bloc);
        game.addBloc(car7_bloc);
        Log.d("grid",game.toString());
        Log.d("car1",car1_bloc.toString());
        Log.d("car7",car7_bloc.toString());
        ArrayList<Bloc> aList = game.blocs;

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
                                    game.removeBloc(x);
                                    x.incCol();
                                    game.addBloc(x);
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
                                if ((game.canMoveDown(x)) && (!x.isHorizontal)){
                                        game.removeBloc(x);
                                        x.decRow();
                                        game.addBloc(x);
                                        wasmoved = true;
                                }
                            //car1_bloc.decRow();
                            wasmoved = true;
                            }
                            if (dpDeltaY > 100) {
                                if ((game.canMoveUp(x)) && (!x.isHorizontal)){
                                    game.removeBloc(x);
                                    x.incRow();
                                    game.addBloc(x);
                                    wasmoved = true;
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