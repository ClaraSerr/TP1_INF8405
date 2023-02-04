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

public class GameTest extends AppCompatActivity {
    float xDown = 0, yDown = 0;

    public class Bloc{
        String name;
        int row;
        int col;
        int row_span;
        int column_span;
        View view;
        int type; //0 if vertical, 1 if horizontal
        Bloc (int row, int col, int row_span, int col_span, View v, String name){
            this.row = row;
            this.col = col;
            this.row_span = row_span;
            this.column_span = col_span;
            this.view = v;
            this.name = name;
            if (row > col) {
                this.type = 0;
            }
            else{
                this.type = 1;
            }
        }

        @NonNull
        @Override
        public String toString() {
            String res = "for car " + this.name + " /n ";
            res = res + "row = " + Integer.toString(row) + " /n ";
            res += "row_span = " + Integer.toString(row_span) + " /n ";
            res += "col = " + Integer.toString(col) + " /n ";
            res += "column_span = " + Integer.toString(column_span) + " /n ";

            return res;
            //return super.toString();
        }

        public void incCol(){
            if (((this.col + 1) > 0) & ((this.col + 1) < 8 )){
                this.col = this.col +1;
            }
        }
        public void decCol(){
            if (((this.col - 1) > 0) & ((this.col - 1) < 8 )){
                this.col = this.col -1;
            }
        }

        public void incRow(){
            if ((this.row + 1) < 6 ){
                this.row = this.row +1;
            }
        }
        public void decRow(){
            if ((this.row - 1) > -1 ){
            this.row = this.row -1;}
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
        car1_bloc.view.setOnTouchListener(new View.OnTouchListener(){


            public boolean onTouch(View v, MotionEvent event){

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        yDown = event.getY();
                        Log.d("DOWN","Car1 was touched");
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float movedX, movedY;
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
                        if (dpDeltaX > 50) {
                            car1_bloc.incCol();
                        } else if (dpDeltaX < -50) {
                            car1_bloc.decCol();
                        } else if (dpDeltaY < -50) {
                            car1_bloc.decRow();
                        } else if (dpDeltaY > 50) {
                            car1_bloc.incRow();
                        }
                        layoutParams.columnSpec = GridLayout.spec(car1_bloc.col,car1_bloc.column_span);
                        layoutParams.rowSpec =  GridLayout.spec(car1_bloc.row,car1_bloc.row_span);
                        Log.d("Result",car1_bloc.toString());
                        car1_bloc.view.setLayoutParams(layoutParams);
                        break;



                }
                return true;
            }
        });

    }
}