package com.asking91.app.ui.mine;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.BaseActivity;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.entity.user.UserEntity;
import com.asking91.app.global.MineConstant;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.mvpframe.rx.RxBus;
import com.asking91.app.ui.adapter.mine.MineAdapter;
import com.asking91.app.ui.coupon.CouponListActivity;
import com.asking91.app.ui.login.LoginActivity;
import com.asking91.app.ui.mine.more.MineOtoRecordActivity;
import com.asking91.app.ui.mine.more.ShareAwardActivity;
import com.asking91.app.ui.mine.more.SignInActivity;
import com.asking91.app.ui.mine.myfavor.MyFavorActivity;
import com.asking91.app.ui.mine.mymoneyrecord.AskMoneyRecordActivity;
import com.asking91.app.ui.mine.mynote.MyNoteActivity;
import com.asking91.app.ui.mine.mywrongtopic.MyWrongTopicActivity;
import com.asking91.app.ui.onlineqa.OnlineQAActivity;
import com.asking91.app.ui.pay.PayAskActivity;
import com.asking91.app.ui.refer.ReferActivity;
import com.asking91.app.ui.search.knowledge.SearchKnowledgeActivity;
import com.asking91.app.ui.set.SetActivity;
import com.asking91.app.ui.set.SetPersonModifyActivity;
import com.asking91.app.ui.widget.NumberAnimTextView;
import com.asking91.app.ui.widget.RecycleViewDivider;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 我的
 * Created by wxiao on 2016/10/28.
 */

public class MineFragment extends BaseFrameFragment<MinePresenter, MineModel>
        implements MineContract.View {

    @BindView(R.id.user_img_iv)
    ImageView askSimpleDraweeView;

    @BindView(R.id.mine_midde_rv)
    RecyclerView mMineMenuRv1;
//
//    @BindView(R.id.mine_bottom_rv)
//    RecyclerView mMineMenuRv2;

    @BindView(R.id.btn_login)
    Button mloginBtn;
    @BindView(R.id.set_tv)
    TextView mSetTv;
    @BindView(R.id.mine_name_tv)
    TextView mMineNameTv;
    @BindView(R.id.mine_vip_iv)
    ImageView mMineVipIv;
    @BindView(R.id.mine_ask_money_tv)
    NumberAnimTextView mMineAskMoneyTv;
    @BindView(R.id.layout_ask_money)
    LinearLayout mAskMoney;

    List<LabelEntity> mDatas1;
    //List<LabelEntity> mDatas2;
    MineAdapter mineAdapter1;
    //MineAdapter mineAdapter2;

    Subscription mLoginSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine);
        ButterKnife.bind(this, getContentView());
        EventBus.getDefault().register(this);
    }

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
//            case AppEventType.REF_USER_DATA:
//                showUserInfo();
//                break;
            case AppEventType.ASK_COIN_SUCCESS://阿思币购买成功后
                if (getUserConstant().getUserEntity() != null) {
                    setAskMoney();
                }
                break;


        }
    }

    @Override
    public void initData() {
        super.initData();
        mDatas1 = new ArrayList<>();
        //mDatas2 = new ArrayList<>();
        setDatas1();
        //setDatas2();
        mineAdapter1 = new MineAdapter(getContext(), mDatas1);
        //mineAdapter2 = new MineAdapter(getContext(), mDatas2);
        setMap();
    }

    private void showUserInfo() {
        if (getUserConstant().getUserEntity() != null) {
            if (!TextUtils.isEmpty(getUserConstant().getUserEntity().getNickName())) {
                mMineNameTv.setText(getUserConstant().getUserEntity().getNickName());
            } else {
                mMineNameTv.setText(getUserConstant().getUserEntity().getUserName());
            }
            setAskMoney();
            showUserImg();
            showUI(true);
        }
    }

    private void showUI(boolean isLogin) {
        if (isLogin) {
            mloginBtn.setVisibility(View.GONE);
            mAskMoney.setVisibility(View.VISIBLE);
            mMineVipIv.setVisibility(View.VISIBLE);
        } else {
            mloginBtn.setVisibility(View.VISIBLE);
            mAskMoney.setVisibility(View.GONE);
            mMineVipIv.setVisibility(View.GONE);
        }
    }

    private void setAskMoney() {
        mMineAskMoneyTv.setText(String.valueOf(getUserConstant().getUserEntity().getIntegral()));
    }

    @Override
    public void initView() {
        super.initView();

        mMineMenuRv1.setAdapter(mineAdapter1);
        //mMineMenuRv2.setAdapter(mineAdapter2);

        //不格式化为字符串形式
        mMineAskMoneyTv.setFormatNumStr(false);
        mMineAskMoneyTv.setDuration(2000);

        mMineMenuRv1.setHasFixedSize(true);
        setRv(mMineMenuRv1);
        //setRv(mMineMenuRv2);

        mMineMenuRv1.setNestedScrollingEnabled(false);

    }

    private void setRv(RecyclerView rv) {
        LinearLayoutManager mgr = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mgr);
        //设置分割线
        rv.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getContext(), R.color.color_da)));
    }

    @Override
    public void initLoad() {
        super.initLoad();
        //如果已经登录 并且用户信息为空 获取用户信息
        if (getUserConstant().isTokenLogin() && getUserConstant().getUserEntity() == null)
            mPresenter.getAppUser();

    }

    @Override
    public void initListener() {
        mLoginSubscription = RxBus.$().register(UserEntity.class, new Action1<UserEntity>() {
            @Override
            public void call(UserEntity userEntity) {
                try {
                    mMineNameTv.setText(userEntity.getNickName());
                    mMineAskMoneyTv.setText(String.valueOf(userEntity.getIntegral()));
                    showUserImg();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        askSimpleDraweeView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getUserConstant().isTokenLogin()) {
                    openActivity(SetPersonModifyActivity.class);
                } else {
                    openActivity(LoginActivity.class);
                }
                return false;
            }
        });

        mMineNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getUserConstant().isTokenLogin()) {
                    openActivity(LoginActivity.class);
                }
            }
        });
    }

    @OnClick({R.id.set_tv, R.id.layout_ask_money, R.id.btn_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_tv:
                openActivity(SetActivity.class);
                break;
            case R.id.layout_ask_money:
                CommonUtil.openAuthActivity(getActivity(), AskMoneyRecordActivity.class);
                break;
            case R.id.btn_login:
                openActivity(LoginActivity.class);
                break;
        }
    }


    @Override
    public void onSuccess(int type, ResponseBody body) {
        switch (type) {
            case MineConstant.Mine.userInfo:
                getAppUser(body);
                break;
            case MineConstant.Mine.avatar:
                break;
//            case MineConstant.Mine.sign://签到
//          //      sign(body);
//                break;
        }
    }

