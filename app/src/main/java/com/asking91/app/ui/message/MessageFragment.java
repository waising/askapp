package com.asking91.app.ui.message;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.asking91.app.R;
import com.asking91.app.common.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by wxiao on 2016/10/28.
 */

public class MessageFragment extends BaseFragment {

    @SuppressLint("AddJavascriptInterface")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message);
        ButterKnife.bind(this,getContentView());
    }

}
