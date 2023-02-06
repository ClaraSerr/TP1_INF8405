package com.example.tp1inf8405;

class Block {
    private int x;
    private int y;
    enum Orientation {
        HORIZONTAL, VERTICAL
    }
    private Orientation orientation;

    public Block(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public boolean isHorizontal() {
        return orientation == Orientation.HORIZONTAL;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveUp() {
        y--;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }
}