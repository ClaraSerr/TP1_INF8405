package com.example.tp1inf8405;

        import androidx.appcompat.app.AppCompatActivity;
        import android.content.Intent;

        import android.view.View;
        import android.widget.Button;
        import android.util.Log;
        import android.widget.Toast;
        import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void moreinf(View v){
        //test from Studio 3
        Intent i= new Intent(this, More_Information.class);
        startActivity(i);
    }

    public void exit() {
        // super.onBackPressed();
        finish();
    }

}