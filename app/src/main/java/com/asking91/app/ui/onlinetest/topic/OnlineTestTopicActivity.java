package com.asking91.app.ui.onlinetest.topic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.basepacket.ClassEntity;
import com.asking91.app.entity.basepacket.CourseEntity;
import com.asking91.app.global.BasePacketConstant;
import com.asking91.app.global.OnlineTestConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.basepacket.choose.KnowledgeTreeActivity;
import com.asking91.app.ui.onlinetest.topicask.OnlineTestTopicAskActivity;
import com.asking91.app.ui.widget.dialog.ChooseDialog;
import com.asking91.app.ui.widget.tagview.TagContainerLayout;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by wxwang on 2016/11/14.
 */
public class OnlineTestTopicActivity extends BaseFrameActivity<OnlineTestTopicPresenter, OnlineTestTopicModel>
    implements OnlineTestTopicContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.choose_course_tv)
    TextView mVersionTv;

    @BindView(R.id.tagcontainerLayout)
    TagContainerLayout mTagContainerLayout;

    @BindView(R.id.expandable_layout)
    ExpandableLayout mExpandableLayout;

    @BindView(R.id.choose_class_tv)
    TextView mClassTv;

    @BindView(R.id.level_rg)
    RadioGroup mLevelRg;

    @BindView(R.id.selected_tv)
    TextView mSelectedTv;

    @BindString(R.string.onlinetest_title)
    String title;
    String subjectCatalog, versionId, classLevelId, KnowledgeClassicId;
    int nanyi;
    List<LabelEntity> allLabelList=null;
    //知识点id
    String[] tipIds ;

    public static final int EXECPAPER = 100;
    private List<String> tipNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinetest_topic);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar, title);
        //tag 最大6个汉字
        mTagContainerLayout.setTagMaxLength(10);
        mLevelRg.check(R.id.all);
    }

    @Override
    public void initListener() {
        super.initListener();

        mLevelRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                nanyi = Integer.parseInt(String.valueOf(group.findViewById(checkedId).getTag()));
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        subjectCatalog = getIntent().getStringExtra("subjectCatalog");
        versionId = "";
        classLevelId = "";
        KnowledgeClassicId = "";
        //tipIds = new String[OnlineTestConstant.KNOWLEDGE_MAX_SIZE];
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onSuccess(int type, ResponseBody body) {
        switch (type) {
            case BasePacketConstant.Choose.COURSE_VERSION:
                showCourseDialog(body);
                break;
            case BasePacketConstant.Choose.CLASS_LEVEL:
                showClassDialog(body);
                break;
            case EXECPAPER:
                execPaper(body);
                break;
        }

    }

    public void execPaper(ResponseBody body){
        Map<String,Object> map = CommonUtil.parseDataToMap(body);

        if("0".equals(map.get("flag")) && map.get("paperId")!=null){

            //跳转出题界面
            Bundle bundle = new Bundle();
            bundle.putString("subjectCatalog", subjectCatalog);
            bundle.putString("paperId", map.get("paperId").toString());
            //版本
            bundle.putString("versionName", (String) mVersionTv.getText());
            //年级
            bundle.putString("className", (String) mClassTv.getText());

            openActivity(OnlineTestTopicAskActivity.class,bundle);
        }else{
            showShortToast(map.get("msg").toString());
        }

    }

    @OnClick({R.id.choose_course_ly, R.id.choose_class_ly, R.id.choose_knowledge_ry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_course_ly:
                mPresenter.getVersionList(subjectCatalog);
                break;
            case R.id.choose_class_ly:
                if (!TextUtils.isEmpty(versionId))
                    mPresenter.getClassLevelList(versionId);
                break;
            case R.id.choose_knowledge_ry:
                if (!TextUtils.isEmpty(classLevelId)){
                    Bundle bundle = new Bundle();
                    bundle.putString("versionLevelId",classLevelId);
                    bundle.putBoolean("isMuliSelect",true);
                    bundle.putString("backActivity",OnlineTestTopicActivity.class.getName());
                    bundle.putParcelableArrayList("allLabelList", (ArrayList<? extends Parcelable>) allLabelList);
                    openResultActivity(KnowledgeTreeActivity.class,bundle);
                }

                break;
        }
    }

    private void showCourseDialog(ResponseBody body){
        try{
            List<CourseEntity> list = new ArrayList<>();
            list = CommonUtil.parseDataToList(body,new TypeToken<List<CourseEntity>>(){});

            final List<LabelEntity> dialogEntities = new ArrayList<>();

            for (CourseEntity courseEntity : list){
                dialogEntities.add(new LabelEntity(String.valueOf(courseEntity.getVersionId()),courseEntity.getVersionName()));
            }


            final ChooseDialog chooseDialog = new ChooseDialog(this);
            chooseDialog.title(getString(R.string.onlinetest_choose_version))
                    .datas(dialogEntities)
                    .callBackListener(new ChooseDialog.DialogCallBackListener() {
                        @Override
                        public void callBack(LabelEntity dialogEntity) {
                            setVersionText(dialogEntity.getName(),dialogEntity.getId());
                            setKnowledgeTv("0");
                            setClassText("","");
                            if(tipNames!=null && tipNames.size()>0 ){
                                tipNames.clear();
                                mTagContainerLayout.setTags(tipNames);
                            }
                            chooseDialog.dismiss();
                        }
                    }).show();

        }catch (Exception e){
            Map<String,Object> map = CommonUtil.parseDataToMap(body);
            if("0".equals(map.get("flag")) && map.get("msg")!=null){
                showShortToast(map.get("msg").toString());
            }
        }


    }


    private void showClassDialog(ResponseBody body){
        try {
            List<ClassEntity> list = new ArrayList<>();
            list = CommonUtil.parseDataToList(body,new TypeToken<List<ClassEntity>>(){});

            final List<LabelEntity> dialogEntities = new ArrayList<>();

            for (ClassEntity classEntity : list){
                dialogEntities.add(new LabelEntity(String.valueOf(classEntity.getVersionLevelId()),classEntity.getTextbook()));
            }

            final ChooseDialog chooseDialog = new ChooseDialog(this);
            chooseDialog.title(getString(R.string.onlinetest_choose_classlevel))
                    .datas(dialogEntities)
                    .callBackListener(new ChooseDialog.DialogCallBackListener() {
                        @Override
                        public void callBack(LabelEntity dialogEntity) {
                            setKnowledgeTv("0");
                            setClassText(dialogEntity.getName(),dialogEntity.getId());
                            if(tipNames!=null && tipNames.size()>0 ){
                                tipNames.clear();
                                mTagContainerLayout.setTags(tipNames);
                            }
                            chooseDialog.dismiss();
                        }
                    }).show();
        }catch (Exception e) {
            Map<String,Object> map = CommonUtil.parseDataToMap(body);
            if("0".equals(map.get("flag")) && map.get("msg")!=null){
                showShortToast(map.get("msg").toString());
            }
        }

    }

    private void setVersionText(String name,String id){
        mVersionTv.setText(name);
        versionId = id;
    }

    private void setClassText(String name,String id){
        mClassTv.setText(name);
        classLevelId = id;
    }

    private void setKnowledgeTv(String num){
        mSelectedTv.setText(num);
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(tipNames!=null && tipNames.size()>0 ){
            tipNames.clear();
        }
        // 根据上面发送过去的请求吗来区别
        if (resultCode == RESULT_OK){
            List<LabelEntity> labelList = data.getParcelableArrayListExtra("labelList");
            allLabelList = data.getParcelableArrayListExtra("allLabelList");
            int i = 0;
            tipNames = new ArrayList<>();
            tipIds = new String[labelList.size()];
            for(LabelEntity labelEntity : labelList) {
                tipIds[i] = labelEntity.getId();
                tipNames.add(labelEntity.getName());
                i++;
            }

            setKnowledgeTv(String.valueOf(i));
            //显示标签
            mTagContainerLayout.setTags(tipNames);
            mExpandableLayout.expand();
        }

    }

    @OnClick(R.id.onlinetest_topic_btn)
    public void onClick(){
        if(tipIds!=null && !TextUtils.isEmpty(versionId)&& !TextUtils.isEmpty(classLevelId)){
            //拼接字符串  nanyi为0时填空为不限
            StringBuilder str = new StringBuilder();

            str.append("{").append("tipIds:[").append(TextUtils.join(",",tipIds)).append("],degree:");
            str.append("[").append(nanyi==0 ? "" : nanyi).append("]");
            str.append(",").append("pdt:").append("0").append(",").append("xzt:").append(OnlineTestConstant.XZT_SIZE).append(",");
            str.append("tkt:").append("0").append(",").append("jdt:").append("0").append("}");

            mPresenter.execPaper(subjectCatalog,str.toString());
        }

    }

    @Override
    public void onRequestError(RequestEntity requestEntity){
        super.onRequestError(requestEntity);
        if(requestEntity.getCode()!=401 && requestEntity.getTag()!=null && requestEntity.getTag().equals(String.valueOf(EXECPAPER))){
            showShortToast("服务器故障，出卷失败！");
        }
    }
}
