package com.carsonjones.tuber;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LandingPage extends AppCompatActivity {

    protected Button mFindTutorButton;
    protected Button mBrowseTutorsButton;
    protected Button mBecomeATutorButton;
    protected TextView mLearnMoreLink;
    protected TextView mSignInLink;

    // ID of activity
    public static final String UNIQUE_ID = "UNIQUE_ID";

    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        mFindTutorButton = (Button) findViewById(R.id.findATutorButton);
        mBrowseTutorsButton = (Button) findViewById(R.id.browseTutorsButton);
        mBecomeATutorButton = (Button) findViewById(R.id.becomeATutorButton);
        mLearnMoreLink = (TextView) findViewById(R.id.learnMoreTextView);
        mSignInLink = (TextView) findViewById(R.id.signInTextView);

        // Find Tutor Button
        mFindTutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, FindTutorActivity.class);
                startActivity(intent);
            }
        });

        // Browse Tutors Button
        mBrowseTutorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Become a Tutor Button
        mBecomeATutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, BecomeATutorActivity.class);
                startActivity(intent);
            }
        });

        // Learn More Link
        mLearnMoreLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, LearnMoreActivity.class);
                startActivity(intent);
            }
        });

        // Sign In Link
        mSignInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPage.this, SignInActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGED_IN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(LandingPage.this, ProfileActivity.class);
            intent.putExtra(UNIQUE_ID, "LANDING_PAGE");
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing_page, menu);
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
