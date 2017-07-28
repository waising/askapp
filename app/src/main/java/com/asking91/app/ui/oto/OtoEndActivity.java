package com.asking91.app.ui.oto;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.oto.OtoRecord;
import com.asking91.app.entity.oto.UserInfo;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.mine.more.FeedBackActivity;
import com.asking91.app.ui.mine.more.MineOtoRecordActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.AskEditText;
import com.asking91.app.ui.widget.StarView;
import com.asking91.app.ui.widget.camera.comm.AppManager;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.camera.utils.ParamHelper;
import com.asking91.app.util.CommonUtil;
import com.asking91.app.util.DateUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;


/**
 * Created by wxiao on 2016/11/25.
 * 答疑结束
 */

public class OtoEndActivity extends BaseFrameActivity<OtoAskPresent, OtoAskModel> implements OtoAskPresent.View {

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
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.r1)
    RelativeLayout r1;
    @BindView(R.id.user_img_iv)
    ImageView userImgIv;
    @BindView(R.id.teacherName)
    TextView teacherName;
    @BindView(R.id.tea_code)
    TextView tea_code;
    @BindView(R.id.cb_favor)
    TextView cb_favor;
    @BindView(R.id.r2)
    RelativeLayout r2;
    @BindView(R.id.et_content)
    AskEditText et_content;
    @BindView(R.id.t4)
    TextView t4;
    @BindView(R.id.rbtn1)
    RadioButton rbtn1;
    @BindView(R.id.rbtn2)
    RadioButton rbtn2;
    @BindView(R.id.rbtn3)
    RadioButton rbtn3;
    @BindView(R.id.rbtn4)
    RadioButton rbtn4;
    @BindView(R.id.rbtn5)
    RadioButton rbtn5;
    @BindView(R.id.reply)
    Button reply;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;


    @BindView(R.id.radioGroup1)
    RadioGroup radioGroup1;
    @BindView(R.id.start_view)
    StarView startView;
    @BindView(R.id.iv_firstorder_flag)
    ImageView iv_firstorder_flag;
    @BindView(R.id.ll_to_students)
    LinearLayout llToStudent;
    @BindView(R.id.tv_teacher_guide)
    TextView tvTeacherGuide;

    private MaterialDialog materialDialog;
    private int score = 5, askCoin;

    int askTimes;
    String orderId;
    String teaName;
    String teaAccount;
    String teaAvatar;
    String teacherAccount;


    @BindView(R.id.price_num)
    TextView tvPriceNum;

    @BindView(R.id.tv_suggest)
    TextView tvSuggest;
    private int state;

    @BindView(R.id.ll_star)
    LinearLayout llStar;

    @BindView(R.id.ll_edt_content)
    LinearLayout ll_edt_content;

    @BindView(R.id.ll_thanks)
    LinearLayout ll_thanks;


    @BindView(R.id.ll_complain)
    LinearLayout ll_complain;

    @BindView(R.id.tv_complain_reason)
    TextView tv_complain_reason;

    @BindView(R.id.tv_complain_detail)
    TextView tv_complain_detail;

    private String askTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oto_end);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
        HashMap<String, Object> mParams = ParamHelper.acceptParams(OtoLearningActivity.class.getName());//一对一学习传递过来
        if (mParams != null && mParams.size() > 0) {
            orderId = (String) mParams.get("orderId");
            askTimes = (int) mParams.get("askTimes");
            teaName = (String) mParams.get("teaName");
            teaAvatar = (String) mParams.get("teaAvatar");
            teacherAccount = (String) mParams.get("teacherAccount");
            setToolbar(toolBar, "答疑结束");
            //隐藏掉投诉
            toolBar.inflateMenu(R.menu.menu_oto_end);
            if (askTimes == 0) {
                mPresenter.orderfirstorder(this, orderId, new ApiRequestListener<String>() {
                    @Override
                    public void onResultSuccess(String res) {
                        initUserInfo(res);
                    }

                    @Override
                    public void onResultFail() {
                        balance.setText(getUserConstant().getUserEntity().getIntegral() + "");
                    }
                });
            } else {
                mPresenter.ordercheckbill(this, orderId, new ApiRequestListener<String>() {
                    @Override
                    public void onResultSuccess(String res) {
                        initUserInfo(res);
                    }

                    @Override
                    public void onResultFail() {
                        balance.setText(getUserConstant().getUserEntity().getIntegral() + "");
                    }
                });
                iv_firstorder_flag.setVisibility(View.GONE);
            }


        }
    }


    /**
     * 是否收藏该老师
     *
     * @return
     */
    private boolean isFavorTeacher(ArrayList<String> teachers, String teacherCode) {
        boolean has = false;
        for (int i = 0; i < teachers.size(); i++) {
            if (teacherCode.equals(teachers.get(i))) {
                has = true;
                break;
            }

        }
        return has;
    }

    @Override
    public void initView() {
        super.initView();

        materialDialog = getLoadingDialog().build();
        materialDialog.setContent("加载中...");
    }

    @Override
    public void initData() {
        super.initData();
        HashMap<String, Object> mOtoRecordParams = ParamHelper.acceptParams(MineOtoRecordActivity.class.getName());//答疑记录页面传递过来
        if (mOtoRecordParams != null && mOtoRecordParams.size() > 0) {
            state = (int) mOtoRecordParams.get("state");
            if (state == 11) {//已投诉
                setToolbar(toolBar, "投诉详情");
            } else {
                setToolbar(toolBar, "评价详情");
            }
            orderId = (String) mOtoRecordParams.get("orderId");
            askTime = (String) mOtoRecordParams.get("askTimes");
            teaName = (String) mOtoRecordParams.get("teaName");
            teaAvatar = (String) mOtoRecordParams.get("teaAvatar");
            String toStudent = (String) mOtoRecordParams.get("toStudent");

            if (!TextUtils.isEmpty(askTime)) {
                if (Integer.parseInt(askTime) == 0)//首单免费
                {
                    iv_firstorder_flag.setVisibility(View.VISIBLE);
                } else {
                    iv_firstorder_flag.setVisibility(View.GONE);
                }
            } else {
                iv_firstorder_flag.setVisibility(View.GONE);
            }


            if (!TextUtils.isEmpty(toStudent)) {
                llToStudent.setVisibility(View.VISIBLE);
                tvTeacherGuide.setText(toStudent);
            }
            if (state == 4)//待评价
            {
                llBottom.setVisibility(View.VISIBLE);
                reply.setText("提交评价");
                //隐藏掉投诉
                toolBar.inflateMenu(R.menu.menu_oto_end);
            } else if (state == 5)//已评价
            {
                radioGroup1.setVisibility(View.GONE);
                et_content.setVisibility(View.GONE);
                tvSuggest.setVisibility(View.VISIBLE);
                llBottom.setVisibility(View.GONE);
                String suggest = (String) mOtoRecordParams.get("suggest");
                tvSuggest.setText(suggest);
                String star = (String) mOtoRecordParams.get("star");
                if (!TextUtils.isEmpty(star)) {
                    Integer starDouble = Integer.valueOf(star);
                    if (starDouble instanceof Integer)
                        // 设置星星数
                        if (starDouble > 0) {
                            startView.setmStarNum(starDouble);
                        } else {
                            startView.setmStarNum(0);
                        }
                }
                tvPriceNum.setVisibility(View.VISIBLE);
                int reward = (int) mOtoRecordParams.get("reward");
                tvPriceNum.setText(reward + "枚");

            } else if (state == 11) {//已投诉
                llStar.setVisibility(View.GONE);
                ll_edt_content.setVisibility(View.GONE);
                ll_thanks.setVisibility(View.GONE);
                radioGroup1.setVisibility(View.GONE);
                llBottom.setVisibility(View.GONE);
                ll_complain.setVisibility(View.VISIBLE);
                String reason = (String) mOtoRecordParams.get("reason");
                if (!TextUtils.isEmpty(reason)) {
                    tv_complain_reason.setText(reason);
                }

                String details = (String) mOtoRecordParams.get("details");
                if (!TextUtils.isEmpty(details)) {
                    tv_complain_detail.setText(details);
                }

            }
            teacherAccount = (String) mOtoRecordParams.get("teacherAccount");
            teaAccount = (String) mOtoRecordParams.get("teacherAccount");
            String billPrice = (String) mOtoRecordParams.get("billPrice");
            if (!TextUtils.isEmpty(billPrice)) {
                money.setText(billPrice);
            }
            String teacherCode = (String) mOtoRecordParams.get("teacherCode");
            if (!TextUtils.isEmpty(teacherCode)) {
                tea_code.setText(teacherCode);
            }
            Double integral = (Double) mOtoRecordParams.get("integral");
            balance.setText(integral + "");
            Integer holdingSeconds = (Integer) mOtoRecordParams.get("holdingSeconds");
            time.setText(formatTime(holdingSeconds));

        }

        requestFavorState();


    }

    private String formatTime(int time) {
        int hour = time / 3600;
        int minute = (time - hour * 3600) / 60;
        int second = time - hour * 300 - minute * 60;
        return String.format("%02d:%02d", minute, second);
    }


    /**
     * 请求显示收藏状态
     */

    private void requestFavorState() {
        String userName = getUserConstant().getUserName();
        mPresenter.studentinfo(this, userName, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String resStr) {
                UserInfo userInfo = JSON.parseObject(resStr, UserInfo.class);
                if (userInfo != null) {
                    if (userInfo.favorTeachers != null && userInfo.favorTeachers.size() > 0) {
                        if (isFavorTeacher(userInfo.favorTeachers, teacherAccount)) {//是否收藏该老师
                            cb_favor.setSelected(true);
                            if (state == 5) {
                                cb_favor.setEnabled(false);
                            }
                        } else {
                            cb_favor.setSelected(false);
                            if (state == 5) {
                                cb_favor.setEnabled(false);
                            }
                        }

                    } else {//没有收藏老师
                        cb_favor.setSelected(false);
                        if (state == 5) {
                            cb_favor.setEnabled(false);
                        }
                    }
                }
            }

            @Override
            public void onResultFail() {
            }
        });
    }

    private void initUserInfo(String res) {
        try {
            OtoRecord e = JSON.parseObject(res, OtoRecord.class);
            if (e.bill != null) {
                money.setText(e.bill.price);
            }
            if (e.time != null) {
                time.setText(DateUtil.getTimeExpend(e.time.startTime, e.time.endTime));
            }
            if (e.teacher != null) {
                teaAccount = e.teacher.account;
                tea_code.setText(e.teacher.code);
            }
            if (e.student != null) {
                balance.setText(e.student.integral + "");

                getUserConstant().getUserEntity().setIntegral(e.student.integral);
                getUserConstant().saveUserData(new Gson().toJson(getUserConstant().getUserEntity()));
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_follow://跳转到投诉界面
                        Bundle parameter = new Bundle();
                        parameter.putString("orderId", orderId);
                        parameter.putString("price", money.getText().toString());
                        parameter.putString("integral", balance.getText().toString());
                        parameter.putString("connectTime", time.getText().toString());
                        parameter.putString("teaName", teaName);
                        if (!TextUtils.isEmpty(teaAvatar)) {
                            parameter.putString("teaAvatar", teaAvatar);
                        }
                        parameter.putBoolean("favor", cb_favor.isSelected());
                        openActivity(FeedBackActivity.class, parameter);
                        break;
                }
                return true;
            }
        });
        rbtn1.setOnClickListener(clickListener);
        rbtn2.setOnClickListener(clickListener);
        rbtn3.setOnClickListener(clickListener);
        rbtn4.setOnClickListener(clickListener);
        rbtn5.setOnClickListener(clickListener);
        radioGroup1.clearCheck();
        askCoin = 0;
        startView.setmStarItemClickListener(new StarView.OnStarItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                score = pos + 1;
            }
        });

        teacherName.setText(teaName);
        if (!TextUtils.isEmpty(teaAvatar)) {
            BitmapUtil.displayUserImage(this, teaAvatar, userImgIv);
        }

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton r = (RadioButton) v;
            int tmpMoney = Integer.parseInt(r.getText().toString());
            if (askCoin == tmpMoney) {
                radioGroup1.clearCheck();
                askCoin = 0;
            } else {
                r.setChecked(true);
                askCoin = tmpMoney;
            }
        }
    };

    @OnClick({R.id.cb_favor, R.id.reply})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_favor://点赞
                if (!cb_favor.isSelected()) {//未选中状态
                    studentfavor();
                } else {//选中状态
                    studentUnfavor();
                }
                break;
            case R.id.reply://提交
                materialDialog.show();
                mPresenter.orderevaluate(this, orderId, askCoin + "", score + "", et_content.getText().toString(), new ApiRequestListener<String>() {
                    @Override
                    public void onResultSuccess(String res) {
                        CommonUtil.Toast(OtoEndActivity.this, "评价成功");
                        if (askCoin > 0) {
                            getUserConstant().getUserEntity().setIntegral(getUserConstant().getUserEntity().getIntegral() - askCoin);
                            getUserConstant().saveUserData(new Gson().toJson(getUserConstant().getUserEntity()));
                        }
                        EventBus.getDefault().post(new AppEventType(AppEventType.SUBMIT_SUCCESS));
                        finish();
                    }

                    @Override
                    public void onResultFail() {
                        materialDialog.dismiss();
                    }
                });
                break;
        }
    }

    /**
     * 收藏老师
     */
    private void studentfavor() {
        String userName = getUserConstant().getUserName();
        mPresenter.studentfavor(this, userName, teaAccount, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                cb_favor.setSelected(true);
            }
        });
    }

    /**
     * 取消收藏老师
     */
    private void studentUnfavor() {
        String userName = getUserConstant().getUserName();
        mPresenter.studentUnFavor(this, userName, teaAccount, new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String res) {
                cb_favor.setSelected(false);
            }
        });
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

    @Override
    public void onSuccess(String methodName, ResponseBody baseRsqEntity) {
    }

    @Override
    public void onSuccess(String methodName, String string) {
    }

    @Override
    protected void onDestroy() {
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }
}
