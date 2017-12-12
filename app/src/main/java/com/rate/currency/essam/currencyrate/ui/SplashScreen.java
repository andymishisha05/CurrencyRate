package com.rate.currency.essam.currencyrate.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import com.rate.currency.essam.currencyrate.NavActivity;
import com.rate.currency.essam.currencyrate.R;
import com.rate.currency.essam.currencyrate.model.BankListModel;
import com.rate.currency.essam.currencyrate.model.DetailBankModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.rate.currency.essam.currencyrate.ui.MainFragment.LOG_TAG;
import static com.rate.currency.essam.currencyrate.ui.MainFragment.progressBar;

/**
 * Created by EsSaM on 10/30/2017.
 */

public class SplashScreen extends AppCompatActivity {
    public static List<BankListModel> Banks ;

    Context context = SplashScreen.this;
    private final int SPLASH_DISPLAY_LENGTH = 100;
    String uri ;
     public static int count =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Banks= new ArrayList<>();
        uri = Uri.parse("https://dollarprice.me/api/dev/v1/banks/" + "USD"
                + "?access_token=kaHrat129wGAMVxv6erAEHI0hNpcpKIJJ5QfwDLs")
                .buildUpon()
                .build().toString();
        FetchD2(context ,null,1,uri);

    }

    public void FetchD2(final Context context, String codeOfBanks, final int id,
                        String uri) {

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
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject bankObject = jsonArray.getJSONObject(i);
                            //        Log.d(LOG_TAG,bankObject.toString());
                                String nameOfBanksENG = bankObject.getJSONObject("bank_name")
                                        .getString("en");
                                String nameOfBanksar =bankObject.getJSONObject("bank_name")
                                        .getString("ar");
                                Double buy = bankObject.getJSONObject("last_exchange_rate")
                                        .getDouble("buy");


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


                            }
                            new Handler().postDelayed(new Runnable(){
                                @Override
                                public void run() {
                                    Intent intent = new Intent(context, NavActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, SPLASH_DISPLAY_LENGTH);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());

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
}