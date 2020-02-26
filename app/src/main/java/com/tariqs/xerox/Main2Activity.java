package com.tariqs.xerox;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CardView customer,product,sales,report,machine;
    String str,name,cas,Phone;
    String groupid,ID;
    ImageView exit;
    TextView title,Name,phone;
    String checkIfSaler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = (TextView) findViewById(R.id.user_name);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //groupid = getIntent().getStringExtra("groupid");
        //Fetching groupid from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        groupid =sharedPreferences.getString(Config.groupid_SHARED_PREF
                ,"Not Available");
        ID = sharedPreferences.getString(Config.id_SHARED_PREF
                ,"Not Available");
        name =sharedPreferences.getString(Config.name_SHARED_PREF
                ,"Not Available");
        cas =sharedPreferences.getString(Config.cas_SHARED_PREF
                ,"Not Available");
        Phone = sharedPreferences.getString(Config.Phone_SHARED_PREF,"Not Available");
        // ID = getIntent().getStringExtra("id");

        //name = getIntent().getStringExtra("name");
        //cas = getIntent().getStringExtra("cas");

        if (groupid.equals("0"))
        { ///App of 4 Tabs///
            App0();
        }
        else if (groupid.equals("1"))
        { ///App of 1 Tab that is Sale///
            App1();
        }
        else if (groupid.equals("2"))
        { ///App of 1 Tab that is .....///
            App2();
        }


    }
    public void App0()
    {

        //setContentView(R.layout.activity_app2);
        setContentView(R.layout.activity_main2);
        //Drawer
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        Name = (TextView) header.findViewById(R.id.Name);
        phone = (TextView) header.findViewById(R.id.phone);
        Name.setText(name);
       title.setText(name);
        phone.setText(Phone);
        navigationView.setNavigationItemSelectedListener(this);

        customer =(CardView)findViewById(R.id.customer);
        product =(CardView)findViewById(R.id.product);
        sales =(CardView)findViewById(R.id.sales);
        report = (CardView) findViewById(R.id.report);
        machine = (CardView) findViewById(R.id.machine);;
       // title.setText(name);
       // exit = (ImageView) findViewById(R.id.exit);

       /* exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating a shared preference
                SharedPreferences sharedPreferences = Main2Activity.this
                        .getSharedPreferences(Config.SHARED_PREF_NAME
                                , Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                sharedPreferences.edit().clear().apply();
                Intent i = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i);
            }
        });
*/
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "Customers";
                Intent i = new Intent(Main2Activity.this,Add.class);
                i.putExtra("s",str);
                startActivity(i);
            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "Products";
                Intent i = new Intent(Main2Activity.this,Add.class);
                i.putExtra("s",str);
                startActivity(i);
            }
        });
        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i = new Intent(Main2Activity.this,profieSaes.class);
            //    i.putExtra("s","Sales");
                Intent i = new Intent(Main2Activity.this,Sales.class);
                startActivity(i);


            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this,AddReport.class);
                i.putExtra("s","Reports");
                startActivity(i);


            }
        });
        machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this,AddMachine.class);
                i.putExtra("s","Machines");
                startActivity(i);


            }
        });
    }
    public void App1()
    {
        // setContentView(R.layout.addsale);
       // name = getIntent().getStringExtra("name");
        //Fetching name from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ID = sharedPreferences.getString(Config.id_SHARED_PREF
                ,"Not Available");
        name =sharedPreferences.getString(Config.name_SHARED_PREF
                ,"Not Available");

        Intent i = new Intent(Main2Activity.this,AddSale2.class);
        i.putExtra("checkIfSaler","1");
        i.putExtra("name",name);
        title.setText(name);

        startActivity(i);
    }
    public void App2()
    {
        //name = getIntent().getStringExtra("name");
        //cas = getIntent().getStringExtra("cas");
        //Fetching name and cas  from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ID = sharedPreferences.getString(Config.id_SHARED_PREF
                ,"Not Available");
        name =sharedPreferences.getString(Config.name_SHARED_PREF
                ,"Not Available");
        cas =sharedPreferences.getString(Config.cas_SHARED_PREF
                ,"Not Available");

        Intent i = new Intent(Main2Activity.this,Main3Activity.class);

        i.putExtra("id",ID);
        i.putExtra("cas",cas);
        i.putExtra("name",name);
        title.setText(name);

        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);

                    }
                }).setNegativeButton("no", null).show();
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

     if (id == R.id.cus) {
         str = "Customers";
         Intent i = new Intent(Main2Activity.this,Add.class);
         i.putExtra("s",str);
         startActivity(i);
        } else if (id == R.id.pro) {
         str = "Products";
         Intent i = new Intent(Main2Activity.this,Add.class);
         i.putExtra("s",str);
         startActivity(i);
        } else if (id == R.id.sale) {
         Intent i = new Intent(Main2Activity.this,Sales.class);
         startActivity(i);

        } else if (id == R.id.report) {
         Intent i = new Intent(Main2Activity.this,AddReport.class);
         i.putExtra("s","Reports");
         startActivity(i);
        }else if (id == R.id.signOut) {
         //Creating a shared preference
         SharedPreferences sharedPreferences = Main2Activity.this
                 .getSharedPreferences(Config.SHARED_PREF_NAME
                         , Context.MODE_PRIVATE);

         //Creating editor to store values to shared preferences
         SharedPreferences.Editor editor = sharedPreferences.edit();
         sharedPreferences.edit().clear().apply();
         Intent i = new Intent(Main2Activity.this,MainActivity.class);
         startActivity(i);
     }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
