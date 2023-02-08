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
            for (int j = 0; j < grid.getSize(); j++){
                Block block = grid.getBlocks()[i][j];
                if (block != null) {
                    if (block.getX() == i && block.getY() == j){
                        Log.d("BlockViewI",Integer.toString(i));
                        Log.d("BlockViewJ",Integer.toString(j));
                        BlockView blockView = new BlockView(context);
                        blockView.setBlock(block);
                        this.addView(blockView);
                    }
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < getChildCount(); i++) {
            Log.d("GridDisplayLooping",Integer.toString(i));

            BlockView child = (BlockView) getChildAt(i);
            Block block = child.getBlock();

            int x = block.getX();
            Log.d("BlockViewX", Integer.toString(x));
            int y = block.getY();
            Log.d("BlockViewY", Integer.toString(y));

            if (block.isHorizontal()) {
                canvas.drawRect(new Rect(x * getWidth() / 6, y * getWidth() / 6, (x + 2) * getWidth() / 6, (y + 1) * getWidth() / 6), child.getPaint());
                Log.d("BlockViewOrientation", "Horizontal");
            } else {
                canvas.drawRect(new Rect(x * getWidth() / 6, y * getWidth() / 6, (x + 1) * getWidth() / 6, (y + 2) * getWidth() / 6), child.getPaint());
                Log.d("BlockViewOrientation", "Vertical");
            }
        }
    }
}
