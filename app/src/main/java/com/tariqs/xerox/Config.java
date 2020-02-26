package com.tariqs.xerox;

public class Config {
    //URL to our login.php file
    public static final String LOGIN_URL = "http://192.168.94.1/Android/LoginLogout/login.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";
    public static final int TOTAL_SHARED_PREF = 0;
    //This would be used to store the phone of current logged in user
    public static final String Phone_SHARED_PREF = "phone";
    public static final String groupid_SHARED_PREF = "groupid";
    public static final String id_SHARED_PREF = "id";
    public static final String cas_SHARED_PREF = "cas";
    public static final String name_SHARED_PREF = "name";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String edit_SHARED_PREF = "false";

}
