package com.example.registeractivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registeractivity.Connection.AdminSQLiteOpenHelper;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button loginbtn,regbtn,recoveryPasswordbtn;


    AdminSQLiteOpenHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        regbtn   = findViewById(R.id.regbtn);
        recoveryPasswordbtn = findViewById(R.id.recoveryPasswordbtn);
        DB = new AdminSQLiteOpenHelper(this);

        loginbtn.setOnClickListener(v -> {
            String usuario     = username.getText().toString();
            String contrasenna = password.getText().toString();

            if(username.equals("")||contrasenna.equals(""))
                Toast.makeText(LoginActivity.this, "Por favor ingrese todos los campos", Toast.LENGTH_SHORT).show();
            else{
                Boolean checkuserpass = DB.checkusernamepassword(usuario, contrasenna);
                if(checkuserpass==true){
                    Toast.makeText(LoginActivity.this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(getApplicationContext(), AlertActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        regbtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });

        recoveryPasswordbtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,RecoveryActivity.class);
            startActivity(intent);
            finish();
        });
    }
}