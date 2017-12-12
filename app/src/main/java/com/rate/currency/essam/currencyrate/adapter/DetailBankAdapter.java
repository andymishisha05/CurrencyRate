package com.rate.currency.essam.currencyrate.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rate.currency.essam.currencyrate.R;
import com.rate.currency.essam.currencyrate.model.DetailBankModel;
import com.squareup.picasso.Picasso;

import java.util.List;



/**
 * Created by EsSaM on 7/18/2017.
 */

public class DetailBankAdapter extends RecyclerView.Adapter<DetailBankAdapter.ViewHolder> {
    List <DetailBankModel> models ;
    Context context;
    Typeface myType ;

    public DetailBankAdapter(List  models, Context context) {
        this.models = models;
        this.context = context;
        myType = Typeface.createFromAsset(context.getAssets(),"arabic.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(models.get(position).getImg()).into(holder.bankIcon);
        holder.bankName.setText(models.get(position).getNameCurr());
        holder.bankName.setTypeface(myType);
        holder.bankNamear.setText(models.get(position).getCodeCurr());
        holder.bankNamear.setTypeface(myType);
        holder.currBuy.setText(models.get(position).getBuy());
        holder.currBuy.setTypeface(myType);
        holder.currSell.setText(models.get(position).getSell());
        holder.currSell.setTypeface(myType);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bankIcon ;
        TextView  bankName ;
        TextView  bankNamear;
        TextView  currSell ;
        TextView  currBuy  ;
        RelativeLayout relativeLayout ;
        public ViewHolder(View itemView) {
            super(itemView);
            bankIcon =(ImageView)itemView.findViewById(R.id.bank_logo_imageView);
            bankName =(TextView)itemView.findViewById(R.id.banks_name_textView);
            bankNamear =(TextView)itemView.findViewById(R.id.banks_name_textViewar);
            currBuy=(TextView)itemView.findViewById(R.id.buyCurr_TextView);
            currSell= (TextView) itemView.findViewById(R.id.currSEll_TextView);
            relativeLayout =(RelativeLayout)itemView.findViewById(R.id.cardview);
        }
    }
}
