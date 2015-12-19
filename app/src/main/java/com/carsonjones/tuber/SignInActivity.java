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
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import com.carsonjones.tuber.SignUpActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    // Username for sending to other activities
    public static final String USER_NAME = "USER_NAME";

    // Password for sending to other activities
    public static final String PASSWORD = "PASSWORD";



    // ID of activity
    public static final String UNIQUE_ID = "UNIQUE_ID";

    // Set login url of where the php file is on the server
    private static final String LOGIN_URL = "http://www.madmensoftware.com/login.php";

    // Defining views
    private EditText editTextUserName;
    private EditText editTextPassword;
    private TextView signUpTextView;

    // Defining login button
    private Button buttonLogin;

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initializing views
        editTextUserName = (EditText) findViewById(R.id.usernameEditText);
        editTextPassword = (EditText) findViewById(R.id.passwordEditText);
        signUpTextView = (TextView) findViewById(R.id.signUpLink);
        buttonLogin = (Button) findViewById(R.id.submitButton);

        // When the sign up textview is clicked send to the sign up activity
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        //Adding click listener
        buttonLogin.setOnClickListener(this);
    }

    // The login method
    private void login(){
        //Getting values from edit texts
        String username = editTextUserName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // calling userlogin
        userLogin(username,password);
    }

    // Communicates with the login.php file and sees if the username and password is in the
    // User table of the madmen7_tuber database.
    private void userLogin(final String username, final String password){
        class UserLoginClass extends AsyncTask<String,Void,String>{
            // Declaring a loading spinner
            ProgressDialog loading;

            // Before the request to the php file is executed, show a loading spinner
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignInActivity.this,"Please Wait",null,true,true);
            }

            // After the request has been made either change the loggedIn preference to true and send
            // the user to the profile activity or display an invalid username and password toast.
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                // If the string that the login.php file sends back says success log the user in.
                if(s.trim().equalsIgnoreCase("success")){

                    //Creating a shared preference so that we can see and change the preferences
                    SharedPreferences sharedPreferences = SignInActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Adding values to editor, changing logged in shared preference to true,
                    // and changing the username shared preference to the username entered
                    editor.putBoolean(Config.LOGGED_IN_SHARED_PREF, true);
                    editor.putString(Config.USERNAME_SHARED_PREF, username);

                    //Saving values to editor
                    editor.commit();

                    Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
                    intent.putExtra(UNIQUE_ID, "SIGN_IN_ACTIVITY");
                    startActivity(intent);
                }
                // If it is not success then display an invalid username and password toast.
                else{
                    Toast.makeText(SignInActivity.this, "Invalid username or password",Toast.LENGTH_LONG).show();
                }
            }

            // While the request is being made, put the entered values into a hashmap and run it
            // through the sendPostRequest method along with the URL of the login.php file and
            // return the string that the method sends back which is what the server said.
            @Override
            protected String doInBackground(String... params) {
                // Create a new hashmap for storing the data entered
                HashMap<String,String> data = new HashMap<>();

                // put the username to the first index of the hashmap
                data.put("username",params[0]);

                // put the password to the second index of the hashmap
                data.put("password",params[1]);

                // create a new registeruserclass so that we can use its sendPostRequest method
                RegisterUserClass ruc = new RegisterUserClass();

                // store the result of running the sendPostRequest method using the
                // LOGIN_URL and the hashmap into a string called result
                String result = ruc.sendPostRequest(LOGIN_URL, data);

                // return the result
                return result;
            }
        }
        // create a new userloginclass
        UserLoginClass ulc = new UserLoginClass();

        // execute the Userloginclass with the username and password entered.
        ulc.execute(username,password);
    }

    // If the login button is clicked, run the login method.
    @Override
    public void onClick(View v) {

        if(v == buttonLogin){
            login();
        }
    }
}
