package com.example.todoapp;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//performs functions to display all the data into a row(s) in recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

   OnLongClickListener longClickListener;
   OnClickListener clickListener;
   public interface OnClickListener{
       void onItemClicked(int position);
   }
    //implemented by main activity
    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }

    List<String> items;

    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use inflater to inflate a view - inflate XML layout to render the items by creating a view in memory
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        //wrap it in the View Holder class
        return new ViewHolder(todoView);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        //grab item at position mentioned
        String specItem = items.get(position);
        //bind the item into the specified view holder (binding process of Adapter)
        holder.bind(specItem);

    }

    //counts number of items in the RV list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //container that provides view of each row of list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //update the view holder with the data inside String specItem
        public void bind(String specItem) {

            tvItem.setText(specItem);
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //return the position
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true; //callback is consuming on-click and notifying which viewholder was long pressed
                }
            });
        }
    }
}
