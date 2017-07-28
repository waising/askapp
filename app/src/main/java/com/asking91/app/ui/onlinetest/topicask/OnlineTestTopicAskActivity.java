package com.asking91.app.ui.onlinetest.topicask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.basepacket.AnswerOption;
import com.asking91.app.entity.onlinetest.CommitPaperEntity;
import com.asking91.app.entity.onlinetest.PaperEntity;
import com.asking91.app.entity.onlinetest.ResultEntity;
import com.asking91.app.entity.onlinetest.TopicEntity;
import com.asking91.app.entity.onlinetest.TopicType;
import com.asking91.app.global.OnlineTestConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.onlinetest.OnlineTestAskFragmentAdapter;
import com.asking91.app.ui.onlinetest.topic.OnlineTestTopicContract;
import com.asking91.app.ui.onlinetest.topic.OnlineTestTopicModel;
import com.asking91.app.ui.onlinetest.topic.OnlineTestTopicPresenter;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DateUtil;
import com.asking91.app.util.RxCountDown;
import com.asking91.app.util.SerializableMap;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import okhttp3.ResponseBody;
import rx.Observer;

/**
 * Created by wxwang on 2016/11/14.
 */
public class OnlineTestTopicAskActivity extends BaseFrameActivity<OnlineTestTopicPresenter, OnlineTestTopicModel>
    implements OnlineTestTopicContract.View , Toolbar.OnMenuItemClickListener ,OnlineTestTopicAskFragment.SwitchFragmentCall {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.ask_viewPager)
    ViewPager mViewPager;

    @BindView(R.id.total_index_tv)
    TextView mTotalIndexTv;

    @BindView(R.id.index_tv)
    TextView mIndexTv;

    public static final int GETPAPER = 101;

    String subjectCatalog,paperId,state,typeId;

    List<OnlineTestTopicAskFragment> mFragments;
    OnlineTestAskFragmentAdapter mOnlineTestAskFragmentAdapter;

    List<TopicEntity> mTopicList;

    Map<String,ResultEntity> answerMap;
    private View viewEmpty = null;

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        this.mTime = time;
    }

    private Integer mTime = 0;
    boolean isSelected=true;
    int mCurrIndex = 0;
    int type = 0;
    String versionName,className;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinetest_topic_ask);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, getString(R.string.onlinetest_title));

        mToolBar.inflateMenu(R.menu.menu_onlinetest_ask);

        //取消全屏滑动
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);

        //如果是分数跳转回来 则隐藏答题卡
        //mToolBar.getMenu().getItem(1).setVisible(type==OnlineTestConstant.CardType.ASK_CARD);
    }

    @Override
    public void initListener() {
        super.initListener();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //showShortToast("onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                mCurrIndex = position;
                mIndexTv.setText(String.valueOf(position+1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    //第1个执行， state = 1
                    //showShortToast("onPageScrollStateChanged00000000000");
                } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    //showShortToast("onPageScrollStateChanged1111111111111");
                    //第3个执行，手放开后， state = 2
                } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                    //第6个执行，结束， state = 0
                    //showShortToast("onPageScrollStateChanged2222222222");
                }
            }
        });
        mToolBar.setOnMenuItemClickListener(this);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果是分数页面回来关闭时回到分数页面
