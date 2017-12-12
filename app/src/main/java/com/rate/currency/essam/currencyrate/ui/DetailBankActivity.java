package com.rate.currency.essam.currencyrate.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.rate.currency.essam.currencyrate.R;
import com.rate.currency.essam.currencyrate.Utlity;
import com.rate.currency.essam.currencyrate.adapter.DetailBankAdapter;
import com.rate.currency.essam.currencyrate.model.DetailBankModel;

import java.util.ArrayList;
import java.util.List;

public class DetailBankActivity extends AppCompatActivity {
    String url ;
    String code ;
    Context context = DetailBankActivity.this;
    public static List<DetailBankModel>modelList ;
    public static DetailBankAdapter detailBankAdapter ;
    RecyclerView recyclerView ;
    public static TextView textView,textView2 ;
    public static ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MobileAds.initialize(context, "ca-app-pub-9811511775087346~1397200908");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
              //  .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
               // .addTestDevice("AC98C820A50B4AD8A2106EDE96FB87D4")
                .build()
                ;
        mAdView.loadAd(adRequest);
        Typeface myType = Typeface.createFromAsset(getAssets(),"arabic.ttf");

        modelList = new ArrayList<>();
        Bundle b = getIntent().getExtras();
        if (b != null){
            code = b.getString("0");
        }

        url = Uri.parse("https://dollarprice.me/api/dev/v1/banks/" + code
                + "?access_token=kaHrat129wGAMVxv6erAEHI0hNpcpKIJJ5QfwDLs")
                .buildUpon()
                .build().toString();
        textView =(TextView)findViewById(R.id.text);
        textView.setTypeface(myType);
        textView2=(TextView)findViewById(R.id.text2);
        textView2.setTypeface(myType);
        imageView=(ImageView)findViewById(R.id.imageView3);
        recyclerView =(RecyclerView)findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailBankAdapter =new DetailBankAdapter(modelList,context);
        recyclerView.setAdapter(detailBankAdapter);
        Utlity.FetchD(context,null,2,url);

    }
}
