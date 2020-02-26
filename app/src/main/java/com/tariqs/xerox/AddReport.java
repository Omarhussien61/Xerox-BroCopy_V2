package com.tariqs.xerox;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.tariqs.xerox.util.FixedGridLayoutManager;
import com.tariqs.xerox.util.model.Report2;

import okhttp3.OkHttpClient;

public class AddReport extends Activity
        {
    TextView from,to;
            TextView t;
TextView title;
Button showReport;
    int scrollX = 0;
RelativeLayout RDate;
    String ca;
    RecyclerView rvClub;
    Spinner pro,cus;
    HorizontalScrollView headerScroll;
    DialogFragment newFragment = new MyDatePickerFragment();
    String cusID,proID;
    ArrayList<Customers> customersData = new ArrayList<>();
    ArrayList<Products> productsData = new ArrayList<>();
    List<String> CustomersSpin = new ArrayList<String>();
    List<String> ProductsSpin =new ArrayList<String>();
    List<String> cusIDs = new ArrayList<String>();
            List<Integer> total = new ArrayList<Integer>();

            List<String> proIDs = new ArrayList<String>();
    public List<Report2> filteredreports;
    //new array list that will hold the filtered data
    ArrayList<Report2> filterd = new ArrayList<>();
    ArrayList<Report2> filterd2 = new ArrayList<>();

  public String showUrl,Fs,Ts;
    RecyclerView recyclerView;
    reportAdapter2 adapter;
    reportAdapter adapter2;
    ArrayList<Report2> reportData = new ArrayList<>();
    ArrayList<Report> reportData2 = new ArrayList<>();
    String S;
    int sum = 0 ;
    String SUM;
    String check;
    String ShowURL,cusName,proName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
cusID = "0";
proID="0";
        check = "0";
  startActivity();

    }
