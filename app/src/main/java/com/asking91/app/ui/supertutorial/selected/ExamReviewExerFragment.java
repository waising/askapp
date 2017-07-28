package com.asking91.app.ui.supertutorial.selected;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.supertutorial.ExerAskEntity;
import com.asking91.app.mvpframe.base.BaseFrameFragment;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.supertutorial.selected.ExamReviewExerAskFragment.OnExerFragmentListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 实战演练
 * Created by jswang on 2017/3/2.
 */
public class ExamReviewExerFragment extends BaseFrameFragment<SuperSelectPresenterImpl, SuperSelectModelImpl> {
    String pid;
    ViewPager view_pager;
    ExamAdapter mAdapter;

    TextView tv_index;
    TextView tv_size;

    private List<ExerAskEntity> dataList = new ArrayList<>();

    public static ExamReviewExerFragment newInstance(String pid) {
        ExamReviewExerFragment fragment = new ExamReviewExerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("pid", pid);
        fragment.setArguments(bundle);
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            pid = bundle.getString("pid");
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_exam_review_tree, null);
        view_pager = (ViewPager)rootView.findViewById(R.id.view_pager);
        mAdapter = new ExamAdapter(getChildFragmentManager());
        view_pager.setAdapter(mAdapter);

        tv_index  = (TextView)rootView.findViewById(R.id.tv_index);
        tv_size  = (TextView)rootView.findViewById(R.id.tv_size);

        if(!TextUtils.isEmpty(pid)){
            initLoadData(pid);
        }

        view_pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){//viewPager切换页
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tv_index.setText((position+1)+"");//切到第几页
            }
        });

        return rootView;
    }

    /**
     * 请求页面数据
     * @param pid
     */
    public void initLoadData(String pid) {
        this.pid = pid;
        if(getActivity()!=null && view_pager!=null){
            mPresenter.firstreviewshizyl(getActivity(),pid,new ApiRequestListener<String>(){
                @Override
                public void onResultSuccess(String res) {
                    if(dataList == null){
                        dataList = new ArrayList<>();
                    }
                    dataList.clear();
                    dataList.addAll(JSON.parseArray(res,ExerAskEntity.class));
                    mAdapter.notifyDataSetChanged();

                    tv_index.setText(1+"");//切到第几页
                    tv_size.setText("/"+dataList.size());//总页数
                    view_pager.post(new Runnable() {
                        @Override
                        public void run() {
                            view_pager.setCurrentItem(0);//默认第一页
                        }
                    });
                }
            });
        }
    }

    /**
     * viewPager的fragment的adpater
     */
    class ExamAdapter extends FragmentStatePagerAdapter  {

        public ExamAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = ExamReviewExerAskFragment.newInstance(position+1,dataList.get(position),getOnExerFragmentListener(),dataList.size());
            return f;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    private OnExerFragmentListener getOnExerFragmentListener(){
        return  new OnExerFragmentListener(){
            @Override
            public void sendNextFragmentMessage(String id, String answer) {

            }

            @Override
            public void sendIndex(int index) {

            }
        };
    }

    public void onRequestStart() {

    }

    public void onRequestError(RequestEntity requestEntity) {

    }

    public void onRequestEnd() {

    }
}
