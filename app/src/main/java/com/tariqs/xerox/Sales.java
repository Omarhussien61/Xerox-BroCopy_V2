package com.tariqs.xerox;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.List;

public class Sales extends Activity {
    public ArrayList<SalesModel> salesModels = new ArrayList<>();
    public List<SalesModel> filteredsalesModels;
    SalesAdapter adapter;
    RecyclerView recyclerView;
    Button nameBu,dateBu;
    ImageView btnSave;
    TextView title;
    EditText search;
    boolean datebu,namebu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales);
        datebu = false;
        namebu = true;
        ///////////////////////////////////////////////
       // String url = "http://n.exeative.com/ABI/returnsales.php";
        String url = "http://www.mixpix-xerox.com/ABI/today.php";

        ShowinDB(url);
        btnSave = (ImageView) findViewById(R.id.btnSave);
        title = (TextView) findViewById(R.id.title);
        title.setText("Sales");
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        //the recycler view definition and initilization
        ////////////////////////////////////////////////
        search = (EditText) findViewById(R.id.search);
        dateBu = (Button) findViewById(R.id.dateBu);
        nameBu= (Button) findViewById(R.id.nameBu);
        dateBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datebu = true;
                namebu = false;
                dateBu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                        , R.color.colorPrimaryAddSale));
                dateBu.setTextColor(ContextCompat.getColor(getApplicationContext()
                        , R.color.white));
                ///////////////////////////////////
                nameBu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                        , R.color.white));
                nameBu.setTextColor(ContextCompat.getColor(getApplicationContext()
                        , R.color.colorPrimaryAddSale));
            }
        });
        nameBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datebu = false;
                namebu = true;
                nameBu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                        , R.color.colorPrimaryAddSale));
                nameBu.setTextColor(ContextCompat.getColor(getApplicationContext()
                        , R.color.white));
                ///////////////////////////////////
                dateBu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                        , R.color.white));
                dateBu.setTextColor(ContextCompat.getColor(getApplicationContext()
                        , R.color.colorPrimaryAddSale));
            }
        });
        //adding a TextChangedListener
        //to call a method whenever there is some change on the EditText
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString(),datebu,namebu);
            }
        });
        //////////////////////////////////////////////////////
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sales.this,AddSale2.class);
                i.putExtra("checkIfSaler","0");
                startActivity(i);
            }
        });
    }
    //Search Service
    private void filter(String text,boolean datebu,boolean namebu) {
        //new array list that will hold the filtered data
        ArrayList<SalesModel> filterdSales = new ArrayList<>();

        //looping through existing elements
        for (SalesModel obj : salesModels) {
            //if Name is checked
            if (namebu) {
                //if the existing elements contains the search input
                if (obj.getBill_name().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdSales.add(obj);
                }
            }
            else if (datebu)
            {
                //if the existing elements contains the search input
                if (obj.getBill_date().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdSales.add(obj);
                }
            }

        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filteredsalesModels(filterdSales);
    }
    //Show data from DB
    public void ShowinDB( String showUrl) {


        ///////////////////////////////////////////////////////

        RequestQueue queue = Volley.newRequestQueue(Sales.this);
        String cat = null;


        final String finalResponse = cat;

        StringRequest postRequest = new StringRequest(Request.Method.GET, showUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String cat) {


                        try {

                            JSONArray jsonArray = new JSONArray(cat);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Toast.makeText(getApplicationContext(), "hhhhhhhh", Toast.LENGTH_LONG).show();
                                //String id = object.getString("bill_id");

                                String name = object.getString("bill_name");
                                String date = object.getString("PRO_NAME");
                                String total = object.getString("bi_total");
                                String num = object.getString("bill_id");
                                //String date = object.getString("bill_date");

                               // String num = object.getString("bill_num");
                              //  String total = object.getString("total");
                              //  String paid = object.getString("paid");
                               // String remain = object.getString("remainder");
                              //  String phone = object.getString("phone");

                               SalesModel salesModelObj = new SalesModel("9",name, date
                                       , num, total,"9","0","0100");
                               // SalesModel salesModelObj = new SalesModel("8","nn", "bjnb"
                                 //             , "86", "990","98","987","0100");

                                //Add each object with value in the array 'salesModels'
                                salesModels.add(salesModelObj);
                                //the recycler view definition and initilization
                                adapter = new SalesAdapter(Sales.this, salesModels);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Sales.this)); // Vertical Orientation By Default
                                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(Sales.this); // (Context context)
                                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(mLinearLayoutManagerVertical);


                            }

                            //JSONObject jsonResponse = new JSONObject(cat);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        /*  Log.d("ErrorResponse", finalResponse); */
                        VolleyLog.d("ErrorResponse", finalResponse);
                    }
                }
        );
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }
    ////////////////////////////////////////////////////////////////////

}
