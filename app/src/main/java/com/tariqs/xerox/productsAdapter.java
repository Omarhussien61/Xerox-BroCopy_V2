package com.tariqs.xerox;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


public class productsAdapter extends RecyclerView.Adapter<productsAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<Products> productsData;
    private LayoutInflater inflater;



    private int previousPosition = 0;

    public productsAdapter(Context context,ArrayList<Products> productsData) {


        this.context = context;
        this.productsData = productsData;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,   int   position) {

        View view = inflater.inflate(R.layout.prodmodel, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    //the view of the items
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {



        myViewHolder.name.setText(productsData.get(position).getName());
        myViewHolder.skuX.setText(productsData.get(position).getSkuX());
        myViewHolder.actualpriceX.setText(productsData.get(position).getActualpriceX());
        myViewHolder.amountX.setText(productsData.get(position).getAmountX());




        previousPosition = position;


        final int currentPosition = position;
        final Products infoData = productsData.get(position);




    }

    @Override
    //the size of each item of the array
    public int getItemCount() {
        return productsData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,skuX,actualpriceX,amountX;


        // access All by ID
        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            skuX =(TextView) itemView.findViewById(R.id.skuX);
            actualpriceX = (TextView) itemView.findViewById(R.id.actualpriceX);
            amountX = (TextView) itemView.findViewById(R.id.amountX);


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
    public void filteredproducts(ArrayList<Products> filteredproducts) {
        this.productsData = filteredproducts;
        notifyDataSetChanged();
    }
}