public void startActivity(){
    setContentView(R.layout.report_main);
    Button grand = (Button) findViewById(R.id.grand);
    Button yesterday = (Button) findViewById(R.id.yesterday);
    Button today = (Button) findViewById(R.id.today);
    Button curmon = (Button) findViewById(R.id.curmon);
    Button premon = (Button) findViewById(R.id.premon);
    Button custom = (Button) findViewById(R.id.custom);
    LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
            new IntentFilter("custom-message"));
    grand.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShowURL="http://www.mixpix-xerox.com/ABI/grand.php";

            grand(ShowURL,"Grand Report");

        }
    });
    yesterday.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShowURL="http://www.mixpix-xerox.com/ABI/yesterday.php";

            grand(ShowURL,"Yesterday Report");

        }
    });
    today.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShowURL="http://www.mixpix-xerox.com/ABI/today.php";

            grand(ShowURL,"Today Report");

        }
    });
    curmon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //View Current Month Report by Changing URL
            ShowURL="http://www.mixpix-xerox.com/ABI/cernt.php";
            grand(ShowURL,"Current Month Report");

        }
    });
    premon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShowURL="http://www.mixpix-xerox.com/ABI/prev-month.php";

            grand(ShowURL,"Previous Month Report");

        }
    });
    custom.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShowURL="http://www.mixpix-xerox.com/ABI/custmR.php";

            custom(ShowURL);

        }
    });
    ////////////////////////////////////////////

}
    //////////////////////////////////////////////////////////////////////
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
            reportData.clear();
        total.clear();

        filterd.clear();
            startActivity();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to go Home", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    /////////////////////////////////////////////////////////////////////////////////////
    //View Grand Report
    public void grand(final String ShowURL,String S)
    {   check = "1";
        setContentView(R.layout.report);
        title =(TextView) findViewById(R.id.title);
        t = (TextView) findViewById(R.id.t);
        title.setText(S);
        //showUrl = "http://n.exeative.com/ABI/grand.php";
        rvClub = findViewById(R.id.rvClub);
        //headerScroll = findViewById(R.id.headerScroll);
        showReport = (Button) findViewById(R.id.showReport);
        pro = (Spinner) findViewById(R.id.pro);
        cus = (Spinner) findViewById(R.id.cus);


        //Getting the instance of Spinner and applying OnItemSelectedListener on it
       // pro.setOnItemSelectedListener(AddReport.this);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
       // cus.setOnItemSelectedListener(AddReport.this);
       /* cus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
*/
/*
       if (proID.equals("0")&&cusID.equals("0")) {
           load_data_from_server(0, ShowURL);
       }
       else
       {
           ShowReportbIDs(proID,cusID);

       }
*/
  //      load_data_from_server(0, ShowURL);
//        load_data_from_server(0, ShowURL);
        load_data_from_server(0, ShowURL);
      /*  sum =0;
        for (int i=0; i<total.size(); i++)
        {
            sum = sum + total.get(i);

            SUM = String.valueOf(sum);
            t.setText(SUM);

        }
        */
  /*      t.setText(SUM);

        total.clear();
*/        //set Recyclerview
        adapter = new reportAdapter2(AddReport.this, reportData);
        rvClub.setAdapter(adapter);
        rvClub.setLayoutManager(new LinearLayoutManager(AddReport.this)); // Vertical Orientation By Default
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this); // (Context context)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        rvClub.setLayoutManager(mLinearLayoutManagerVertical);

        //Get Data of Products
        ShowinDB();
        //Get Data of Customers
        ShowinDB2();
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        cus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cusName= parent.getItemAtPosition(position).toString();
                //cusID = cusIDs.get(position);
                cusID = cusIDs.get(position);
                if (cusName.equals("All"))
                { load_data_from_server(0, ShowURL);
                    //set Recyclerview
                    adapter = new reportAdapter2(AddReport.this, reportData);
                    rvClub.setAdapter(adapter);
                    rvClub.setLayoutManager(new LinearLayoutManager(AddReport.this)); // Vertical Orientation By Default
                    LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddReport.this); // (Context context)
                    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                    rvClub.setLayoutManager(mLinearLayoutManagerVertical);}
                ///////////////////////////////
                else {
                    filter(proName, cusName, "pro");
                    Toast.makeText(getApplicationContext(), cusName + "Selected", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

               /*
                load_data_from_server(0, ShowURL);
                //set Recyclerview
                adapter = new reportAdapter2(AddReport.this, reportData);
                rvClub.setAdapter(adapter);
                rvClub.setLayoutManager(new LinearLayoutManager(AddReport.this)); // Vertical Orientation By Default
                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddReport.this); // (Context context)
                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                rvClub.setLayoutManager(mLinearLayoutManagerVertical);
*/
                Toast.makeText(getApplicationContext(), cusName+"Noooo" , Toast.LENGTH_LONG).show();

            }
        });
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        pro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                proName= parent.getItemAtPosition(position).toString();
                //cusID = cusIDs.get(position);
                proID = proIDs.get(position);
                ////////////////////////////////
                if (proName.equals("All"))
                { load_data_from_server(0, ShowURL);
                    //set Recyclerview
                    adapter = new reportAdapter2(AddReport.this, reportData);
                    rvClub.setAdapter(adapter);
                    rvClub.setLayoutManager(new LinearLayoutManager(AddReport.this)); // Vertical Orientation By Default
                    LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddReport.this); // (Context context)
                    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                    rvClub.setLayoutManager(mLinearLayoutManagerVertical);}
                ///////////////////////////////
                else {
                    ///////////////////////////////
                    filter(proName, cusName, "pro");
                }
                ////////////////////////////////////////////////////////////

                Toast.makeText(getApplicationContext(), proName+"Selected" , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
/*
                load_data_from_server(0, ShowURL);
                //set Recyclerview
                adapter = new reportAdapter2(AddReport.this, reportData);
                rvClub.setAdapter(adapter);
                rvClub.setLayoutManager(new LinearLayoutManager(AddReport.this)); // Vertical Orientation By Default
                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddReport.this); // (Context context)
                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                rvClub.setLayoutManager(mLinearLayoutManagerVertical);
*/
                Toast.makeText(getApplicationContext(), proName+"Noooo" , Toast.LENGTH_LONG).show();

            }
        });

//        spinner_imp(ShowURL);



