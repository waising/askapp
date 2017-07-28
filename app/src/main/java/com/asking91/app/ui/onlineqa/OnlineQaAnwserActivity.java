package com.asking91.app.ui.onlineqa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asking91.app.R;
import com.asking91.app.entity.onlineqa.OnlineQADetailEntity;
import com.asking91.app.ui.adapter.onlineqa.OnlineQaAnwserAdapter;
import com.asking91.app.ui.widget.AskEditText;
import com.asking91.app.ui.widget.MultiStateView;
import com.asking91.app.ui.widget.camera.ui.BaseEvenActivity;
import com.asking91.app.ui.widget.camera.ui.CameraActivity;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DateUtil;
import com.asking91.app.util.JLog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

/**
 * Created by wxiao on 2016/11/9.
 * 回答、提问
 */

public class OnlineQaAnwserActivity extends BaseEvenActivity<OnlineQAPresenterImpl, OnlineQAModelImpl> implements  OnlineQAContract.View {

    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.title_more)
    ImageView titleMore;
    @BindView(R.id.title_click)
    LinearLayout titleClick;
    @BindView(R.id.user_info_rl)
    RelativeLayout userInfoRl;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.multiStateView)
    MultiStateView multiStateView;
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.input)
    AskEditText input;
    @BindView(R.id.ok)
    Button ok;
    @BindView(R.id.layout)
    RelativeLayout layout;
    /**userId--问题的提问者ID*/
    private String id,userId, anserId;
    /**是提问人*/
    private boolean userFlag;
    private List<OnlineQADetailEntity.AnwserMoreEntity> anwserMoreEntities;
    private OnlineQaAnwserAdapter onlineQaAnwserAdapter;
    private MaterialDialog materialDialog;
    /**只是用来创建子元素的*/
    private OnlineQADetailEntity onlineQADetailEntityNoUse;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView= LayoutInflater.from(this).inflate(R.layout.activity_onlineqa_anwser,null);
        setContentView(contentView);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        id = getIntent().getStringExtra("id");
        userId = getIntent().getStringExtra("userId");
        anwserMoreEntities = new ArrayList<>();
        onlineQADetailEntityNoUse = new OnlineQADetailEntity();
    }

    @Override
    public void initView() {
        super.initView();
        materialDialog = getLoadingDialog().build();
        materialDialog.setContent("提交中...");
        recyclerView.setLayoutManager(new LinearLayoutManager(mthis));
        setSupportActionBar(toolBar);
        if(userId.equals(getUserConstant().getUserEntity().getId())){
            toolBar.setTitle("提问");
            ok.setText("提问");
            userFlag = true;
            anserId = getIntent().getStringExtra("anserId");//自己进来的是追问，有回答ID
        }else{
            toolBar.setTitle("回答");
            ok.setText("回答");
            userFlag = false;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();
        multiStateView.setViewState(multiStateView.VIEW_STATE_LOADING);
        onlineQaAnwserAdapter = new OnlineQaAnwserAdapter(mthis, userFlag, userId, anwserMoreEntities);
        recyclerView.setAdapter(onlineQaAnwserAdapter);
        mPresenter.onlineQADetail(id);
//        mPresenter.onlineQATalk(id);
    }

    @Override
    public void onSuccess(String method, String str) {
        if(method.equals("onSubmitPicSuccess")){
            String content="";
            try {
                JSONObject object = new JSONObject(str);
                content = object.getString("content");
               submitControl(content);
            }  catch (JSONException e) {
                e.printStackTrace();
                materialDialog.dismiss();
            }
        }
        else {//提交结果
            try {
                JLog.i("OnlineQaAnwserActivity", "method=" + method + "--str=" + str);
                JSONObject object = new JSONObject(str);
                if (object.getString("msg").indexOf("你没有权限操作") == -1) {
                    if(anserId==null){
                        anserId = object.getString("answerId");
                        isFirst = false;
                    }
                    //界面增加回答的内容
                    OnlineQADetailEntity.AnwserMoreEntity anwserMoreEntity=null;
                    if(userFlag){
                        anwserMoreEntity = onlineQADetailEntityNoUse.createAnwserMoreEntity(DateUtil.currentDatetime(), submitMessage,null,null);
                    }else{
                        anwserMoreEntity = onlineQADetailEntityNoUse.createAnwserMoreEntity(null,null,DateUtil.currentDatetime(), submitMessage);
                    }
                    anwserMoreEntities.add(anwserMoreEntity);
                    onlineQaAnwserAdapter.notifyItemInserted(anwserMoreEntities.size());

                    showShortToast(object.getString("msg"));
                    if (getIntent().getIntExtra("type", 0) == 0) {//列表界面过来
                    } else if (getIntent().getIntExtra("type", 0) == 1) {//详情界面过来
                        //通知详情界面更新
                        Intent intent = new Intent("com.asking91.app.ui.onlineqa.OnlineQADetailActivity");
                        intent.putExtra("anserId", anserId);
                        sendBroadcast(intent);
                    }
                    //通知列表更新
                    Intent intent = new Intent("com.asking91.app.ui.onlineqa.OnlineQAFragment1");
                    intent.putExtra("anserId", anserId);
                    intent.putExtra("position", getIntent().getIntExtra("position", -1));
                    sendBroadcast(intent);
                } else {
                    showShortToast(object.getString("msg"));
                }
                materialDialog.dismiss();
//            }else if(method.equals("onlineQaAgainAsk")){
//            }
//            }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @OnClick({R.id.ok, R.id.photo})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok:
                submitControl(input.getText().toString());
                break;
            case R.id.photo:
                CameraActivity.openCameraActivity(this,AppEventType.ONLINE_QA_AN_CAMERA_REQUEST,0);
                break;
        }
    }
    /**提交内容*/
    private String submitMessage;

    /**提交/拍照直接提交*/
    private void submitControl(String message){
        submitMessage = message;
        if (userFlag) {//追问
            if(message.length()==0){
                showShortToast("请输入追问内容");
                return;
            }
            mPresenter.onlineQaAgainAsk(id, anserId, message);
        } else {
            if(message.length()==0){
                showShortToast("请输入回答内容");
                return;
            }
            if(isFirst){//第一次回答问题
                mPresenter.onlineQAAnser(id,  message);
            }else {//追答
                mPresenter.onlineQaAgainAnswer(id, anserId, message);
            }
        }
    }

    //-----------拍照End------------------
    private String picTakePath;
    public void onEventMainThread(AppEventType event) {
        switch (event.type){
            case AppEventType.ONLINE_QA_AN_CAMERA_REQUEST:
                materialDialog.show();
                picTakePath = (String)event.values[0];
                //将本地图片转成bitmap上传
                File file = new File(picTakePath);
                JLog.i("OnlineQAAskActivity","file.exist="+file.exists());
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(picTakePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap= BitmapFactory.decodeStream(fis);
                MultipartBody.Part body = CommonUtil.getMultipartBodyPart(mthis, bitmap, picTakePath);
                mPresenter.onSubmitPicSuccess(body);//提交图片，获取地址，在上传问题
                break;
        }
    }

    /**
     * 判段是否是要追答
     */
    private boolean isFirst = true;
    @Override
    public void onOnlineQAonlineQASuccess(ResponseBody obj) {
        try {
            String str = obj.string();
            JLog.logi(mthis.getClass().getSimpleName(), str);
            JSONObject object = new JSONObject(str);
            JLog.i(mthis.getClass().getSimpleName(),"OnlineQaAnwserActivity=="+object.toString()+"--user.id="+getUserConstant().getUserId());
            if(object.getInt("flag")==0) {
                String x = object.getString("userQA");
                OnlineQADetailEntity e = new Gson().fromJson(x, OnlineQADetailEntity.class);
                OnlineQADetailEntity.AnwserMoreEntity anwserMoreEntity0 = null;
                if(userFlag){//提问者查看界面
                    //提问数据
                    anwserMoreEntity0 = e.createAnwserMoreEntity(e.getCreateDateFmt(),e.getDescription(),null,null);
                    anwserMoreEntities.clear();
                    anwserMoreEntities.add(anwserMoreEntity0);
                    if(e.getList()!=null) {
                        for (OnlineQADetailEntity.AnwserEntity a : e.getList()) {
                            //判断是否是要追问的答案
                            if (a.getId().equals(anserId)) {
                                //回答数据
                                OnlineQADetailEntity.AnwserMoreEntity anwserMoreEntity1 = e.createAnwserMoreEntity(null,null,a.getCreateDateFmt(),a.getContent());
                                anwserMoreEntities.add(anwserMoreEntity1);
                                if(a.getList()!=null){//判断是否有追答
                                    for(int i=0;i<a.getList().size();i++){
                                        if(a.getList().get(i).getAnswerTime()!=null&&a.getList().get(i).getAnswerTime().length()!=0){
                                            a.getList().get(i).setAnswerTime(DateUtil.getDateToString(Long.parseLong(a.getList().get(i).getAnswerTime())));
                                        }else{
                                            a.getList().get(i).setAskTime(DateUtil.getDateToString(Long.parseLong(a.getList().get(i).getAskTime())));
                                        }
                                        anwserMoreEntities.add(a.getList().get(i));
                                    }
                                }
                                isFirst = false;
                                break;
                            }
//                            else {//别人回答的
//                                //回答数据
//                                OnlineQADetailEntity.AnwserMoreEntity anwserMoreEntity1 = e.createAnwserMoreEntity(null, null, a.getCreateDateFmt(),a.getContent());
//                                anwserMoreEntities.add(anwserMoreEntity1);
//                                if(a.getList()!=null){//判断是否有追答
//                                    for(int i=0;i<a.getList().size();i++){
//                                        anwserMoreEntities.add(a.getList().get(i));
//                                    }
//                                }
//                                isFirst = false;
//                            }
                        }
                    }
                }else{//回答者查看界面
                    anwserMoreEntity0 = e.createAnwserMoreEntity(e.getCreateDateFmt(),e.getDescription(),null, null);
                    anwserMoreEntities.clear();
                    anwserMoreEntities.add(anwserMoreEntity0);
                    if(e.getList()!=null) {
                        for (OnlineQADetailEntity.AnwserEntity a : e.getList()) {
                            //获取第一个是自己的回答，后面的就不要了，后面的回答在追答里面
                            if (a.getUserId().equals(getUserConstant().getUserEntity().getId())) {
                                anserId = a.getId();
                                //回答数据
                                OnlineQADetailEntity.AnwserMoreEntity anwserMoreEntity1 = e.createAnwserMoreEntity(null, null, a.getCreateDateFmt(),a.getContent());
                                anwserMoreEntities.add(anwserMoreEntity1);
                                if(a.getList()!=null){//判断是否有追答
                                    for(int i=0;i<a.getList().size();i++){
                                        if(a.getList().get(i).getAnswerTime()!=null&&a.getList().get(i).getAnswerTime().length()!=0){
                                            a.getList().get(i).setAnswerTime(DateUtil.getDateToString(Long.parseLong(a.getList().get(i).getAnswerTime())));
                                        }else{
                                            a.getList().get(i).setAskTime(DateUtil.getDateToString(Long.parseLong(a.getList().get(i).getAskTime())));
                                        }
                                        anwserMoreEntities.add(a.getList().get(i));
                                    }
                                }
                                isFirst = false;
                                break;
                            }
                        }
                    }
                }
                onlineQaAnwserAdapter.notifyDataSetChanged();
                multiStateView.setViewState(multiStateView.VIEW_STATE_CONTENT);
            }else{
                multiStateView.setViewState(multiStateView.VIEW_STATE_ERROR);
                showShortToast(object.getString("msg"));
            }
//            onlineQADetailAdapter = new OnlineQADetailAdapter(mthis, e, flag);
//            recyclerView.setAdapter(onlineQADetailAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
            multiStateView.setViewState(multiStateView.VIEW_STATE_ERROR);
        } catch (IOException e1) {
            e1.printStackTrace();
            multiStateView.setViewState(multiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onRefreshData(ResponseBody obj) {

    }

    @Override
    public void onLoadMoreData(ResponseBody obj) {

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }
}
