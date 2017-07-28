package com.asking91.app.ui.basepacket.comment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.widget.AskMathView;
import com.asking91.app.util.CommonUtil;

import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wxwang on 2016/11/8.
 */
public class CommentActivity extends BaseFrameActivity<CommentPresenter,CommentModel>
        implements Toolbar.OnMenuItemClickListener, CommentContract.View {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.type_name_tv)
    TextView mTypeNameTv;

    @BindView(R.id.mathView)
    AskMathView mMathView;
    @BindView(R.id.comment_edt)
    EditText mCommentEdt;


    @BindString(R.string.comment)
    String title;

    String typeName,tipId,contentHtml;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
    }

    @Override
    public void initView(){
        super.initView();
        title =  title.replace("/","");
        setToolbar(mToolbar, title);

        mToolbar.inflateMenu(R.menu.menu_commit);
    }

    @Override
    public void initData(){
        super.initData();
        typeName = getIntent().getStringExtra("typeName");
        tipId = getIntent().getStringExtra("tipId");
        contentHtml = getIntent().getStringExtra("contentHtml");
    }

    @Override
    public void initLoad(){
        super.initLoad();
        mMathView.setText(contentHtml);
        mTypeNameTv.setText(typeName);
    }

    @Override
    public void initListener(){
        super.initListener();
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public void onSuccess(Object obj) {
        Map<String,Object> map = CommonUtil.parseDataToMap(obj);
        if("0".equals(map.get("flag"))){
            showShortToast(map.get("msg").toString());
            Bundle bundle = new Bundle();
            bundle.putString("tipId",tipId);
            openActivity(CommentListActivity.class,bundle);
            finish();
        }else{
            showShortToast("提交失败");
        }

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_commit:
                 if(TextUtils.isEmpty(mCommentEdt.getText())){
                     showShortToast("请输入内容..");
                 }else{
                     mPresenter.saveReview(mCommentEdt.getText().toString(),tipId);
                 }

                 break;
        }
        return true;
    }

    @Override
    public void onRefreshData(Object obj) {

    }

    @Override
    public void onLoadMoreData(Object obj) {

    }
}
