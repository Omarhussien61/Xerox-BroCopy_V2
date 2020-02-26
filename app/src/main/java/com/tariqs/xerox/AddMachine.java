package com.tariqs.xerox;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMachine extends Activity {
     View v;
     String str,id,showUrl,cusName,cusID;
    String Mac_id,cusname,macname,papers,adddate,mid,cid;
     Button post;
     Spinner cus;
     ImageView btnSave;
     EditText midX,cidX,search,papX,maxX,moX;
    public String Fs,Ts;
    RelativeLayout RDate;
    List<String> CustomersSpin = new ArrayList<String>();
    List<String> cusIDs = new ArrayList<String>();
    String midS,cidS,DateS,papS,maxs,mos;
    TextView title,DateX;
    MachineAdapter adapter;
    public ArrayList<machinesModel> machinesData = new ArrayList<>();
    RecyclerView recyclerView;
    String S ,ids,addUrl;
    ImageView remove;
    Snackbar snackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        S = getIntent().getStringExtra("s");
        setContentView(R.layout.add2);

        showUrl = "http://www.mixpix-xerox.com/ABI/allmach.php";
        Addition(v,str,id);

        ShowinDB(showUrl);


    }
    //Methode Show Date Picker
    public void DatePickerMethode(){
        RDate = (RelativeLayout) findViewById(R.id.RDate);
        RDate.setVisibility(View.VISIBLE);
        final DatePicker simpleDatePicker = (DatePicker)findViewById(R.id.date_picker); // initiate a date picker
               /* int month = simpleDatePicker.getMonth(); // get the selected month
                int day = simpleDatePicker.getDayOfMonth(); //get the selected day
                int year = simpleDatePicker.getYear(); //get the selected year
               */
        DateX = (TextView) findViewById(R.id.DateX);

        Button date_time_set = (Button) findViewById(R.id.date_time_set);
        date_time_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RDate.setVisibility(View.GONE);

                String Y = String.valueOf(simpleDatePicker.getYear());
                String M = String.valueOf((simpleDatePicker.getMonth()+1));
                String D = String.valueOf(simpleDatePicker.getDayOfMonth());
                Fs= Y+"-"+M+"-"+D;
                DateX.setText(Fs);
            }
        });

    }
    public void Addition(final View v, final String str, final String id) {

        btnSave = (ImageView) findViewById(R.id.btnSave);
        //listView = (ListView) findViewById(R.id.listView);
        title = (TextView) findViewById(R.id.title);
        search = (EditText) findViewById(R.id.search);
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
                filter(editable.toString());
            }
        });
        //the recycler view definition and initilization
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        adapter = new MachineAdapter(AddMachine.this, machinesData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddMachine.this)); // Vertical Orientation By Default
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddMachine.this); // (Context context)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
        ////////////////////////////////////////////////

        title.setText(S);
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ids ="";
                String ids2="";

                    addMachine(v,ids,Mac_id,papers,adddate,mid,cid,papS,maxs,mos);

                //Addition(strItem);
            }
        /*        Log log = new Log();
                log.setItem(strItem);
                logArrayAdapter.add(log);
                logArrayAdapter.notifyDataSetChanged();
        */

        });