//        setUpRecyclerView();
     /*   rvClub.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                scrollX += dx;

                headerScroll.scrollTo(scrollX, 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
   */
    }
    /////////////////////////////////////////////////////////////////////
   public void spinner_imp(final String ShowURL)
   {
       //Getting the instance of Spinner and applying OnItemSelectedListener on it
       pro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               proName= parent.getItemAtPosition(position).toString();
               //cusID = cusIDs.get(position);
               proID = proIDs.get(position);
               filter(proName,cusName,"pro");

               ////////////////////////////////////////////////////////////

               Toast.makeText(getApplicationContext(), "Selected" , Toast.LENGTH_LONG).show();

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

         /*
                load_data_from_server(0, ShowURL);
                //set Recyclerview
                adapter = new reportAdapter2(AddReport.this, reportData);
                rvClub.setAdapter(adapter);
                rvClub.setLayoutManager(new LinearLayoutManager(AddReport.this)); // Vertical Orientation By Default
                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddReport.this); // (Context context)
                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                rvClub.setLayoutManager(mLinearLayoutManagerVertical);
*/
               Toast.makeText(getApplicationContext(), cusName+"Noooo" , Toast.LENGTH_LONG).show();

           }
       });


       cus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               cusName= parent.getItemAtPosition(position).toString();
               //cusID = cusIDs.get(position);
               cusID = cusIDs.get(position);
               filter(proName,cusName,"pro");


           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

               load_data_from_server(0, ShowURL);
               //set Recyclerview
               adapter = new reportAdapter2(AddReport.this, reportData);
               rvClub.setAdapter(adapter);
               rvClub.setLayoutManager(new LinearLayoutManager(AddReport.this)); // Vertical Orientation By Default
               LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddReport.this); // (Context context)
               mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
               rvClub.setLayoutManager(mLinearLayoutManagerVertical);

           }
       });

   }
            /////////////////////////////////////////
    public void ShowReportbIDs(final String cusid,final String proid){
        ///////////////////////////////////////////////////////

        RequestQueue queue = Volley.newRequestQueue(AddReport.this);
        String response = null;


        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST,
                "http://www.mixpix-xerox.com/ABI/crentcond.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i=0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                reportData.add(new Report2(object.getString("bill_name")
                                        ,object.getString("item_name")
                                        , object.getString("bi_qtty")
                                        , object.getString("bi_price")
                                        , object.getString("PRO_NAME")));


