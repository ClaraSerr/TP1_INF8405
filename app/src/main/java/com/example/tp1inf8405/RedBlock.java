package com.example.tp1inf8405;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

public class RedBlock extends Block {
    private boolean isWin;

    public RedBlock(int x, int y, Orientation orientation, boolean isWin) {
        super(x,y,orientation);
        this.isWin = isWin;
    }

    public boolean getIsWin() {
        return isWin;
    }

    public void setIsWin(boolean isWin) {
        this.isWin = isWin;
    }
}