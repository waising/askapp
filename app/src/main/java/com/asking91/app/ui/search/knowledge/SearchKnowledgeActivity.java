package com.asking91.app.ui.search.knowledge;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asking91.app.R;
import com.asking91.app.entity.RequestEntity;
import com.asking91.app.entity.search.KnowledgeEntity;
import com.asking91.app.global.Constants;
import com.asking91.app.global.OnlineQAConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.adapter.search.SearchKnowledgeAdapter;
import com.asking91.app.ui.search.subject.SubjectDetailContract;
import com.asking91.app.ui.search.subject.SubjectDetailModel;
import com.asking91.app.ui.search.subject.SubjectDetailPresenter;
import com.asking91.app.ui.widget.RecycleViewDivider;
import com.asking91.app.ui.widget.tagview.TagContainerLayout;
import com.asking91.app.ui.widget.tagview.TagView;
import com.asking91.app.util.CommonUtil;
import com.google.gson.reflect.TypeToken;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class SearchKnowledgeActivity extends BaseFrameActivity<SubjectDetailPresenter, SubjectDetailModel> implements SubjectDetailContract.View {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;

    int width, num;
    String subjectCatalog = Constants.SubjectCatalog.CZSX; // 初中数学 M2
    private List<String> mDataList;
    private SearchKnowledgeAdapter adapter;
    private RecycleViewDivider recycleViewDivider;
    private TagContainerLayout mTagContainerLayout1;
    private List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_search_knowledge);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        mDataList = Arrays.asList(OnlineQAConstant.searchKnowledge);
        list = new ArrayList<String>();
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.app_name));
        initmagicIndicator();
        mTagContainerLayout1 = (TagContainerLayout) findViewById(R.id.tagcontainerLayout1);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recycleViewDivider = new RecycleViewDivider(this, RecyclerView.HORIZONTAL, 3, ContextCompat.getColor(this,R.color.colorPrimary));
//        mRecyclerView.addItemDecoration(recycleViewDivider);
    }

    @Override
    public void onRequestError(RequestEntity requestEntity) {
        super.onRequestError(requestEntity);
        showShortToast(R.string.no_result_search);
    }

    @Override
    public void initListener() {
        super.initListener();
        mTagContainerLayout1.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                mPresenter.presenterSearchKnowledge(111, subjectCatalog, text); // 根据Tag标签的内容搜知识点
            }

            @Override
            public void onTagLongClick(final int position, String text) {
            }

            @Override
            public void onTagCrossClick(int position) {
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mEditText.getText().toString().trim().length() > 0) {
                    mPresenter.presenterSearchKnowledge(111, subjectCatalog, mEditText.getText().toString().trim()); // 实现输入即搜索
                    imgSearch.setImageResource(R.mipmap.ic_search_knowledge_blue);
                } else {
                    imgSearch.setImageResource(R.mipmap.ic_search_knowledge);
                    adapter = null;
                    linearLayout.setVisibility(View.GONE);
                    mTagContainerLayout1.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();
        mPresenter.presenterSearchKnowledge(222, subjectCatalog, "");
    }

    private void initmagicIndicator() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getWindow().getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        width /= 3;
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setWidth(width);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(getApplication(), R.color.colorText_c7));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(mthis, R.color.colorAccent));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEditText.setText("");
                        if (mDataList.get(index).equals(getString(R.string.ask_czsx))) {
                            subjectCatalog = Constants.SubjectCatalog.CZSX; // 初中数学 M2
                        } else if (mDataList.get(index).equals(getString(R.string.ask_gzsx))) {
                            subjectCatalog = Constants.SubjectCatalog.GZSX; // 高中数学 M3
                        } else if (mDataList.get(index).equals(getString(R.string.ask_gzwl))) {
                            subjectCatalog = Constants.SubjectCatalog.GZWL; // 高中物理 P3
                        }
                        magicIndicator.onPageSelected(index);
                        magicIndicator.onPageScrollStateChanged(index);
                        magicIndicator.onPageScrolled(index, 0, 0);
                        mPresenter.presenterSearchKnowledge(222, subjectCatalog, "");
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(ContextCompat.getColor(mthis, R.color.colorAccent));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
    }

    @Override
    public void onRequestSuccess(int type, ResponseBody responseBody) {
        switch (type) {
            case 111:
                // 搜索知识点
                List<KnowledgeEntity> knowledgeEntities = CommonUtil.parseDataToList(responseBody, new TypeToken<List<KnowledgeEntity>>() {
                });
                if (knowledgeEntities != null && knowledgeEntities.size() > 0) {
                    adapter = new SearchKnowledgeAdapter(mthis, knowledgeEntities, subjectCatalog);
                    mRecyclerView.setAdapter(adapter);
                    mTagContainerLayout1.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                } else {
                    showShortToast(R.string.retry_other_key_word);
                }
                break;
            case 222:
                // 热词搜索结果
                List<KnowledgeEntity> hotEntities = CommonUtil.parseDataToList(responseBody, new TypeToken<List<KnowledgeEntity>>() {
                });
                if (hotEntities != null && hotEntities.size() > 0) {
                    linearLayout.setVisibility(View.GONE);
                    list.clear();
                    if (hotEntities.size() > 10) {
                        num = 10;
                    } else {
                        num = hotEntities.size();
                    }
                    for (int i = 0; i < num; i++) {
                        list.add(hotEntities.get(i).getTipName());
                    }
                    mTagContainerLayout1.removeAllTags();
                    mTagContainerLayout1.setTags(list);
                    mTagContainerLayout1.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @OnClick({R.id.imgSearch})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSearch:
                if (mEditText.getText().toString().trim().length() > 0) {
                    mPresenter.presenterSearchKnowledge(111, subjectCatalog, mEditText.getText().toString().trim()); // 根据输入的文字搜知识点
                } else {
                    showShortToast(R.string.please_enter_keyword);
                }
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
    public void onSuccess(String methodName, String string) {
    }

    @Override
    public void onInternetError(String methodName) {

    }

    @Override
    public void onError(int type) {

    }
}
