package com.asking91.app.ui.onlinetest;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.global.Constants;
import com.asking91.app.ui.onlinetest.topic.OnlineTestTopicActivity;

import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wxwang on 2016/11/11.
 */
public class OnlineTestActivity extends SwipeBackActivity{

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindString(R.string.onlinetest_title)
    String title;

    ArrayMap<Integer,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinetest);
        ButterKnife.bind(this);
    }

    @Override
    public void initView(){
        super.initView();
        setToolbar(mToolbar, title);
    }

    @Override
    public void initData(){
        super.initData();
        map = new ArrayMap<>();
        map.put(R.id.online_test_czsx, Constants.SubjectCatalog.CZSX);
        map.put(R.id.online_test_czwl, Constants.SubjectCatalog.CZWL);
        map.put(R.id.online_test_gzsx, Constants.SubjectCatalog.GZSX);
        map.put(R.id.online_test_gzwl, Constants.SubjectCatalog.GZWL);
    }

    @OnClick({R.id.online_test_czsx,R.id.online_test_czwl,R.id.online_test_gzsx,R.id.online_test_gzwl})
    public void imgOnClick(View view){
        Bundle bundle = new Bundle();
        bundle.putString("subjectCatalog",map.get(view.getId()));
        openActivity(OnlineTestTopicActivity.class,bundle);
    }
}
