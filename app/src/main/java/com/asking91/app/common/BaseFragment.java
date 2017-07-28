package com.asking91.app.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asking91.app.R;
import com.asking91.app.entity.user.UserEntity;
import com.asking91.app.global.UserConstant;
import com.asking91.app.mvpframe.rx.RxBus;

import rx.Subscription;
import rx.functions.Action1;


/**
 * Created by Jun on 2016/5/2.
 */
public class BaseFragment extends Fragment implements BaseFuncIml {
    protected  BaseFragment mthis;

    private View mContentView;

    private ViewGroup container;

    private UserConstant mUserConstant;

    private MaterialDialog.Builder mLoadingDialog;

    private Subscription mSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mthis = this;
        initData();
        initView();
        initListener();
        initLoad();
        this.container = container;
        return mContentView;
    }

    public void setContentView(int viewId) {
        this.mContentView = getActivity().getLayoutInflater().inflate(viewId, container, false);
    }

    public View getContentView() {
        return mContentView;
    }

    protected void showShortToast(String pMsg) {
        Toast.makeText(getActivity(), pMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initData() {
        mUserConstant = UserConstant.getInstance(getContext());
    }

    @Override
    public void initView() {
        initLoadingDialog();
    }

    @Override
    public void initListener() {
        mSubscription = RxBus.$().register(UserEntity.class, new Action1<UserEntity>() {
            @Override
            public void call(UserEntity userEntity) {
                try {
                    initLoad();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void initLoad() {

    }

    protected void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }

    protected void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(getActivity(), toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.enter_left_in,R.anim.enter_left_out);
    }

    public UserConstant getUserConstant() {
        return mUserConstant;
    }

    private void initLoadingDialog() {
        mLoadingDialog = new MaterialDialog.Builder(getActivity());
        mLoadingDialog.title("请稍候");
        mLoadingDialog.progress(true, 0);
    }

    public MaterialDialog.Builder getLoadingDialog() {
        return mLoadingDialog;
    }

}
