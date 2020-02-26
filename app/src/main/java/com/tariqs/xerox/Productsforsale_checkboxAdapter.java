package com.tariqs.xerox;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public  class  Productsforsale_checkboxAdapter extends RecyclerView.Adapter<Productsforsale_checkboxAdapter.MyViewHolder>

{
     ArrayList<addProductforsale> checkedProducts=new ArrayList<>();
Button addProduct2;
    public Context context;
    public ArrayList<Products> productsData;
    private LayoutInflater inflater;
     String qty;

    private int previousPosition = 0;

    public Productsforsale_checkboxAdapter(Context context,ArrayList<Products> productsData) {


        this.context = context;
        this.productsData = productsData;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,   int   position) {

        View view = inflater.inflate(R.layout.productforsale_model, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    //the view of the items
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {


        ImageView productImageView;

        myViewHolder.nameTextView.setText(productsData.get(position).getName());
      /*  myViewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox myCheckBox= (CheckBox) v;
                ArrayList<addProductforsale> addProductforsales;
                String id,  currentProduct, qty, APrice;

                id = productsData.get(position).getId();

                qty = productsData.get(position).getAmountX();
                APrice = productsData.get(position).getActualpriceX();
                currentProduct= productsData.get(position).getName();
                addProductforsale checkedProductsObj = new addProductforsale(id,currentProduct,qty, APrice);
                Products currentproductObj = productsData.get(position);
                if(myCheckBox.isChecked()) {
                    currentproductObj.setSelected(true);
                    //checkedProducts.get(position).setName(currentProduct );
                    checkedProducts.add(checkedProductsObj);
                    //Toast.makeText(context, checkedProducts.get(position).getName()+id, Toast.LENGTH_LONG).show();
                    //myViewHolder.nameTextView.setText(checkedProducts.get(position).getName());

                }
                else if(!myCheckBox.isChecked()) {
                    currentproductObj.setSelected(false);
                    checkedProducts.remove(checkedProductsObj);
                  //  Toast.makeText(context, "Removed"+id, Toast.LENGTH_LONG).show();

                }

            }


        });
        */
        previousPosition = position;


        final int currentPosition = position;
        final Products infoData = productsData.get(position);
       // myViewHolder.qty.setChecked(productsData.get(position).isSelected());
        //final addProductforsale infoData2 = checkedProducts.get(position);
       // myViewHolder.name.setTag(position);
        /*
        myViewHolder.qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 qty = myViewHolder.qty.getText().toString().trim();
                Intent intent = new Intent("custom-message");
                intent.putExtra("quantity",qty);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


            }
        });
        */

        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                qty = myViewHolder.qty.getText().toString().trim();
                Intent intent = new Intent("custom-message");
                intent.putExtra("quantity",qty);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                Toast.makeText(context, qty, Toast.LENGTH_LONG).show();

                if (filterLongEnough()) {
                  //  populateList();

                }
            }

            private boolean filterLongEnough() {
                return myViewHolder.qty.getText().toString().trim().length() > 2;
            }
        };
        myViewHolder.qty.addTextChangedListener(fieldValidatorTextWatcher);

        myViewHolder.r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolder.r1.setBackground(ContextCompat.getDrawable(context, R.drawable.stroke4));
                myViewHolder.nameTextView.setTextColor(ContextCompat.getColor(context, R.color.stroke2));
                myViewHolder.qty.setTextColor(ContextCompat.getColor(context, R.color.stroke2));
                myViewHolder.qtyX.setTextColor(ContextCompat.getColor(context, R.color.stroke2));
                //final String quantityS = myViewHolder.qty.getText().toString();
           //checkedProducts.get(position).setQty(quantityS);

            }
        });





     /*    myViewHolder.setItemClickListener(new MyViewHolder.ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox myCheckBox= (CheckBox) v;
                ArrayList<addProductforsale> addProductforsales;
                String id,  currentProduct, qty, APrice;
                boolean ischecked;

                id = productsData.get(position).getId();
                //id ="3";
                qty = productsData.get(position).getAmountX();
                //qty ="6";
                APrice = productsData.get(position).getActualpriceX();
                //APrice = "89";
                currentProduct= productsData.get(position).getName();

                ischecked = false;
                Products currentproductObj = productsData.get(pos);

                    if (myCheckBox.isChecked()) {
                        addProductforsale checkedProductsObj = new addProductforsale(id, currentProduct, qty, APrice);
                        //addProductforsale checkedProductsObj = checkedProducts.get(pos);
                        currentproductObj.setSelected(true);
                        //checkedProductsObj.setChecked("true");
                        add(checkedProductsObj);
                        checkedProducts.add(checkedProductsObj);
                        //addItem(position,infoData2);
                        Toast.makeText(context, currentProduct + id, Toast.LENGTH_LONG).show();

                    } else if (!myCheckBox.isChecked()) {
                        currentproductObj.setSelected(false);
                        addProductforsale checkedProductsObj = new addProductforsale(id, currentProduct, qty, APrice);
                        //addProductforsale checkedProductsObj = checkedProducts.get(pos);
                        //checkedProductsObj.setChecked("false");
                        checkedProducts.remove(checkedProductsObj);
                        Toast.makeText(context, "Removed " + currentProduct + id, Toast.LENGTH_LONG).show();

                    }


                Context con = v.getContext();
                Intent i = new Intent(con, AddSale.class);
                i.putExtra("id",id);
                i.putExtra("qty",qty);
                i.putExtra("APrice",APrice);
                i.putExtra("currentProduct",currentProduct);

                con.startActivity(i);

            }
        });

*/

/////////////////////////////////////////////////////////////////////////////






    }

    @Override
    //the size of each item of the array
    public int getItemCount() {
        return productsData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
       implements View.OnClickListener
    {

        ItemClickListener itemClickListener;

        EditText qty;
        TextView nameTextView,qtyX;
        ImageView productImageView;
        Button addProduct2;
        LinearLayout r1;

        // access All by ID
        public MyViewHolder(View itemView) {
            super(itemView);
       //     this.setIsRecyclable(false);

            qty = (EditText) itemView.findViewById(R.id.qty);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            qtyX = (TextView) itemView.findViewById(R.id.qtyX);
            productImageView = (ImageView) itemView.findViewById(R.id.productImageView);
            qty.setOnClickListener(this);
            r1 = (LinearLayout) itemView.findViewById(R.id.r1);

        }

        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
        @Override
        public void onClick(View v) {
//            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
        interface ItemClickListener {
            void onItemClick(View v,int pos);
        }

    }


    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(Products infoData) {

        int currPosition = productsData.indexOf(infoData);
        productsData.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, Products infoData) {

        productsData.add(position, infoData);
        notifyItemInserted(position);
    }
  /*  private void addItem(int position, addProductforsale infoData2) {

        checkedProducts.add(position, infoData2);
        notifyItemInserted(position);
    }
    ///////////////////////////////////////////
    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(addProductforsale infoData2) {

        int currPosition = checkedProducts.indexOf(infoData2);
        checkedProducts.remove(currPosition);
        notifyItemRemoved(currPosition);
    }
    */
    private void findViewsById() {

    }

}
