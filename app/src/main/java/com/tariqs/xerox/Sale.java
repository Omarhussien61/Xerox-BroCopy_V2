package com.tariqs.xerox;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sale extends Activity implements
        AdapterView.OnItemSelectedListener {
    View v;
    Boolean isChecked;

    RecyclerView recyclerView;
    addProductforsaleAdapter adapter;
    Productsforsale_checkboxAdapter adapter2;
    //productsAdapter adapter2;
    LinearLayout linearlayout;
    ArrayList<Customers> customersData = new ArrayList<>();
    ArrayList<Products> productsData = new ArrayList<>();
    ArrayList<addProductforsale> checkedProducts=new ArrayList<>();
    //addProductforsale[] checkedProducts;
    List<String> carts = new ArrayList<String>();
    //List<model> models = new ArrayList<>();
    List<String> cusIDs = new ArrayList<String>();

    Snackbar snackbar;
    TextView Detailx;
    Spinner spinnerCus;
    Button btnSave, post;
    EditText etItem, cusname, cusphone, cusemail,gender,mailingaddress,amountPaid;
    EditText productname, sku, actuallyprice, quantitytype, notes, barcode, sellingprice;
    EditText date;
    ListView listView;
    String S, URL,ids,addsaleURL;
    String addproductforsale;
    String cusName,cusID;
    List<String> CustomersSpin = new ArrayList<String>();
    List<String> ProductsSpin =new ArrayList<String>();
    List<String> actuallypriceL =new ArrayList<String>();
    //List<String> selectedProduct = new ArrayList<String>();
    //ArrayList<addProductforsale> addproforsaleData = new ArrayList<>();

    //String[] selectedProductS = new String[4];
    public  Button Add_bu;
    String id,  currentProduct, qty, APrice,ischecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String showUrl = "http://www.mixpix-xerox.com/ABI/selectcustomer.php";
        addproductforsale = "0";
        Add_bu =(Button) findViewById(R.id.addProduct);

        ShowinDB(S,showUrl,cusID,addproductforsale);
        OutParams();
    }
    public void OutParams(){
        id = getIntent().getStringExtra("id");
        currentProduct = getIntent().getStringExtra("currentProduct");
        APrice = getIntent().getStringExtra("APrice");
        qty = getIntent().getStringExtra("qty");


    }
    /////////////////////////////////////////////////////////////////////
    public void addSale( final List<String> cusIDs,final List<String> CustomersSpin, final String Addition) {
        setContentView(R.layout.addsale);
        Detailx = (TextView) findViewById(R.id.Detailx);
        Button addsale = (Button) findViewById(R.id.addsale);

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
        adapter = new addProductforsaleAdapter(this, checkedProducts);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Vertical Orientation By Default
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this); // (Context context)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

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
        date = (EditText) findViewById(R.id.Date);
        //The Current date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        date.setText(formattedDate);
        //When Customer buy new product So must Add product
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String showUrl = "http://www.mixpix-xerox.com/ABI/selectpro.php";
                String addproductforsale = "1";
                spinnerCus.setOnItemSelectedListener(Sale.this);

                ShowinDB(S, showUrl,cusID, addproductforsale);

            }
        });
        ///////////////////////////////////////////////////////

        addsale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Addition.equals("1")) {
               /*     //Toast.makeText(getApplicationContext(), "The Sale was Added", Toast.LENGTH_LONG).show();
                     addProductforsale addProductforsale = new addProductforsale(id, currentProduct, qty, APrice);
                    String addsaleURL = "http://n.exeative.com/ABI/sal.php";
                    spinnerCus.setOnItemSelectedListener(AddSale.this);

                    AddSaleinDB(addProductforsale, addsaleURL,cusName);
                    */
                    if (Addition.equals("1")){
                        String curPro;
                        //addProductforsale addProductforsale = new addProductforsale(id, curPro, qty, APrice);
                        spinnerCus.setOnItemSelectedListener(Sale.this);

                        //Add each object with value in the array 'productsData'
                        // showCarts(cusID,addProductforsale);
                        showCarts(cusID);


                        //addSale(obj, cusIDs,CustomersSpin, "1");

                        ////////////////////////////////////////////////
                    }
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

    /////////////////////////////////////////////////////////////////////
    //Performing action onItemSelected and onNothing selected of Spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {
        cusName= parent.getItemAtPosition(position).toString();
        //cusID = CustomersSpin.get(position).getId();
        //cusID="76";
        //cusID = String.valueOf(position);
        cusID = cusIDs.get(position);
        Toast.makeText(getApplicationContext(), "Selected" , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    /////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////
    public void addProductforSale(final String cusID)
    {
        EditText quantityX,sellingPriceX,actualPriceX;
        TextView profit;
        final Button Add_Product;

        setContentView(R.layout.addproductforsale_n);
        /*quantityX = (EditText) findViewById(R.id.quantityX);
        sellingPriceX = (EditText) findViewById(R.id.sellingPriceX);
        actualPriceX =(EditText) findViewById(R.id.actualPriceX);
        profit =(TextView) findViewById(R.id.profit);
        */
        recyclerView = (RecyclerView) findViewById(R.id.Rv);
       // adapter2 = new Productsforsale_checkboxAdapter(this, productsData);
        recyclerView.setAdapter(adapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Vertical Orientation By Default
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this); // (Context context)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
        ////////////////////////////////////////////////
        Add_Product = (Button) findViewById(R.id.addProduct);
        Add_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sale.this,AddSale2.class);
                i.putExtra("cusID",cusID);
                startActivity(i);
                //  setContentView(R.layout.activity_add_sale2);

            }



        });
        /////////////////////////////////////////////////////////////////////////////////


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new Interface.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //String currentProduct, qty, APrice;
                //final boolean isChecked;
                //isChecked = true;
                // final int idI = Integer.valueOf(id);
                //CheckBox myCheckBox= (CheckBox) v;
                final String id = productsData.get(position).getId();

                ArrayList<addProductforsale> addProductforsales;


                qty = productsData.get(position).getAmountX();
                APrice = productsData.get(position).getActualpriceX();
                isChecked = productsData.get(position).isSelected();


                Products currentproductObj = productsData.get(position);
                /////////////////////////////////////////////////////
                String currentProduct= productsData.get(position).getName();

                addProductforsale addProductforsale = new addProductforsale(id, currentProduct, qty, APrice);
                //  checkedProducts.add(addProductforsale);
                Toast.makeText(getApplicationContext(), "Added" + id, Toast.LENGTH_LONG).show();
                addtoCart(cusID,addProductforsale);

                // addSale( cusIDs,CustomersSpin, "1");
            /*    Add_Product.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(AddSale.this,AddSale2.class);
                        i.putExtra("cusID",cusID);
                        startActivity(i);
                      //  setContentView(R.layout.activity_add_sale2);
                        TextView vv;
                        String currentProduct= productsData.get(position).getName();

                        if (isChecked) {
                            //currentproductObj.setSelected(true);
                            //addProductforsale checkedProductsObj = new addProductforsale(id,currentProduct,qty, APrice);
                            // checkedProducts.add(checkedProductsObj);
                            addProductforsale addProductforsale = new addProductforsale(id, currentProduct, qty, APrice);
                          //  checkedProducts.add(addProductforsale);
                            Toast.makeText(getApplicationContext(), "Added" + id, Toast.LENGTH_LONG).show();
                            addtoCart(cusID,addProductforsale);

                            addSale( cusIDs,CustomersSpin, "1");

                        } else if (!isChecked) {
                            //currentproductObj.setSelected(false);
                            //   addProductforsale checkedProductsObj = new addProductforsale(id,currentProduct,qty, APrice);
                            // checkedProducts.remove(checkedProductsObj);
                             addProductforsale addProductforsale = new addProductforsale(id, currentProduct, qty, APrice);
                            checkedProducts.clear();
                            Toast.makeText(AddSale.this, "Removed" + id, Toast.LENGTH_LONG).show();
                            //addSale(addProductforsale,cusIDs, CustomersSpin, "0");

                        }
                        //AddSaleinDB(checkedProducts,addsaleURL);

                  //  vv =(TextView)  findViewById(R.id.vv);
                    //vv.setText(checkedProducts.get(position).getName());
                }



                });
*/

            }

            @Override
            public void onLongClick(View view, int position) {
                addProductforsale addProductforsale = new addProductforsale(id, currentProduct, qty, APrice);
                checkedProducts.clear();
                Toast.makeText(Sale.this, "Removed" + id, Toast.LENGTH_LONG).show();
                //addSale(addProductforsale,cusIDs, CustomersSpin, "0");


            }
        }));

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
        RequestQueue queue = Volley.newRequestQueue(Sale.this);
        String cat = null;
        String url="http://www.mixpix-xerox.com/ABI/carts.php";
        final String finalResponse = cat;
        //addSale( cusIDs,CustomersSpin, "1");

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


        ///////////////////////////////////////////////////////

        RequestQueue queue = Volley.newRequestQueue(Sale.this);
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
                                    String descrp = object.getString("Descrp");
                                    String actuallypriceS = object.getString("proPrice");
                                    Products products = new Products(id,name, quantitytypeS
                                            , actuallypriceS, skuS,descrp);                                        productsData.add(products);
                                    actuallypriceL.add(actuallypriceS);
                                    //addProductforsale addPro = new addProductforsale(id, name, quantitytypeS, actuallypriceS);
                                    //addproforsaleData.add(addPro);
                                    addProductforSale(cusID);
                                }
                                else
                                {    String id = object.getString("id");
                                    //Names of Customers
                                    String Name = object.getString("name");
                                    CustomersSpin.add(Name);
                                    cusIDs.add(id);
                                    addProductforsale addProductforsale = new addProductforsale(id, currentProduct, qty, APrice);

                                    addSale(cusIDs, CustomersSpin,"0");
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

        RequestQueue queue = Volley.newRequestQueue(Sale.this);
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
//                List<model> mo = new ArrayList<>();
//                mo.add(new model("12","5","75"));
//                mo.add(new model("28","5","75"));
//
//                modelList mmo = new modelList((mo));
//                final String newDataArray=gson.toJson(mmo.getMmod());
                //final String newDataArray = gson.toJson(mmo);
                // VolleyLog.setTag(newDataArray);
                //   params.put("carts", newDataArray);

           /*     for(int i =0;i <2;i++) {
                    params.put("carts[" + i + "]",newDataArray);
                }
                VolleyLog.setTag(newDataArray);
*/
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
    public void showCarts(final String cusID)
    {
        Toast.makeText(getApplicationContext(), cusName +cusID+" 2 Selected" , Toast.LENGTH_LONG).show();
/*
        String id ="12";
        String name = "hhhhhh";
        String quantitytypeS = "7";

        String actuallypriceS = "50";

        addProductforsale products = new addProductforsale(id, name, quantitytypeS, actuallypriceS);

        //Add each object with value in the array 'productsData'
        checkedProducts.add(products);
        addSale(products,cusIDs,CustomersSpin,"1");
*/
        RequestQueue queue = Volley.newRequestQueue(Sale.this);
        String response = null;
        String showsaleproducts ="http://www.mixpix-xerox.com/ABI/selectid.php";

        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, showsaleproducts,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);





                                String name = object.getString("Name");
                                String id = object.getString("Item_ID");
                                String quantitytypeS = object.getString("pro_quantity");
                                String skuS = object.getString("code");

                                String actuallypriceS = object.getString("proPrice");
                                addProductforsale products = new addProductforsale(id, name, quantitytypeS, actuallypriceS);

                                //Add each object with value in the array 'productsData'
                                checkedProducts.add(products);
                                // addSale(products,cusIDs,CustomersSpin,"1");
                                addSale(cusIDs,CustomersSpin,"1");

                            }

                            //addSale(addProductforsale,cusIDs,CustomersSpin,"1");






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

                params.put("customerid","20");


                return params;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }
}
