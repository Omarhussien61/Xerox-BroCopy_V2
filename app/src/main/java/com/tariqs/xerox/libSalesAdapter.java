package com.tariqs.xerox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class libSalesAdapter extends RecyclerView.Adapter<libSalesAdapter.MyViewHolder> {
    public Context context;
    public ArrayList<libSalesData> libsalesData;
    private LayoutInflater inflater;


    private int previousPosition = 0;

    public libSalesAdapter(Context context, ArrayList<libSalesData> libsalesData) {


        this.context = context;
        this.libsalesData = libsalesData;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public libSalesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.libraries_model2, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
//the view of the items
    public void onBindViewHolder(final libSalesAdapter.MyViewHolder myViewHolder, final int position) {



        myViewHolder.bill_name.setText(libsalesData.get(position).getBill_name());
        myViewHolder.bill_date.setText(libsalesData.get(position).getBill_date());
        myViewHolder.total_b_desc.setText(libsalesData.get(position).getTotal_b_desc());
        myViewHolder.total_a_desc.setText(libsalesData.get(position).getTotal_a_desc());
        myViewHolder.tax14.setText(libsalesData.get(position).getTax14());
        myViewHolder.disc.setText(libsalesData.get(position).getDisc());
        myViewHolder.total_a_tax.setText(libsalesData.get(position).getTotal_a_tax());

        final libSalesData infoData = libsalesData.get(position);




        previousPosition = position;


        final int currentPosition = position;


    }

    @Override
//the size of each item of the array
    public int getItemCount() {
        return libsalesData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bill_name, bill_date, total_b_desc,disc,total_a_desc,total_a_tax,tax14;


        // access All by ID
        public MyViewHolder(View itemView) {
            super(itemView);

            bill_name = (TextView) itemView.findViewById(R.id.bill_name);
            bill_date = (TextView) itemView.findViewById(R.id.bill_date);
            total_b_desc = (TextView) itemView.findViewById(R.id.total_b_desc);
            disc = (TextView) itemView.findViewById(R.id.disc);
            total_a_desc = (TextView) itemView.findViewById(R.id.total_a_desc);
            total_a_tax = (TextView) itemView.findViewById(R.id.total_a_tax);
            tax14 = (TextView) itemView.findViewById(R.id.tax14);



        }
    }
}

