package com.example.tp1inf8405;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

class BlockView extends View {
    private Block block;
    private Paint paint;

    public BlockView(Context context) {//ajouter parametre attrs
        super(context);//ajouter parametre attrs
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    public void setBlock(Block block) {
        this.block = block;
        if (block instanceof RedBlock){
            paint.setColor(Color.RED);
            Log.d("BlockViewColor", "RED");
        }else{
            Log.d("BlockViewColor","BLUE");
        }
    }

    public Block getBlock(){
        return this.block;
    }

    public Paint getPaint(){
        return this.paint;
    }


}
