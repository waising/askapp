package com.asking91.app.ui.mine.more;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asking91.app.R;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.mine.MineContract;
import com.asking91.app.ui.mine.MineModel;
import com.asking91.app.ui.mine.MinePresenter;
import com.asking91.app.ui.oto.OtoEndActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.camera.comm.AppManager;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.dialog.AlertDialogUtil;
import com.asking91.app.util.CommonUtil;
import com.flyco.dialog.listener.OnOperItemClickL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by jswang on 2017/3/24.
 */

public class FeedBackActivity extends BaseFrameActivity<MinePresenter, MineModel> implements MineContract.View {
    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.tv_cause)
    TextView tv_cause;

    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.ll_des)
    View ll_des;
    @BindView(R.id.ll_fb)
    View ll_fb;
    @BindView(R.id.reply)
    View reply;

    @BindView(R.id.user_img_iv)
    ImageView user_img_iv;
    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.money)
    TextView money;

    @BindView(R.id.tv_stu_cause)
    TextView tv_stu_cause;
    @BindView(R.id.tv_stu_feed)
    TextView tv_stu_feed;



    @BindView(R.id.teacherName)
    TextView teacherName;
    @BindView(R.id.cb_favor)
    CheckBox cb_favor;

    private MaterialDialog materialDialog;

    String orderId;
    String price;
    String integral;
    String connectTime;
    String teaName;
    String teaAvatar;
    boolean favor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_feed_back);
        ButterKnife.bind(this);

        orderId = getIntent().getStringExtra("orderId");
        price = getIntent().getStringExtra("price");
        integral = getIntent().getStringExtra("integral");
        connectTime = getIntent().getStringExtra("connectTime");
        teaName = getIntent().getStringExtra("teaName");
        teaAvatar = getIntent().getStringExtra("teaAvatar");
        favor = getIntent().getBooleanExtra("favor",favor);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.myFeedBack));
        ll_des.setVisibility(View.GONE);

        materialDialog = getLoadingDialog().build();
        materialDialog.setContent("加载中...");

        balance.setText(integral);
        time.setText(connectTime);
        money.setText(price);
        teacherName.setText(teaName);
        cb_favor.setChecked(favor);

        if(!TextUtils.isEmpty(teaAvatar)){
            BitmapUtil.displayUserImage(this,teaAvatar, user_img_iv);
        }
    }

    private void showCourseDialog(){
        final String[] courseArr = new String[]{"老师讲的不好","不够详细"};

        AlertDialogUtil.showNormalListDialogStringArr(this, courseArr, new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_cause.setText(courseArr[position]);
                AlertDialogUtil.dismiss();
            }
        });

    }

    @OnClick({R.id.rl_cause, R.id.reply})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_cause:
                showCourseDialog();
                break;

            case R.id.reply:
                final String cause = tv_cause.getText().toString();
                final String content = et_content.getText().toString();
                if(TextUtils.isEmpty(cause)){
                    Toast.makeText(FeedBackActivity.this,"投诉原因不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                materialDialog.show();
                mPresenter.studentcomplain(this,cause,content,orderId,new ApiRequestListener<String>(){
                    @Override
                    public void onResultSuccess(String res) {
                        CommonUtil.Toast(FeedBackActivity.this,"投诉成功");
                        AppManager.getAppManager().finishActivity(OtoEndActivity.class);
                        finish();
                    }

                    @Override
                    public void onResultFail() {
                        AppManager.getAppManager().finishActivity(OtoEndActivity.class);
                        finish();
                    }
                });
                break;
        }
    }


    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onError(int type) {
        if (materialDialog!=null)
            materialDialog.dismiss();
    }

    @Override
    public void onRefreshData(ResponseBody body) {

    }

    @Override
    public void onLoadMoreData(ResponseBody body) {

    }

    @Override
    public void onSuccess(int type, ResponseBody body) {
    }
}
