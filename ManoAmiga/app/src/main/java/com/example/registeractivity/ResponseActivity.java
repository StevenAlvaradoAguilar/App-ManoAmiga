package com.example.registeractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ResponseActivity extends AppCompatActivity {

    ImageButton profilebtn,backbtn,list_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        profilebtn = findViewById(R.id.profilebtn);
        list_item   = findViewById(R.id.list_item);
        backbtn    = findViewById(R.id.backbtn);

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResponseActivity.this,AlertActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResponseActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResponseActivity.this,AlertResponseDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}