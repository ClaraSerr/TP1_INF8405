package com.example.tp1inf8405;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class BlockView extends View implements View.OnTouchListener{
    private Paint paint;
    private Block block;
    private Block.Orientation orientation;
    private float x;
    private float y;
    private float dX;
    private float dY;
    private float dX_new;
    private float dY_new;
    private int rowMove;
    private int colMove;

    public BlockView(Block block, Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        this.block = block;
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX =  (view.getX() - event.getRawX());
                dY =  (view.getY() - event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (convertXToRow(dX) < 1 && convertXToRow(dX) < 1) {
                    if (isHorizontal()) {
                        x = event.getRawX() + dX;
                        y = event.getRawY() + dY;
                        dX_new = (view.getX() - event.getRawX());
                        dY_new = (view.getY() - event.getRawY());
                        Log.d("CODE_0_BlockView", Float.toString(x - view.getX()) + " : " + Integer.toString(convertXToRow(x - view.getX())));
                        rowMove = convertXToRow(dX_new - dX);

                        if (rowMove > 1) {
                            this.moveRight();
                            view.setX(x);
                        } else if (rowMove < -1) {
                            this.moveLeft();
                            view.setX(x);
                        }
                    } else {
                        if (colMove > 1) {
                            this.moveUp();
                            view.setX(y);
                        } else if (colMove < -1) {
                            this.moveDown();
                            view.setX(y);
                        }
                    }
                    break;
                }
            default:
                return false;
        }
        return true;
    }

    public float convertRowToX(int row){
        return row * getWidth() / 6;
    }

    public int convertXToRow(float X){
        return (int) (X * 6) / getWidth();
    }

    public float convertColToY(int col){
        return col * getWidth() / 6;
    }

    public int convertYToCol(float Y){
        return (int) (Y * 6) / getWidth();
    }

    public void setRow(int row){
        this.block.setRow(row);
    }

    public int getRow() {
        return this.block.getRow();
    }

    public void setCol(int col){
        this.block.setCol(col);
    }

    public int getCol() {
        return this.block.getCol();
    }

    public void moveUp() {
        block.moveUp();
    }

    public void moveDown() {
        block.moveDown();
    }

    public void moveLeft() {
        block.moveLeft();
    }

    public void moveRight() {
        block.moveRight();
    }

    public Block.Orientation getOrientation(){
        return this.block.getOrientation();
    }

    public boolean isHorizontal(){
        return block.isHorizontal();
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if (this.isHorizontal()) {
                canvas.drawRect(new Rect((int) convertRowToX(this.getRow()), (int) convertColToY(this.getCol()), (int) convertRowToX(this.getRow() + 2), (int) convertColToY(this.getCol() + 1)), paint);
                Log.d("CODE_1_BlockView", "Horizontal");
            } else {
                canvas.drawRect(new Rect((int) convertRowToX(this.getRow()), (int) convertColToY(this.getCol()), (int) convertRowToX(this.getRow() + 1), (int) convertColToY(this.getCol() + 2)), paint);
                Log.d("CODE_1_BlockView", "Vertical");
        }
    }

    public void setBlockColor(Block block) {
        if (block instanceof RedBlock){
            paint.setColor(Color.RED);
            Log.d("CODE_2_BlockView", "RED");
        }else{
            Log.d("CODE_2_BlockView","BLUE");
        }
    }

    public Paint getPaint(){
        return this.paint;
    }
}
