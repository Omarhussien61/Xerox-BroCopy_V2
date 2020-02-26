package com.tariqs.xerox;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class Libraries extends Activity {
Button show_report,show_sales,addbu,edit,changeB;
EditText papers,userN,passW;
TextView title;
RecyclerView RV;
LibraryAdapter adapter;
libSalesAdapter adapter2;
String no_of_papers,cusID,URL,phone,pass;
String bu,cas,name,groupid,ID;
public ArrayList<LibraryData> libraryData = new ArrayList<>();
public ArrayList<libSalesData> libsalesData = new ArrayList<>();
    ImageView exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.libraries);
        addbu = (Button) findViewById(R.id.addBu);
        papers = (EditText) findViewById(R.id.papers);
        show_sales = (Button) findViewById(R.id.b1);
        show_report =(Button) findViewById(R.id.b2);
        edit = (Button) findViewById(R.id.b3);
        RV = (RecyclerView) findViewById(R.id.Rv);
        title = (TextView) findViewById(R.id.title);
        groupid = getIntent().getStringExtra("groupid");
        ID = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        cas = getIntent().getStringExtra("cas");
        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
        title.setText(name);
        exit = (ImageView) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Libraries.this,MainActivity.class);
                startActivity(i);            }
        });

        addbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_of_papers=papers.getText().toString().trim();
                URL = "http://n.exeative.com/ABI/addlib.php";
                ID = getIntent().getStringExtra("id");
                cas = getIntent().getStringExtra("cas");
                if (cas.equals("1"))
                addno_of_Paper(ID,no_of_papers,URL);
                else
                    Toast.makeText(getApplicationContext(), "No Have Permissions To Add", Toast.LENGTH_LONG).show();

            }
        });
        show_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_of_papers=papers.getText().toString().trim();
                //URL = "http://n.exeative.com/ABI/selectsales.php";
                URL = "http://n.exeative.com/ABI/selectelib.php?customerid=";
                ID = getIntent().getStringExtra("id");
                bu = "re";
                load_data_from_server(0,ID,URL,bu);
                //showreport(cusID,URL);

                adapter = new LibraryAdapter(Libraries.this, libraryData);
                RV.setAdapter(adapter);
                RV.setLayoutManager(new LinearLayoutManager(Libraries.this)); // Vertical Orientation By Default
                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(Libraries.this); // (Context context)
                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                RV.setLayoutManager(mLinearLayoutManagerVertical);

            }
        });
        show_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_of_papers=papers.getText().toString().trim();
                URL = "http://n.exeative.com/ABI/selectsales.php?id=";
                ID = getIntent().getStringExtra("id");
                bu = "sa";
                load_data_from_server(0,ID,URL,bu);
                //showreport(cusID,URL);

                adapter2 = new libSalesAdapter(Libraries.this, libsalesData);
                RV.setAdapter(adapter2);
                RV.setLayoutManager(new LinearLayoutManager(Libraries.this)); // Vertical Orientation By Default
                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(Libraries.this); // (Context context)
                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                RV.setLayoutManager(mLinearLayoutManagerVertical);            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.editprofile);
                changeB = (Button) findViewById(R.id.changeB);
                userN = (EditText) findViewById(R.id.userN);
                passW = (EditText) findViewById(R.id.passW);



                URL = "http://n.exeative.com/ABI/editpro.php";
                ID = getIntent().getStringExtra("id");
                changeB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        phone=userN.getText().toString();
                        pass=passW.getText().toString();
                        editProfile(ID,phone,pass,URL);

                    }
                });
                //showreport(cusID,URL);

            }
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this, Libraries.class);
        i.putExtra("groupid",groupid);
        i.putExtra("id",ID);
        i.putExtra("cas",cas);
        i.putExtra("name",name);
        startActivity(i);    }


    public void editProfile(final String cusID,final String phone,final String pass,final String editURL)
    {
        Toast.makeText(getApplicationContext(),"Edited",Toast.LENGTH_LONG ).show();

        RequestQueue queue = Volley.newRequestQueue(Libraries.this);
        String response = null;

        final String finalResponse = response;

        StringRequest postRequest = new StringRequest (Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object = new JSONObject(response);


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

                params.put("id", cusID);
                params.put("phone", phone);
                params.put("pass", pass);


                return params;
            }
        }

                ;
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);




    }
    public void addno_of_Paper(final String cusID,final String no_of_papers,final String addno_of_Paper_URL)
    {
        Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_LONG ).show();

        RequestQueue queue = Volley.newRequestQueue(Libraries.this);
        String response = null;

        final String finalResponse = response;

        StringRequest postRequest = new StringRequest (Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object = new JSONObject(response);


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

                params.put("id", cusID);
                params.put("num", no_of_papers);

                return params;
            }
        }

        ;
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);




    }
    public void showreport(final String cusID,final String url)
    {
        Toast.makeText(getApplicationContext(),"Shown",Toast.LENGTH_LONG ).show();
    /*    String id= "1";
        String numof = "4000";
        String dateadd = "1996/02/14";


        LibraryData data = new LibraryData(id,numof, dateadd);

        //Add each object with value in the array 'libraryData'
        libraryData.add(data);
*/
        RequestQueue queue = Volley.newRequestQueue(Libraries.this);
        String response = null;

        final String finalResponse = response;

        StringRequest postRequest = new StringRequest (Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object = new JSONObject(response);
                           String id= object.getString("id");
                            String numof = object.getString("numberof");
                            String dateadd = object.getString("dateadd");


                            LibraryData data = new LibraryData(id,numof, dateadd);

                            //Add each object with value in the array 'productsData'
                            libraryData.add(data);


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
        )
        /*

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //the parameters that will taken with the request method

                params.put("id", "20");

                return params;
            }
        }
        */
        ;
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);




    }
    /////////////////////////////////////////////////////////////////////////////////

    private void load_data_from_server(int id,final String S,final String url,final String bu) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        //we put tge 'id' here to get the data by id of the customer
                        .url( url+"20")
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    libraryData.clear();
                    libsalesData.clear();

                    for (int i=0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        if (bu.equals("re")) {
                            String id = object.getString("id");
                            String numof = object.getString("numberof");
                            String dateadd = object.getString("dateadd");


                            LibraryData data = new LibraryData(id, numof, dateadd);

                            //Add each object with value in the array 'libraryData'
                            libraryData.add(data);
                        }
                        else if (bu.equals("sa"))
                        {
                            String bill_name = object.getString("bill_name");
                            String bill_id = object.getString("bill_id");
                            String bill_date = object.getString("bill_date");
                            String total_b_desc = object.getString("total_b_desc");
                            String disc = object.getString("disc");
                            String total_a_desc = object.getString("total_a_desc");
                            String tax14 = object.getString("tax14");
                            String total_a_tax = object.getString("total_a_tax");

                            libSalesData data2 = new libSalesData(bill_id, bill_name, bill_date, total_b_desc
                                    ,disc,total_a_desc,tax14,total_a_tax);
                            libsalesData.add(data2);
                        }

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };
        task.execute(id);
    }
    }

