package com.asking91.app.ui.basepacket.choose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.basepacket.ClassEntity;
import com.asking91.app.entity.basepacket.CourseEntity;
import com.asking91.app.global.BasePacketConstant;
import com.asking91.app.global.Constants;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.basepacket.details.KnowledgeDetailActivity;
import com.asking91.app.ui.pay.PayServerActivity;
import com.asking91.app.ui.widget.dialog.AlertDialogUtil;
import com.asking91.app.util.CommonUtil;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseActivity extends BaseFrameActivity<ChoosePresenter, ChooseModel> implements Toolbar.OnMenuItemClickListener ,
        ChooseContract.View{

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.choose_knowledge_tv)
    TextView knowledgeTv;

    @BindView(R.id.choose_course_tv)
    TextView courseTv;

    @BindView(R.id.choose_class_tv)
    TextView classTv;

    @BindString(R.string.basepacket_plase_choose)
    String plaseChoose;

    ArrayMap<String,String> titleMap;

    String knowledgeId ="",versionLevelId="",courseId="";

    String type = Constants.SubjectCatalog.CZSX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basepacket_choose);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buy:
                openActivity(PayServerActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar,titleMap.get(type));
        mToolBar.inflateMenu(R.menu.menu_go_buy);
    }

    @Override
    public void initListener() {
        super.initListener();
        mToolBar.setOnMenuItemClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        setTitleMap();

        versionLevelId = "";
        courseId = "";
    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
    }

    @Override
    public void onRequestEnd() {

    }

    private void setTitleMap(){
        titleMap = new ArrayMap<>();
        titleMap.put(Constants.SubjectCatalog.CZSX,getString(R.string.ask_czsx));
        titleMap.put(Constants.SubjectCatalog.CZWL,getString(R.string.ask_czwl));
        titleMap.put(Constants.SubjectCatalog.GZWL,getString(R.string.ask_gzwl));
        titleMap.put(Constants.SubjectCatalog.GZSX,getString(R.string.ask_gzsx));
    }


    /**
     * 课程 年级 知识点 点击选择时间
     * @param view
     */
    @OnClick({R.id.choose_class_ly,R.id.choose_course_ly,R.id.choose_knowledge_ly,R.id.open_knowledge_btn})
    public void tvOnClick(View view){
        switch (view.getId()){
            case R.id.choose_course_ly:
                mPresenter.getCourseList(type);
                break;
            case R.id.choose_class_ly:
                if (!TextUtils.isEmpty(courseId))
                    mPresenter.getClassVersionList(courseId);
                break;
            case R.id.choose_knowledge_ly:
                if (!TextUtils.isEmpty(versionLevelId)){
                    Bundle bundle = new Bundle();
                    bundle.putString("versionLevelId",versionLevelId);
                    bundle.putString("backActivity",ChooseActivity.class.getName());
                    openResultActivity(KnowledgeTreeActivity.class,bundle);
                }
                break;
            case R.id.open_knowledge_btn:
                if(!TextUtils.isEmpty(knowledgeId)&&!TextUtils.isEmpty(versionLevelId)&&!TextUtils.isEmpty(courseId)){
                    Bundle bundle = new Bundle();
                    bundle.putString("title",titleMap.get(type));
                    bundle.putString("type",type);
                    bundle.putString("knowledge", (String) knowledgeTv.getText());
                    bundle.putString("versionLevelId",versionLevelId);
                    bundle.putString("id",knowledgeId);
                    bundle.putString("backActivity",ChooseActivity.class.getName());
                    openActivity(KnowledgeDetailActivity.class,bundle);
                }
                break;
        }
    }
    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 根据上面发送过去的请求吗来区别
        if (resultCode == RESULT_OK){
            String code = data.getStringExtra("knowledgeId");
            String name = data.getStringExtra("knowledgeName");

            knowledgeTv.setText(name);
            knowledgeId = code;
        }

    }

    @Override
    public void onSuccess(int type,Object obj) {
        switch (type){
            case BasePacketConstant.Choose.COURSE_VERSION:
                showCourseDialog(obj);
                break;
            case BasePacketConstant.Choose.CLASS_LEVEL:
                showClassDialog(obj);
                break;
        }

    }


    /**
     * 显示选择课程dialog
     */
    private void showCourseDialog(Object obj){
        final List<CourseEntity> list = CommonUtil.parseDataToList(obj,new TypeToken<List<CourseEntity>>(){});

        String[] courseArr = new String[list.size()];
        int i = 0;
        for (CourseEntity courseEntity:list){
            courseArr[i] = courseEntity.getVersionName();
            i++;
        }

        AlertDialogUtil.showNormalListDialogStringArr(this, courseArr, new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCourseText(list.get(position).getVersionName(),String.valueOf(list.get(position).getVersionId()));
                //清除年级信息
                setClassText("","");
                //清除知识点信息
                setKnowledgeTv("","");
                AlertDialogUtil.dismiss();
            }
        });

    }

    private void setCourseText(String name,String id){
        courseTv.setText(name);
        courseId = id;
    }

    private void setClassText(String name,String id){
        classTv.setText(name);
        versionLevelId = id;
    }

    private void setKnowledgeTv(String name,String id){
        knowledgeTv.setText(name);
        knowledgeId = id;
    }

    /**
     * 显示选择年级dialog
     */
    private void showClassDialog(Object obj){

        final List<ClassEntity> list = CommonUtil.parseDataToList(obj,new TypeToken<List<ClassEntity>>(){});

        String[] classArr = new String[list.size()];
        int i = 0;
        for (ClassEntity classEntity:list){
            classArr[i] = classEntity.getTextbook()+"("+classEntity.getLevelName()+")";
            i++;
        }

        AlertDialogUtil.showNormalListDialogStringArr(this, classArr, new OnOperItemClickL() {

            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                setClassText(list.get(position).getTextbook()+"("+list.get(position).getLevelName()+")",String.valueOf(list.get(position).getVersionLevelId()));
                setKnowledgeTv("","");
                AlertDialogUtil.dismiss();
            }
        });

    }

}
