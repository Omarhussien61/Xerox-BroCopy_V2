package com.tariqs.xerox;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

public class AddSale2 extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener,
        NavigationView.OnNavigationItemSelectedListener{
    View v;
    Boolean isChecked;
     String Aprice,totalS;
    RecyclerView recyclerView;
    addProductforsaleAdapter adapter;
   // Productsforsale_checkboxAdapter adapter2;
    //productsAdapter adapter2;
    LinearLayout linearlayout;
    ArrayList<Customers> customersData = new ArrayList<>();
    ArrayList<Products> productsData = new ArrayList<>();
    ArrayList<addProductforsale> checkedProducts=new ArrayList<>();
    //addProductforsale[] checkedProducts;
    List<String> carts = new ArrayList<String>();
    //List<model> models = new ArrayList<>();

     List<String> cusIDs = new ArrayList<String>();
    List<String> proIDs = new ArrayList<String>();
    String Proid,APrice,currentProduct;

    Snackbar snackbar;
    TextView Detailx,totalX,Name;
    Spinner spinnerCus;
    Button  post,next,cal;
    ImageView btnSave;
    EditText etItem, cusname, cusphone, cusemail,gender,mailingaddress,amountPaid;
    EditText productname, sku, actuallyprice, quantitytype, notes, barcode, sellingprice;
    EditText paidX;
    TextView remainX,date;
    ListView listView;
    String S, URL,ids,addsaleURL;
    String addproductforsale;
    String cusName,cusID,cusId;
    String proName,proID;
    int total = 0; int qtyi,pricei,paidi ;
    String paidS;
    String T;
    RelativeLayout RDate;
    String Fs;
    int Ti;
    //////////////////////////////////////
    //Datatypes used in proSpinner method
    EditText actualPriceX;
    EditText quantityX;
    android.support.v7.widget.Toolbar toolbar;
    EditText sellingPriceX;
    TextView profit;
    int sellingPriceI,actualPriceI,profitI,quanI;
    String profitS,sellingPriceS;
    /////////////////////////////////
    List<String> CustomersSpin = new ArrayList<String>();
    List<String> ProductsSpin =new ArrayList<String>();
    List<String> actuallypriceL =new ArrayList<String>();
    List<Integer> totalA =new ArrayList<Integer>();

    //List<String> selectedProduct = new ArrayList<String>();
    //ArrayList<addProductforsale> addproforsaleData = new ArrayList<>();
    String quan;
    private PopupWindow mPopupWindow;

    //String[] selectedProductS = new String[4];
    public  Button Add_bu;
    int totali ;

    String id, qty,ischecked;
    EditText quant;
    ////////////////////////////////////
    ImageView exit;
    TextView title;
    String SalePageName;
    String checkIfSaler;

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         //setContentView(R.layout.addsale);

        String showUrl = "http://www.mixpix-xerox.com/ABI/selectcustomer.php";
        addproductforsale = "0";

        //Add_bu =(Button) findViewById(R.id.addProduct);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        OutParams();
        ShowinDB(S,showUrl,cusID,addproductforsale);
        SalePageName = getIntent().getStringExtra("name");
    }
    @Override
    public void onBackPressed()
    {
        checkIfSaler = getIntent().getStringExtra("checkIfSaler");

        if (checkIfSaler.equals("1"))
        {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                    .setMessage("Are you sure?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            moveTaskToBack(true);

                        }
                    }).setNegativeButton("no", null).show();
        }
        else
        {
            Intent intent = new Intent(this, Main2Activity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            //finish();
            //  Intent i = new Intent(this, App.class);
            //  startActivity(i);
        }
    }
    /////////////////////////////////////////////
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            quan = intent.getStringExtra("quantity");
            // Toast.makeText(AddSale.this,quan ,Toast.LENGTH_SHORT).show();
        }
    };
    ////////////////////////////////////////////////////
    public void OutParams(){
        id = getIntent().getStringExtra("id");
        currentProduct = getIntent().getStringExtra("currentProduct");
        APrice = getIntent().getStringExtra("APrice");
        qty = getIntent().getStringExtra("qty");


    }
    /////////////////////////////////////////////////////////////////////
    public void addSale( final String cusId,final String cusname
            ,final List<String> CustomersSpin, final String Addition
             ,final List<Integer> totalA )
    {
        setContentView(R.layout.addsale);
//        final TextView hiddenTextView = new TextView(getApplicationContext());
        //      hiddenTextView.setVisibility(View.GONE);
        ////////////////////////////////////////////////
        Detailx = (TextView) findViewById(R.id.Detailx);
        totalX = (TextView) findViewById(R.id.totalX);
        remainX = (TextView) findViewById(R.id.remainX);
        paidX = (EditText) findViewById(R.id.paidX);
        Button addsale = (Button) findViewById(R.id.addsale);
        ///////////////////////////////////////////////////////////
        title = (TextView) findViewById(R.id.title);
        exit = (ImageView) findViewById(R.id.exit);
        checkIfSaler = getIntent().getStringExtra("checkIfSaler");

        if (checkIfSaler.equals("1")) {
            setContentView(R.layout.saler_main);

            //Drawer
             toolbar = (Toolbar) findViewById(R.id.toolbar);
             setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View header = navigationView.getHeaderView(0);
            Name = (TextView) header.findViewById(R.id.Name);
            //phone = (TextView) header.findViewById(R.id.phone);
            String name = getIntent().getStringExtra("name");
            Name.setText(name);
            navigationView.setNavigationItemSelectedListener(this);



        }
        else
        {
            title.setText("Add Sale");
            exit.setVisibility(View.INVISIBLE);

        }
        /////////////////////////////////////////////////
        if (Addition.equals("1"))
        {   Ti =0;
            for (int i =0 ;i < totalA.size();i++) {
                 Ti = Ti + totalA.get(i);
                 T = String.valueOf(Ti);

            }
            totalX.setText(T);
            remainX.setText(T);
            TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {



                    if (filterLongEnough()) {
                      paidS = paidX.getText().toString();
                      totali = Integer.parseInt(T);
                      paidi = Integer.parseInt(paidS);
                      int remaini = totali - paidi;
                      String remainS = String.valueOf(remaini);
                      remainX.setText(remainS);

                    }
                }

                private boolean filterLongEnough() {
                    return paidX.getText().toString().trim().length() > 0;
                }
            };
            paidX.addTextChangedListener(fieldValidatorTextWatcher);



             // Toast.makeText(getApplicationContext(), "totalS" + totalS+"paid"+paidS , Toast.LENGTH_LONG).show();


        }

        recyclerView =(RecyclerView) findViewById(R.id.recycleViewSale);
        spinnerCus = (Spinner) findViewById(R.id.spinnerCus);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        // spinnerCus.setOnItemSelectedListener(this);
        //Show in carts from table of Api of selectid
