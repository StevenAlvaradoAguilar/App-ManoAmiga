package com.example.registeractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RecoveryActivity extends AppCompatActivity {

    EditText coderecovery,confirm;
    Button getIntobtn,confirmbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        coderecovery = findViewById(R.id.coderecovery);
        confirm      = findViewById(R.id.confirm);
        getIntobtn   = findViewById(R.id.getIntobtn);
        confirmbtn   = findViewById(R.id.confirmbtn);

        getIntobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new RecoveryActivity().checkLogin().execute("");
            }
        });

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecoveryActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}