package com.tariqs.xerox;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;


public class custtomersAdapter extends RecyclerView.Adapter<custtomersAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<Customers> customersData;
    private LayoutInflater inflater;


    private int previousPosition = 0;

    public custtomersAdapter(Context context,ArrayList<Customers> customersData) {


        this.context = context;
        this.customersData = customersData;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,   int   position) {

        View view = inflater.inflate(R.layout.cusmodel, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    //the view of the items
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {



        myViewHolder.name.setText(customersData.get(position).getId()+"/"+
                customersData.get(position).getName());
        myViewHolder.phoneX.setText(customersData.get(position).getPhone());
        myViewHolder.emailX.setText(customersData.get(position).getEmail());
        final Customers infoData = customersData.get(position);





        previousPosition = position;


        final int currentPosition = position;




    }

    @Override
    //the size of each item of the array
    public int getItemCount() {
        return customersData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,phoneX,emailX;


        // access All by ID
        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            phoneX =(TextView) itemView.findViewById(R.id.phoneX);
            emailX = (TextView) itemView.findViewById(R.id.emailX);

        }
    }
    public void filteredcustomers(ArrayList<Customers> filteredcustomers) {
        this.customersData = filteredcustomers;
        notifyDataSetChanged();
    }



}
