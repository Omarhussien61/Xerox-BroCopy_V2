package com.tariqs.xerox;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.tariqs.xerox.R;
import com.tariqs.xerox.SalesModel;

import java.util.ArrayList;
import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder>
      //  implements Filterable
         {

    public Context context;
    public ArrayList<SalesModel> salesModels;
    private LayoutInflater inflater;
    private List<SalesModel> filteredsalesModels;


    private int previousPosition = 0;

    public SalesAdapter(Context context, ArrayList<SalesModel> salesModels) {


        this.context = context;
        this.salesModels = salesModels;
        this.filteredsalesModels = filteredsalesModels;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.showsalesmodel, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    //the view of the items
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {

        myViewHolder.name.setText(salesModels.get(position).getBill_name());
        myViewHolder.skuX.setText(salesModels.get(position).getBill_date());
        myViewHolder.actualpriceX.setText(salesModels.get(position).getNum());
        myViewHolder.amountX.setText(salesModels.get(position).getTotal());

        previousPosition = position;


        final int currentPosition = position;
        final SalesModel infoData = salesModels.get(position);


    }

    @Override
    //the size of each item of the array
    public int getItemCount() {
        return salesModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, skuX, actualpriceX, amountX;


        // access All by ID
        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            skuX = (TextView) itemView.findViewById(R.id.skuX);
            actualpriceX = (TextView) itemView.findViewById(R.id.actualpriceX);
            amountX = (TextView) itemView.findViewById(R.id.amountX);


        }
    }
   /* @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                String charString = charSequence.toString();
                if (charString.isEmpty())
                {
                    filteredsalesModels = salesModels;
                } else
                {
                    List<SalesModel> filteredList = new ArrayList<>();
                    for (SalesModel obj : salesModels)
                    {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (obj.name.toLowerCase().contains(charString.toLowerCase()) )
                        {
                            filteredList.add(obj);
                        }
                    }

                    filteredsalesModels = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredsalesModels;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filteredsalesModels = (ArrayList<SalesModel>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
    */
   //This method will filter the list
   //here we are passing the filtered data
   //and assigning it to the list with notifydatasetchanged method
   public void filteredsalesModels(ArrayList<SalesModel> filteredsalesModels) {
       this.salesModels = filteredsalesModels;
       notifyDataSetChanged();
   }
         }
