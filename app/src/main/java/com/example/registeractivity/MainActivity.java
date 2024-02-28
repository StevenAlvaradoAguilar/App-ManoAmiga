package com.example.registeractivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
    Button registerbtn,loginbtn;
    TextView status;

    EditText editText;
    TextView atoz, AtoZ, num, charcount, specialschar;

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
        loginbtn     = findViewById(R.id.loginbtn);
        status      = findViewById(R.id.status);

        // Validate password
        editText     = findViewById(R.id.password);
        atoz         = findViewById(R.id.atoz);
        AtoZ         = findViewById(R.id.AtoZ);
        num          = findViewById(R.id.num);
        specialschar = findViewById(R.id.specialschar);

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
                if(validateEmailAddress(correo) && validatepass(contrasenna)){
                    Boolean checkuser = DB.checkusername(nombreUsuario);
                    if(checkuser==false){
                        Boolean insert = DB.insertData(nombreUsuario, nombre, correo, contrasenna);
                        if(insert==true){
                            Toast.makeText(MainActivity.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "¡El usuario ya existe! Por favor, inicie sesión", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public boolean validateEmailAddress(String email){

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Correo electrónico validado correctamente!!", Toast.LENGTH_LONG).show();
            return true;
        }else{
            Toast.makeText(this,"Dirección de correo electrónico inválida!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean validatepass(String password) {

        boolean validationupper   = false;
        boolean validationlower   = false;
        boolean validationdigit   = false;
        boolean validationspecial = false;
        boolean validationlenght   = false;

        // check for pattern
        Pattern uppercase     = Pattern.compile("[A-Z]");
        Pattern lowercase     = Pattern.compile("[a-z]");
        Pattern digit         = Pattern.compile("[0-9]");
        Pattern specialdigits = Pattern.compile("(?=.*[@#$_–/]).+");

        // if lowercase character is not present
        if (!lowercase.matcher((CharSequence) password).find()) {
            atoz.setTextColor(Color.RED);
        } else {
            // if lowercase character is  present
            atoz.setTextColor(Color.GREEN);
            validationlower = true;
        }

        // if uppercase character is not present
        if (!uppercase.matcher((CharSequence) password).find()) {
            AtoZ.setTextColor(Color.RED);
        } else {
            // if uppercase character is  present
            AtoZ.setTextColor(Color.GREEN);
            validationupper = true;
        }

        //
        if (!specialdigits.matcher((CharSequence) password).find()) {
            specialschar.setTextColor(Color.RED);
            System.out.println("Fallee");
        } else {
            System.out.println("Paseee");
            // if specialschar is present
            specialschar.setTextColor(Color.GREEN);
            validationspecial = true;
        }

        // if digit is not present
        if (!digit.matcher((CharSequence) password).find()) {
            num.setTextColor(Color.RED);
        } else {
            // if digit is present
            num.setTextColor(Color.GREEN);
            validationdigit = true;
        }

        // if password length is less than 8
        if (password.length() < 8) {
            charcount.setTextColor(Color.RED);
            Toast.makeText(this,"Verificar la contraseña ingresada!", Toast.LENGTH_LONG).show();
        } else {
            charcount.setTextColor(Color.GREEN);
            Toast.makeText(this,"Contraseña Validada Satisfactoriamente!", Toast.LENGTH_LONG).show();
            validationlenght = true;
        }return validationupper && validationlower && validationdigit && validationspecial && validationlenght;
    }
}