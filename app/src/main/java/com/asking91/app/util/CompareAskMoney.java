package com.asking91.app.util;

import com.asking91.app.entity.pay.AskMoney;

import java.util.Comparator;

/**
 * Created by wxiao on 2016/12/30.
 */

public class CompareAskMoney implements Comparator<AskMoney> {



    @Override
    public int compare(AskMoney o1, AskMoney o2) {
        if(o1.getPrice()<o2.getPrice()){
            return -1;
        }
        return 1;
    }
}
