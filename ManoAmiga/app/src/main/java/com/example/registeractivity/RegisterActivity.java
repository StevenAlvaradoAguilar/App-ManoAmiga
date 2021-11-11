package com.example.registeractivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registeractivity.Connection.ConnectionSQLServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText name, lastName, email, password;
    Button registerbtn;
    TextView status;

    EditText editText;
    TextView atoz, AtoZ, num, charcount;

    Connection con;
    Statement stmt;

    //@SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name        = findViewById(R.id.name);
        lastName    = findViewById(R.id.lastName);
        email       = findViewById(R.id.email);
        password    = findViewById(R.id.password);
        registerbtn = findViewById(R.id.registerbtn);
        status      = findViewById(R.id.status);

        // initialise layout
        editText = findViewById(R.id.password);
        atoz = findViewById(R.id.atoz);
        AtoZ = findViewById(R.id.AtoZ);
        num = findViewById(R.id.num);
        charcount = findViewById(R.id.charcount);

        registerbtn.setOnClickListener(v -> {
            validateEmailAddress(email);
            validatepass(password.getText().toString());
            new RegisterActivity.registerUser().execute("");
        });

        // when we start typing
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // get the password when we start typing
                String password = editText.getText().toString();
                validatepass(password);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
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


    public void validatepass(String password) {

        // check for pattern
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit     = Pattern.compile("[0-9]");

        // if lowercase character is not present
        if (!lowercase.matcher(password).find()) {
            atoz.setTextColor(Color.RED);
        } else {
            // if lowercase character is  present
            atoz.setTextColor(Color.GREEN);
        }

        // if uppercase character is not present
        if (!uppercase.matcher(password).find()) {
            AtoZ.setTextColor(Color.RED);
        } else {
            // if uppercase character is  present
            AtoZ.setTextColor(Color.GREEN);
        }
        // if digit is not present
        if (!digit.matcher(password).find()) {
            num.setTextColor(Color.RED);
        } else {
            // if digit is present
            num.setTextColor(Color.GREEN);
        }
        // if password length is less than 8
        if (password.length() < 8) {
            charcount.setTextColor(Color.RED);
            Toast.makeText(this,"Verificar la contraseña ingresada!", Toast.LENGTH_LONG).show();
        } else {
            charcount.setTextColor(Color.GREEN);
            Toast.makeText(this,"Contraseña Validada Satisfactoriamente!", Toast.LENGTH_LONG).show();
        }
    }

    public class registerUser extends AsyncTask<String, String, String>
    {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            status.setText("Sending Data to Database");
        }

        @Override
        protected void onPostExecute(String s) {
            status.setText("Registration Successful");
            name.setText("");
            lastName.setText("");
            email.setText("");
            password.setText("");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                con = connectionClass(ConnectionSQLServer.username, ConnectionSQLServer.password, ConnectionSQLServer.database, ConnectionSQLServer.ip, ConnectionSQLServer.port);
                if (con == null){
                    z = "Check your Internet Connection";
                }
                else{
                    String sql = "Insert into RegisterUser(_name,lastName,email,password) values ('"+name.getText().toString()+"','"+lastName.getText().toString()+"','"+email.getText().toString()+"','"+password.getText().toString()+"')";
                    stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                }

            }catch (Exception e)
            {
                isSuccess = false;
                z = e.getMessage();
            }
            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionClass(String username, String password, String database, String server, String port){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + server + "/" + port  + ";database name = " + database + ";username = " + username + ";password = " + password + ";";
            connection    = DriverManager.getConnection(connectionURL);
        }catch (Exception e){
            Log.e("SQL connection error", e.getMessage());
        }
        return connection;
    }
}