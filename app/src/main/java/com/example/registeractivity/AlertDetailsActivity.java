package com.example.registeractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlertDetailsActivity extends AppCompatActivity {

    Button emergencyCallbtn,cancelbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details);

        emergencyCallbtn = findViewById(R.id.emergencyCallbtn);
        cancelbtn   = findViewById(R.id.cancelbtn);

        emergencyCallbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(AlertDetailsActivity.this,AlertActivity.class);
                startActivity(intent);
                finish();*/
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertDetailsActivity.this,AlertActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}