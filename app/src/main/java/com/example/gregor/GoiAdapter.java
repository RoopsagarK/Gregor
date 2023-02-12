package com.example.gregor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GoiAdapter extends RecyclerView.Adapter<GoiAdapter.GoiViewHolder> {

    LayoutInflater layoutInflater;
    List<GoiDetail> goiDetailList;

    public  GoiAdapter(Context context,List<GoiDetail> details){
        this.layoutInflater = LayoutInflater.from(context);
        this.goiDetailList=details;

    }



    @NonNull
    @Override
    public GoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goiitem,parent,false);
        return new GoiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoiViewHolder holder, int position) {
        holder.itemName.setText(goiDetailList.get(position).getGroceryName());
        holder.itemPrice.setText("₹"+goiDetailList.get(position).getGroceryPrice());
        holder.itemPlace.setText(goiDetailList.get(position).getGroceryPlace());

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(goiDetailList.get(position).getGroceryTime()*1000);
        String date = DateFormat.getDateInstance().format(cal).toString();
        holder.itemDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class GoiViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemPlace,itemPrice,itemDate;


        public GoiViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.ItemNameView);
            itemPlace = itemView.findViewById(R.id.ItemPlace);
            itemPrice = itemView.findViewById(R.id.ItemPrice);
            itemDate = itemView.findViewById(R.id.ItemDate);

        }
    }

}
