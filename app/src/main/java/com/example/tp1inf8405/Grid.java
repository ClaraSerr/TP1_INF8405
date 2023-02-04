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

    public boolean addBlock(Block block, int row, int col) {
        if (isValid(row, col, block.getOrientation())) {
            if (block.getOrientation() == Block.Orientation.HORIZONTAL) {
                blocks[row][col] = block;
                blocks[row][col + 1] = block;
            } else {
                blocks[row][col] = block;
                blocks[row + 1][col] = block;
            }
            return true;
        }
        return false;
    }

    private boolean isValid(int row, int col, Block.Orientation orientation) {
        if (orientation == Block.Orientation.HORIZONTAL) {
            return row >= 0 && row < size && col >= 0 && col < size - 1 && blocks[row][col] == null && blocks[row][col + 1] == null;
        } else {
            return row >= 0 && row < size - 1 && col >= 0 && col < size && blocks[row][col] == null && blocks[row + 1][col] == null;
        }
    }
}