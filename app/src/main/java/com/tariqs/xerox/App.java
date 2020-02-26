package com.tariqs.xerox;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class App extends Activity {
    CardView customer,product,sales,report;
    String str,name,cas;
    String groupid,ID;
    ImageView exit;
    TextView title;
    String checkIfSaler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupid = getIntent().getStringExtra("groupid");
        ID = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        cas = getIntent().getStringExtra("cas");

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
        setContentView(R.layout.app);

        customer =(CardView)findViewById(R.id.customer);
        product =(CardView)findViewById(R.id.product);
        sales =(CardView)findViewById(R.id.sales);
        report = (CardView) findViewById(R.id.report);
        title = (TextView) findViewById(R.id.title);
       // title.setText(name);
      // exit = (ImageView) findViewById(R.id.exit);
     /*  exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(App.this,MainActivity.class);
                startActivity(i);            }
        });
*/
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "Customers";
                Intent i = new Intent(App.this,Add.class);
                i.putExtra("s",str);
                startActivity(i);
            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "Products";
                Intent i = new Intent(App.this,Add.class);
                i.putExtra("s",str);
                startActivity(i);
            }
        });
        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(App.this,Add.class);
                i.putExtra("s","Sales");
                startActivity(i);


            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(App.this,AddReport.class);
                i.putExtra("s","Reports");
                startActivity(i);
    }
        });
    }
    public void App1()
    {
       // setContentView(R.layout.addsale);
        name = getIntent().getStringExtra("name");

        Intent i = new Intent(App.this,AddSale2.class);
        i.putExtra("checkIfSaler","1");
        i.putExtra("name",name);

        startActivity(i);
    }
    public void App2()
    {        name = getIntent().getStringExtra("name");
        cas = getIntent().getStringExtra("cas");


    }
  /*  @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this, App.class);
        i.putExtra("groupid",groupid);
        i.putExtra("id",ID);
        i.putExtra("cas",cas);
        i.putExtra("name",name);
        startActivity(i);

        }
*/

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

////////////////////////////////////////////////////////////////////////////////////////


  ///////////////////////////////////////////////////////////////
}
