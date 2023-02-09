package com.example.tp1inf8405;

import android.content.Context;
import android.util.Log;

class Block {
    private int row;
    private int col;
    private BlockView blockView;

    public BlockView getBlockView() {
        return blockView;
    }

    public void setBlockView(BlockView blockView) {
        this.blockView = blockView;
    }

    enum Orientation {
        HORIZONTAL, VERTICAL
    }
    private Orientation orientation;

    public Block(int row, int col, Orientation orientation, Context context) {
        this.row = row;
        this.col = col;
        this.orientation = orientation;
        this.blockView = new BlockView(this,context);
        blockView.setBlockColor(this);
        Log.d("CODE_1_Block", "Constructor");

    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public boolean isHorizontal() {
        return orientation == Orientation.HORIZONTAL;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void moveUp() {
        row--;
    }

    public void moveDown() {
        col++;
    }

    public void moveLeft() {
        row--;
    }

    public void moveRight() {
        col++;
    }

}