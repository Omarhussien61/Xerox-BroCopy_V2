package com.tariqs.xerox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {
    Button login;
    ProgressBar pb;
    ProgressDialog loading;

    Gson gson;
    private Snackbar snackbar;
    EditText username,password;
    String S_user,S_pass,URL;

    //boolean variable to check user is logged in or not
    //initially it is false
    private boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        pb = (ProgressBar) findViewById(R.id.pBar);

        username =(EditText)findViewById(R.id.userN);
        password =(EditText)findViewById(R.id.passW);
        login =(Button)findViewById(R.id.loginB);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();//function to login
            }
        });

    }
    ////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////
    public void login(){
        //the definitions of variables
        initialize();
        // 'validate' method is used to do the validations
        if(!validate())
        {
            Toast.makeText(this,"Your Data has Error",Toast.LENGTH_LONG).show();
        }
        else
        {
            //the method that send the phone and password to database to check if right
                loginIsValid();
            //es(); //for test
        }
    }
    /////////////////////
    public  void es()
    {
        Intent i = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(i);    }
    ////////////////////

    public void initialize()
    {        //URL="https://rotq4all.000webhostapp.com/UserLogin.php";

        URL="http://www.mixpix-xerox.com/ABI/login.php";
         S_user=username.getText().toString().trim();
         S_pass=password.getText().toString().trim();
    }

    public boolean validate(){

        boolean valid =true;
        if(S_pass.isEmpty()||S_pass.length()<2)
        {password.setError("Your Password is Empty or Short");
            valid=false;
        }
        if(S_user.isEmpty())
        {
            username.setError("Your UserName is Empty");
            valid=false;

        }


        return valid;
    }
    ///////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent i = new Intent(MainActivity.this
                    , Main2Activity.class);

            startActivity(i);
        }

    }
    ///////////////////////////////////////////////////
    public  void loginIsValid(){
        //to show the progress bar
       // pb.setVisibility(View.VISIBLE);
        loading = ProgressDialog.show(MainActivity.this, "Signing in ....", "Please wait...", true, true);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String response = null;


        final String finalResponse = response;

        StringRequest postRequest = new StringRequest (Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);

                            //to hide the progress bar
                            //pb.setVisibility(View.GONE);
                            loading.dismiss();

                            String error = jsonObject.getString("error");
                            //String msg = jsonObject.getString("message");
                           // showSnackbar("Invalid Phone or Password");

                            //JSONArray jsonArray = new JSONArray(response);
                            if (error.equals("false")) {
                               String id = jsonObject.getString("id");
                                String username = jsonObject.getString("phone");
                                String groupid = jsonObject.getString("GroupID");
                                String cas = jsonObject.getString("case");
                                String name = jsonObject.getString("name");
                                //String msg = jsonObject.getString("message");
                                //String GroupId = jsonObject.getString("GroupId");
                                Toast.makeText(getApplicationContext(), "Successfully login", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(MainActivity.this
                                        , Main2Activity.class);
                                i.putExtra("groupid",groupid);
                                i.putExtra("id",id);
                                i.putExtra("cas",cas);
                                i.putExtra("name",name);
                                startActivity(i);
                                //Creating a shared preference
                                SharedPreferences sharedPreferences = MainActivity.this
                                        .getSharedPreferences(Config.SHARED_PREF_NAME
                                                , Context.MODE_PRIVATE);

                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                //Adding values to editor
                                editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                                editor.putString(Config.Phone_SHARED_PREF
                                        , username);
                                editor.putString(Config.groupid_SHARED_PREF
                                        , groupid);
                                editor.putString(Config.id_SHARED_PREF
                                        , id);
                                editor.putString(Config.cas_SHARED_PREF
                                        , cas);

                                editor.putString(Config.name_SHARED_PREF
                                        , name);
                                //Saving values to editor
                                editor.commit();


                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /*  Log.d("ErrorResponse", finalResponse); */
                        VolleyLog.d("ErrorResponse", finalResponse);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //the parameters that will taken with the request method
            /*    params.put("Email", S_user);
                params.put("Password", S_pass);
              */
                params.put("phone", "58");
                params.put("password", "2010");

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);




    }
    public void showSnackbar(String stringSnackbar){
        snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

//////////////////////////////////////////////////////////////
@Override
public void onBackPressed() {
    new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
            .setMessage("Are you sure?")
            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton("no", null).show();
}
    ///////////////////////////////////////////////////////
}
