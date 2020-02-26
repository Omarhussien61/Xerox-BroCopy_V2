package com.tariqs.xerox;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//******* For Adding Customers and Products *******//

public class Add extends Activity implements
        AdapterView.OnItemSelectedListener {
    View v;
    RecyclerView recyclerView;
    custtomersAdapter adapter;
    productsAdapter adapter2;
    private LinearLayout linearlayout;
    public ArrayList<Customers> customersData = new ArrayList<>();
    public ArrayList<Products> productsData = new ArrayList<>();
    Snackbar snackbar;
    TextView title;
    Spinner spinnerCus;
    Button  post;
    ImageView btnSave;
    Button nameBu,Bu;
    boolean namebu,bu;
    EditText search;
    EditText etItem, cusname, cusphone, cusemail,note,pass;
    Spinner gender;
    EditText productname, sku, actuallyprice, quantitytype, notes, barcode, sellingprice;
    EditText date;
    ListView listView;
    String S, URL,ids;
    //Cus Datatypes
    String Name,Phone,Email,password,Notes,Gender;
    //Pro Datatypes
    String NameP,SKU,Price,Amount,Descrp;
    List<String> CustomersSpin = new ArrayList<String>();
    List<String> ProductsSpin =new ArrayList<String>();
    List<String> actuallypriceL =new ArrayList<String>();
    List<String> selectedProduct = new ArrayList<String>();
    ImageView remove;
    public List<Customers> filteredcustomers;
    public List<Products> filteredproducts;
    String addproductforsale;
    String updateUrl;
    List<String> GenderSpin = new ArrayList<String>();

    private void findViewsById() {
        btnSave = (ImageView) findViewById(R.id.btnSave);
        //listView = (ListView) findViewById(R.id.listView);
        title = (TextView) findViewById(R.id.title);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
////////////////////////////////////////////////////////////////////////////////
        productname = (EditText) findViewById(R.id.productname);
        sku = (EditText) findViewById(R.id.sku);
        actuallyprice = (EditText) findViewById(R.id.actuallyprice);
        quantitytype = (EditText) findViewById(R.id.quantitytype);
        notes = (EditText) findViewById(R.id.notes);
        post = (Button) findViewById(R.id.post);
        sellingprice = (EditText) findViewById(R.id.sellingprice);
        ///////////////////////////////////////////////////////////////////
        cusname = (EditText) findViewById(R.id.customerNameX);
        cusphone = (EditText) findViewById(R.id.contactnumber);
        cusemail = (EditText) findViewById(R.id.customeremail);
        gender = (Spinner) findViewById(R.id.gender);
        pass = (EditText) findViewById(R.id.pass);
        note = (EditText) findViewById(R.id.noteX);
        post = (Button) findViewById(R.id.post);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        S = getIntent().getStringExtra("s");
        bu = false;
        namebu = true;
        //////////////////////////////////
        //Toast.makeText(getApplicationContext(), S, Toast.LENGTH_LONG).show();
       startActivity();
    }
    public void startActivity()
    {
        findViewsById();
        //adapterImplementation();
        //addItemToList(S);
        //listClick();
        ////////////////////////////////////////////
        addproductforsale = "0";
        ////////////////////////////////////////////
        //title of page if customer or product
        if (S.equals("Customers")) {
            setContentView(R.layout.add);
            addItemToList(S);

            title.setText(S);
            String showUrl = "http://www.mixpix-xerox.com/ABI/selectcustomer.php";

            ShowinDB(S, showUrl,addproductforsale);
        }
        else  if (S.equals("Products")) {
            setContentView(R.layout.add);
            addItemToList(S);

            title.setText(S);
            String showUrl = "http://www.mixpix-xerox.com/ABI/selectpro.php";
            ShowinDB(S,showUrl,addproductforsale);
        }
        else if (S.equals("Sales"))
        { String showUrl = "http://www.mixpix-xerox.com/ABI/selectcustomer.php";
            //  ShowinDB(S,showUrl,addproductforsale);
            Intent i = new Intent(Add.this, Sales.class);
            i.putExtra("checkIfSaler","0");
            startActivity(i);
            //addSale(v, CustomersSpin);
        }
    }
    //////////////////////////////////////////////////////////////
    @SuppressLint("WrongViewCast")
    public void addSale(View v, List<String> CustomersSpin, List<String> selectedProduct){
        setContentView(R.layout.addsale);
        spinnerCus = (Spinner) findViewById(R.id.spinnerCus);
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spinnerCus.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the customer list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,CustomersSpin);

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
                String addproductforsale ="1";
                ShowinDB(S,showUrl,addproductforsale);            }
        });
    }
    //Performing action onItemSelected and onNothing selected of Spinners
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), "Selected" , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    /////////////////////////////////////////////////////////////////////
public void addProductforSale(List<String> ProductsSpin,final List<String> actuallypriceL)
{
 EditText quantityX,sellingPriceX,actualPriceX;
 TextView profit;
Button Add_Product;

    setContentView(R.layout.addproduct_forsale);
     quantityX = (EditText) findViewById(R.id.quantityX);
    sellingPriceX = (EditText) findViewById(R.id.sellingPriceX);
    actualPriceX =(EditText) findViewById(R.id.actualPriceX);
    profit =(TextView) findViewById(R.id.profit);
    Add_Product = (Button) findViewById(R.id.addProduct);
    Spinner productsSpin = (Spinner) findViewById(R.id.products);
    //Creating the ArrayAdapter instance having the product list
    ArrayAdapter AA = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ProductsSpin);


    AA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    //Setting the ArrayAdapter data on the Spinner
    productsSpin.setAdapter(AA);
    productsSpin.setOnItemSelectedListener

    (
            new AdapterView.OnItemSelectedListener() {

                @Override
                                          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                             final String proName= parent.getItemAtPosition(position).toString();
                                             String positionS = String.valueOf(position);
                                             final String APrice= actuallypriceL.get(position);
                                              ////////////////////////////////////////////
                    EditText quantityX,sellingPriceX,actualPriceX;
                    TextView profitX;
                    Button Add_Product;

                    quantityX = (EditText) findViewById(R.id.quantityX);
                    sellingPriceX = (EditText) findViewById(R.id.sellingPriceX);
                    actualPriceX =(EditText) findViewById(R.id.actualPriceX);
                    profitX =(TextView) findViewById(R.id.profitX);
                    Add_Product = (Button) findViewById(R.id.addProduct);

                    final String quantityS = quantityX.getText().toString();
                    final String sellingPriceS =  sellingPriceX.getText().toString();
                    actualPriceX.setText(APrice);
                    int sellingPriceI = Integer.parseInt(sellingPriceS);
                     int actualPriceI = Integer.parseInt(APrice);
                     int quantityI = Integer.parseInt(quantityS);
                     int profitI =  sellingPriceI - (quantityI*actualPriceI);
                      String profitS = String.valueOf(profitI);
                    profitX.setText(profitS);
                    //addProduct Button Action
                    Add_Product.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedProduct.add(proName);
                            selectedProduct.add(quantityS);
                            selectedProduct.add(APrice);
                            selectedProduct.add(sellingPriceS);


                            addSale(v,CustomersSpin,selectedProduct);

                        }
                    });

                    Toast.makeText(getApplicationContext(),proName+positionS+APrice
                            , Toast.LENGTH_LONG).show();
                                          }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //Another interface callback
                }

            });
    ///////////////////////////////////////