//////////////////////////////////////////////////

                            }
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
                Map<String, String> params2 = new HashMap<String, String>();
                //the parameters that will taken with the request method


                params2.put("item", proid);
                params2.put("cust", cusid);



                return params2;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }
  /////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////
    //Filter Service
    private void filter(String text,String textC,String checkPro_or_Cus) {

        {
            filterd.clear();

            //looping through existing elements
            for (Report2 obj : reportData) {
                if (proName.equals("All")&&cusName.equals("All"))
                {


                    //adding the element to filtered list
                    filterd.add(obj);
                }
                    else if (proName.equals("All") && !cusName.equals("All")) {
                        //  filterd.add(obj);
                        if (obj.getCusname().toLowerCase().contains(textC.toLowerCase())) {
                            //adding the element to filtered list
                            filterd.add(obj);
                        }
                    } else if (!proName.equals("All") && cusName.equals("All")) {
                        if (obj.getName().toLowerCase().contains(text.toLowerCase())) {
                            //adding the element to filtered list
                            filterd.add(obj);
                        } else
                            //if the existing elements contains the spinner
                            if (obj.getName().toLowerCase().contains(text.toLowerCase())
                                    &&
                                    obj.getCusname().toLowerCase().contains(textC.toLowerCase())) {
                                //adding the element to filtered list
                                filterd.add(obj);
                            }

                    }
                   /* else {
                        if (cusName.equals("All")) {
                            filterd2.add(obj);

                        } else
                            //if the existing elements contains the spinner
                            if (obj.getCusname().toLowerCase().contains(text.toLowerCase())) {
                                //adding the element to filtered list
                                filterd2.add(obj);
                            }
                        //calling a method of the adapter class and passing the filtered list
                        adapter.filteredreports(filterd2);
                    }
*/


            }
            //calling a method of the adapter class and passing the filtered list
            adapter.filteredreports(filterd);


        }
    }
    /////////////////////////////////////////////////////////////////////
    public void ShowinDB() {
         Toast.makeText(this,"TesT11111" ,Toast.LENGTH_SHORT).show();
         showUrl = "http://www.mixpix-xerox.com/ABI/selectpro.php";

        ///////////////////////////////////////////////////////

        RequestQueue queue = Volley.newRequestQueue(AddReport.this);
        String cat = null;


        final String finalResponse = cat;

        StringRequest postRequest = new StringRequest(Request.Method.GET, showUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String cat) {

                        ProductsSpin.clear();
                        proIDs.clear();
                        try {

                            JSONArray jsonArray = new JSONArray(cat);
                            ProductsSpin.add("All");
                            proIDs.add("0");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);


                                  //Names of Products
                                    String name = object.getString("Name");
                                    String id = object.getString("Item_ID");
                                    String quantitytypeS = object.getString("pro_quantity");
                                    String skuS = object.getString("code");
                                    String descrp = object.getString("Descrption");
                                    String actuallypriceS = object.getString("proPrice");
                                    Products products = new Products(id,name, quantitytypeS
                                            , actuallypriceS, skuS,descrp);                                        productsData.add(products);
                                    ProductsSpin.add(name);
                                    proIDs.add(id);







                            }

                            //Creating the ArrayAdapter instance having the customer list
                            ArrayAdapter aa = new ArrayAdapter(AddReport.this, android.R.layout.simple_spinner_item, ProductsSpin);
                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //Setting the ArrayAdapter data on the Spinner
                            pro.setAdapter(aa);

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
    ///////////////////////////////////////////////////////
    public void ShowinDB2() {
        Toast.makeText(this,"TesT11111" ,Toast.LENGTH_SHORT).show();
        showUrl = "http://www.mixpix-xerox.com/ABI/selectcustomer.php";
        ///////////////////////////////////////////////////////

        RequestQueue queue = Volley.newRequestQueue(AddReport.this);
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
                            CustomersSpin.add("All");
                            cusIDs.add("0");
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
                            ArrayAdapter aa2 = new ArrayAdapter(AddReport.this, android.R.layout.simple_spinner_item, CustomersSpin);
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
    //////////////////////////////////////////////////////
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            ca = intent.getStringExtra("Date");
            // Toast.makeText(AddSale.this,quan ,Toast.LENGTH_SHORT).show();
        }
    };
    public void showDatePicker(View v) {
        newFragment.show(getFragmentManager(), "date picker");
    }
    //Methode Show Date Picker
    public void DatePickerMethode(final String Check){
        RDate = (RelativeLayout) findViewById(R.id.RDate);
        RDate.setVisibility(View.VISIBLE);
        final DatePicker simpleDatePicker = (DatePicker)findViewById(R.id.date_picker); // initiate a date picker
               /* int month = simpleDatePicker.getMonth(); // get the selected month
                int day = simpleDatePicker.getDayOfMonth(); //get the selected day
                int year = simpleDatePicker.getYear(); //get the selected year
               */
        Button date_time_set = (Button) findViewById(R.id.date_time_set);
        date_time_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RDate.setVisibility(View.GONE);
                String Y = String.valueOf(simpleDatePicker.getYear());
                String M = String.valueOf((simpleDatePicker.getMonth()+1));
                String D = String.valueOf(simpleDatePicker.getDayOfMonth());
                if (Check.equals("f"))
                {
                    Fs= Y+"/"+M+"/"+D;
                    from.setText(Fs);

                }
                else
                {
                    Ts = Y+"/"+M+"/"+D;
                    to.setText(Ts);

                }
            }
        });

    }
    //View custom report
    public  void custom(final String showUrl)
    {
        setContentView(R.layout.activity_add_report);

        //////////////////////////////////////////////////////
        from = (TextView) findViewById(R.id.from);
        to = (TextView) findViewById(R.id.to);
        showReport = (Button) findViewById(R.id.showReport);
        recyclerView =(RecyclerView) findViewById(R.id.recycleView);
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showDatePicker(v);
                DatePickerMethode("f");

                //Toast.makeText(getApplicationContext(), "Da"+ca, Toast.LENGTH_SHORT).show();

            }
        });
       to.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // showDatePicker(v);
               DatePickerMethode("t");

               //Toast.makeText(getApplicationContext(), "Da"+ca, Toast.LENGTH_SHORT).show();

           }
       });
        showReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.addreport2);
                title =(TextView) findViewById(R.id.title);
                title.setText("Dates");
                rvClub = findViewById(R.id.rvClub);
                headerScroll = findViewById(R.id.headerScroll);
                ShowinDB(showUrl,Fs,Ts);

                //load_data_from_server(0,ShowURL);

                setUpRecyclerView();
                rvClub.addOnScrollListener(new RecyclerView.OnScrollListener()
                {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
                    {
                        super.onScrolled(recyclerView, dx, dy);

                        scrollX += dx;

                        headerScroll.scrollTo(scrollX, 0);
                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState)
                    {
                        super.onScrollStateChanged(recyclerView, newState);
                    }
                });
                Toast.makeText(getApplicationContext(), "hh"+Fs+Ts, Toast.LENGTH_LONG).show();

            }
        });
