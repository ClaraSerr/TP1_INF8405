package com.example.tp1inf8405;

class Grid {
    private int size;
    private Block[][] blocks;

    public Grid(int size) {
        this.size = size;
        this.blocks = new Block[size][size];
    }

    public int getSize() {
        return size;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public boolean addBlock(Block block){
        int row = block.getX();
        int col = block.getY();

        if (isValid(block)) {
            if (block.isHorizontal()) {
                blocks[row][col] = block;
            } else {
                blocks[row][col] = block;
            }
            return true;
        }
        return false;
    }

    private boolean isValid(Block block) {
        int row = block.getX();
        int col = block.getY();

        if (block.isHorizontal()) {
            return row >= 0 && row < size && col >= 0 && col < size - 1 && blocks[row][col] == null && blocks[row][col + 1] == null;
        } else {
            return row >= 0 && row < size - 1 && col >= 0 && col < size && blocks[row][col] == null && blocks[row + 1][col] == null;
        }
    }
}