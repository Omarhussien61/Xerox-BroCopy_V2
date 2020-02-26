package com.tariqs.xerox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
//import com.android.volley.request.SimpleMultiPartRequest;

public class Main3Activity extends AppCompatActivity
        implements View.OnClickListener ,
        AdapterView.OnItemSelectedListener ,
        NavigationView.OnNavigationItemSelectedListener {
   // public static final String UPLOAD_URL = "http://www.mixpix-xerox.com/ABI/upload.php";
    public static final String UPLOAD_URL = "http://www.mixpix-xerox.com/ABI/up.php";
    ProgressDialog loading;
    boolean editB;
    // public static final String UPLOAD_KEY = "image";
  // ArrayList<ImageData> imgData = new ArrayList<>();
    String id,cusname,macname,papers,adddate,mid,cid;
    public static final String TAG = "MY MESSAGE";

    private int PICK_IMAGE_REQUEST = 1;

    private TextView buttonChoose;
    private Button buttonUpload;
    String showUrl;
    private ImageView imageView;

    private Bitmap bitmap;
    List<String> MachinesSpin = new ArrayList<String>();
    List<String> macIDs = new ArrayList<String>();

    private Uri filePath;
//////////////////////////////////////////////////////////////
    Button show_report,show_sales,edit,changeB;
    EditText userN,passW;
    TextView title,Name,phone;
    RecyclerView RV;
    MachineAdapter adapter;

    LibraryAdapter adapter3;
    libSalesAdapter adapter2;
    String no_of_papers,cusID,URL,pass;
    String bu,cas,name,groupid,ID,macID;
    public ArrayList<LibraryData> libraryData = new ArrayList<>();
    public ArrayList<libSalesData> libsalesData = new ArrayList<>();
    public ArrayList<machinesModel> machinesData = new ArrayList<>();
    Spinner spinnerMac;
    EditText Papers;
    ImageView exit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

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
        name = getIntent().getStringExtra("name");
        SharedPreferences sharedPreferences = getSharedPreferences(
                Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ID = sharedPreferences.getString(Config.id_SHARED_PREF
                ,"Not Available");
       // Toast.makeText(getApplicationContext(),"Seee"+ID,Toast.LENGTH_LONG ).show();

        Name.setText(name);
        navigationView.setNavigationItemSelectedListener(this);


        ////////////////////////////////////////////////////
        spinnerMac = (Spinner) findViewById(R.id.spinnerMac);
        buttonChoose = (TextView) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        ////////////////////////////////////////////
        Papers = (EditText) findViewById(R.id.papers);
        show_sales = (Button) findViewById(R.id.b1);
        show_report =(Button) findViewById(R.id.b2);
        //edit = (Button) findViewById(R.id.b3);
        RV = (RecyclerView) findViewById(R.id.Rv);

        show_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // no_of_papers=Papers.getText().toString().trim();
                //URL = "http://n.exeative.com/ABI/selectsales.php";
                URL = "http://www.mixpix-xerox.com/ABI/selectelib.php?customerid=";
                //Fetching name and cas  from shared preferences
                SharedPreferences sharedPreferences = getSharedPreferences(
                        Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                ID = sharedPreferences.getString(Config.id_SHARED_PREF
                        ,"Not Available");
                bu = "re";
        //        load_data_from_server(0,ID,URL,bu);
                //showreport(cusID,URL);
                Intent i = new Intent(Main3Activity.this
                        , LibraryReport.class);
                i.putExtra("URL",URL);
                i.putExtra("id",ID);
                i.putExtra("bu",bu);
                startActivity(i);


            }
        });
        show_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //no_of_papers=Papers.getText().toString().trim();
                URL = "http://www.mixpix-xerox.com/ABI/selectsales.php?id=";
                SharedPreferences sharedPreferences = getSharedPreferences(
                        Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                ID = sharedPreferences.getString(Config.id_SHARED_PREF
                        ,"Not Available");                bu = "sa";
      //          load_data_from_server(0,ID,URL,bu);
                //showreport(cusID,URL);
                Intent i = new Intent(Main3Activity.this
                        , LibraryReport.class);
                i.putExtra("URL",URL);
                i.putExtra("id",ID);
                i.putExtra("bu",bu);

                startActivity(i);

            }
        });

        //////////////////////////////////////////////////////////////////


                showUrl = "http://www.mixpix-xerox.com/ABI/returnmachine.php";
        ID = getIntent().getStringExtra("id");

        ShowinDB(ID,showUrl);
        //MachinesSpin.add("HP Printer");
        //MachinesSpin.add("Xerrox Scanner");

        ///////////////////////////////////////////////

        imageView = (ImageView) findViewById(R.id.imageView);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }
