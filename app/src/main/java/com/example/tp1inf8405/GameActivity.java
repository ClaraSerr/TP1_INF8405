package com.example.tp1inf8405;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

    }

    public void restart(View view) {

        Grid grid = GridLoader.loadFromFile("grid.txt");

        GridDisplay gridDisplay = new GridDisplay(this, grid);
        setContentView(gridDisplay);
    }

}
