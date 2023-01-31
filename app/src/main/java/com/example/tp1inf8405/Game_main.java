package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

public class Game_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        //setContentView(new UnblockMeView(this));
/*
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setRowCount(4);
        gridLayout.setColumnCount(4);

        for (int i = 0; i < 16; i++) {
            Button button = new Button(this);
            button.setText("Block " + i);
            gridLayout.addView(button);




        }

        setContentView(gridLayout);

  */
    }
    public void restart(View view){
        setContentView(new UnblockMeView(this));
    }
    public class UnblockMeView extends View {
        private int blockSize;
        private int gridSize;
        private Paint blockPaint;
        private boolean[][][][] blocks;

        public UnblockMeView(Context context) {
            super(context);
            blockSize = 175;
            gridSize = 6;
            blockPaint = new Paint();
            blockPaint.setColor(Color.BLUE);
            //block[left][top][horizontal][vertical]
            blocks = new boolean[gridSize][gridSize][gridSize][gridSize];
            blocks[0][1][2][1] = true; //mandatory block
            blocks[2][2][1][2] = true;
            blocks[3][4][1][2] = true;

        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    for (int k = 0; k < gridSize; k++) {
                        for (int m = 0; m < gridSize; m++) {

                            if (blocks[i][j][k][m]) {

                                canvas.drawRect(i * blockSize, j * blockSize, (i+k) * blockSize, (j+m) * blockSize, blockPaint);
                            }
                        }
                    }
                }
            }


                Paint linePaint = new Paint();
                linePaint.setColor(Color.BLACK);
                linePaint.setStrokeWidth(2);
                canvas.drawLine(0, 0, gridSize * blockSize, 0, linePaint);
                canvas.drawLine(0, 0, 0, gridSize * blockSize, linePaint);
                canvas.drawLine(0, gridSize * blockSize, gridSize * blockSize, gridSize * blockSize, linePaint);
                canvas.drawLine(gridSize * blockSize, 0, gridSize * blockSize, gridSize * blockSize, linePaint);


        }
      /*  public void moveBlock(int fromX, int fromY, int toX, int toY) {
            blocks[toX][toY] = blocks[fromX][fromY];
            blocks[fromX][fromY] = false;
            invalidate();
        } */
    }


   /* class Block {
        int size;
        int x, y;
        int color;

        public Block(int size, int x, int y, int color) {
            this.size = size;
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }

    class UnblockMeView extends View {
        Block[][] blocks;
        Paint paint;

        public UnblockMeView(Context context) {
            super(context);
            blocks = new Block[5][5];
            paint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            for (int i = 0; i < blocks.length; i++) {
                for (int j = 0; j < blocks[i].length; j++) {
                    Block block = blocks[i][j];
                    if (block != null) {
                        paint.setColor(block.color);
                        canvas.drawRect(block.x, block.y, block.x + block.size, block.y + block.size, paint);
                    }
                }
            }
        }

        public void moveBlock(int fromX, int fromY, int toX, int toY) {
            Block block = blocks[fromX][fromY];
            blocks[fromX][fromY] = null;
            blocks[toX][toY] = block;
            invalidate();
        }
    }
*/

    }
