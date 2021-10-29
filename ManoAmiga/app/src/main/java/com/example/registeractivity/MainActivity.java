package com.example.registeractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.registeractivity.Session.SessionManager;

public class MainActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();
    }
}