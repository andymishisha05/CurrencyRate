package com.rate.currency.essam.currencyrate.model;

/**
 * Created by EsSaM on 7/15/2017.
 */

public class GoldModel {
    String nameAR;
    String nameEN ;
    String price ;
    public GoldModel (String nameAR ,String nameEN,String price){
        this.nameAR=nameAR;
        this.nameEN=nameEN;
        this.price=price;
    }
    public String getNameAR(){
        return  nameAR;
    }
    public String getNameEN (){
        return nameEN ;
    }

    public String getPrice() {
        return price;
    }
}
