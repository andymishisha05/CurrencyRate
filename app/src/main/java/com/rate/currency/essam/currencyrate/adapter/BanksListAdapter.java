package com.rate.currency.essam.currencyrate.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rate.currency.essam.currencyrate.R;
import com.rate.currency.essam.currencyrate.model.BankListModel;
import com.rate.currency.essam.currencyrate.ui.DetailBankActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by EsSaM on 7/10/2017.
 */

public class BanksListAdapter extends RecyclerView.Adapter<BanksListAdapter.ViewHolder>  implements Filterable {
    List <BankListModel> bankdata ;
    private List<BankListModel> filteredBank= null;

    private BankFilter cityFilter;
    Context context ;
    Typeface myType;
    public BanksListAdapter (Context context ,List bankdat){
        this.context=context;
        this.bankdata=bankdat ;
        myType = Typeface.createFromAsset(context.getAssets(),"arabic.ttf");
        this.filteredBank = new ArrayList<>();

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.banks_list_item,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bankName.setText(bankdata.get(position).getBankName());
        holder.bankName.setTypeface(myType);
        holder.bankNamear.setText(bankdata.get(position).getBankNemear());
        holder.bankNamear.setTypeface(myType);
        Picasso.with(context).load(bankdata.get(position).getImageID())
              .into(holder.bankIcon);
        NumberFormat format = new DecimalFormat("#0.000");

        double oldbuy = bankdata.get(position).getOldCurrBuy();
        double buy = bankdata.get(position).getCurrBuy();
        buy = Double.valueOf(format.format(buy));
        if (oldbuy>buy){
            holder.currBuy.setTextColor(Color.RED);
        }else if (oldbuy<buy){
            holder.currBuy.setTextColor(Color.GREEN);
        }

        double sell =bankdata.get(position).getCurrSell();
        sell = Double.valueOf(format.format(sell));

        double oldsell = bankdata.get(position).getOldCurrSell();
        if (sell>oldsell){
            holder.currSell.setTextColor(Color.GREEN);

        }else if (sell<oldsell) {
            holder.currSell.setTextColor(Color.RED);
        }


        if (sell>70){
            holder.currSell.setTextSize(10);
            holder.currBuy.setTextSize(10);
        }
        // holder.bankIcon.setBackground(bankdata.get(position).getImageID());
        holder.currBuy.setText(String.valueOf(buy));
        holder.currSell.setText(String.valueOf(sell));

        holder.currBuy.setTypeface(myType);
        holder.currSell.setTypeface(myType);



        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("RESPONSE", String.valueOf(bankdata.get(position).getBankCode()));
                String code =bankdata.get(position).getBankCode();
                Intent intent = new Intent(context, DetailBankActivity.class);
                Bundle b = new Bundle();
                b.putString("0",code);
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return bankdata.size();
    }

    @Override
    public Filter getFilter() {
        if (cityFilter == null)
            cityFilter = new BankFilter(this, bankdata);
        return cityFilter;
    }

    private  static class BankFilter extends Filter {

        private  final  BanksListAdapter  adapter ;
        private final List<BankListModel> originalList;
        private final List<BankListModel> filteredList;

        private BankFilter(BanksListAdapter adapter, List<BankListModel> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new ArrayList<>(originalList);
            filteredList=new ArrayList<>();

        }


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                if(isProbablyArabic(filterPattern)){
                    for (final BankListModel list : originalList) {
                        if (list.getBankNemear().toLowerCase().contains(filterPattern)) {
                            filteredList.add(list);
                        }
                    }

                }else {
                    for (final BankListModel list : originalList) {
                        if (list.getBankName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(list);
                        }
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            adapter.filteredBank.clear();
            adapter.filteredBank.addAll((ArrayList<BankListModel>) results.values);
            adapter.bankdata.clear();
            adapter.bankdata.addAll(filteredList);
            adapter.notifyDataSetChanged();
        }
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
            bankIcon =(ImageView)itemView.findViewById(R.id.bank_logo_imageView1);
            bankName =(TextView)itemView.findViewById(R.id.banks_name_textView);
            bankNamear =(TextView)itemView.findViewById(R.id.banks_name_textViewar);
            currBuy=(TextView)itemView.findViewById(R.id.buyCurr_TextView);
            currSell= (TextView) itemView.findViewById(R.id.currSEll_TextView);
            relativeLayout =(RelativeLayout)itemView.findViewById(R.id.cardview);
        }
    }
    public static boolean isProbablyArabic(String s) {
        for (int i = 0; i < s.length();) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }
}
