package com.tariqs.xerox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class LibraryAdapter2 extends RecyclerView.Adapter<LibraryAdapter2.MyViewHolder> {

    public Context context;
    public ArrayList<LibraryData> libraryData;
    private LayoutInflater inflater;



    private int previousPosition = 0;

    public LibraryAdapter2(Context context, ArrayList<LibraryData> libraryData) {


        this.context = context;
        this.libraryData = libraryData;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,   int   position) {

        View view = inflater.inflate(R.layout.libraries_model, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    //the view of the items
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {


        myViewHolder.no_of_papers.setText(libraryData.get(position).getNumberof()+" Papers");
        myViewHolder.date.setText(libraryData.get(position).getDateadd());
       // myViewHolder.actualpriceX.setText(productsData.get(position).getActualpriceX());
       // myViewHolder.amountX.setText(productsData.get(position).getAmountX());




        previousPosition = position;


        final int currentPosition = position;
        final LibraryData infoData = libraryData.get(position);




    }

    @Override
    //the size of each item of the array
    public int getItemCount() {
        return libraryData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView no_of_papers,date;


        // access All by ID
        public MyViewHolder(View itemView) {
            super(itemView);

            no_of_papers = (TextView) itemView.findViewById(R.id.no_of_papers);
            date =(TextView) itemView.findViewById(R.id.date);
           // actualpriceX = (TextView) itemView.findViewById(R.id.actualpriceX);
           // amountX = (TextView) itemView.findViewById(R.id.amountX);


        }
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(LibraryData infoData) {

        int currPosition = libraryData.indexOf(infoData);
        libraryData.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, LibraryData infoData) {

        libraryData.add(position, infoData);
        notifyItemInserted(position);
    }

}
