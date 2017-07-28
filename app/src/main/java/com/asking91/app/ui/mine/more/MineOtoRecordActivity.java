package com.asking91.app.ui.mine.more;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asking91.app.R;
import com.asking91.app.entity.oto.OtoRecord;
import com.asking91.app.entity.oto.SubjectImage;
import com.asking91.app.global.OtoConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.mine.MineContract;
import com.asking91.app.ui.mine.MineModel;
import com.asking91.app.ui.mine.MinePresenter;
import com.asking91.app.ui.oto.OtoEndActivity;
import com.asking91.app.ui.oto.OtoRecordDetailActivity;
import com.asking91.app.ui.supertutorial.selected.ApiRequestListener;
import com.asking91.app.ui.widget.AskSwipeRefreshLayout;
import com.asking91.app.ui.widget.camera.utils.AppEventType;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;
import com.asking91.app.ui.widget.camera.utils.ParamHelper;
import com.asking91.app.util.DateUtil;
import com.asking91.app.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.ResponseBody;

/**
 * 答疑记录
 * Created by jswang on 2017/3/24.
 */

public class MineOtoRecordActivity extends BaseFrameActivity<MinePresenter, MineModel> implements MineContract.View {
    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.swipe_layout)
    AskSwipeRefreshLayout swipeLayout;

    private ArrayList<OtoRecord> dataList = new ArrayList<>();
    CommAdapter mAdapter;

    int start = 0, limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_oto_record);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.myOtoRecord));

        recycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommAdapter(this);
        recycler.setAdapter(mAdapter);

        swipeLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout ptrFrameLayout) {
                start += limit;
                loadData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                start = 0;
                dataList.clear();
                swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
                loadData();
            }
        });
        loadData();
    }

    private void loadData() {
        String userName = getUserConstant().getUserName();
        mPresenter.orderhistory(this, start + "", limit + "", userName, "s", new ApiRequestListener<String>() {
            @Override
            public void onResultSuccess(String resStr) {
                JSONObject resobj = JSON.parseObject(resStr);
                String orders = resobj.getString("Orders");
                dataList.addAll(JSON.parseArray(orders, OtoRecord.class));
                mAdapter.notifyDataSetChanged();
                swipeLayout.refreshComplete();
            }
        });
    }

    int[] resIconId = {R.mipmap.oto_record_type1, R.mipmap.oto_record_type2, R.mipmap.oto_record_type3};

    class CommViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_oto_bg)
        ImageView iv_oto_bg;
        @BindView(R.id.iv_oto_tip)
        ImageView iv_oto_tip;
        @BindView(R.id.tv_class)
        TextView tv_class;
        @BindView(R.id.tv_tea_name)
        TextView tv_tea_name;
        @BindView(R.id.tv_tea_code)
        TextView tv_tea_code;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public CommViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CommAdapter extends RecyclerView.Adapter<CommViewHolder> {
        private Context mContext;

        public CommAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public CommViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_oto_record_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(final CommViewHolder holder, final int position) {
            final OtoRecord e = dataList.get(position);
            switch (e.state) {
                case 4://待评价
                    holder.iv_oto_tip.setImageResource(resIconId[0]);
                    break;
                case 5://已评价
                    holder.iv_oto_tip.setImageResource(resIconId[1]);
                    break;
                case 11://已投诉
                    holder.iv_oto_tip.setImageResource(resIconId[2]);
                    break;

            }
            if (e.teacher != null) {
                holder.tv_tea_name.setText(e.teacher.name);
                holder.tv_tea_code.setText(e.teacher.code);
            }
            if (e.time != null) {
                holder.tv_time.setText(DateUtil.stampToDate(e.time.uploadTime));
            }
            if (e.subject != null) {
                String subjectClass;
                if (TextUtils.equals(e.subject.subject, OtoConstant.subjectValues[0])) {
                    subjectClass = getString(R.string.online_dialog1_t1);
                } else {
                    subjectClass = getString(R.string.online_dialog1_t2);
                }
                switch (e.subject.grade) {
                    case 7:
                        subjectClass = subjectClass + "-" + getString(R.string.online_dialog1_t3);
                        break;
                    case 8:
                        subjectClass = subjectClass + "-" + getString(R.string.online_dialog1_t4);
                        break;
                    case 9:
                        subjectClass = subjectClass + "-" + getString(R.string.online_dialog1_t5);
                        break;
                    case 10:
                        subjectClass = subjectClass + "-" + getString(R.string.online_dialog1_t6);
                        break;
                    case 11:
                        subjectClass = subjectClass + "-" + getString(R.string.online_dialog1_t7);
                        break;
                    case 12:
                        subjectClass = subjectClass + "-" + getString(R.string.online_dialog1_t8);
                        break;
                }
                holder.tv_class.setText(subjectClass);

                if (e.subject.images != null && e.subject.images.size() > 0) {
                    String imgUrl = e.subject.images.get(0).url;
                    if (!TextUtils.isEmpty(imgUrl)) {
                        BitmapUtil.displayImage(imgUrl, holder.iv_oto_bg, true);
                    }
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {//每项的点击事件
                    @Override
                    public void onClick(View v) {
                        if (e.videos != null && e.videos.size() > 0) {
                            Bundle bundle = new Bundle();
                            String videoUrl = e.videos.get(e.videos.size() - 1);
                            bundle.putString("videoUrl", videoUrl);
                            OtoRecord.OtoSubject otoSubject = e.subject;
                            if (otoSubject != null) {
                                if (otoSubject.images != null && otoSubject.images.size() > 0) {
                                    SubjectImage subjectImage = otoSubject.images.get(0);
                                    if (subjectImage != null) {
                                        bundle.putString("imageUrl", subjectImage.url);//
                                        openActivity(OtoRecordDetailActivity.class, bundle);
                                    }
                                }
                            }

                        } else {
                            ToastUtil.showMessage("没有视频资源");
                        }

                    }
                });

/**
 * 标签页
 */
                holder.iv_oto_tip.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {

                        HashMap<String, Object> mParams = ParamHelper.acquireParamsReceiver(MineOtoRecordActivity.class.getName());
                        mParams.put("orderId", e.id);
                        mParams.put("state", e.state);
                        if (e.teacher != null) {
                            mParams.put("teaName", e.teacher.name);
                            mParams.put("teaAvatar", e.teacher.avatar);
                            mParams.put("toStudent", e.teacher.toStudent);
                            mParams.put("teacherAccount", e.teacher.account);
                            mParams.put("teacherCode", e.teacher.code);
                        }
                        if (e.time != null) {
                            mParams.put("holdingSeconds", e.time.holdingSeconds);//答疑时间
                        }
                        if (e.evaluation != null) {
                            mParams.put("reward", e.evaluation.reward);////谢师币
                            mParams.put("star", e.evaluation.star);//评价星星数
                            mParams.put("suggest", e.evaluation.suggest);//建议

                        }

                        if (e.bill != null) {
                            mParams.put("billPrice", e.bill.price);////花费阿思币
                        }

                        if (e.complain != null) {
                            mParams.put("reason", e.complain.reason);//投诉原因
                            mParams.put("details", e.complain.details);//投诉细节

                        }
                        if (e.student != null) {
                            mParams.put("askTimes", e.student.askTimes);//是否首单免费，为0是首单免费
                            mParams.put("integral", e.student.integral);//余额
                        }

                        openActivity(OtoEndActivity.class);

                    }
                });

            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public OtoRecord getItem(int position) {
            return dataList.get(position);
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

    public void onEventMainThread(AppEventType event) {
        switch (event.type) {
            case AppEventType.SUBMIT_SUCCESS://刷新
                start = 0;
                dataList.clear();
                swipeLayout.setMode(PtrFrameLayout.Mode.BOTH);
                loadData();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
