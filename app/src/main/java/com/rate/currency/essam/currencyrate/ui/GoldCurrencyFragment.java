package com.rate.currency.essam.currencyrate.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.rate.currency.essam.currencyrate.R;
import com.rate.currency.essam.currencyrate.adapter.CurrencySpinnerAdapter;
import com.rate.currency.essam.currencyrate.adapter.GoldListAdapter;
import com.rate.currency.essam.currencyrate.model.GoldModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GoldCurrencyFragment extends Fragment {
    RecyclerView recyclerView ;
    List<GoldModel> goldModels ;
    GoldListAdapter goldListAdapter ;

    String []gold ={
      "دهب",
            "فضة"
    };

    String []goldString={
            "كيلو الدهب",
            "جنية الدهب",
            "اونصة الدهب",
            "عيار 9",
            "عيار 12",
            "عيار 14","عيار 18","عيار 21","عيار 22","عيار 24"
    };

    String[]silverString = {
            "جرام فضة خالص",
            "جرام فضة مصري",
            "جرام فضة هندي",
            "جرام فضة تركي",
            "جرام فضة ايطالي"
    };

    Spinner goldSpinner;

    public GoldCurrencyFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_gold_currency,container,false);
        getActivity().setTitle("اسعار الذهب");
         recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        goldModels =new ArrayList<GoldModel>();
        MobileAds.initialize(getActivity(), "ca-app-pub-9811511775087346~1397200908");

        AdView mAdView = (AdView)view. findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4")
                .build()
                ;
        goldSpinner = (Spinner) view.findViewById(R.id.from_spinner);
        CurrencySpinnerAdapter adapter = new CurrencySpinnerAdapter(getActivity(),gold);
        goldSpinner.setAdapter(adapter);

        mAdView.loadAd(adRequest);
        goldListAdapter =new GoldListAdapter(getActivity(),goldModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(goldListAdapter);

        goldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                if (spinner.getId() == R.id.from_spinner) {
                    if (position==0){
                        FetchData();
                    }else if(position==1){
                        Fetchsilver();
                    }
                    //String address = getaddressList.get(position);
                   // addressTV.setText(getResources().getString(R.string.adress1)+address);
                    // String item = parent.getItemAtPosition(position).toString();
                    // int parentId =parent.getId();
                    //   Log.i(LOG_TAG, String.valueOf(parent.getSelectedItemPosition()));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

            FetchData();

        return view ;
    }
    void FetchData(){
    //    Log.d("ID", String.valueOf(id));
        goldModels.clear();
        goldListAdapter.notifyDataSetChanged();

        String url ="https://www.parsehub.com/api/v2/projects/t5KfvsT3fx4H/last_ready_run/data?api_key=tRW8TUWECUEt";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPNSE",response);
                        if (!response.isEmpty()){
                            try {
                                JSONObject jsonObject =new JSONObject(response);
                                JSONArray jsonArray =jsonObject.getJSONArray("gold");
                                for (int i = 0;i<jsonArray.length();i++){
                                    JSONObject goldObject = jsonArray.getJSONObject(i);
                                    String name = goldString[i];
                                   // name = name.substring(5);
                                    String priceegp = goldObject.getString("gold_egp")+" ج.م";
                                    String pricedollar = goldObject.getString("gold_dollar");
                                    GoldModel goldModel = new GoldModel(name,priceegp,
                                            pricedollar
                                    );
                                    goldModels.add(goldModel);
                                    goldListAdapter.notifyDataSetChanged();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.d("RESPNSE", String.valueOf(error));
                    Toast.makeText(getActivity(), "Check your network", Toast.LENGTH_LONG).show();
                }catch (Exception e){

                }
            }
        }){
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity()); ;

        requestQueue.add(stringRequest);

    }
    void Fetchsilver(){
        //    Log.d("ID", String.valueOf(id));
        goldModels.clear();
        goldListAdapter.notifyDataSetChanged();

        String url ="https://www.parsehub.com/api/v2/projects/t5J7smw__RM_/last_ready_run/data?api_key=tRW8TUWECUEt";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPNSE",response);
                        if (!response.isEmpty()){
                            try {
                                JSONObject jsonObject =new JSONObject(response);
                                JSONArray jsonArray =jsonObject.getJSONArray("silver");
                                for (int i = 0;i<jsonArray.length();i++){
                                    JSONObject goldObject = jsonArray.getJSONObject(i);
                                    String name = silverString[i];
                                    // name = name.substring(5);
                                    String priceegp = goldObject.getString("price");
                                    priceegp = priceegp.substring(0,2)+" ج.م";
                                   // String pricedollar = goldObject.getString("gold_dollar");
                                    GoldModel goldModel = new GoldModel(name,priceegp,
                                            null
                                    );
                                    goldModels.add(goldModel);
                                    goldListAdapter.notifyDataSetChanged();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.d("RESPNSE", String.valueOf(error));
                    Toast.makeText(getActivity(), "Check your network", Toast.LENGTH_LONG).show();
                }catch (Exception e){

                }
            }
        }){
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity()); ;

        requestQueue.add(stringRequest);

    }

}
