package com.example.tp1inf8405;

class Grid {
    private int size;
    private boolean isWin;
    private Block[][] blocks;

    public Grid(int size) {
        this.size = size;
        this.blocks = new Block[size][size];
        this.isWin = false;
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
            } else {
                blocks[row][col] = block;
                blocks[row + 1][col] = block;
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