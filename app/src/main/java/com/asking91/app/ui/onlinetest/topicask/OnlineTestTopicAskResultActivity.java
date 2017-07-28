package com.asking91.app.ui.onlinetest.topicask;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SpaceItemDecoration;
import com.asking91.app.entity.basepacket.SubjectEntity;
import com.asking91.app.entity.onlinetest.ResultEntity;
import com.asking91.app.entity.onlinetest.RightAnswerEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.OnlineTestConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.onlinetest.OnlineTestAskTopicCardResultAdapter;
import com.asking91.app.ui.main.MainActivity;
import com.asking91.app.ui.onlinetest.topic.OnlineTestTopicContract;
import com.asking91.app.ui.onlinetest.topic.OnlineTestTopicModel;
import com.asking91.app.ui.onlinetest.topic.OnlineTestTopicPresenter;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DateUtil;
import com.asking91.app.util.JLog;
import com.asking91.app.util.SerializableMap;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by wxwang on 2016/11/18.
 */
public class OnlineTestTopicAskResultActivity extends BaseFrameActivity<OnlineTestTopicPresenter, OnlineTestTopicModel>
        implements OnlineTestTopicContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.test_result_rv)
    RecyclerView resultRv;

    @BindView(R.id.ask_result_score)
    TextView mAskResultScoreTv;
    @BindView(R.id.user_test_tv)
    TextView mUserTestTv;
    @BindView(R.id.km_tv)
            TextView mKmTv;
    @BindView(R.id.version_tv)
            TextView mVersionTv;
    @BindView(R.id.classLevel_tv)
            TextView mClassLevelTv;
    @BindView(R.id.time_tv)
            TextView mTimeTv;


    OnlineTestAskTopicCardResultAdapter onlineTestAskTopicCardResultAdapter;
    Map<String,ResultEntity> resultMap = null;
    String commitPaper = "",subjectCatalog="";
    int time=0;
    public static final int GETANSWER = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinetest_topic_ask_result);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, " "+getString(R.string.onlinetest_ask_result));
        GridLayoutManager mgr=new GridLayoutManager(this,5);
        resultRv.addItemDecoration(new SpaceItemDecoration(12));
        resultRv.setLayoutManager(mgr);

        //不滑动
        setSwipeBackEnable(false);

        mToolBar.setNavigationIcon(R.mipmap.nvi_home);
    }

    @Override
    public void initData(){
        super.initData();
        Bundle bundle = getIntent().getExtras();
        SerializableMap serializableMap = (SerializableMap) bundle.get("answerMap");
        resultMap = serializableMap.getMap();

        commitPaper = bundle.getString("commitPaper");
        subjectCatalog = bundle.getString("subjectCatalog");
        time = bundle.getInt("time");
        onlineTestAskTopicCardResultAdapter = new OnlineTestAskTopicCardResultAdapter(this,resultMap, OnlineTestConstant.CardType.ASK_RESULT);
        resultRv.setAdapter(onlineTestAskTopicCardResultAdapter);

        String name = getUserConstant().getUserName();
        String date = DateUtil.date2Str(new Date(),"yyyy年MM月dd日");
        String km = getMipMap().get(subjectCatalog);
        mUserTestTv.setText(name+" " +km+" "+date);
        mKmTv.setText(km);
        mVersionTv.setText(bundle.getString("versionName"));
        mClassLevelTv.setText(bundle.getString("className"));
        mTimeTv.setText(DateUtil.secToTime(time));
    }

    @Override
    public void initLoad(){
        super.initLoad();
        mPresenter.getAnswer(subjectCatalog,commitPaper);
    }

    @Override
    public void initListener(){
        super.initListener();
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(MainActivity.class);
            }
        });
    }

    public void onBackResultActivity(String num,Map<String,ResultEntity> mDatas){
        Bundle bundle = new Bundle();
        bundle.putString("num",num);
        bundle.putInt("type",OnlineTestConstant.CardType.ASK_RESULT);
        SerializableMap serializableMap = new SerializableMap();
        serializableMap.setMap(mDatas);
        bundle.putSerializable("answerMap",serializableMap);

        openBackResultActivity(OnlineTestTopicAskCardActivity.class,bundle);
    }

    @Override
    public void onSuccess(int type, ResponseBody body) {
        Map<String ,Object> map = CommonUtil.parseDataToMap(body);
        if(String.valueOf(map.get("msg")).indexOf("你没有权限操作")!=-1){
            showShortToast("提交试卷失败");
            return;
        }
//        String store = String.valueOf(map.get("store"));
//        //分值
//        mAskResultScoreTv.setText(store.substring(0,store.indexOf(".")));
        //题目状态
        List<RightAnswerEntity> rightAnswerEntityList = CommonUtil.parseDataToList(map.get("list"),new TypeToken<List<RightAnswerEntity>>(){});
        /**对的题目数*/
        int rightNum = 0;
        if(rightAnswerEntityList.size()>0){
            for (RightAnswerEntity rightAnswerEntity : rightAnswerEntityList){
//                如果是选择题
                if ("1".equals(rightAnswerEntity.getTypeId())){
                    for (String key : resultMap.keySet()){
                        for (SubjectEntity subjectEntity : rightAnswerEntity.getSubjectEntityList()){
                            if(resultMap.get(key).getId().equals(subjectEntity.getId())){
                                resultMap.get(key).setRightResult(subjectEntity.getRightAnswer());
                                if(resultMap.get(key).getAnswerResult().equals(resultMap.get(key).getRightResult())){
                                    rightNum++;
                                }
                                break;
                            }
                        }
                    }

                }
            }
            onlineTestAskTopicCardResultAdapter.notifyDataSetChanged();
        }
        int score = 100/resultMap.size()*rightNum;
        JLog.i(mthis.getClass().getSimpleName(), "分数："+score);
        mAskResultScoreTv.setText(String.valueOf(score));
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    private Map<String,String> getMipMap(){
        ArrayMap<String, String> map = new ArrayMap<String, String>();
        map.put(Constants.SubjectCatalog.CZSX,getString(R.string.ask_czsx));
        map.put(Constants.SubjectCatalog.CZWL,getString(R.string.ask_czwl));
        map.put(Constants.SubjectCatalog.GZSX,getString(R.string.ask_gzsx));
        map.put(Constants.SubjectCatalog.GZWL,getString(R.string.ask_gzwl));

        return map;
    }

    @OnClick(R.id.answer_detail_btn)
    public void onClick(){
        //答案解析 默认第一题
        onBackResultActivity("1",resultMap);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
