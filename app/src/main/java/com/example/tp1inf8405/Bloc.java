package com.example.tp1inf8405;

import android.view.View;
import android.widget.GridLayout;
import android.content.Context;

import androidx.annotation.NonNull;
/**
 * This bloc class makes the views inside the grid layout behave like actual blocs. Incrementing and decrementing the column and rows.
 *
 * */
public class Bloc{
    String name;
    int row; //currrent
    int col; //currrent
    int row_span;
    int column_span;
    View view;
    boolean isHorizontal; //0 if vertical, 1 if horizontal
    int height =0;
    int width = 0;

    int original_row;
    int original_col;
    boolean isTarget;
    Bloc (int row, int col, int row_span, int col_span, View v, String name, boolean isTarget){
        this.row = row;
        this.col = col;
        this.row_span = row_span;
        this.column_span = col_span;
        this.view = v;
        this.name = name;
        this.isTarget = isTarget;
        if (row_span > col_span) {
            this.isHorizontal = false;
        }
        else{
            this.isHorizontal = true;
        }
        this.original_row = row;
        this.original_col = col;
        this.width = 50*column_span;
        this.height = 50*row_span;
    }
    /**
     * This constructor is used uniquely for making deep copies
     * */
    Bloc(Bloc bloc){
        this.row = bloc.row;
        this.col = bloc.col;
        this.row_span = bloc.row_span;
        this.column_span = bloc.column_span;
        this.view = bloc.view;
        this.name = bloc.name;
        this.isTarget = bloc.isTarget;
        if (bloc.row_span > bloc.column_span) {
            this.isHorizontal = false;
        }
        else{
            this.isHorizontal = true;
        }
        this.original_row = bloc.row;
        this.original_col = bloc.col;
        this.height = bloc.height;
        this.width = bloc.width;
    }

    @NonNull
    @Override
    public String toString() {
        String res = "for car " + this.name + " \n ";
        res = res + "row = " + Integer.toString(row) + " \n ";
        res += "row_span = " + Integer.toString(row_span) + " \n ";
        res += "col = " + Integer.toString(col) + " \n ";
        res += "column_span = " + Integer.toString(column_span) + " \n ";
        res += "horizontal = " + Boolean.toString(isHorizontal) + " \n";
        res += "original_col = " + Integer.toString(original_col) + " \n ";
        res += "original_row = " + Integer.toString(original_row) + " \n ";
        return res;
        //return super.toString();
    }
    /**
     * Function that increments the column
     * */
    public void incCol(){
        int test =  0;
        if (isHorizontal){
        if (this.column_span>1)  {
            test = this.col + 1 + this.column_span;
        }else{
            test = this.col + 1;
        }
        if (test < 8 ) { //this is a cheat because that's Grid class job
            this.col = this.col +1;
        }
        }
    }
    /**
     * Function that decrements the column
     * */
    public void decCol(){
        if (((this.col - 1) > 0)&& (isHorizontal)){
            this.col = this.col -1;
        }
    }
    /**
     * Function that increments the line
     * */
    public void incRow(){
        if (!isHorizontal){
        this.row = this.row +1;
        }
    }
    /**
     * Function that decrements the line
     * */
    public void decRow(){
        if (((this.row - 1) > -1 ) && (!isHorizontal)){
            this.row = this.row -1;}
    }
    /** Function used to update the original position of the bloc once the bloc has been
     *  sucessfully mooved
     * */
    public void update_original_pos(){
        this.original_col = this.col;
        this.original_row = this.row;
    }
    /** Function that update the view linked to the bloc, shoudl work well
     * */
    public void update_view(){
        float factor = this.view.getContext().getResources().getDisplayMetrics().density;
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) this.view.getLayoutParams();
        layoutParams.columnSpec = GridLayout.spec(this.col,this.column_span);
        layoutParams.rowSpec =  GridLayout.spec(this.row,this.row_span);
        layoutParams.height = (int) (this.height* factor);
        layoutParams.width = (int) (this.width* factor);
        this.view.setLayoutParams(layoutParams);
    }

    public void set_view(View v){
        this.view = v;
    }
}