package com.example.registeractivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registeractivity.Connection.AdminSQLiteOpenHelper;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    EditText username, name, email, password;
    Button registerbtn,backbtn;
    TextView status;

    EditText editText;
    TextView atoz, AtoZ, num, charcount;

    AdminSQLiteOpenHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username    = findViewById(R.id.username);
        name        = findViewById(R.id.name);
        email       = findViewById(R.id.email);
        password    = findViewById(R.id.password);
        registerbtn = findViewById(R.id.registerbtn);
        backbtn     = findViewById(R.id.backbtn);
        status      = findViewById(R.id.status);

        // Validate password
        editText = findViewById(R.id.password);
        atoz = findViewById(R.id.atoz);
        AtoZ = findViewById(R.id.AtoZ);
        num = findViewById(R.id.num);
        charcount = findViewById(R.id.charcount);

        DB = new AdminSQLiteOpenHelper(this);

        registerbtn.setOnClickListener(v -> {
            String nombreUsuario = username.getText().toString();
            String nombre        = name.getText().toString();
            String correo        = email.getText().toString();
            String contrasenna   = password.getText().toString();

            if(nombreUsuario.equals("")||nombre.equals("")||correo.equals("")||contrasenna.equals(""))
                Toast.makeText(MainActivity.this,"Por favor ingrese todos los campos", Toast.LENGTH_LONG).show();
            else{
                if(validateEmailAddress(email)){
                    Boolean checkuser = DB.checkusername(nombreUsuario);
                    Toast.makeText(MainActivity.this,"Correo electrónico validado correctamente!", Toast.LENGTH_LONG).show();
                    if(validatepass(password)){
                        if(checkuser==false){
                            Boolean insert = DB.insertData(nombreUsuario, nombre, correo, contrasenna);
                            if(insert==true){
                                Toast.makeText(MainActivity.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "¡El usuario ya existe! Por favor, registrese", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Dirección de correo electrónico inválida!", Toast.LENGTH_LONG).show();
                }
            }
        });

        backbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public boolean validateEmailAddress(EditText email){
        String emailInput = email.getText().toString();

        if (!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            Toast.makeText(this,"Email Validated Succesfully!", Toast.LENGTH_LONG).show();
            return true;
        }else{
            Toast.makeText(this,"Invalid Email Address!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean validatepass(EditText password) {

        // check for pattern
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit     = Pattern.compile("[0-9]");

        // if lowercase character is not present
        if (!lowercase.matcher((CharSequence) password).find()) {
            atoz.setTextColor(Color.RED);
        } else {
            // if lowercase character is  present
            atoz.setTextColor(Color.GREEN);
        }

        // if uppercase character is not present
        if (!uppercase.matcher((CharSequence) password).find()) {
            AtoZ.setTextColor(Color.RED);
        } else {
            // if uppercase character is  present
            AtoZ.setTextColor(Color.GREEN);
        }
        // if digit is not present
        if (!digit.matcher((CharSequence) password).find()) {
            num.setTextColor(Color.RED);
        } else {
            // if digit is present
            num.setTextColor(Color.GREEN);
        }
        // if password length is less than 8
        if (password.length() < 8) {
            charcount.setTextColor(Color.RED);
            Toast.makeText(this,"Verificar la contraseña ingresada!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            charcount.setTextColor(Color.GREEN);
            Toast.makeText(this,"Contraseña Validada Satisfactoriamente!", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}