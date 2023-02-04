package com.example.tp1inf8405;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

public class GridDisplay extends GridLayout {
    private Grid grid;

    public GridDisplay(Context context, Grid grid) {
        super(context);
        this.grid = grid;

        setColumnCount(grid.getSize());
        setRowCount(grid.getSize());

        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                TextView cell = new TextView(context);
                cell.setText("");
                addView(cell);
            }
        }
    }
}
