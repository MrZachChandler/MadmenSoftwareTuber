package com.carsonjones.tuber;

/**
 * Created by Carson on 12/17/2015
 */
public class Config {
    //URL to our login.php file
    public static final String LOGIN_URL = "http://www.madmensoftware.com/login.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myTuberLoginPreference";

    //This would be used to store thename of current logged in user
    public static final String NAME_SHARED_PREF = "name";

    //This would be used to store the username of current logged in user
    public static final String USERNAME_SHARED_PREF = "username";

    //This would be used to store the email of current logged in user
    public static final String PASSWORD_SHARED_PREF = "password";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "username";

    //This would be used to store the username of current logged in user
    public static final String PHONE_NUMBER_SHARED_PREF = "username";

    //We will use this to store the boolean in sharedpreference to track user is logged in or not
    public static final String LOGGED_IN_SHARED_PREF = "loggedin";
}
