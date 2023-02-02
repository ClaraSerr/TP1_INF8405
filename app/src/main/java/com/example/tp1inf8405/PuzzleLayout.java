package com.example.tp1inf8405;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

public class PuzzleLayout extends ConstraintLayout {
    private int numColumns;
    private int numRows;
    private Paint paint;

    public PuzzleLayout(Context context, AttributeSet attrs, int defStyleAttr, int numColumns, int numRows) {
        super(context, attrs, defStyleAttr);
        this.numColumns = numColumns;
        this.numRows = numRows;
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        this.paint = paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int cellWidth = width / numColumns;
        int cellHeight = height / numRows;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        int cellWidth = getWidth() / numColumns;
        int cellHeight = getHeight() / numRows;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            int column = i % numColumns;
            int row = i / numColumns;

            int leftPos = column * cellWidth;
            int topPos = row * cellHeight;

            child.layout(leftPos, topPos, leftPos + cellWidth, topPos + cellHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw horizontal lines
        for (int i = 1; i < numRows; i++) {
            int y = i * (getHeight() / numRows);
            canvas.drawLine(0, y, getWidth(), y, paint);
        }

        // Draw vertical lines
        for (int i = 1; i < numColumns; i++) {
            int x = i * (getWidth() / numColumns);
            canvas.drawLine(x, 0, x, getHeight(), paint);
        }
    }

}
