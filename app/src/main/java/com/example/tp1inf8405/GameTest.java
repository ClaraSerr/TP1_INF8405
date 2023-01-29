package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class GameTest extends AppCompatActivity {
    float xDown = 0, yDown = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);
        ImageView bloc = findViewById(R.id.bloc);
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