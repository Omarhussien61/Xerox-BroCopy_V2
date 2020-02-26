package com.tariqs.xerox;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class LibraryReport extends AppCompatActivity {
    LibraryAdapter adapter3;
    libSalesAdapter2 adapter2;
    public ArrayList<LibraryData> libraryData = new ArrayList<>();
    public ArrayList<libSalesData> libsalesData = new ArrayList<>();
    RecyclerView RV;
    String URL,ID,bu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bu = getIntent().getStringExtra("bu");

        if (bu.equals("sa")) {
            setContentView(R.layout.activity_library_report3);
            RV = (RecyclerView) findViewById(R.id.rvClub);

            URL = "http://www.mixpix-xerox.com/ABI/selectsales.php?id=";
            ID = getIntent().getStringExtra("id");
           // Toast.makeText(getApplicationContext(),"reeeee"+ID,Toast.LENGTH_LONG ).show();

            load_data_from_server(0,ID,URL,bu);

            adapter2 = new libSalesAdapter2(LibraryReport.this, libsalesData);
            RV.setAdapter(adapter2);
            RV.setLayoutManager(new LinearLayoutManager(LibraryReport.this)); // Vertical Orientation By Default
            LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(LibraryReport.this); // (Context context)
            mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
            RV.setLayoutManager(mLinearLayoutManagerVertical);
        }
else
        {
            setContentView(R.layout.activity_library_report2);
            RV = (RecyclerView) findViewById(R.id.rvClub);

            URL = "http://www.mixpix-xerox.com/ABI/selectelib.php?id=";
            ID = getIntent().getStringExtra("id");
            //Toast.makeText(getApplicationContext(),"Seee"+ID,Toast.LENGTH_LONG ).show();

            load_data_from_server(0,ID,URL,bu);
            //showreport(cusID,URL);

            adapter3 = new LibraryAdapter(LibraryReport.this, libraryData);
            RV.setAdapter(adapter3);
            RV.setLayoutManager(new LinearLayoutManager(LibraryReport.this)); // Vertical Orientation By Default
            LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(LibraryReport.this); // (Context context)
            mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
            RV.setLayoutManager(mLinearLayoutManagerVertical);
        }
        }
    //Show Reports as Needed if Sales or no_of_papers
    private void load_data_from_server(int id,final String S,final String url,final String bu) {
        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                okhttp3.Request request = new okhttp3.Request.Builder()
                        //we put tge 'id' here to get the data by id of the customer
                        .url( url+S)
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
                            String numof = object.getString("num");
                            String dateadd = object.getString("pdate");
                            String machine = object.getString("mid");
                            String cus = object.getString("cid");



                            LibraryData data = new LibraryData(id, numof, dateadd);

                            //Add each object with value in the array 'libraryData'
                            libraryData.add(data);
                        }
                        else
                        {
                            //   String bill_name = object.getString("bill_name");
                            String bill_id = object.getString("bill_id");
                           // String bill_date = object.getString("bill_date");
                            String bi_qtty = object.getString("bi_qtty");
                            String bi_price = object.getString("bi_price");
                            String bi_total = object.getString("bi_total");
                            String item_name = object.getString("item_name");
                            String PRO_NAME = object.getString("PRO_NAME");
                            String cid = object.getString("cid");

                            libSalesData data2 = new libSalesData(bill_id, item_name, PRO_NAME, bi_qtty
                                    ,bi_price,bi_total,cid,PRO_NAME);

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
                if (bu.equals("sa"))
                adapter2.notifyDataSetChanged();
                else if (bu.equals("re"))
                    adapter3.notifyDataSetChanged();

            }
        };
        task.execute(id);
    }
}
