package com.tariqs.xerox;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    private static int splash_time_out=4000;

    Animation anmi;
    TextView load;
    Button buttonConect;
    boolean connected = false;
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        load= (TextView) findViewById(R.id.load);
        ImageView logo= (ImageView) findViewById(R.id.imgee);
        buttonConect=  findViewById(R.id.buttonConect);
        buttonConect.setVisibility(View.INVISIBLE);

        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
            connected = true;
        }
        else
            connected = false;
        anmi= AnimationUtils.loadAnimation(this,R.anim.load);
        // load.setAnimation(anmi);
        logo.setAnimation(anmi);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (connected) {
                    Intent homeintent = new Intent(Splash.this, MainActivity.class);
                    //homeactivity name of second layout.java
                    startActivity(homeintent);
                    finish();
                }
                else {
                    load.setText("No internet Connection");
                    buttonConect.setVisibility(View.VISIBLE);
                }

            }
        },splash_time_out);

    }

    public void cloked(View view) {
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            Intent homeintent = new Intent(Splash.this, MainActivity.class);
            //homeactivity name of second layout.java
            startActivity(homeintent);
            finish();
        }

    }
}
