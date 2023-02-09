package com.example.tp1inf8405;

import android.util.Log;

class Grid {
    private int size;
    private boolean isWin;
    private Block[][] blocks;

    public Grid(int size) {
        this.size = size;
        this.blocks = new Block[size][size];
        this.isWin = false;
        Log.d("Constructing Grid", "");
    }

    public void updateGridMoveUp(Block block){
        int row = block.getCol();
        int col = block.getRow();

        blocks[row+2][col] = null;
        blocks[row][col] = block;
        blocks[row+1][col] = block;
    }

    public void updateGridMoveDown(Block block){
        int row = block.getCol();
        int col = block.getRow();

        blocks[row-1][col] = null;
        blocks[row][col] = block;
        blocks[row+1][col] = block;
    }

    public void updateGridMoveLeft(Block block){
        int row = block.getCol();
        int col = block.getRow();

        blocks[row][col+2] = null;
        blocks[row][col] = block;
        blocks[row][col+1] = block;
    }

    public void updateGridMoveRight(Block block){
        int row = block.getCol();
        int col = block.getRow();

        blocks[row][col-1] = null;
        blocks[row][col] = block;
        blocks[row][col+1] = block;
    }

    public int getSize() {
        return size;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    private void setWin(boolean isWin) {
        this.isWin = isWin;
    }

    public boolean getWin() {
        return this.isWin;
    }

    private void checkWin(Block block){
        if (((RedBlock) block).getIsWin()){
            setWin(true);
        }
    }

    public boolean addBlock(Block block){
        int row = block.getCol();
        int col = block.getRow();

        if (isValid(block)) {
            if (block.isHorizontal()) {
                blocks[row][col] = block;
                blocks[row][col + 1] = block;
                Log.d("Adding H Block to Grid", Float.toString(block.getRow()) + " : " + Integer.toString(block.getCol()));
            } else {
                blocks[row][col] = block;
                blocks[row + 1][col] = block;
                Log.d("Adding V Block to Grid", Float.toString(block.getRow()) + " : " + Integer.toString(block.getCol()));
            }
            block.setGrid(this);
            return true;
        }
        return false;
    }

    private boolean isValid(Block block) {
        int row = block.getRow();
        int col = block.getCol();

        if (block.isHorizontal()) {
            return row >= 0 && row < size && col >= 0 && col < size - 1 && blocks[row][col] == null && blocks[row][col + 1] == null;
        } else {
            return row >= 0 && row < size - 1 && col >= 0 && col < size && blocks[row][col] == null && blocks[row + 1][col] == null;
        }
    }

    public boolean canMoveUp(Block block){
        int row = block.getRow();
        int col = block.getCol();

        if (block.isHorizontal()) {
            return false;
        } else {
            return row > 0 && blocks[row-1][col] == null;
        }
    }
    public boolean canMoveDown(Block block){
        int row = block.getRow();
        int col = block.getCol();

        if (block.isHorizontal()) {
            return false;
        } else {
            return row < size - 2 && blocks[row-2][col] == null;
        }
    }
    public boolean canMoveLeft(Block block){
        int row = block.getRow();
        int col = block.getCol();

        if (block.isHorizontal()) {
            return col > 0 && blocks[row][col-1] == null;
        } else {
            return false;
        }
    }
    public boolean canMoveRight(Block block){
        int row = block.getRow();
        int col = block.getCol();

        if (block.isHorizontal()) {
            return col < size - 2 && blocks[row][col+2] == null;
        } else {
            return false;
        }
    }

}