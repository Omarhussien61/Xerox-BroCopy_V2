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

public class reportAdapter2 extends RecyclerView.Adapter<reportAdapter2.MyViewHolder>
        //implements Filterable
{
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private List<Report2> report2List;
    private Context context;

    public reportAdapter2(Context context, List<Report2> report2List)
    {
        this.context = context;
        this.report2List = report2List;
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
        Report2 report2 = report2List.get(position);
        holder.txtcusName.setText(report2.cusname);
        holder.txtName.setText(report2.name);
        holder.txtLocation.setText(report2.qty);
        holder.txtStadiumName.setText(report2.total);
        holder.txtStarPlayerName.setText(report2.date);

    }

    @Override
    public int getItemCount()
    {
        return report2List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
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
    public void filteredreports(ArrayList<Report2> filteredreports) {
        this.report2List = filteredreports;
        notifyDataSetChanged();
    }

}