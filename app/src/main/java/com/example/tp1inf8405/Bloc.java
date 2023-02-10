package com.example.tp1inf8405;

import android.view.View;

import androidx.annotation.NonNull;

public class Bloc {
    String name;
    int row;
    int col;
    int row_span;
    int column_span;
    View view;
    boolean isHorizontal; //0 if vertical, 1 if horizontal

    boolean isTarget;
    Bloc(int row, int col, int row_span, int col_span, View v, String name, boolean isTarget){
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
    }
    //Constructeur pour la deepcopy
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
        return res;
        //return super.toString();
    }

    public void incCol(){
        int test =  0;
        if (this.column_span>1) {
            test = this.col + 1 + this.column_span;
        }else{
            test = this.col + 1;
        }
        if (test < 8 ){
            this.col = this.col +1;
        }
    }
    public void decCol(){
        if (((this.col - 1) > 0)){
            this.col = this.col -1;
        }
    }

    public void incRow(){
        this.row = this.row +1;
    }
    public void decRow(){
        if ((this.row - 1) > -1 ){
            this.row = this.row -1;}
    }

}