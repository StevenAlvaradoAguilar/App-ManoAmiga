package com.example.registeractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AlertActivity extends AppCompatActivity {

    ImageButton profilebtn,backbtn,alertbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);


        profilebtn = findViewById(R.id.profilebtn);
        backbtn    = findViewById(R.id.backbtn);
        alertbtn   = findViewById(R.id.alertbtn);

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertActivity.this,ResponseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        alertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertActivity.this,AlertDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}