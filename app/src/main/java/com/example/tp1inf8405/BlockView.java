package com.example.tp1inf8405;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

class BlockView extends View {
    private Block block;
    private Paint paint;

    public BlockView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    public void setBlock(Block block) {
        this.block = block;
        if (block instanceof RedBlock){
            paint.setColor(Color.RED);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (block != null) {
            int x = block.getX();
            int y = block.getY();
            if (block.isHorizontal()) {
                canvas.drawRect(new Rect(x * getWidth() / 6, y * getWidth() / 6, (x + 2) * getWidth() / 6, (y + 1) * getWidth() / 6), paint);
            } else {
                canvas.drawRect(new Rect(x * getWidth() / 6, y * getWidth() / 6, (x + 1) * getWidth() / 6, (y + 2) * getWidth() / 6), paint);
            }
        }
    }
}
