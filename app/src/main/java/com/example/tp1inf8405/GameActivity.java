package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Grid grid = GridLoader.loadFromFile(this, "grid.txt");

        GridDisplay gridDisplay = new GridDisplay(this, grid);
        setContentView(gridDisplay);

    }

    public void restart(View view) {

        Grid grid = GridLoader.loadFromFile(this, "grid.txt");

        GridDisplay gridDisplay = new GridDisplay(this, grid);
        setContentView(gridDisplay);
    }

}
