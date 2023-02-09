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
                Log.d("Touching screen", Float.toString(dY) + " : " + Float.toString(dX));
                break;
            case MotionEvent.ACTION_MOVE:
                if (convertYXToRowCol(dX) < 1 && convertYXToRowCol(dX) < 1) {
                    x = event.getRawX() + dX;
                    y = event.getRawY() + dY;
                    dX_new = (view.getX() - event.getRawX());
                    dY_new = (view.getY() - event.getRawY());
                    colMove = convertYXToRowCol(dX-dX_new);
                    rowMove = convertYXToRowCol(dY-dY_new);
                    Log.d("Moving on screen", Integer.toString(rowMove) + " : " + Integer.toString(colMove));
                    if (isHorizontal()) {
                        if (colMove > 1 && canMoveRight(this.getBlock())) {
                            this.moveRight();
                            view.setX(convertRowColToYX(this.getRow()));
                        } else if (colMove < -1 && canMoveLeft(this.getBlock())) {
                            this.moveLeft();
                            view.setX(x);
                        }
                    } else {
                        if (rowMove > 1 && canMoveDown(this.getBlock())) {
                            this.moveDown();
                            view.setY(y);
                        } else if (rowMove < -1 && canMoveUp(this.getBlock())) {
                            this.moveUp();
                            view.setY(y);
                        }
                    }
                    break;
                }
            default:
                return false;
        }
        return true;
    }

    public Grid getGrid(){
        return this.getBlock().getGrid();
    }

    public boolean canMoveUp(Block block){
        return this.getGrid().canMoveUp(block);
    }
    public boolean canMoveDown(Block block){
        return this.getGrid().canMoveDown(block);
    }
    public boolean canMoveLeft(Block block){
        return this.getGrid().canMoveLeft(block);
    }
    public boolean canMoveRight(Block block){
        return this.getGrid().canMoveRight(block);
    }

    public float convertRowColToYX(int rowcol){
        return rowcol * getWidth() / 6;
    }
    public int convertYXToRowCol(float YX){
        return (int) (YX * 6) / getWidth();
    }

    public void setRow(int row){
        this.getBlock().setRow(row);
    }
    public int getRow() {
        return this.getBlock().getRow();
    }
    public void setCol(int col){
        this.getBlock().setCol(col);
    }
    public int getCol() {
        return this.getBlock().getCol();
    }

    public Block getBlock(){
        return this.block;
    }

    public void moveUp() {
        getBlock().moveUp();
    }
    public void moveDown() {
        getBlock().moveDown();
    }
    public void moveLeft() {
        getBlock().moveLeft();
    }
    public void moveRight() {
        getBlock().moveRight();
    }

    public Block.Orientation getOrientation(){
        return this.getBlock().getOrientation();
    }

    public boolean isHorizontal(){
        return this.getBlock().isHorizontal();
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if (this.isHorizontal()) {
                canvas.drawRect(new Rect((int) convertRowColToYX(this.getRow()), (int) convertRowColToYX(this.getCol()), (int) convertRowColToYX(this.getRow() + 2), (int) convertRowColToYX(this.getCol() + 1)), paint);
                Log.d("CODE_1_BlockView", "Horizontal");
            } else {
                canvas.drawRect(new Rect((int) convertRowColToYX(this.getRow()), (int) convertRowColToYX(this.getCol()), (int) convertRowColToYX(this.getRow() + 1), (int) convertRowColToYX(this.getCol() + 2)), paint);
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
