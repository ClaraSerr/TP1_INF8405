package com.example.tp1inf8405;

import android.content.Context;
import android.graphics.Rect;
import android.widget.GridLayout;
import android.widget.TextView;
import android.view.View;

public class GridDisplay extends GridLayout {
    private Grid grid;

    public GridDisplay(Context context, Grid grid) {
        super(context);
        this.grid = grid;

        setColumnCount(grid.getSize());
        setRowCount(grid.getSize());

        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++){
                Block block = grid.getBlocks()[i][j];
                if (block != null) {
                    if (block.getX() == i && block.getY() == j){
                        BlockView blockView = new BlockView(context);
                        blockView.setBlock(block);
                        addView(blockView);
                    }
                }
            }
        }
    }
}