//    String quantityS = quantityX.getText().toString();
  //  String sellingPriceS =  sellingPriceX.getText().toString();
    //String actualPriceS =  actualPriceX.getText().toString();
    // int sellingPriceI = Integer.valueOf(sellingPriceS);
    // int actualPriceI = Integer.valueOf(actualPriceS);
    // int profitI =  sellingPriceI - actualPriceI;
    // final String profitS = String.valueOf(profitI);

}

    //Dublicate
    private void adapterImplementation() {
        adapter = new custtomersAdapter(this, customersData);
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView); // on long click, show menu
    }

    String strItem = "";

    private void addItemToList(final String S) {
       // setContentView(R.layout.add);
        findViewsById();
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ids ="";
                String ids2="";
                    if (S.equals("Customers"))
                        addCustomer(v,ids,Name,Phone,Email,password,Notes,Gender);
                    else
                        addProduct(v,ids,NameP,SKU,Price,Amount,Descrp);

                    //Addition(strItem);
                }
        /*        Log log = new Log();
                log.setItem(strItem);
                logArrayAdapter.add(log);
                logArrayAdapter.notifyDataSetChanged();
        */

        });
    }



    /*
   NOTE: Below handles the context menu for when the list item is long pressed
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) { // Add Items to menu
        if (v.getId() == R.id.recycleView) {
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }



    //////////////////////////////////////////////////////////
    public void addCustomer(View v,final String ids , final String Name ,
                            final String Phone , final String Email,final String Pass,
                            final String notes,final String genderS) {
        final String updateUrl;
        setContentView(R.layout.addcustomer);
        findViewsById();
        GenderSpin.clear();
        GenderSpin.add("Normal");
        GenderSpin.add("Maintenance contract");
        GenderSpin.add("Rental");
        GenderSpin.add("Mixpix customer");
        gender = (Spinner) findViewById(R.id.gender);
        //Creating the ArrayAdapter instance having the customer list
        ArrayAdapter aa2 = new ArrayAdapter(Add.this, android.R.layout.simple_spinner_item, GenderSpin);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        gender.setAdapter(aa2);
        remove = (ImageView) findViewById(R.id.remove);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Add Customer");
        updateUrl = "http://www.mixpix-xerox.com/ABI/updatacustomer.php";
        if(ids.equals("")) {
        }
        else {
            //to show the remove icon
            remove.setVisibility(View.VISIBLE);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String removeUrl;
                    removeUrl = "http://www.mixpix-xerox.com/ABI/deletecustomer.php";
                    Toast.makeText(Add.this, "The item was Removed:"+ids,
                            Toast.LENGTH_LONG).show();
                    removefromServer(ids,removeUrl);
                    finish();
                    //restart onCrete
                    //startActivity();
                }
            });

            title.setText("Edit Customer");
            post.setText("Edit Customer");

            cusname = (EditText) findViewById(R.id.customerNameX);
            cusphone = (EditText) findViewById(R.id.contactnumber);
            cusemail = (EditText) findViewById(R.id.customeremail);
            gender = (Spinner) findViewById(R.id.gender);
            pass = (EditText) findViewById(R.id.pass);
            note = (EditText) findViewById(R.id.noteX);
            ///////////////////////////////////



            gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Gender= parent.getItemAtPosition(position).toString();
                    //cusID = cusIDs.get(position);


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    // sometimes you need nothing here
                }
            });
            //////////////////////////////////////
            cusname.setText(Name);
            cusphone.setText(Phone);
            cusemail.setText(Email);
           // gender.setText(genderS);
            pass.setText(Pass);
            note.setText(notes);
        }
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = cusname.getText().toString();
                String phone = cusphone.getText().toString();
                String email = cusemail.getText().toString();
                URL = "http://n.exeative.com/ABI/customers.php";

                if(ids.equals("")) {
                    AdditioninDB(S, URL,Gender);
                    //restart onCrete
                    //startActivity();
                }
                else
                {

                    updatefromServer(updateUrl,ids,Gender);
                    finish();
                    //restart onCrete
                   // startActivity();
                }
               // AdditioninDB(S,URL);
                //Addition(v,"Customers");
            }
        });
    }

    //////////////////////////////////////////////////////
    public void addProduct(View v,final String ids,final String NameP,
                           final String SKU,final String Price,final String Amount
            ,final String descrp) {
        setContentView(R.layout.addproduct);
        findViewsById();
        ///////////////////////////////////////////////////
        remove = (ImageView) findViewById(R.id.remove);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("Add Product");

        if(ids.equals("")) {
        }
        else {
            //to show the remove icon
            remove.setVisibility(View.VISIBLE);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String removeUrl;
                    removeUrl = "http://www.mixpix-xerox.com/ABI/deletepro.php";
                    Toast.makeText(Add.this, "The item was Removed:"+ids,
                            Toast.LENGTH_LONG).show();
                    removefromServer(ids,removeUrl);
                    finish();
                    //restart onCrete
                    //startActivity();

                }
            });

            title.setText("Edit Product");
            post.setText("Edit Product");

            productname = (EditText) findViewById(R.id.productname);
            sku = (EditText) findViewById(R.id.sku);
            actuallyprice = (EditText) findViewById(R.id.actuallyprice);
            quantitytype = (EditText) findViewById(R.id.quantitytype);
            notes = (EditText) findViewById(R.id.notes);
            post = (Button) findViewById(R.id.post);
            sellingprice = (EditText) findViewById(R.id.sellingprice);
            //////////////////////////////////////
            productname.setText(NameP);
            sku.setText(SKU);
            actuallyprice.setText(Price);
            quantitytype.setText(Amount);
            notes.setText(descrp);
        }
        ///////////////////////////////////
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = productname.getText().toString();
                String skuS = sku.getText().toString();
                String actuallypriceS = actuallyprice.getText().toString();
                String quantitytypeS = quantitytype.getText().toString();
             /*   Products products = new Products(id,name, skuS, actuallypriceS, quantitytypeS);
                products.setName(name);
                products.setSkuX(skuS);
                products.setActualpriceX(actuallypriceS);
                products.setAmountX(quantitytypeS);
                productsData.add(products);
                */
                //Toast.makeText(getApplicationContext(),name +skuS+actuallypriceS+quantitytypeS+" was Added",Toast.LENGTH_LONG ).show();
                //ArrayList<Products> productsData
                //Addition(v);
               // S = getIntent().getStringExtra("s");
                if(ids.equals("")) {
                    URL = "http://n.exeative.com/ABI/addpro.php";
                    AdditioninDB2("Products", URL);
                    finish();
                    //AdditioninDB("Products", URL);
                    //restart onCrete
                    //startActivity();
                }
                else
                {
                    updateUrl = "http://www.mixpix-xerox.com/ABI/updatapro.php";
                    Toast.makeText(getApplicationContext(),"up"+ids,Toast.LENGTH_LONG ).show();

                    updatefromServer(updateUrl,ids,Gender);
                    finish();
                    //restart onCrete
                    //startActivity();
                }

            }
        });
    }

    //////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
    //To Add to the recyclerview
    public void Addition(final View v, final String str, final String id) {
        setContentView(R.layout.add);
        btnSave = (ImageView) findViewById(R.id.btnSave);
        //listView = (ListView) findViewById(R.id.listView);
        title = (TextView) findViewById(R.id.title);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        search = (EditText) findViewById(R.id.search);
        Bu = (Button) findViewById(R.id.Bu);
        nameBu= (Button) findViewById(R.id.nameBu);
        //////////////////////////////////
        //title of page if customer or product
        if (S.equals("Customers")) {
            title.setText(str);
            Bu.setText("Phone");
            nameBu.setText("Customer");

//////////////////////////////////////////////////////
            //filter
            Bu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bu = true;
                    namebu = false;
                    Bu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                            , R.color.bluelight));
                    Bu.setTextColor(ContextCompat.getColor(getApplicationContext()
                            , R.color.white));
                    ///////////////////////////////////
                    nameBu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                            , R.color.white));
                    nameBu.setTextColor(ContextCompat.getColor(getApplicationContext()
                            , R.color.bluelight));
                }
            });
            nameBu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bu = false;
                    namebu = true;
                    nameBu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                            , R.color.bluelight));
                    nameBu.setTextColor(ContextCompat.getColor(getApplicationContext()
                            , R.color.white));
                    ///////////////////////////////////
                    Bu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                            , R.color.white));
                    Bu.setTextColor(ContextCompat.getColor(getApplicationContext()
                            , R.color.bluelight));
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
                    filter("Cus",editable.toString(),bu,namebu);
                }
            });
            //////////////////////////////////////
            //the recycler view definition and initilization
            adapter = new custtomersAdapter(this, customersData);
            recyclerView.setAdapter(adapter);

        } else {
            title.setText(str);
            //////////////////////////////////////////////////////
            Bu.setText("SKU");
            nameBu.setText("Product");
            //filter
            Bu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bu = true;
                    namebu = false;
                    Bu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                            , R.color.bluelight));
                    Bu.setTextColor(ContextCompat.getColor(getApplicationContext()
                            , R.color.white));
                    ///////////////////////////////////
                    nameBu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                            , R.color.white));
                    nameBu.setTextColor(ContextCompat.getColor(getApplicationContext()
                            , R.color.bluelight));
                }
            });
            nameBu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bu = false;
                    namebu = true;
                    nameBu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                            , R.color.bluelight));
                    nameBu.setTextColor(ContextCompat.getColor(getApplicationContext()
                            , R.color.white));
                    ///////////////////////////////////
                    Bu.setBackground(ContextCompat.getDrawable(getApplicationContext()
                            , R.color.white));
                    Bu.setTextColor(ContextCompat.getColor(getApplicationContext()
                            , R.color.bluelight));
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
                    filter("Pro",editable.toString(),bu,namebu);
                }
            });
            //the recycler view definition and initilization
            adapter2 = new productsAdapter(this, productsData);
            recyclerView.setAdapter(adapter2);

        }
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
        findViewsById();
        //adapterImplementation();
        addItemToList(S);

        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Vertical Orientation By Default
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this); // (Context context)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
        ////////////////////////////////////////////////
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new Interface.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                String ids;
                String ids2="";

                if (S.equals("Customers")) {
                    ids = customersData.get(position).getId();
                    Name = customersData.get(position).getName();
                    Phone = customersData.get(position).getPhone();
                    Email = customersData.get(position).getEmail();
                    password =customersData.get(position).getPass();
                    Notes = customersData.get(position).getNote();
                    Toast.makeText(Add.this, "updatte Cusss"+ids,
                            Toast.LENGTH_LONG).show();
                    addCustomer(v,ids,Name,Phone,Email,password,Notes,Gender);
                }
                else {
                    ids = productsData.get(position).getId();
                    NameP = productsData.get(position).getName();
                    SKU = productsData.get(position).getSkuX();
                    Price = productsData.get(position).getActualpriceX();
                    Amount = productsData.get(position).getAmountX();
                    Descrp = productsData.get(position).getDescrp();
                    Toast.makeText(Add.this, "updatte Prooo"+ids,
                            Toast.LENGTH_LONG).show();
                    addProduct(v,ids,NameP,SKU,Price,Amount,Descrp);
                }

                //updatefromServer(updateUrl,ids);
            }

            @Override
            public void onLongClick(View view, int position) {

                int ID = (position+1);
                String removeUrl;
                String IDs = Integer.toString(ID);
                String ids;


                if (S.equals("Customers")) {
                    ids = customersData.get(position).getId();
                    Toast.makeText(Add.this, "Long press on position :"+ids,
                            Toast.LENGTH_LONG).show();
                    removeUrl = "http://www.mixpix-xerox.com/ABI/deletecustomer.php";
                }
                 else {
                    removeUrl = "http://www.mixpix-xerox.com/ABI/deletepro.php";
                    ids = productsData.get(position).getId();

                    Toast.makeText(Add.this, "Long press on position :"+ids,
                            Toast.LENGTH_LONG).show();
                }

                removefromServer(ids,removeUrl);
            }
        }));
    }

    //////////////////////////////////////////////////////
    //Search Service
    private void filter(String s,String text,boolean bu,boolean namebu) {
        //new array list that will hold the filtered data
        ArrayList<Customers> filterd = new ArrayList<>();
        ArrayList<Products> filterd2 = new ArrayList<>();

        if (s.equals("Cus")) {
           //looping through existing elements
           for (Customers obj : customersData) {
               //if Name is checked
               if (namebu) {
                   //if the existing elements contains the search input
                   if (obj.getName().toLowerCase().contains(text.toLowerCase())) {
                       //adding the element to filtered list
                       filterd.add(obj);
                   }
               } else if (bu) {
                   //if the existing elements contains the search input
                   if (obj.getPhone().toLowerCase().contains(text.toLowerCase())) {
                       //adding the element to filtered list
                       filterd.add(obj);
                   }
               }

           }
           //calling a method of the adapter class and passing the filtered list
           adapter.filteredcustomers(filterd);
       }
       else
       {
           //looping through existing elements
           for (Products obj : productsData) {
               //if Name is checked
               if (namebu) {
                   //if the existing elements contains the search input
                   if (obj.getName().toLowerCase().contains(text.toLowerCase())) {
                       //adding the element to filtered list
                       filterd2.add(obj);
                   }
               } else if (bu) {
                   //if the existing elements contains the search input
                   if (obj.getSkuX().toLowerCase().contains(text.toLowerCase())) {
                       //adding the element to filtered list
                       filterd2.add(obj);
                   }
               }

           }
           //calling a method of the adapter class and passing the filtered list
           adapter2.filteredproducts(filterd2);
       }


    }
    ////////////////////////////////////////////////////////////////
    public void removefromServer(final String id,String removeUrl){
         RequestQueue queue = Volley.newRequestQueue(Add.this);
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
    //////////////////////////////////////////////////////////
    public void updatefromServer(final String updateUrl, final String ids,final String gender)
    {
        Toast.makeText(Add.this, "updated "+ids,
                Toast.LENGTH_LONG).show();

        RequestQueue queue = Volley.newRequestQueue(Add.this);
        String cat = null;
        showSnackbar("The item was edited");

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
                if (S.equals("Customers")) {
                    String name = cusname.getText().toString();
                    String phone = cusphone.getText().toString();
                    String email = cusemail.getText().toString();
                   // String Gender = gender.getText().toString();
                    String noteS = note.getText().toString();
                    String passS = pass.getText().toString();

                    params.put("id", ids);
                    params.put("name", name);
                    params.put("phone", phone);
                    params.put("email", email);
                    params.put("type", gender);
                    params.put("notes", noteS);
                    params.put("pass", passS);

                } else  {
                   /* String name = productname.getText().toString();
                    String skuS = sku.getText().toString();
                    String actuallypriceS = actuallyprice.getText().toString();
                    String quantitytypeS = quantitytype.getText().toString();
                    String notesS = notes.getText().toString();
                    String barcodeS = barcode.getText().toString();
                    String sellingpriceS = sellingprice.getText().toString();
                    */
                    params.put("id", ids);
                    params.put("qty", quantitytype.getText().toString());
                    params.put("name", productname.getText().toString());
                    params.put("Aprice",actuallyprice.getText().toString());
                    params.put("sku", sku.getText().toString());
                    params.put("price", sellingprice.getText().toString());
                    params.put("desc",notes.getText().toString());


                }
                return params;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }
    //////////////////////////////////////////////////////////
    public void AdditioninDB(final String Str,final String URL,final String gender) {
        ///////////////////////////////////////////////////////

            ///////////////////////////////////////////////////////
        showSnackbar("Added");

            RequestQueue queue = Volley.newRequestQueue(Add.this);
            String response = null;


            final String finalResponse = response;

            StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                //Addition(v,Str);
                                //ShowinDB(Str,URL);
                                //showSnackbar(response);


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
                    if (Str.equals("Customers")) {
                        String name = cusname.getText().toString();
                        String phone = cusphone.getText().toString();
                        String email = cusemail.getText().toString();
                       // String genderS = gender.getText().toString();
                        String passS = pass.getText().toString();
                        String noteS = note.getText().toString();


                        params.put("Name", name);
                        params.put("phone", phone);
                        params.put("email", email);
                        params.put("type", gender);
                        params.put("notes", noteS);
                        params.put("pass", passS);

                    } else  {
                        String name = productname.getText().toString();
                        String skuS = sku.getText().toString();
                        String actuallypriceS = actuallyprice.getText().toString();
                        String quantitytypeS = quantitytype.getText().toString();
                        String notesS = notes.getText().toString();
                        String barcodeS = barcode.getText().toString();
                        String sellingpriceS = sellingprice.getText().toString();
                        params.put("Name", name);
                        params.put("qty", quantitytypeS);
                        params.put("actprice", actuallypriceS);
                        params.put("not",notesS);
                        params.put("barcode", barcodeS);
                        params.put("sellingprice", sellingpriceS);


                    }
                    return params;
                }

            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(postRequest);


        }
