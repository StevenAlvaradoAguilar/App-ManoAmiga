package com.example.registeractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AlertResponseDetailsActivity extends AppCompatActivity {

    ImageButton backbtn;
    Button helpbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_response_details);

        backbtn    = findViewById(R.id.backbtn);
        helpbtn    = findViewById(R.id.helpbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertResponseDetailsActivity.this,ResponseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        helpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertResponseDetailsActivity.this,AlertFollowupActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}