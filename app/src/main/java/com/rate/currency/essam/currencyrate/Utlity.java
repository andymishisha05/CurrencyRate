package com.rate.currency.essam.currencyrate;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rate.currency.essam.currencyrate.model.BankListModel;
import com.rate.currency.essam.currencyrate.model.DetailBankModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import static com.rate.currency.essam.currencyrate.ui.DetailBankActivity.detailBankAdapter;
import static com.rate.currency.essam.currencyrate.ui.DetailBankActivity.imageView;
import static com.rate.currency.essam.currencyrate.ui.DetailBankActivity.modelList;
import static com.rate.currency.essam.currencyrate.ui.DetailBankActivity.textView;
import static com.rate.currency.essam.currencyrate.ui.DetailBankActivity.textView2;
import static com.rate.currency.essam.currencyrate.ui.MainFragment.LOG_TAG;
import static com.rate.currency.essam.currencyrate.ui.MainFragment.banksListAdapter;
import static com.rate.currency.essam.currencyrate.ui.MainFragment.progressBar;
import static com.rate.currency.essam.currencyrate.ui.SplashScreen.Banks;


/**
 * Created by EsSaM on 7/13/2017.
 */
public class Utlity {

    public static void FetchD(final Context context, String codeOfBanks, final int id,
                              String uri) {

        if (id==1){
            progressBar.setVisibility(View.VISIBLE);
            Banks.clear();
         //   banksListAdapter.notifyDataSetChanged();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, uri,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(LOG_TAG, String.valueOf(response));
                if (response != null) {
                    try {
                       if (id ==1 ) {

                           JSONArray jsonArray = new JSONArray(response);
                           for (int i = 0; i < response.length()-2; i++) {
                               JSONObject bankObject = jsonArray.getJSONObject(i);
                               //    Log.d(LOG_TAG,bankObject.toString());
                               String nameOfBanksENG = bankObject.getJSONObject("bank_name")
                                       .getString("en");
                               String nameOfBanksar =bankObject.getJSONObject("bank_name")
                                       .getString("ar");
                               Double buy = bankObject.getJSONObject("last_exchange_rate")
                                       .getDouble("buy") ;


                               Double sell = bankObject.getJSONObject("last_exchange_rate")
                                       .getDouble("sell") ;
                               NumberFormat format = new DecimalFormat("#0.000");
                                buy = Double.valueOf(format.format(buy));

                               sell = Double.valueOf(format.format(sell));
                               JSONArray oldBuyArray = bankObject.getJSONArray("buy_rates_today");
                               Double oldBuy = oldBuyArray.getJSONObject(0)
                                       .getDouble("rate");

                               JSONArray oldSellArray= bankObject.getJSONArray("sell_rates_today");

                               Double oldSell = oldSellArray.getJSONObject(0)
                                       .getDouble("rate");
                              // Log.i("fffffffffffffffff", String.valueOf(oldSell));
                               //Log.i("DDDDDDDDDDDDDDDdD", String.valueOf(oldBuy));
                               String codeBank = bankObject.getString("bank_acronym");

                               String imageURL = bankObject.getString("bank_image");

                               imageURL ="https://dollarprice.me/img/banks/"+ imageURL;

                            //   imageURL="https://dollarprice.me/img/banks/"+imageURL;
                               //Log.d(LOG_TAG,imageURL);

                               //Log.d(LOG_TAG,imageURL);
                               //imageURL="https://dollarprice.me/"+imageURL;
                               BankListModel bankListModel = new BankListModel(
                                       0, nameOfBanksENG, buy, sell
                                       ,imageURL, codeBank,nameOfBanksar,oldBuy,oldSell
                               );

                               Banks.add(bankListModel);
                               banksListAdapter.notifyDataSetChanged();
                           }
                       }
                       else if (id ==2){
                           JSONObject jsonObject = new JSONObject(response);

                           String nameOfBanksar =jsonObject.getJSONObject("bank_name")
                                   .getString("ar");
                           String code =jsonObject.getString("bank_acronym");
                           final String urlOfBank =jsonObject.getString("bank_url");
                           String bankimage =jsonObject.getString("bank_image");
                           if (bankimage.endsWith(".png")) {
                               bankimage = bankimage.substring(0, bankimage.length() - 4);
                           }
                           JSONArray currArray =jsonObject.getJSONArray("currencies");
                           //Log.i("BBBB", String.valueOf(currArray));
                           for (int i = 0 ;i<currArray.length();i++){
                               JSONObject currObject1 = currArray.getJSONObject(i);
                               //Log.i("BBBB", String.valueOf(currObject1));
                               String currency_name_ar =currObject1.getString("currency_name_ar");
                               String currency_name_en=currObject1.getString("currency_type");
                               String currency_name_e =currency_name_en.toLowerCase();
                               String buy = currObject1.getJSONObject("last_exchange_rate").getString("buy");
                               String sell =currObject1.getJSONObject("last_exchange_rate").getString("sell");

                               DetailBankModel model = new DetailBankModel(
                                       null,null,GetStr(context,currency_name_e),buy,sell,null,currency_name_ar,currency_name_en
                               );
                               modelList.add(model);
                               detailBankAdapter.notifyDataSetChanged();
                           }textView.setText(nameOfBanksar);
                           textView2.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   String url = urlOfBank;
                                   Intent i = new Intent(Intent.ACTION_VIEW);
                                   i.setData(Uri.parse(url));
                                   context.startActivity(i);
                               }
                           });



                           Picasso.with(context).load(GetStr(context,bankimage)).into(imageView);

                       }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
                if (id == 1 ) {
                    progressBar.setVisibility(View.GONE);
                }
                try {
                    Toast.makeText(context,"Check your network",Toast.LENGTH_LONG).show();

                }
                catch (Exception e){
                    e.printStackTrace();

                }
            }

        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //     params.put("X-Mashape-Key", "Ag04eQFluWEgt3fhXWLVUriJCsECpeVHfC2LyWWJ");
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0,
             DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      //  return result[0];
    }

    public static int GetStr(Context c, String ImageName) {
        return c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName());
    }
    public static Drawable GetImage(Context c, String ImageName) {
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }


}
