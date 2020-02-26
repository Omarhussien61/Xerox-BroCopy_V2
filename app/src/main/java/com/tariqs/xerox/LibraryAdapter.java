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

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MyViewHolder>
        //implements Filterable
{
    private static final int TYPE_ROW = 0;
    private static final int TYPE_ROW_COLORFUL = 1;

    private List<Report2> report2List;
    private Context context;
    public ArrayList<LibraryData> libraryData;

    public LibraryAdapter(Context context, ArrayList<LibraryData> libraryData)
    {
        this.context = context;
        this.libraryData = libraryData;
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
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reportmodel_t2, viewGroup, false);
            return new MyViewHolder(view);
        } else
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_club_colorful2,
                    viewGroup, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.no_of_papers.setText(libraryData.get(position).getNumberof()+" Papers");
        holder.date.setText(libraryData.get(position).getDateadd());

    }

    @Override
    public int getItemCount()
    {
        return libraryData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView no_of_papers,date;

        public MyViewHolder(View view)
        {
            super(view);
            no_of_papers = (TextView) view.findViewById(R.id.no_of_papers);
            date =(TextView) view.findViewById(R.id.date);

        }
    }


}