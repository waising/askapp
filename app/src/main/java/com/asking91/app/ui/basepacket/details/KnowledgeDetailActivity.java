package com.asking91.app.ui.basepacket.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SwipeBackActivity;
import com.asking91.app.ui.pay.PayServerActivity;
import com.asking91.app.util.CommonUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

/**
 * Created by wxwang on 2016/11/4.
 */
public class KnowledgeDetailActivity extends SwipeBackActivity implements Toolbar.OnMenuItemClickListener, KnowledgeDetailFragment.CallBackValue{

    @BindView(R.id.toolBar)
    Toolbar mToolBar;

    @BindView(R.id.knowledge_detail_viewpager)
    ViewPager viewPager;

    @BindView(R.id.knowledgeTv)
    TextView knowledgeTv;

    @BindView(R.id.knowledge_type_btn)
    Button typeBtn;

    @BindView(R.id.knowledge_detail_btn)
    Button detailBtn;

    @BindView(R.id.collection_iv)
    ImageView mCollectionIv;

    String me ;

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    KnowledgeDetailFragment knowledgeDetailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_detail);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buy:
                CommonUtil.openAuthActivity(this,PayServerActivity.class);
                break;
        }
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolBar,getIntent().getStringExtra("title"));

        Bundle bundle = getIntent().getExtras();

        knowledgeDetailFragment = new KnowledgeDetailFragment();
        knowledgeDetailFragment.setArguments(bundle);
        mFragmentList.add(knowledgeDetailFragment);

        if( !TextUtils.isEmpty(me) && me.equals("searchDetail") ){
            typeBtn.setVisibility(View.GONE);
            detailBtn.setVisibility(View.GONE);
            mCollectionIv.setVisibility(View.GONE);
        }else{
            mToolBar.inflateMenu(R.menu.menu_go_buy);
            KnowledgeTypeFragment knowledgeTypeFragment = new KnowledgeTypeFragment();
            knowledgeTypeFragment.setArguments(bundle);
            mFragmentList.add(knowledgeTypeFragment);
        }
        knowledgeTv.setText(getIntent().getStringExtra("knowledge"));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                toFragment(mFragmentList.get(position));
            }

            @Override
            public void onPageSelected(int position) {
//                toFragment(mFragmentList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    //空闲状态  pager处于空闲状态
                    setDetailBtnBg(viewPager.getCurrentItem());
                    setTypeBtnBg(viewPager.getCurrentItem());
                }
            }
        });

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });

        //取消全屏滑动
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.ORIGINAL);
    }

    @Override
    public void initListener() {
        super.initListener();
        mToolBar.setOnMenuItemClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        me = getIntent().getStringExtra("me");
    }


    @OnClick({R.id.knowledge_detail_btn,R.id.knowledge_type_btn})
    public void btnOnClick(View view){
        switch (view.getId()){
            case R.id.knowledge_type_btn:
                viewPager.setCurrentItem(1,false);
                break;
            case R.id.knowledge_detail_btn:
                viewPager.setCurrentItem(0,false);
                break;
        }

        setDetailBtnBg(viewPager.getCurrentItem());
        setTypeBtnBg(viewPager.getCurrentItem());
    }


    /**
     * 设置背景色
     * @param
     */
    private void setDetailBtnBg(int index){
        detailBtn.setBackgroundResource(index==0 ? R.mipmap.next_btn_bg:R.mipmap.btn_bg);
        detailBtn.setTextColor(index==0 ? Color.WHITE : ContextCompat.getColor(this,R.color.colorText_c7));
    }

    private void setTypeBtnBg(int index){
        typeBtn.setBackgroundResource(index==1 ? R.mipmap.next_btn_bg:R.mipmap.btn_bg);
        typeBtn.setTextColor(index ==1 ? Color.WHITE : ContextCompat.getColor(this,R.color.colorText_c7));
    }

    boolean isCollected = false;
    @Override
    public void SendChangeCollImg(String msg,boolean isCollected) {
        this.isCollected = isCollected;
        mCollectionIv.setImageResource(isCollected ? R.mipmap.love_1 : R.mipmap.love);
        showShortToast(msg);
    }

    @OnClick(R.id.collection_iv)
    public void collectionOnclick(){
        if(!isCollected) {
            knowledgeDetailFragment.collectionSave();
        }else{
            knowledgeDetailFragment.collectionDelete();
        }
    }
}
