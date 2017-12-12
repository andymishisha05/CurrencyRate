package com.rate.currency.essam.currencyrate.model;

/**
 * Created by EsSaM on 7/18/2017.
 */

public class DetailBankModel {
    String name ;
    String code ;
    int img ;
    String buy ;
    String sell;
    String img2 ;
    String nameCurr;
    String codeCurr;

    public DetailBankModel(String name, String code, int img, String buy, String sell, String img2, String nameCurr,String codeCurr) {
        this.name = name;
        this.code = code;
        this.img = img;
        this.buy = buy;
        this.sell = sell;
        this.img2 = img2;
        this.nameCurr = nameCurr;
        this.codeCurr=codeCurr;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getImg() {
        return img;
    }

    public String getBuy() {
        return buy;
    }

    public String getSell() {
        return sell;
    }

    public String getImg2() {
        return img2;
    }

    public String getNameCurr() {
        return nameCurr;
    }

    public String getCodeCurr() {
        return codeCurr;
    }
}
