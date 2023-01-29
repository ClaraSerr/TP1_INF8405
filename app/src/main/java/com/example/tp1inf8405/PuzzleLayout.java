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

    public PuzzleLayout(Context context, int columns, int rows){
        super(context);
        this.numColumns = columns;
        this.numRows = rows;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();

        // Divide the layout into a grid using the number of columns and rows
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            // Position the child views accordingly
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);

        // Draw the lines of the grid using the number of columns and rows
        for (int i = 1; i < numColumns; i++) {
            float x = width * i / numColumns;
            canvas.drawLine(x, 0, x, height, paint);
        }

        for (int i = 1; i < numRows; i++) {
            float y = height * i / numRows;
            canvas.drawLine(0, y, width, y, paint);
        }
    }

}