//                if (mToolBar.getMenu().getItem(1).isVisible()){
//                    openCameraActivity(OnlineTestTopicAskResultActivity.class);
//                }
                finish();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        subjectCatalog = getIntent().getStringExtra("subjectCatalog");
        paperId = getIntent().getStringExtra("paperId");
        versionName = getIntent().getStringExtra("versionName");
        className = getIntent().getStringExtra("className");

        type = getIntent().getIntExtra("type",OnlineTestConstant.CardType.ASK_CARD);
        mFragments = new ArrayList<>();
        mTopicList = new ArrayList<>();
        for(int i=0;i<10;i++)
            mFragments.add(new OnlineTestTopicAskFragment());

        mOnlineTestAskFragmentAdapter = new OnlineTestAskFragmentAdapter(getSupportFragmentManager());
        mOnlineTestAskFragmentAdapter.fragments(mFragments).maxFragment(OnlineTestConstant.XZT_SIZE);
        mViewPager.setAdapter(mOnlineTestAskFragmentAdapter);
        answerMap = new HashMap<>();
        setMapDefault(OnlineTestConstant.XZT_SIZE);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        mPresenter.getPaper(subjectCatalog,paperId);
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onSuccess(int type, ResponseBody body) {
        switch (type){
            case GETPAPER:
                getPager(body);
                break;
        }
    }


    private void getPager(ResponseBody body){
        Map<String,Object> map = CommonUtil.parseDataToMap(body);

        PaperEntity paperEntity = CommonUtil.data2Clazz(map.get("paper"),PaperEntity.class);

        state = "2";//paperEntity.getState();
        typeId = paperEntity.getTopicTypeList().get(0).getTypeId();
        List<TopicType> typeList = paperEntity.getTopicTypeList();
        for (TopicType topicType : typeList){
            if (OnlineTestConstant.TopicType.XZT.equals(topicType.getTypeId())){
                mTopicList = topicType.getTopicEntityList();
            }
        }

        if(mTopicList.size()>0){
            mOnlineTestAskFragmentAdapter.maxFragment(mTopicList.size());
            String size = String.valueOf(mTopicList.size());
            mTotalIndexTv.setText(size);
            setMapDefault(mTopicList.size());
            setFragmentData(mTopicList);
            //开始记时
            time(mTime);
        }else{
           if(viewEmpty==null) {
               ViewStub viewStub = (ViewStub) findViewById(R.id.viewStub);
               viewEmpty = viewStub.inflate();
           }
            viewEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setMapDefault(int count){
        answerMap.clear();
        for (int i=0;i<count;i++){
            answerMap.put(String.valueOf(i),new ResultEntity("","",i+1));
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_node:
                openActivity(OnlineTestTopicAskNodeActivity.class);
                break;
            case R.id.action_ask_card:
                SerializableMap map = new SerializableMap();
                map.setMap(answerMap);
                Bundle bundle = getIntent().getExtras();
                bundle.putSerializable("answerMap", map);
                bundle.putInt("time", mTime);
                bundle.putString("commitPaper",getCommitPaper());
                bundle.putBoolean("isSelected",isSelected);

                openResultActivity(OnlineTestTopicAskCardActivity.class,bundle);
                break;
        }
        return true;
    }

    private String getCommitPaper(){
        List<CommitPaperEntity.Answer> answerList = new ArrayList<>();
        for(String key : answerMap.keySet()){
            answerList.add(new CommitPaperEntity().answer(answerMap.get(key).getId(),answerMap.get(key).getAnswerResult()));

            if("".equals(answerMap.get(key).getAnswerResult())){
                isSelected = false;
            }
        }

        List<CommitPaperEntity.AnswerType> typeList = new ArrayList<>();
        //暂时只有一种题目类型
        typeList.add(new CommitPaperEntity().answerType(typeId,answerList));

        CommitPaperEntity commitPaperEntity = new CommitPaperEntity();
        commitPaperEntity.setPaperId(paperId);
        commitPaperEntity.setState(state);
        commitPaperEntity.setList(typeList);
        Gson gson = new Gson();
        return gson.toJson(commitPaperEntity);
    }

    private void setFragmentData(List<TopicEntity> topicEntityList){
        mOnlineTestAskFragmentAdapter.setData(topicEntityList);
        mOnlineTestAskFragmentAdapter.notifyDataSetChanged();
    }
    /**是否不显示计时--提交回来*/
    private boolean isStop;
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
                if(!isStop) {
                    mToolBar.setTitle(DateUtil.secToTime(integer));
                    mTime = integer;
                }
            }
        });
    }

    @Override
    public void sendNextFragmentMessage(String id,String answer) {
        //放置答案
        answerMap.put(String.valueOf(mCurrIndex),new ResultEntity(id,answer,mCurrIndex+1));
        if (mCurrIndex+1 < mTopicList.size()){
            mViewPager.setCurrentItem(mCurrIndex+1);
        }else{
            //跳转答题卡
            if(mToolBar.getMenu().getItem(1).isVisible())
                showShortToast("请查看答题卡，并提交试卷");
        }
    }

    @Override
    public void sendIndex(int index) {
        mIndexTv.setText(String.valueOf(index));
    }
    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        isSelected = true;
        // 根据上面发送过去的请求吗来区别
        if (resultCode == RESULT_OK) {
            String num = data.getStringExtra("num");
            int type = data.getIntExtra("type",0);
            mViewPager.setCurrentItem(Integer.valueOf(num)-1);
            SerializableMap serializableMap = (SerializableMap) data.getSerializableExtra("answerMap");
            answerMap = serializableMap.getMap();
            mOnlineTestAskFragmentAdapter.setAnswerMap(answerMap);

            //如果是分数跳转回来 则隐藏答题卡按钮
            mToolBar.getMenu().getItem(1).setVisible(type==OnlineTestConstant.CardType.ASK_CARD);

            if(type==OnlineTestConstant.CardType.ASK_CARD){//答题卡

            }else{//交卷后的
                isStop = true;//不显示顶部计时
                setToolbar(mToolBar, getString(R.string.onlinetest_title));//设置顶部标题
                //设置可以显示解析
                mOnlineTestAskFragmentAdapter.setShowAnswerDetail(true);
                for(int i=0;i<mTopicList.size();i++){
                    for(AnswerOption a:mTopicList.get(i).getAnswerOptionList()){
                        a.setResultEntity(answerMap.get(String.valueOf(i)));
                    }
                }
                mOnlineTestAskFragmentAdapter.notifyDataSetChanged();
            }
        }
    }
    /**修改选择*/
    public void updateOptions(int parentPosition, int optionPosition){
        for(AnswerOption a:mTopicList.get(parentPosition).getAnswerOptionList()){
            a.setSelectId(optionPosition);
        }
    }
}
