package com.tariqs.xerox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;

import com.tariqs.xerox.util.model.Report2;

import java.util.ArrayList;
import java.util.List;

public class libSalesAdapter2 extends RecyclerView.Adapter<libSalesAdapter2.MyViewHolder>
        //implements Filterable
{
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private List<Report2> report2List;
    private Context context;
    public ArrayList<libSalesData> libsalesData;

    public libSalesAdapter2(Context context, ArrayList<libSalesData> libsalesData)
    {
        this.context = context;
        this.libsalesData = libsalesData;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position % 2 == 0)
        {
            return TYPE_ROW_COLORFUL;
        }

        return TYPE_ROW;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        if (viewType == TYPE_ROW)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reportmodel_t, viewGroup, false);
            return new MyViewHolder(view);
        } else
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_club_colorful,
                    viewGroup, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {

        holder.txtcusName.setText(libsalesData.get(position).getBill_name());
        holder.txtName.setText(libsalesData.get(position).getBill_date());
        holder.txtLocation.setText(libsalesData.get(position).getTotal_b_desc());
        holder.txtStadiumName.setText(libsalesData.get(position).getTotal_a_desc()+" LE");
        holder.txtStarPlayerName.setText(libsalesData.get(position).getBill_id());


    }

    @Override
    public int getItemCount()
    {
        return libsalesData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
       // TextView bill_name, bill_date, total_b_desc,disc,total_a_desc,total_a_tax,tax14;
       public TextView txtcusName,txtName, txtLocation, txtStadiumName,   txtStarPlayerName;

        public MyViewHolder(View view)
        {
            super(view);
            txtcusName = view.findViewById(R.id.txtcusName);
            txtName = view.findViewById(R.id.txtName);
            txtLocation = view.findViewById(R.id.txtLocation);
            txtStadiumName = view.findViewById(R.id.txtStadiumName);
            txtStarPlayerName = view.findViewById(R.id.txtStarPlayerName);
        }
    }


}