package com.example.tp1inf8405;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

public class RedBlock extends Block {
    private boolean isWin;

    public RedBlock(int x, int y, Orientation orientation, boolean isWin, Context context) {
        super(x,y,orientation, context);
        this.isWin = isWin;
    }

    public boolean getIsWin() {
        return isWin;
    }

    private void setIsWin(boolean isWin) {
        this.isWin = isWin;
    }

    public void checkWin(){
        if(super.getCol() == 4){
            setIsWin(true);
        }
    }
}