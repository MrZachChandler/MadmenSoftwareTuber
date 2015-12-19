package com.carsonjones.tuber;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String USER_NAME = "USER_NAME";

    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextPhoneNumber;
    private EditText editTextSchool;

    private Button buttonRegister;


    private static final String REGISTER_URL = "http://www.madmensoftware.com/register.php";

    // ID of activity
    public static final String UNIQUE_ID = "UNIQUE_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextName = (EditText) findViewById(R.id.nameEditText);
        editTextUsername = (EditText) findViewById(R.id.usernameEditText);
        editTextPassword = (EditText) findViewById(R.id.passwordEditText);
        editTextEmail = (EditText) findViewById(R.id.emailEditText);
        editTextPhoneNumber = (EditText) findViewById(R.id.phoneNumberEditText);
        editTextSchool = (EditText) findViewById(R.id.schoolEditText);


        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }



    private void registerUser() {
       // Parses and saves inputs to put into register method
        String name = editTextName.getText().toString().trim().toLowerCase();
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim().toLowerCase();
        String school = editTextSchool.getText().toString().trim().toLowerCase();

        // Registers users
        register(name,username,password,email, phoneNumber, school);
    }

    private void register(final String name, final String username, final String password,
                          final String email, final String phoneNumber, final String school) {

        // Register User class
        class RegisterUser extends AsyncTask<String, Void, String>{
            // creates a progress loading indicator
            ProgressDialog loading;

            // Creates a new register user class which
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignUpActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();

                if(s.trim().equalsIgnoreCase("success")){

                    //Creating a shared preference
                    SharedPreferences sharedPreferences = SignUpActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //Adding values to editor
                    editor.putBoolean(Config.LOGGED_IN_SHARED_PREF, true);
                    editor.putString(Config.NAME_SHARED_PREF, school);
                    editor.putString(Config.USERNAME_SHARED_PREF, username);
                    editor.putString(Config.PASSWORD_SHARED_PREF, school);
                    editor.putString(Config.EMAIL_SHARED_PREF, school);
                    editor.putString(Config.PHONE_NUMBER_SHARED_PREF, phoneNumber);
                    editor.putString(Config.SCHOOL_SHARED_PREF, school);


                    //Saving values to editor
                    editor.commit();

                    // Starting Profile Activity
                    Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
                    intent.putExtra(UNIQUE_ID, "SIGN_UP_ACTIVITY");
                    startActivity(intent);
                }
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("username",params[1]);
                data.put("password",params[2]);
                data.put("email",params[3]);
                data.put("phone_number",params[4]);
                data.put("school",params[5]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, username, password, email, phoneNumber, school);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
