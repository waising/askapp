package com.asking91.app.ui.onlinetest.topicask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.asking91.app.R;
import com.asking91.app.common.SpaceItemDecoration;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.entity.onlinetest.ResultEntity;
import com.asking91.app.global.OnlineTestConstant;
import com.asking91.app.ui.adapter.onlinetest.OnlineTestAskTopicCardResultAdapter;
import com.asking91.app.util.DateUtil;
import com.asking91.app.util.RxCountDown;
import com.asking91.app.util.SerializableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

/**
 * Created by wxwang on 2016/11/18.
 */
public class OnlineTestTopicAskCardActivity extends SwipeBackActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.test_card_rv)
    RecyclerView cardRv;
    OnlineTestAskTopicCardResultAdapter onlineTestAskTopicCardResultAdapter;
    List<ResultEntity> mDatas;
    Integer mTime = 0;
    boolean isSelected = true;
    SerializableMap serializableMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinetest_topic_ask_card);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.onlinetest_ask_card));
        GridLayoutManager mgr=new GridLayoutManager(this,5);
        cardRv.addItemDecoration(new SpaceItemDecoration(12));
//        cardRv.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 8, ContextCompat.getColor(this, R.color.no_color)));
        cardRv.setLayoutManager(mgr);
    }

    @Override
    public void initData(){
        super.initData();
        mDatas = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        mTime = bundle.getInt("time");
        time(mTime);
        isSelected = bundle.getBoolean("isSelected");//是否都已经选
        serializableMap = (SerializableMap) bundle.get("answerMap");
        Map<String,ResultEntity> map = serializableMap.getMap();
        onlineTestAskTopicCardResultAdapter = new OnlineTestAskTopicCardResultAdapter(this,map,OnlineTestConstant.CardType.ASK_CARD);
        cardRv.setAdapter(onlineTestAskTopicCardResultAdapter);
    }

    private void time(int time){
        RxCountDown.countUp(time).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                mToolBar.setTitle(DateUtil.secToTime(integer));
                mTime = integer;
            }
        });
    }

    @Override
    public void initLoad(){
        super.initLoad();
    }

    public void onBackResultActivity(String num,Map<String,ResultEntity> mDatas){
        Bundle bundle = new Bundle();
        bundle.putString("num",num);
        bundle.putInt("time",mTime);
        bundle.putInt("type",OnlineTestConstant.CardType.ASK_CARD);

        SerializableMap serializableMap = new SerializableMap();
        serializableMap.setMap(mDatas);
        bundle.putSerializable("answerMap",serializableMap);
        openBackResultActivity(OnlineTestTopicAskActivity.class,bundle);
    }

    @OnClick(R.id.commit_ask_topic)
    public void commitOnClick(){
        if(isSelected){
            Bundle bundle = getIntent().getExtras();
            bundle.putSerializable("answerMap", serializableMap);
            bundle.putInt("time",mTime);
            //提交试卷
            openResultActivity(OnlineTestTopicAskResultActivity.class,bundle);
        }else{
            showShortToast("请完成没有选择的题目");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 根据上面发送过去的请求吗来区别
        if (resultCode == RESULT_OK) {
            setResult(resultCode,data);
            finish();
        }
    }

}

