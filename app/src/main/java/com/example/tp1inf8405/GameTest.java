package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.gridlayout.widget.GridLayout;;

public class GameTest extends AppCompatActivity {
    float xDown = 0, yDown = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);
        ImageView bloc = findViewById(R.id.bloc);
        View car1 = findViewById(R.id.car1);
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
        car1.setOnTouchListener(new View.OnTouchListener(){


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
                            //GridLayout.Spec columnSpec = layoutParams.columnSpec;
                            //int columnSpan = columnSpec.column;
                        } else if (dpDeltaY < -50) {
                            //layoutParams.rowSpec = GridLayout.spec(layoutParams.rowSpec.getGridIndex() + 1);
                        }
                        //v.setLayoutParams(layoutParams);
                        break;



                }
                return true;
            }
        });

    }
}