///////////////////////////////////////////////////////////////////////////
public void editProfile(final String cusID,final String phone,final String pass,final String editURL)
{

    Toast.makeText(getApplicationContext(),"Edited",Toast.LENGTH_LONG ).show();

    RequestQueue queue = Volley.newRequestQueue(Main3Activity.this);
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




} @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.signOut) {
            //Creating a shared preference
            SharedPreferences sharedPreferences = Main3Activity.this
                    .getSharedPreferences(Config.SHARED_PREF_NAME
                            , Context.MODE_PRIVATE);

            //Creating editor to store values to shared preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            sharedPreferences.edit().clear().apply();
            Intent i = new Intent(Main3Activity.this,MainActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //////////////////////////////////////////////////////

  ///////////////////////////////////////////////////////////
    //Performing action onItemSelected and onNothing selected of Spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {

        ////////////////////////////////////////////////////////////
        macID = macIDs.get(position);
       // Toast.makeText(getApplicationContext(), "Selected " +macID , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    /////////////////////////////////////////////////////////////////////

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                buttonUpload.setOnClickListener(this);

                //uploadBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
////////////////////////////////////////////////////////////
public byte[] getFileDataFromDrawable(Bitmap bitmap) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
}
///////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public void uploadImage() {
       class UploadImage extends AsyncTask<Bitmap, Void, String> {

           private ProgressDialog loading;
           private RequestHandler rh = new RequestHandler();
           private byte[] dataB;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Main3Activity.this, "Uploading Image", "Please wait...", true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "The Image was Uploaded Successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<>();
                //data.put(UPLOAD_KEY, uploadImage);

                data.put("pic",uploadImage );
                data.put("tags","tesssssssssssss");


                //data.put("image",uploadImage);
                String result = rh.sendPostRequest(UPLOAD_URL, data);

                return result;
            }


        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }
///////////////////////////////////////////////////////////////////////////
private void uploadBitmap(final Bitmap bitmap,final String ID) {

    loading = ProgressDialog.show(Main3Activity.this, "Adding ....", "Please wait...", true, true);

    //our custom volley request
    VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST
            ,UPLOAD_URL,
            new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    try {
                        JSONObject obj = new JSONObject(new String(response.data));
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "The Item was Added Successfully"+macID, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {

        /*
         * If you want to add more parameters with the image
         * you can do it here
         * here we have only one parameter with the image
         * which is tags
         * */
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
           // ID = getIntent().getStringExtra("id");
            no_of_papers = Papers.getText().toString().trim();
            params.put("mid", macID);
            params.put("cid", ID);
            params.put("num", no_of_papers);

            return params;
        }

        /*
         * Here we are passing image by renaming it with a unique name
         * */
        @Override
        protected Map<String, DataPart> getByteData() {
            Map<String, DataPart> params = new HashMap<>();
            long imagename = System.currentTimeMillis();
            params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
            return params;
        }
    };

    //adding the request to volley
    Volley.newRequestQueue(this).add(volleyMultipartRequest);
}
    /////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if(v == buttonUpload){
            //uploadImage();
            //Toast.makeText(getApplicationContext(),"Seee"+ID,Toast.LENGTH_LONG ).show();

             uploadBitmap(bitmap,ID);

        }
    }
    /////////////////////////////////////////////////////////////////////////////////
    public void ShowinDB(final String Cusid, final String showUrl) {
        ///////////////////////////////////////////////////////
        //Toast.makeText(getApplicationContext(), strItem + " essssssssssss", Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),"shooooooooooo" ,Toast.LENGTH_LONG ).show();
        // setContentView(R.layout.addsale);
       // Toast.makeText(this,"Our Machines.... " ,Toast.LENGTH_SHORT).show();

        ///////////////////////////////////////////////////////

        RequestQueue queue = Volley.newRequestQueue(Main3Activity.this);
        String cat = null;


        final String finalResponse = cat;

        StringRequest postRequest = new StringRequest(Request.Method.POST, showUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String cat) {


                        try {

                            JSONArray jsonArray = new JSONArray(cat);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String mid = object.getString("mid");
                                String id = object.getString("id");

                                //machinesModel mac = new machinesModel()
                               // MachinesSpin.add("99");
                                MachinesSpin.add(mid);
                                //MachinesSpin.add(id);
                               // macIDs.add("1");
                                macIDs.add(id);


                            }
                            spinner(MachinesSpin,macIDs);

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
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //the parameters that will taken with the request method



                params.put("id",Cusid);


                return params;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);


    }
    public void spinner(List<String> MachinesSpin,List<String> macIDs)
    {

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        spinnerMac.setOnItemSelectedListener(Main3Activity.this);

        //Creating the ArrayAdapter instance having the customer list
        ArrayAdapter aa = new ArrayAdapter(Main3Activity.this, android.R.layout.simple_spinner_item, MachinesSpin);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinnerMac.setAdapter(aa);


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

}
