package com.rate.currency.essam.currencyrate.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rate.currency.essam.currencyrate.R;


public class CurrencySpinnerAdapter extends ArrayAdapter {
    Typeface myType;


    public CurrencySpinnerAdapter(Context context, String[] resourceArray) {

        super(context, R.layout.spinner_layout, resourceArray);
        myType = Typeface.createFromAsset(context.getAssets(),"arabic.ttf");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.spinner_layout, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.currency_name_text_view);
        textView.setTypeface(myType);
        textView.setText((String) getItem(position));
        //TextView imageView = (TextView) view.findViewById(R.id.currency_image_view);
       // imageView.setText(CurrencyModel.getImageId()[position]);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.spinner_layout, parent, false);
        TextView textView = (TextView) view.findViewById(R.id.currency_name_text_view);
        textView.setTypeface(myType);
        textView.setText((String) getItem(position));
       // TextView imageView = (TextView) view.findViewById(R.id.currency_image_view);
        //imageView.setText(CurrencyModel.getImageId()[position]);
        return view;
    }
}