//    private void sign(ResponseBody body) {
//        Map<String, Object> map = CommonUtil.parseDataToMap(body);
//        if (map.get("flag") != null) {
//            if ("0".equals(map.get("flag"))) {
//                showShortToast(map.get("msg").toString());
//            } else if ("1".equals(map.get("flag"))) {
//                setMoney(0.5);
//                showShortToast(map.get("msg").toString());
//            } else if ("2".equals(map.get("flag"))) {
//                setMoney(3.5);
//                showShortToast(map.get("msg").toString());
//            }
//        }
//
//    }

    /**
     * 签到后增加金币
     * //     * @param money
     */
//    private void setMoney(double money) {
//        mMineAskMoneyTv.setNumberString(mMineAskMoneyTv.getText().toString(), String.valueOf(Float.valueOf(mMineAskMoneyTv.getText().toString()) + money));
//
//        getUserConstant().getUserEntity().setIntegral(money + getUserConstant().getUserEntity().getIntegral());
//        getUserConstant().saveUserData(new Gson().toJson(getUserConstant().getUserEntity()));
//    }
    private void getAppUser(ResponseBody body) {
        Map<String, Object> map = CommonUtil.parseDataToMap(body);
        if (map.get("flag") != null && !TextUtils.isEmpty((CharSequence) map.get("flag"))) {
            getUserConstant().setUserEntity(CommonUtil.data2Clazz(map.get("user"), UserEntity.class));

            mMineNameTv.setText(getUserConstant().getUserEntity().getNickName());
            mMineAskMoneyTv.setText(String.valueOf(getUserConstant().getUserEntity().getIntegral()));
            showUserImg();
        }
    }


    @Override
    public void onError(int type) {

    }

    @Override
    public void onRefreshData(ResponseBody body) {

    }

    @Override
    public void onLoadMoreData(ResponseBody body) {

    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    Map<Integer, Class<? extends BaseActivity>> map = new ArrayMap<>();

    public void setMap() {
        map.put(R.id.qa_ly, OnlineQAActivity.class);
        map.put(R.id.search_lv, SearchKnowledgeActivity.class);
        //教育
        map.put(R.id.refer_ly, ReferActivity.class);
        map.put(R.id.menu_item_liwu, ShareAwardActivity.class);
        map.put(R.id.menu_item_sign, SignInActivity.class);
        map.put(R.id.menu_item_youhui, CouponListActivity.class);
        map.put(R.id.menu_item_pay, PayAskActivity.class);
    }

    @OnClick({R.id.qa_ly, R.id.search_lv, R.id.refer_ly, R.id.menu_item_liwu, R.id.menu_item_sign, R.id.menu_item_youhui, R.id.menu_item_pay})
    public void OnClick(View v) {
        CommonUtil.openAuthActivity(getActivity(), map.get(v.getId()));
    }

    private void setDatas1() {
        mDatas1.add(new LabelEntity(R.mipmap.mine_error_note, getString(R.string.err_note), MyWrongTopicActivity.class));
        mDatas1.add(new LabelEntity(R.mipmap.mine_note, getString(R.string.my_note), MyNoteActivity.class));
        // mDatas1.add(new LabelEntity(R.mipmap.mine_stu_record, getString(R.string.stu_record), MineStuRecordActivity.class));
        mDatas1.add(new LabelEntity(R.mipmap.mine_oto_record, getString(R.string.myOtoRecord), MineOtoRecordActivity.class));
        mDatas1.add(new LabelEntity(R.mipmap.mine_collect, getString(R.string.my_collect), MyFavorActivity.class));
    }

    private void setDatas2() {
        //mDatas2.add(new LabelEntity(R.mipmap.mine_pay, getString(R.string.pay), PayAskActivity.class));
        //mDatas2.add(new LabelEntity(R.mipmap.mine_shopping_record, getString(R.string.shopping_record), MyBuyRecordsActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoginSubscription != null)
            mLoginSubscription.unsubscribe();

        EventBus.getDefault().unregister(this);
    }

    private void showUserImg() {
        //String path = Constants.USER_AVATAR + getUserConstant().getUserEntity().getId();
        if (getUserConstant().getUserEntity().getAvatar() != null)
            BitmapUtil.displayUserImage(getActivity(), getUserConstant().getUserEntity().getAvatar(), askSimpleDraweeView);
    }

    public void clearData() {
        mMineNameTv.setText("您还未登录");
        mMineAskMoneyTv.setText("0");
        askSimpleDraweeView.setImageResource(R.mipmap.default_user_img);
        showUI(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserConstant().isTokenLogin()) {
            showUserInfo();
        } else if (!getUserConstant().isTokenLogin()) {
            clearData();
        }
    }

}
