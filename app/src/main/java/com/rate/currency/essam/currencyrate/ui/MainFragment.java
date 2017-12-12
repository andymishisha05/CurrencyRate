package com.rate.currency.essam.currencyrate.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.rate.currency.essam.currencyrate.R;
import com.rate.currency.essam.currencyrate.Utlity;
import com.rate.currency.essam.currencyrate.adapter.BanksListAdapter;
import com.rate.currency.essam.currencyrate.adapter.CurrencySpinnerAdapter;
import com.rate.currency.essam.currencyrate.model.BankListModel;
import com.rate.currency.essam.currencyrate.model.CurrencyModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.rate.currency.essam.currencyrate.ui.SplashScreen.Banks;
import static com.rate.currency.essam.currencyrate.ui.SplashScreen.count;


/**
 * Created by EsSaM on 7/13/2017.
 */
public class MainFragment extends Fragment {
    public static final String LOG_TAG =MainFragment.class.getSimpleName();
    RecyclerView bankRecyclerView ;
    public static BanksListAdapter banksListAdapter ;
    ImageView currImg;
    Spinner convertFromSpinner;
    EditText convertFromEditText;

    List<BankListModel> upBanks = new ArrayList<>();
    List<BankListModel> upBanks2 = new ArrayList<>();
    static String currencyFrom = "";
    public static ProgressBar progressBar ;
    String uri;
    SearchView searchView;
    public MainFragment(){
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        getActivity().setTitle("اسعار العملات");
        MobileAds.initialize(getActivity(), "ca-app-pub-8135243106946359~6939132195");

        AdView mAdView = (AdView)view. findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
               // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)

                .build()
                ;
        mAdView.loadAd(adRequest);
        setHasOptionsMenu(true);
        upBanks.addAll(Banks);

        upBanks2.addAll(Banks);
        Typeface myType = Typeface.createFromAsset(getActivity().getAssets(),"arabic.ttf");
        currImg = (ImageView) view.findViewById(R.id.convert_from_edit_text);
        //  resultTextView = (TextView) findViewById(R.id.result_text_view);
        // updatedTimeTextView = (TextView) findViewById(R.id.updated_time_text_view);
        convertFromSpinner = (Spinner) view.findViewById(R.id.from_spinner);
        // convertToSpinner = (Spinner) findViewById(R.id.to_spinner);
        bankRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        convertFromEditText = (EditText) view.findViewById(R.id.convert_from_edit_text1);
        convertFromEditText.setTypeface(myType);

//        gridLayoutManager = new GridLayoutManager(getActivity(),1);
        bankRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        banksListAdapter =new BanksListAdapter(getActivity(),Banks);
        bankRecyclerView.setAdapter(banksListAdapter);
       // banksListAdapter.notifyDataSetChanged();
        uri = Uri.parse("https://dollarprice.me/api/dev/v1/banks/" + "USD"
                + "?access_token=kaHrat129wGAMVxv6erAEHI0hNpcpKIJJ5QfwDLs")
                .buildUpon()
                .build().toString();
        CurrencySpinnerAdapter adapter = new CurrencySpinnerAdapter(getActivity(), CurrencyModel.getCurrencyName());
        convertFromSpinner.setAdapter(adapter);
        convertFromEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
              //  upBanks=Banks;

                if (!convertFromEditText.getText().toString().isEmpty()) {
                    Double in = Double.valueOf(convertFromEditText.getText().toString());
                    for (int i=0;i<Banks.size();i++){
                       upBanks.get(i).setCurrBuy(Banks.get(i).getCurrBuy()*in);
                       upBanks.get(i).setCurrSell(Banks.get(i).getCurrSell()*in);

                    }
                    Banks.clear();
                    Banks.addAll(upBanks);
                    upBanks.clear();
                    upBanks.addAll(upBanks2);

                    banksListAdapter.notifyDataSetChanged();

                } else {
                    Banks.clear();

                    Banks.addAll(upBanks2);

                    banksListAdapter.notifyDataSetChanged();
                }
            }
        });
  //      dismissKeyPad(convertFromSpinner);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        bankRecyclerView.setItemAnimator(itemAnimator);


        //   dismissKeyPad(convertToSpinner);
        try {

                setFromListener(convertFromSpinner);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  setToListener(convertToSpinner);

        return view;

    }
    public void dismissKeyPad(Spinner spinner) {
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
               // imm.hideSoftInputFromWindow(convertFromEditText.getWindowToken(), 0);
                return false;
            }
        });
    }
    public void setFromListener(Spinner spinner) throws JSONException {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(count==0){
                    bankRecyclerView.setAdapter(banksListAdapter);
                    count++;

                }else  {
                    currencyFrom = CurrencyModel.getCurrencyName2()[position];
                    currImg.setImageResource(CurrencyModel.getCurrencyImg()[position]);
                    //          valuee = Double.parseDouble(String.valueOf(convertFromEditText.getText()));
                    uri = Uri.parse("https://dollarprice.me/api/dev/v1/banks/" + currencyFrom
                            + "?access_token=kaHrat129wGAMVxv6erAEHI0hNpcpKIJJ5QfwDLs")
                            .buildUpon()
                            .build().toString();

                    Utlity.FetchD(getActivity(), null, 1, uri);
//                    Log.i(LOG_TAG,result.toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_currency, menu);
        super.onCreateOptionsMenu(menu, inflater);
       // getActivity().getMenuInflater().inflate(R.menu.menu_currency, menu);
        //final MenuItem searchItem = menu.findItem(R.id.search_icon);
        //searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            Utlity.FetchD(getActivity(),null,1,uri);
            return  true ;

        }else if (id==R.id.search_icon){
            Search();
            return  true ;
        }else if (id==R.id.sortbyMaxSell){
            Collections.sort(Banks,BankListModel.ByMaxSell);
            banksListAdapter.notifyDataSetChanged();
            return true ;
        }else if (id==R.id.sotrbyMaxBuy){
            Collections.sort(Banks,BankListModel.ByMaxbuy);
            banksListAdapter.notifyDataSetChanged();
            return true ;
        }
        else if (id==R.id.sortbuMinSell){
            Collections.sort(Banks,BankListModel.ByMinSell);
            banksListAdapter.notifyDataSetChanged();
            return true ;
        }
        else if (id==R.id.sotrbyMinBuy){
            Collections.sort(Banks,BankListModel.ByMinbuy);
            banksListAdapter.notifyDataSetChanged();
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }



    private void Search() {
        final AutoCompleteTextView searchEditText = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHint("Searching");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                // Here is where we are going to implement the filter logic
                banksListAdapter.getFilter().filter(searchEditText.getText());
                bankRecyclerView.setAdapter(banksListAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String filterString) {
                banksListAdapter.getFilter().filter(searchEditText.getText());
                return true;
            }
        });
    }
    public void updateAndSort(List<BankListModel> list) {
        //Go through all your item and computer their time left.

        Collections.sort(Banks,BankListModel.ByMaxSell);
    }
}