//////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new Interface.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                String ids;
                String ids2="";

                    ids = machinesData.get(position).getId();
                    midS = machinesData.get(position).getMid();
                    cidS = machinesData.get(position).getCid();
                    DateS = machinesData.get(position).getAdddate();
                    papS = machinesData.get(position).getPap();
                    maxs = machinesData.get(position).getMax();
                     Toast.makeText(AddMachine.this, "update machine"+ids,
                            Toast.LENGTH_LONG).show();
                    addMachine(v,ids,Mac_id,papers,DateS,midS,cidS,papS,maxs,mos);


                //updatefromServer(updateUrl,ids);
            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));
    }
    //////////////////////////////////////////////////////
    //Search Service
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<machinesModel> filterd = new ArrayList<>();

        {
            //looping through existing elements
            for (machinesModel obj : machinesData) {
                //if Name is checked
                 {
                    //if the existing elements contains the search input
                    if (obj.getMid().toLowerCase().contains(text.toLowerCase())) {
                        //adding the element to filtered list
                        filterd.add(obj);
                    }
                }

            }
            //calling a method of the adapter class and passing the filtered list
            adapter.filteredmachines(filterd);
        }



    }
    //////////////////////////////////////////////////////////////
    public void addMachine(View v,final String ids,String Mac_id,String papers,String adddate
            ,String mid,String cid,String pap,String max,String mo)
    {
        final String updateUrl;
        setContentView(R.layout.addmachine);
        remove = (ImageView) findViewById(R.id.remove);
        midX = (EditText) findViewById(R.id.midX);
        papX = (EditText) findViewById(R.id.papX);
        maxX = (EditText) findViewById(R.id.maxX);
        moX = (EditText) findViewById(R.id.moX);
        cus = (Spinner) findViewById(R.id.cidX);
        DateX = (TextView) findViewById(R.id.DateX);
        post = (Button) findViewById(R.id.post);
        //Get Data of Customers
        ShowinDB2();
        cus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cusName= parent.getItemAtPosition(position).toString();
                //cusID = cusIDs.get(position);
                cusID = cusIDs.get(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        TextView title = (TextView) findViewById(R.id.title);
        DateX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showDatePicker(v);
                DatePickerMethode();


            }
        });
        title.setText("Add Machine");
        updateUrl = "http://www.mixpix-xerox.com/ABI/updm.php";
        if(ids.equals("")) {
        }
        else {
            //to show the remove icon
            remove.setVisibility(View.VISIBLE);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String removeUrl;
                    removeUrl = "http://www.mixpix-xerox.com/ABI/delm.php";
                    Toast.makeText(AddMachine.this, "The item was Removed:"+ids,
                            Toast.LENGTH_LONG).show();
                    removefromServer(ids,removeUrl);


                }
            });

            title.setText("Edit Machine");
            post.setText("Edit Machine");
            midX = (EditText) findViewById(R.id.midX);
            cus = (Spinner) findViewById(R.id.cidX);
            DateX = (TextView) findViewById(R.id.DateX);
            papX = (EditText) findViewById(R.id.papX);
            maxX = (EditText) findViewById(R.id.maxX);
            moX = (EditText) findViewById(R.id.moX);

            //////////////////////////////////////
            midX.setText(mid);
            papX.setText(pap);
            maxX.setText(max);
            moX.setText(mo);
            //Update cid by customer Name
            // cus.setOnItemClickListener(getApplicationContext());
            DateX.setText(adddate);

        }
        //////////////////////////////////////////////////////////////////
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ids.equals("")) {

                    AdditioninDB( );
                    Toast.makeText(AddMachine.this, "The item was Added"+ids,
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                {

                    updatefromServer(updateUrl,ids);
                }
                // AdditioninDB(S,URL);
                //Addition(v,"Customers");
            }
        });
    }
    /////////////////////////////////////////////////////////////
    //Removing Machine From Database
    //////////////////////////////////////////////////////
    public void removefromServer(final String id,String removeUrl){
        RequestQueue queue = Volley.newRequestQueue(AddMachine.this);
        String cat = null;
        showSnackbar("The item was removed");

        final String finalResponse = cat;

        StringRequest postRequest = new StringRequest(Request.Method.POST, removeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String cat) {


                        try {

                            JSONArray jsonArray = new JSONArray(cat);


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
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //the parameters that will taken with the request method



                params.put("id",id);

                return params;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }
    ///////////////////////////////////////////////////////////
    //Editing Machine to Database
    //////////////////////////////////////////////////////////
    public void updatefromServer(String updateUrl, final String ids)
    {
        Toast.makeText(AddMachine.this, "updated "+ids,
                Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(AddMachine.this);
        String cat = null;
        showSnackbar("The item was Edited");

        final String finalResponse = cat;

        StringRequest postRequest = new StringRequest(Request.Method.POST, updateUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String cat) {


                        try {

                            JSONArray jsonArray = new JSONArray(cat);


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
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //the parameters that will taken with the request method



                //the parameters that will taken with the request method
               /* String mid = midX.getText().toString();
                String cid = cidX.getText().toString();
                String date = DateX.getText().toString();
                */
                params.put("id", ids);
                params.put("mid", midX.getText().toString());
                params.put("cid", cusID);
                params.put("pap",papX.getText().toString());
                params.put("max",maxX.getText().toString());
                params.put("model",moX.getText().toString());
                params.put("date", DateX.getText().toString());


                return params;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }
    /////////////////////////////////////////////////////////////
    //Adding Machine to Database
    public void AdditioninDB()
    {

        RequestQueue queue = Volley.newRequestQueue(AddMachine.this);
        String response = null;


        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST,
                "http://www.mixpix-xerox.com/ABI/addm.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);



                            boolean error = jsonObject.getBoolean("error");
                              if (!error) {
                                showSnackbar(response);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error",e.getMessage());
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
        ) {
            @Override
            protected Map<String, String> getParams()
            {
            Map<String, String> params = new HashMap<String, String>();
            //the parameters that will taken with the request method
          /*  String mid = midX.getText().toString();
            String cid = cidX.getText().toString();
            String date = DateX.getText().toString();
*/
                params.put("mid", midX.getText().toString());
                params.put("cid", cusID);
                params.put("date", DateX.getText().toString());
                params.put("pap",papX.getText().toString());
                params.put("max",maxX.getText().toString());
                params.put("model",moX.getText().toString());

                return params;

        }


        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }
    /////////////////////////////////////////////////////////////
    //Showing Machine from Database
    public void ShowinDB(  String showUrl) {
        ///////////////////////////////////////////////////////
        //Toast.makeText(getApplicationContext(), strItem + " essssssssssss", Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),"shooooooooooo" ,Toast.LENGTH_LONG ).show();


        ///////////////////////////////////////////////////////

        RequestQueue queue = Volley.newRequestQueue(AddMachine.this);
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
                               String id = object.getString("id");
                                String mid = object.getString("mid");
                                String cid = object.getString("cid");
                                String adddate = object.getString("datead");
                                String papers = object.getString("st");
                                String pap = object.getString("pap");
                                String max = object.getString("max");
                                String mo = object.getString("model");
                                String cusname = object.getString("CUST_NAME");
                                machinesModel obj = new machinesModel(
                                        id,cusname,"Xerox Name",papers,adddate
                                        ,mid,cid,pap,max,mo);

                                //Add each object with value in the array 'machinesData'
                                machinesData.add(obj);
                                Addition(v,str,id);

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
    //Names of Customers
    ///////////////////////////////////////////////////////
    public void ShowinDB2() {
        Toast.makeText(this,"TesT11111" ,Toast.LENGTH_SHORT).show();
        showUrl = "http://www.mixpix-xerox.com/ABI/selectcustomer.php";
        ///////////////////////////////////////////////////////

        RequestQueue queue = Volley.newRequestQueue(AddMachine.this);
        String cat = null;


        final String finalResponse = cat;

        StringRequest postRequest = new StringRequest(Request.Method.GET, showUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String cat) {
                        CustomersSpin.clear();
                        cusIDs.clear();

                        try {

                            JSONArray jsonArray = new JSONArray(cat);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);


                                //Names of Customers
                                String id = object.getString("ID");
                                //Names of Customers
                                String Name = object.getString("name");
                                CustomersSpin.add(Name);
                                cusIDs.add(id);
                                addProductforsale addProductforsale = new addProductforsale(id,
                                        Name, "1", "1");
                                // Toast.makeText(AddSale2.this,"TesT22222" ,Toast.LENGTH_SHORT).show();







                            }
                            //Creating the ArrayAdapter instance having the customer list
                            ArrayAdapter aa2 = new ArrayAdapter(AddMachine.this, android.R.layout.simple_spinner_item, CustomersSpin);
                            aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            cus.setAdapter(aa2);

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
        )
                ;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }
    public void showSnackbar (String stringSnackbar){
        snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }
    }
