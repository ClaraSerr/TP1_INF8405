package com.example.tp1inf8405;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.widget.GridLayout;

public class GridDisplay extends GridLayout {

    public GridDisplay(Context context, Grid grid) {
        super(context);

        setColumnCount(grid.getSize());
        setRowCount(grid.getSize());

        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                Block block = grid.getBlocks()[i][j];
                if (block != null) {
                    if (block.getRow() == i && block.getCol() == j) {
                        this.addView(block.getBlockView());

                        Log.d("CODE_1_GridDisplay", Integer.toString(i) + Integer.toString(j));
                    }
                }
            }
        }
    }
}
