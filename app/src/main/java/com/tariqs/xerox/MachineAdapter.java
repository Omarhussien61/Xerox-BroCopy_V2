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


public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<machinesModel> machinesData;
    private LayoutInflater inflater;



    private int previousPosition = 0;

    public MachineAdapter(Context context,ArrayList<machinesModel> machinesData) {


        this.context = context;
        this.machinesData = machinesData;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,   int   position) {

        View view = inflater.inflate(R.layout.machinemodel, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    //the view of the items
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {



        myViewHolder.name.setText(machinesData.get(position).getMid());
        myViewHolder.cusNameX.setText(machinesData.get(position).getCusname());
        myViewHolder.DataddX.setText(machinesData.get(position).getAdddate());
        myViewHolder.stX.setText(machinesData.get(position).getMo());




        previousPosition = position;


        final int currentPosition = position;
        final machinesModel infoData = machinesData.get(position);




    }

    @Override
    //the size of each item of the array
    public int getItemCount() {
        return machinesData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,cusNameX,DataddX,stX;


        // access All by ID
        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            cusNameX =(TextView) itemView.findViewById(R.id.cusNameX);
            DataddX = (TextView) itemView.findViewById(R.id.DataddX);
            stX = (TextView) itemView.findViewById(R.id.stX);


        }
    }
    public void filteredmachines(ArrayList<machinesModel> filteredmachines) {
        this.machinesData = filteredmachines;
        notifyDataSetChanged();
    }


}
