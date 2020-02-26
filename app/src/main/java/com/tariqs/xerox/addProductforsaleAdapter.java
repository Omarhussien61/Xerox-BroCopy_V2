package com.tariqs.xerox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class addProductforsaleAdapter extends RecyclerView.Adapter<addProductforsaleAdapter.MyViewHolder> {
    public Context context;
    public ArrayList<addProductforsale> addproforsaleData;
    private LayoutInflater inflater;


    private int previousPosition = 0;

    public addProductforsaleAdapter(Context context, ArrayList<addProductforsale> addproforsaleData) {


        this.context = context;
        this.addproforsaleData = addproforsaleData;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public addProductforsaleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.salemodel, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    //the view of the items
    public void onBindViewHolder(final addProductforsaleAdapter.MyViewHolder myViewHolder, final int position) {


        myViewHolder.Detailx.setText(addproforsaleData.get(position).getName());
        myViewHolder.qtyx.setText(addproforsaleData.get(position).getQty());
        myViewHolder.pricex.setText(addproforsaleData.get(position).getAPrice());
        final addProductforsale infoData = addproforsaleData.get(position);
        int qtyi = Integer.parseInt(addproforsaleData.get(position).getQty());
        int pricei = Integer.parseInt(addproforsaleData.get(position).getAPrice());
        int subT = qtyi * pricei;
        String subT_S = String.valueOf(subT);
        myViewHolder.subtotalx.setText(subT_S);



        previousPosition = position;


        final int currentPosition = position;


    }

    @Override
    //the size of each item of the array
    public int getItemCount() {
        return addproforsaleData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Detailx, qtyx, pricex,subtotalx;


        // access All by ID
        public MyViewHolder(View itemView) {
            super(itemView);

            Detailx = (TextView) itemView.findViewById(R.id.Detailx);
            qtyx = (TextView) itemView.findViewById(R.id.qtyx);
            pricex = (TextView) itemView.findViewById(R.id.pricex);
            subtotalx = (TextView) itemView.findViewById(R.id.subtotalx);

        }
    }
}


