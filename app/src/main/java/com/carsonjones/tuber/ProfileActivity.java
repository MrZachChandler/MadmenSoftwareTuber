package com.carsonjones.tuber;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.logging.Handler;

public class ProfileActivity extends AppCompatActivity {

    // Declaring views
    private TextView mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Setting mUsername to the usernameTextView
        mUsername = (TextView) findViewById(R.id.usernameTextView);


        //Fetching username from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Config.USERNAME_SHARED_PREF, "Not Available");


        // Getting the last activity that the user came from
        Intent intent = getIntent();

        // Getting the information sent from the last activity in the form of a bundle
        Bundle bundle = getIntent().getExtras();

        // If there is data in the Extra's from the last activity
        if (bundle != null) {
            // Get the unique id put from the last activity
            String activityId = bundle.getString("UNIQUE_ID");

            // if there is no activity id print an error message
            if(activityId == null) {
                mUsername.setText("Error cannot determine last activity ID");
            }
            // If from sign in activity set the TextView to Welcome back message
            else if(activityId.equals("SIGN_IN_ACTIVITY") || activityId.equals("LANDING_PAGE")) {
                mUsername.setText("Welcome back, " + username + "!");
            }
            // if from sign up activity set the textview to welcoming message
            // later make it a short intro to Tuber.
            else if(activityId.equals("SIGN_UP_ACTIVITY"))
            {
                mUsername.setText("Welcome to Tuber, " + username + "!");
            }
        }

    }

    //Logout function
    private void logout(){

        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Set the message of the alert dialog
        alertDialogBuilder.setMessage("Are you sure you want to logout?");

        // Set the click listener for the positive button to change the status of loggedIn to false
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Putting the value false for loggedin
                        editor.putBoolean(Config.LOGGED_IN_SHARED_PREF, false);

                        //Putting blank value to username
                        editor.putString(Config.USERNAME_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(ProfileActivity.this, LandingPage.class);
                        startActivity(intent);
                    }
                });

        // Set the click listener for the negative button to exit the dialog
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    // When something in the options is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            //calling logout method when the logout button is clicked
            if (id == R.id.menuLogout) {
                logout();
            }

            return super.onOptionsItemSelected(item);
    }
}