/*        if (Addition.equals("1")){
            addProductforsale addProductforsale = new addProductforsale(id, currentProduct, qty, APrice);

            showCarts(cusID,addProductforsale);
        }
  */

        //set Recyclerview
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new addProductforsaleAdapter(AddSale2.this, checkedProducts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddSale2.this)); // Vertical Orientation By Default
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddSale2.this); // (Context context)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        //////////////////////////////////////////////////////

           ////////////////////////////////////////////////
        spinnerCus = (Spinner) findViewById(R.id.spinnerCus);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spinnerCus.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the customer list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CustomersSpin);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerCus.setAdapter(aa);
        ///////////////////////////////////////////////
        Button addProduct = (Button) findViewById(R.id.addProduct);
        date = (TextView) findViewById(R.id.Date);
        //The Current date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        date.setText(formattedDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showDatePicker(v);
                DatePickerMethode();


            }
        });
        //When Customer buy new product So must Add product
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String showUrl = "http://www.mixpix-xerox.com/ABI/selectpro.php";
                String addproductforsale = "1";
                spinnerCus.setOnItemSelectedListener(AddSale2.this);

                ShowinDB(S, showUrl,cusID, addproductforsale);

            }
        });
        ///////////////////////////////////////////////////////

        addsale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Addition.equals("1")){
                    String curPro;
                    //addProductforsale addProductforsale = new addProductforsale(id, curPro, qty, APrice);
                    spinnerCus.setOnItemSelectedListener(AddSale2.this);
                    int qtyi,pricei ;
                    paidS = paidX.getText().toString();
                    paidi = Integer.parseInt(paidS);

                    for (int i =0 ;i < checkedProducts.size();i++)
                    {
                        qtyi = Integer.parseInt(checkedProducts.get(i).getQty());
                        pricei = Integer.parseInt(checkedProducts.get(i).getAPrice());
                        totali = totali + (qtyi
                                *pricei);


                    }
                    totalS = String.valueOf(totali);

                    int remaini = totali- paidi;
                    String remainS =String.valueOf(remaini);

                    //Add each object with value in the array 'productsData'
                    // showCarts(cusID,addProductforsale);
                    addsaleinDB(cusId,cusname,totalS,paidS,remainS);
                   // Toast.makeText(getApplicationContext(), "totalS" + totalS+"paid"+paidS+"remain"+remainS , Toast.LENGTH_LONG).show();
                    totalX.setText(totalS);
                    remainX.setText(remainS);
                    finish();

                    //addSale(obj, cusIDs,CustomersSpin, "1");

                    ////////////////////////////////////////////////
                }

                else
                {  /* addProductforsale addProductforsale = new addProductforsale(id, currentProduct, qty, APrice);
                     String showsaleproducts ="http://192.168.1.10/showFavouritesOfUser.php";
                    showCarts(cusName,addProductforsale,showsaleproducts);


                    //set Recyclerview
                    adapter = new addProductforsaleAdapter(AddSale.this, checkedProducts);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AddSale.this)); // Vertical Orientation By Default
                    LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddSale.this); // (Context context)
                    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
                    ////////////////////////////////////////////////
                    */
                    Toast.makeText(getApplicationContext(), "No Data To Add" , Toast.LENGTH_LONG).show();

                }
            }
        });

      /*  if (Addition.equals("1")) {
            String n = selectedProduct.get(1);
            Detailx.setText(n);
        } else {
            Detailx.setText("xxxx");


        }
        */
    }
    //Implementation of Drawer Navigation
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.signOut) {
            //Creating a shared preference
            SharedPreferences sharedPreferences = AddSale2.this
                    .getSharedPreferences(Config.SHARED_PREF_NAME
                            , Context.MODE_PRIVATE);

            //Creating editor to store values to shared preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            sharedPreferences.edit().clear().apply();
            Intent i = new Intent(AddSale2.this,MainActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        date = (TextView) findViewById(R.id.Date);

        Button date_time_set = (Button) findViewById(R.id.date_time_set);
        date_time_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RDate.setVisibility(View.GONE);

                String Y = String.valueOf(simpleDatePicker.getYear());
                String M = String.valueOf((simpleDatePicker.getMonth()+1));
                String D = String.valueOf(simpleDatePicker.getDayOfMonth());
                Fs= Y+"-"+M+"-"+D;
                date.setText(Fs);
            }
        });

    }
    /////////////////////////////////////////////////////////////////////
    //Performing action onItemSelected and onNothing selected of Spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        cusName= parent.getItemAtPosition(position).toString();
        //cusID = CustomersSpin.get(position).getId();
        //cusID="76";
        //cusID = String.valueOf(position);
        cusID = cusIDs.get(position);

        ////////////////////////////////////////////////////////////

        Toast.makeText(getApplicationContext(), "Selected" , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    /////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    public void addProductforSale(final String cusID,final List<String> ProductsSpin)
    {

        final Button Add_Product;
        setContentView(R.layout.addproduct_forsale);
       // myDialog.setContentView(R.layout.addproduct_forsale);
        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      //  myDialog.show();
        Spinner spinnerPro = (Spinner) findViewById(R.id.products);
        //Creating the ArrayAdapter instance having the customer list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ProductsSpin);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerPro.setAdapter(aa);
        spinnerPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Views
                actualPriceX =(EditText) findViewById(R.id.actualPriceX);
                sellingPriceX = (EditText) findViewById(R.id.sellingPriceX);
                profit =(TextView) findViewById(R.id.profitX);
                quantityX = (EditText) findViewById(R.id.quantityX);
                //for addproforsale
                proID = proIDs.get(position);
                proName = parent.getItemAtPosition(position).toString();
                Proid = productsData.get(position).getId();
                APrice = productsData.get(position).getActualpriceX();
                currentProduct= productsData.get(position).getName();
                Toast.makeText(getApplicationContext(), "The item Selected"+proName, Toast.LENGTH_LONG).show();
                Aprice = APrice;
                actualPriceX.setText(APrice);
                //sellingPriceS = "0";
//////////////////////////////////////////////////////////////////
                TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {



                        if (filterLongEnough()) {
                            //  populateList();

                            sellingPriceS = sellingPriceX.getText().toString().trim();
                            quan= quantityX.getText().toString().trim();

                            actualPriceI = Integer.parseInt(Aprice);

                            sellingPriceI = Integer.parseInt(sellingPriceS);
                            quanI = Integer.parseInt(quan);

                            actualPriceI = actualPriceI * quanI;
                            profitI = sellingPriceI - (actualPriceI);
                            profitS = String.valueOf(profitI);
                            //Actual Price after calculated Quantity
                            APrice = String.valueOf(actualPriceI);
                            actualPriceX.setText(APrice);
                            profit.setText(profitS);
                        }
                    }

                    private boolean filterLongEnough() {
                        return quantityX.getText().toString().trim().length() > 0;
                    }
                };
                quantityX.addTextChangedListener(fieldValidatorTextWatcher);

///////////////////////////////////////////////////////////////////
                 fieldValidatorTextWatcher = new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if (filterLongEnough()) {
                            //  populateList();
                            sellingPriceS = sellingPriceX.getText().toString().trim();
                            quan= quantityX.getText().toString().trim();

                            actualPriceI = Integer.parseInt(Aprice);

                            sellingPriceI = Integer.parseInt(sellingPriceS);
                            quanI = Integer.parseInt(quan);

                            actualPriceI = actualPriceI * quanI;
                            profitI = sellingPriceI - (actualPriceI);
                            profitS = String.valueOf(profitI);
                            //Actual Price after calculated Quantity
                            APrice = String.valueOf(actualPriceI);
                            actualPriceX.setText(APrice);
                            profit.setText(profitS);
                        }
                    }

                    private boolean filterLongEnough() {
                        return sellingPriceX.getText().toString().trim().length() > 0;
                    }
                };
                sellingPriceX.addTextChangedListener(fieldValidatorTextWatcher);

                //////////////////////////////////////////////////////
                profitI = 0;
               // actualPriceI = 0;
  /*              cal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sellingPriceS = sellingPriceX.getText().toString().trim();
                        quan= quantityX.getText().toString().trim();
                        actualPriceI = Integer.parseInt(Aprice);

                        sellingPriceI = Integer.parseInt(sellingPriceS);
                        quanI = Integer.parseInt(quan);

                        actualPriceI = actualPriceI*quanI;
                        profitI = sellingPriceI  - (actualPriceI);
                        profitS = String.valueOf(profitI);
                        //Actual Price after calculated Quantity
                        APrice = String.valueOf(actualPriceI);
                        actualPriceX.setText(APrice);
                        profit.setText(profitS);


                    }
                });
*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        ////////////////////////////////////////////////
        Add_Product = (Button) findViewById(R.id.addProduct);

        Add_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent i = new Intent(AddSale.this,AddSale2.class);
                i.putExtra("cusID",cusID);
                startActivity(i);
                //  setContentView(R.layout.activity_add_sale2);
*/
                EditText quantityX;
                actualPriceX =(EditText) findViewById(R.id.actualPriceX);
                sellingPriceX = (EditText) findViewById(R.id.sellingPriceX);


                quantityX = (EditText) findViewById(R.id.quantityX);
                quan= quantityX.getText().toString().trim();
                APrice = actualPriceX.getText().toString().trim();
                sellingPriceS = sellingPriceX.getText().toString().trim();

                addProductforsale addProductforsale = new addProductforsale(Proid, currentProduct, quan, APrice);

                addtoCart(cusID, addProductforsale);
                actualPriceI = Integer.parseInt(APrice);
                sellingPriceI = Integer.parseInt(sellingPriceS);
                load_data_from_server(0, cusID);
                //totalA.add(actualPriceI);
                totalA.add(sellingPriceI);

                addSale(cusID,cusName,CustomersSpin,"1",totalA);
                // addSale(cusID,cusName, CustomersSpin, "1");
               // Toast.makeText(getApplicationContext(), "total :"+ APrice , Toast.LENGTH_LONG).show();

                ProductsSpin.clear();
                checkedProducts.clear();

            }



        });
        /////////////////////////////////////////////////////////////////////////////////




     /* for (int i = 0; i < Productsforsale_checkboxAdapter.productsData.size(); i++){
            if(Productsforsale_checkboxAdapter.productsData.get(i).isChecked()) {
            }
        }
*/
        ///////////////////////////////////////
