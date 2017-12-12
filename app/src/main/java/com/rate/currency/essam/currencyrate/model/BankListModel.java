package com.rate.currency.essam.currencyrate.model;

import java.util.Comparator;

/**
 * Created by EsSaM on 7/10/2017.
 */

public class BankListModel {
    int _id ;
    String bankName ;
    String bankNemear ;
    String bankCode ;
    Double currSell ;
    Double oldCurrSell ;
    Double currBuy ;
    Double oldCurrBuy ;
    String  imageID ;
    public  BankListModel (int _id , String bankName, Double currBuy , Double currSell,
                           String imageID, String bankCode, String bankNemear,
                           Double oldCurrBuy, Double oldCurrSell){
        this._id=_id;
        this.bankName=bankName;
        this.currBuy=currBuy;
        this.currSell=currSell;
        this.bankCode=bankCode;
        this.imageID=imageID ;
        this.bankNemear=bankNemear;
        this.oldCurrBuy=oldCurrBuy;
        this.oldCurrSell=oldCurrSell;
    }

    public  static final Comparator <BankListModel> ByMaxSell = new Comparator<BankListModel>() {
        @Override
        public int compare(BankListModel bankListModel, BankListModel t1) {
            return t1.currSell.compareTo(bankListModel.currSell);
        }
    };
    public  static final Comparator <BankListModel> ByMaxbuy = new Comparator<BankListModel>() {
        @Override
        public int compare(BankListModel bankListModel, BankListModel t1) {
            return t1.currBuy.compareTo(bankListModel.currBuy);
        }
    };
    public  static final Comparator <BankListModel> ByMinSell = new Comparator<BankListModel>() {
        @Override
        public int compare(BankListModel bankListModel, BankListModel t1) {
            return bankListModel.currSell.compareTo(t1.currSell);
        }
    };
    public  static final Comparator <BankListModel> ByMinbuy = new Comparator<BankListModel>() {
        @Override
        public int compare(BankListModel bankListModel, BankListModel t1) {
            return bankListModel.currBuy.compareTo(t1.currBuy);
        }
    };

    public String getBankName(){return bankName;}
    public String getImageID (){return imageID;}
    public String getBankCode(){return bankCode ;}
    public Double getCurrSell() {return currSell;}
    public Double getCurrBuy(){return currBuy;}
    public String getBankNemear() {return  bankNemear;}
    public Double getOldCurrSell() {return oldCurrSell;}
    public Double getOldCurrBuy() {return oldCurrBuy;}

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankNemear(String bankNemear) {
        this.bankNemear = bankNemear;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setCurrSell(Double currSell) {
        this.currSell = currSell;
    }

    public void setOldCurrSell(Double oldCurrSell) {
        this.oldCurrSell = oldCurrSell;
    }

    public void setCurrBuy(Double currBuy) {
        this.currBuy = currBuy;
    }

    public void setOldCurrBuy(Double oldCurrBuy) {
        this.oldCurrBuy = oldCurrBuy;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }
}