/*
        //The Current date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        from.setText(formattedDate);
        to.setText(formattedDate);
        //ShowURL="http://n.exeative.com/ABI/report.php";
        */
/*
//Date Picker

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = View.inflate(getApplicationContext()
                        , R.layout.date_picker, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext())
                        .create();
                alertDialog.setView(dialogView);
                alertDialog.show();
                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth());

                       ca = calendar.toString();
                        alertDialog.dismiss();
                    }});
            }
        });
        from.setText(ca);
*/

///////////////////////////////////////////////////////
    /*    showReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowURL="http://n.exeative.com/ABI/grand.php";

                ShowinDB(showUrl);
                //load_data_from_server(0,showUrl);


            }
        });
      */
     /*   //set Recyclerview
        adapter2 = new reportAdapter(this, reportData2);
        recyclerView.setAdapter(adapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Vertical Orientation By Default
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this); // (Context context)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
*/

    }
    private void load_data_from_server(int id,final String ShowURL) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        //we put tge 'id' here to get the data by id of the customer
                        .url( ShowURL)
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());



                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        reportData.add(new Report2(object.getString("bill_name")
                                ,object.getString("item_name")
                                , object.getString("bi_qtty")
                                , object.getString("bi_price")
                                , object.getString("PRO_NAME")));
                        int tt = Integer.parseInt(object.
                                getString("bi_price"));
                        total.add(tt);
                       // sum = sum + total.get(i);
                        sum = sum + tt;

                        SUM = String.valueOf(sum);
//////////////////////////////////////////////////

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
    /////////////////////////////////////////////////////////////////////////////////

    private void setUpRecyclerView()
    {
        adapter = new reportAdapter2(AddReport.this, reportData);

        FixedGridLayoutManager manager = new FixedGridLayoutManager();
        manager.setTotalColumnCount(1);
        rvClub.setLayoutManager(manager);
        rvClub.setAdapter(adapter);
        rvClub.addItemDecoration(new DividerItemDecoration(AddReport.this, DividerItemDecoration.VERTICAL));
    }
    //////////////////////////////////////////////////////////////////
    public void ShowinDB(String showUrl,final String Fs,final String Ts)
    {
      /*  reportData.add(new Report2("hkh"
                , "m;/;kk"
                , "kjk"
                ,"hkjk"));
       */
        ///////////////////////////////////////////////////////
        //Toast.makeText(getApplicationContext(), strItem + " essssssssssss", Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),"shooooooooooo" ,Toast.LENGTH_LONG ).show();


        ///////////////////////////////////////////////////////
        ShowURL="http://www.mixpix-xerox.com/ABI/custmR.php";

        RequestQueue queue = Volley.newRequestQueue(AddReport.this);
        String cat = null;


        final String finalResponse = cat;

        StringRequest postRequest = new StringRequest(Request.Method.POST, ShowURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String cat) {


                        try {

                            JSONArray jsonArray = new JSONArray(cat);

                           for (int i = 0; i < jsonArray.length(); i++) {
                              JSONObject object = jsonArray.getJSONObject(i);
                            /*      String bill_name = object.getString("bill_name");
                                String bill_id = object.getString("bill_id");
                                String bill_date = object.getString("bill_date");
                                String paid = object.getString("paid");
                                String remainder = object.getString("remainder");
                                String total = object.getString("total");
                                String bill_num = object.getString("bill_num");

                                Report report = new Report(bill_id, bill_name, bill_date, paid
                                        ,remainder,total,bill_num);
                                reportData2.add(report);
                           */   Report2 report2 = new Report2(object.getString("bill_name"),object.getString("item_name")
                                        , object.getString("bi_qtty")
                                        , object.getString("bi_price")
                                        , object.getString("PRO_NAME"));
                               reportData.add(report2);

                                /* reportData.add(new Report2(object.getString("item_name")
                                        , object.getString("bi_qtty")
                                        , object.getString("bi_price")
                                        , object.getString("PRO_NAME")));
*/
                               // setUpRecyclerView();







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
        )   {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                //the parameters that will taken with the request method

             //   params.put("from", "2019-04-30");
               // params.put("to", "2019-05-11");
                params.put("2019-04-30", "from");
                 params.put("2019-05-11", "to");
                return params;
            }
        }
                ;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }
    }

