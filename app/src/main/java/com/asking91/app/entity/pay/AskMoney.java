package com.asking91.app.entity.pay;

/**
 * Created by wxwang on 2016/11/29.
 */
public class AskMoney  {


    private int value;
    private String _id;
    private String commodityId;
    private int price;
    private String askCurrencyId;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAskCurrencyId() {
        return askCurrencyId;
    }

    public void setAskCurrencyId(String askCurrencyId) {
        this.askCurrencyId = askCurrencyId;
    }
}
