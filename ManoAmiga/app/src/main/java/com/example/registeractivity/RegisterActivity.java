package com.example.registeractivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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

    Connection con;
    Statement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name        = (EditText)findViewById(R.id.name);
        lastName    = (EditText)findViewById(R.id.lastName);
        email       = (EditText)findViewById(R.id.email);
        password    = (EditText)findViewById(R.id.password);
        registerbtn = (Button)findViewById(R.id.registerbtn);
        status      = (TextView)findViewById(R.id.status);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                validateEmailAddress(email);
                new registerUser().execute("");
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

    public void registerUserValidations(View view) {

        TextView name = (TextView) findViewById(R.id.name);
        TextView lastName = (TextView) findViewById(R.id.lastName);
        TextView email = (TextView) findViewById(R.id.email);
        TextView password = (TextView) findViewById(R.id.password);
        TextView status = (TextView) findViewById(R.id.status);

        if (name.getText().toString().length() < 1 || lastName.getText().toString().length() < 1 || email.getText().toString().length() < 1 || password.getText().toString().length() < 1) {
            status.setText("Favor no dejar campos en blanco.");
        } else {
            String regex_password = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$_–[{}]:;',?/*~$^+=<>]).{8,20}$";
            Pattern pattern_password = Pattern.compile(regex_password);

            String regex_email = "^(.+)@(.+)$";
            Pattern pattern_email = Pattern.compile(regex_email);


            if (!pattern_password.matcher(password.getText().toString()).matches()) {
                status.setText("Favor ingresar una contraseña con al menos una mayúscula, un símbolo(@, #, $, _, -) y un número de 8 a 20 caracteres.");
            } else if (!pattern_email.matcher(email.getText().toString()).matches()) {
                status.setText("Favor ingresar un correo válido");
            }
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
                con = connectionClass(ConnectionSQLServer.username.toString(), ConnectionSQLServer.password.toString(), ConnectionSQLServer.database.toString(), ConnectionSQLServer.ip.toString(), ConnectionSQLServer.port.toString());
                if (con == null){
                    z = "Check your Internet Connection";
                }
                else{
                    String sql = "Insert into DBO.registroUsuario(nameU,lastName,email,password) values ('"+name.getText().toString()+"','"+lastName.getText().toString()+"','"+email.getText().toString()+"','"+password.getText().toString()+"')";
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
            connectionURL = "jdbc:jtds:sqlserver://" + server + ":" + port  + ";database name =" + database + ";username =" + username + ";password =" + password + ";";
            connection    = DriverManager.getConnection(connectionURL);
        }catch (Exception e){
            Log.e("SQL connection error", e.getMessage());
        }
        return connection;
    }
}