package com.rate.currency.essam.currencyrate.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.rate.currency.essam.currencyrate.R;
import com.rate.currency.essam.currencyrate.adapter.CurrencySpinnerAdapter;
import com.rate.currency.essam.currencyrate.model.CurrencyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.text.DecimalFormat;
import java.text.NumberFormat;


import static com.rate.currency.essam.currencyrate.ui.MainFragment.progressBar;


/**
 * A placeholder fragment containing a simple view.
 */
public class CurrencyConverterFragment extends Fragment {
    EditText convertFromEditText;
    TextView resultTextView;
    TextView updatedTimeTextView;
    Spinner convertFromSpinner;
    Spinner convertToSpinner;
    String currencyFrom = "";
    String currencyTo = "";
    String updateTime = "";
    double currency = 0;
    ImageView conveter ;
    TextView textView2,textView3;
    private AdView mAdView;

    public CurrencyConverterFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency_converter,container,false);
        getActivity().setTitle("تحويل عملات");
        MobileAds.initialize(getActivity(),"ca-app-pub-9811511775087346~1397200908");
        AdView mAdView = (AdView)view. findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
        //  .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//ca-app-pub-2269341920749093/7798301184
                //.addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4")
                .build()
                ;     // All emulators
        //            // My Galaxy Nexus test phone
        mAdView.loadAd(adRequest);

        Typeface myType = Typeface.createFromAsset(getActivity().getAssets(),"arabic.ttf");
        textView2 =(TextView)view.findViewById(R.id.convert_from_text_view);
        textView3=(TextView)view.findViewById(R.id.convert_to_text_view);
        textView3.setTypeface(myType);
        textView2.setTypeface(myType);
        convertFromEditText = (EditText) view.findViewById(R.id.convert_from_edit_text);
        convertFromEditText.setTypeface(myType);
        resultTextView = (TextView) view.findViewById(R.id.result_text_view);
        resultTextView.setTypeface(myType);
        updatedTimeTextView = (TextView) view.findViewById(R.id.updated_time_text_view);
        updatedTimeTextView.setTypeface(myType);
        convertFromSpinner = (Spinner)view. findViewById(R.id.from_spinner);
        convertToSpinner = (Spinner)view. findViewById(R.id.to_spinner);
        conveter =(ImageView)view.findViewById(R.id.conveter);
        CurrencySpinnerAdapter adapter = new CurrencySpinnerAdapter(getActivity(), CurrencyModel.getCurrencyName4());
        convertFromSpinner.setAdapter(adapter);
        convertToSpinner.setAdapter(adapter);
        dismissKeyPad(convertFromSpinner);
        dismissKeyPad(convertToSpinner);
        setFromListener(convertFromSpinner);
        setToListener(convertToSpinner);


        convertFromEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!convertFromEditText.getText().toString().isEmpty()) {
                    FetchData();
                } else {
                    resultTextView.setText(getString(R.string.to_text_view));
                }
            }
        });
        conveter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fromPos = convertFromSpinner.getSelectedItemPosition();
                int toPos = convertToSpinner.getSelectedItemPosition();
                convertFromSpinner.setSelection(toPos);
                convertToSpinner.setSelection(fromPos);

            }
        });

        return view ;

    }
    public void dismissKeyPad(Spinner spinner) {
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(convertFromEditText.getWindowToken(), 0);
                return false;
            }
        });
    }

    public void setFromListener(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currencyFrom = CurrencyModel.getCurrencyName3()[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void setToListener(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currencyTo = CurrencyModel.getCurrencyName3()[position];
                FetchData();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    void FetchData() {
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://currencydatafeed.com/api/data.php?token=hly39xma67y4mzx0cs5t&currency="+
                currencyFrom+"/"+currencyTo ;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jObject = null;
                        try {
                            jObject = new JSONObject(response);
                            JSONArray jsonArray = jObject.getJSONArray("currency");
                            JSONObject jsonObject =jsonArray.getJSONObject(0);

                            updateTime = jsonObject.getString("date");
                            currency = jsonObject.getDouble("value");
                            if (convertFromEditText.getText().toString().isEmpty()) {
                                return;
                            } else {
                                double fromDecimal =
                                        Double.parseDouble(convertFromEditText.getText().toString());
                                currency = fromDecimal * currency;
                                NumberFormat format = new DecimalFormat("#0.000");
                                String resultString = format.format(currency);
                                String display = fromDecimal + " " + currencyFrom + " = " + resultString
                                        + " " + currencyTo;
                                resultTextView.setText(display);
                                updatedTimeTextView.setText(getString(R.string.update_time) + updateTime);
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                try {
                    Toast.makeText(getActivity(), "Please Check Your Network", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
