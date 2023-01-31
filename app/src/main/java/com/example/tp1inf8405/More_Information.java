package com.example.tp1inf8405;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class More_Information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);
        //Emile comment 3
    }
    public void backmenu(View v){
        Intent i= new Intent(this, MainActivity.class);
        startActivity(i);
    }

}