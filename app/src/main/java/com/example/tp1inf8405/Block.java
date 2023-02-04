package com.example.tp1inf8405;

class Block {
    enum Orientation {
        HORIZONTAL, VERTICAL
    }

    private Orientation orientation;
    private int size;

    public Block(Orientation orientation) {
        this.orientation = orientation;
        this.size = (orientation == Orientation.HORIZONTAL) ? 2 : 1;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getSize() {
        return size;
    }
}