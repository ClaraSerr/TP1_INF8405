package com.example.tp1inf8405;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.GridLayout;

public class GridDisplay extends GridLayout {

    public GridDisplay(Context context, Grid grid) {
        super(context);

        setColumnCount(grid.getSize());
        setRowCount(grid.getSize());

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++){
                Block block = grid.getBlocks()[i][j];
                if (block != null) {
                    if (block.getX() == i && block.getY() == j){
                        BlockView blockView = new BlockView(context);
                        blockView.setBlock(block);
                        this.addView(blockView);
                    }
                }
            }
        }
    }
}
