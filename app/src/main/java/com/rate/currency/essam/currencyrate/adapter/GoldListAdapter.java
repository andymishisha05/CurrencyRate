package com.rate.currency.essam.currencyrate.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rate.currency.essam.currencyrate.R;
import com.rate.currency.essam.currencyrate.model.GoldModel;

import java.util.List;


/**
 * Created by EsSaM on 7/15/2017.
 */

public class GoldListAdapter extends RecyclerView.Adapter<GoldListAdapter.ViewHolder> {
    Context context ;
    List <GoldModel> data ;
    Typeface myType ;
    public GoldListAdapter (Context context , List <GoldModel>data){
        this.context=context ;
        this.data=data;
        myType = Typeface.createFromAsset(context.getAssets(),"arabic.ttf");

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gold_price_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.nameAr.setText(data.get(position).getNameEN());
        holder.nameAr.setTypeface(myType);
        holder.nameEn.setText(data.get(position).getNameAR());
        holder.nameEn.setTypeface(myType);
        holder.price.setText(data.get(position).getPrice());
        holder.price.setTypeface(myType);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  nameAr;
        TextView nameEn ;
        TextView price ;

        public ViewHolder(View itemView) {
            super(itemView);

            nameEn=(TextView)itemView.findViewById(R.id.textView2);
            nameAr=(TextView)itemView.findViewById(R.id.textView3);
            price=(TextView)itemView.findViewById(R.id.text4);

        }
    }
}