//    String quantityS = quantityX.getText().toString();
        //  String sellingPriceS =  sellingPriceX.getText().toString();
        //String actualPriceS =  actualPriceX.getText().toString();
        // int sellingPriceI = Integer.valueOf(sellingPriceS);
        // int actualPriceI = Integer.valueOf(actualPriceS);
        // int profitI =  sellingPriceI - actualPriceI;
        // final String profitS = String.valueOf(profitI);

    }
    /////////////////////////////////////////////////////////
    public void addtoCart(final String cusID,final addProductforsale addProductforsale)
    {
        RequestQueue queue = Volley.newRequestQueue(AddSale2.this);
        String cat = null;
        String url="http://www.mixpix-xerox.com/ABI/carts.php";
        final String finalResponse = cat;
        //addSale( cusIDs,CustomersSpin, "1");
        Toast.makeText(getApplicationContext(), "The item was Added"+
                addProductforsale.getName(), Toast.LENGTH_LONG).show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
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



                params.put("name",cusID);
                params.put("pro",addProductforsale.getId());
                params.put("qty",addProductforsale.getQty());
                VolleyLog.setTag(addProductforsale.getQty());

                return params;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }
    ///////////////////////////////////////////////////////////////////////////////
    public void ShowinDB(final String Str, final String showUrl,final String cusID,final String addproductforsale) {
        ///////////////////////////////////////////////////////
        //Toast.makeText(getApplicationContext(), strItem + " essssssssssss", Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),"shooooooooooo" ,Toast.LENGTH_LONG ).show();
        // setContentView(R.layout.addsale);
        Toast.makeText(this,"TesT11111" ,Toast.LENGTH_SHORT).show();

        ///////////////////////////////////////////////////////

        RequestQueue queue = Volley.newRequestQueue(AddSale2.this);
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


                                if (addproductforsale.equals("1"))
                                {    //Names of Products
                                    String name = object.getString("Name");
                                    String id = object.getString("Item_ID");
                                    String quantitytypeS = object.getString("pro_quantity");
                                    String skuS = object.getString("code");
                                    String descrp = object.getString("Descrption");
                                    String actuallypriceS = object.getString("proPrice");
                                    Products products = new Products(id,name, quantitytypeS
                                            , actuallypriceS, skuS,descrp);                                        productsData.add(products);
                                    actuallypriceL.add(actuallypriceS);
                                    ProductsSpin.add(name);
                                    proIDs.add(id);
                                    //addProductforsale addPro = new addProductforsale(id, name, quantitytypeS, actuallypriceS);
                                    //addproforsaleData.add(addPro);
                                    addProductforSale(cusID,ProductsSpin);
                                }
                                else
                                {    String id = object.getString("ID");
                                    //Names of Customers
                                    String Name = object.getString("name");
                                    CustomersSpin.add(Name);
                                    cusIDs.add(id);
                                    addProductforsale addProductforsale = new addProductforsale(id, currentProduct, qty, APrice);
                                    Toast.makeText(AddSale2.this,"TesT22222" ,Toast.LENGTH_SHORT).show();

                                    addSale(cusID,cusName, CustomersSpin,"0",totalA);
                                }




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
        )
                ;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }
    //////////////////////////////////////////////////////////////////
    //ArrayList<addProductforsale> checkedProducts
    public  void AddSaleinDB(final addProductforsale addProductforsale,final String addsaleURL,final String cusName){
        //Toast.makeText(getApplicationContext(), "The Sale was Added", Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), cusName +" Selected" , Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(AddSale2.this);
        String response = null;


        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, addsaleURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            //Toast.makeText(getApplicationContext(), "The Sale was Added", Toast.LENGTH_LONG).show();

                            //showSnackbar(response);


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
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //the parameters that will taken with the request method
                String price = addProductforsale.getAPrice();
                String qty = addProductforsale.getQty();
                int TPriceI = Integer.parseInt(price) * Integer.parseInt(qty);
                String TPrice = String.valueOf(TPriceI);
              /*  params.put("productId", addProductforsale.getId());
                params.put("quantity", addProductforsale.getQty());
                params.put("price", TPrice);
                */
                //checkedProducts.add(addProductforsale);
                //carts.add(qty);
                //carts.add(TPrice);
                // model model = new model("12" ,"2", "35");
                params.put("name",cusName);
                VolleyLog.setTag(cusName);

                params.put("total", "0");
                Gson gson=new Gson();
        //        List<model> mo = new ArrayList<>();
//                mo.add(new model("12","5","75"));
  //              mo.add(new model("28","5","75"));

    //            modelList mmo = new modelList((mo));
      //          final String newDataArray=gson.toJson(mmo.getMmod());
                //final String newDataArray = gson.toJson(mmo);
                // VolleyLog.setTag(newDataArray);
                //   params.put("carts", newDataArray);

               // for(int i =0;i <2;i++) {
             //       params.put("carts[" + i + "]",newDataArray); }
             //   VolleyLog.setTag(newDataArray);

                return params;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }
    ////////////////////////////////////////////////////////////////////
    public void showSnackbar (String stringSnackbar){
        snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }
    /////////////////////////////////////////////////////////////////////////////////

    private void load_data_from_server(int id,final String S) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                String showsaleproducts ="http://www.mixpix-xerox.com/ABI/selectid.php?customerid=";

                OkHttpClient client = new OkHttpClient();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        //we put tge 'id' here to get the data by id of the customer
                        .url( showsaleproducts+S)
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());



                    for (int i=0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);
                        /* 'Data' the name of class with his object
                        to getData from database with the same name that in database
                         */
                        addProductforsale data = new addProductforsale(object.getString("Item_ID")
                                ,object.getString("Name"),object.getString("qty")
                                ,object.getString("proPrice"));
                        //Add each object with value in the array 'Information'
                        checkedProducts.add(data);
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

    ////////////////////////////////////////////////////////////////////////////////
    public void addsaleinDB(final String cusID,final String cusname,final String totalS,final String paidiiS,final String remainS)
    {
//        Toast.makeText(getApplicationContext(), cusname+cusID+" is Selected" , Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "The sale was added " , Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(AddSale2.this);
        String response = null;
        String addsale ="http://www.mixpix-xerox.com/ABI/addsal.php";

        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, addsale,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject jsonResponse = new JSONObject(response);





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
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //the parameters that will taken with the request method

                params.put("id",cusID);
                params.put("total",totalS);
                params.put("paid",paidiiS);
                params.put("rem",remainS);



                return params;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }
}