//////////////////////////////////////////////////////////////////
    public void AdditioninDB2(final String Str, String Url)
    {

        ///////////////////////////////////////////////////////
        showSnackbar("Added");

        RequestQueue queue = Volley.newRequestQueue(Add.this);
        String response = null;


        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

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

                 /*   String name = productname.getText().toString();
                    String skuS = sku.getText().toString();
                    String actuallypriceS = actuallyprice.getText().toString();
                    String quantitytypeS = quantitytype.getText().toString();
                    String notesS = notes.getText().toString();
                    String barcodeS = barcode.getText().toString();
                    String sellingpriceS = sellingprice.getText().toString();
                   */
                    params2.put("Name", productname.getText().toString());
                    params2.put("qty", quantitytype.getText().toString());
                    params2.put("actprice",  actuallyprice.getText().toString());
                    params2.put("not", notes.getText().toString());
                    params2.put("barcode", sku.getText().toString());
                    params2.put("sellingprice", sellingprice.getText().toString());



                return params2;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }
        /////////////////////////////////////////////////////////////////////////////////
        public void ShowinDB(final String Str, final String showUrl,final String addproductforsale) {
            ///////////////////////////////////////////////////////
            //Toast.makeText(getApplicationContext(), strItem + " essssssssssss", Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(),"shooooooooooo" ,Toast.LENGTH_LONG ).show();


            ///////////////////////////////////////////////////////

            RequestQueue queue = Volley.newRequestQueue(Add.this);
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
                                        if (Str.equals("Products")) {
                                            String id = object.getString("Item_ID");
                                            String name = object.getString("Name");
                                            String quantitytypeS = object.getString("pro_quantity");

                                            String actuallypriceS = object.getString("proPrice");
                                            String skuS = object.getString("code");
                                            String descrp = object.getString("Descrption");
                                            Products products = new Products(id,name, quantitytypeS
                                                    , actuallypriceS, skuS,descrp);

                                            //Add each object with value in the array 'productsData'
                                            productsData.add(products);
                                            Addition(v, Str,id);

                                        }
                                        else if (Str.equals("Customers"))
                                        {

                                            String id = object.getString("ID");
                                            String Name = object.getString("name");
                                            String phone = object.getString("phone");
                                            String email = object.getString("email");
                                            String gender = object.getString("type");
                                            String note = object.getString("nots");
                                            String pass = object.getString("Password");
                                          //  String pass = object.getString("pass");
                                            Customers customers = new Customers(id,Name,phone,email,pass,note,gender);
                                            //Add each object with value in the array 'customersData'
                                            customersData.add(customers);

                                            Addition(v, Str,id);

                                        }
                                        else if (Str.equals("Sales")) {




                                            if (addproductforsale.equals("1"))
                                            {    //Names of Products
                                                String name = object.getString("Name");
                                               // List<String> actuallypriceL =new ArrayList<String>();
                                                String actuallypriceS = object.getString("proPrice");
                                                ProductsSpin.add(name);
                                                actuallypriceL.add(actuallypriceS);
                                            addProductforSale(ProductsSpin,actuallypriceL);
                                        }
                                            else
                                            {    String id = object.getString("id");
                                                //Names of Customers
                                                String Name = object.getString("name");
                                                CustomersSpin.add(Name);
                                            addSale(v, CustomersSpin,selectedProduct);
                                        }

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
            );
            postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(postRequest);


        }
        ////////////////////////////////////////////////////////////////////
        public void showSnackbar (String stringSnackbar){
            snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        }

}