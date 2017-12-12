package com.rate.currency.essam.currencyrate.model;


import com.rate.currency.essam.currencyrate.R;

public class CurrencyModel {

    private static final String[] CURRENCY_NAME =
            {
                    "USD-دولارامريكي",
                    "EUR -يورو",
                    "GBP-جنية البريطاني",
                    "SAR-ريال سعودي",
                    "AED-درهم الامراتي",
                    "KWD-الدينار الكويتي",
                    "CHF-فرنك سوسري",
                    "JPY100-ين اليابني",
                    "SEK-كورنا سويسري",
                    "DKK-الكرون الدنماركي",
                    "NOK-كرونة نروجية",
                    "CAD-دولار كندي",
                    "BHD-دينار البحريني",
                    "QAR=ريال قطري",
                    "JOD-الأردن دينار"


            };
    private static final String[] CURRENCY_NAME2 =
            {       "USD",
                    "EUR",
                    "GBP",
                    "SAR",
                    "AED",
                    "KWD",
                    "CHF",
                    "JPY100",
                    "SEK",
                    "DKK",
                    "NOK",
                    "CAD",
                    "BHD",
                    "QAR",
                    "JOD"
            };

    private static final String[] CURRENCY_NAME3 =

            {       "EGP",
                    "USD",
                    "EUR",
                    "GBP",
                    "SAR",
                    "AED",
                    "KWD",
                    "CHF",
                    "JPY100",
                    "SEK",
                    "DKK",
                    "NOK",
                    "CAD",
                    "BHD",
                    "QAR",
                    "JOD"
            };
    private static final String[] CURRENCY_NAME4 =
            {
                    "EGP-جنية مصري",
                    "USD-دولارامريكي",
                    "EUR -يورو",
                    "GBP-جنية البريطاني",
                    "SAR-ريال سعودي",
                    "AED-درهم الامراتي",
                    "KWD-الدينار الكويتي",
                    "CHF-فرنك سوسري",
                    "JPY100-ين اليابني",
                    "SEK-كورنا سويسري",
                    "DKK-الكرون الدنماركي",
                    "NOK-كرونة نروجية",
                    "CAD-دولار كندي",
                    "BHD-دينار البحريني",
                    "QAR=ريال قطري",
                    "JOD-الأردن دينار"


            };





    private static final  int [] CURRENCY_IMG ={
            R.drawable.usd,
            R.drawable.eur,
            R.drawable.gbp,
            R.drawable.sar,
            R.drawable.aed,
            R.drawable.kwd,
            R.drawable.chf,
            R.drawable.jpy100,
            R.drawable.chf,
            R.drawable.dkk,
            R.drawable.nok,
            R.drawable.cad,
            R.drawable.bhd,
            R.drawable.qar,
            R.drawable.jod

    };



    public static String[] getCurrencyName() {
        return CURRENCY_NAME;
    }
    public  static String []getCurrencyName2(){return CURRENCY_NAME2;}
    public  static  String []getCurrencyName3(){return  CURRENCY_NAME3;}
    public  static  String []getCurrencyName4(){return  CURRENCY_NAME4;}

    public static int[] getCurrencyImg() {
        return CURRENCY_IMG;
    }
}
