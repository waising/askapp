package com.asking91.app.ui.widget.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.common.SpaceItemDecoration;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.ui.adapter.ChooseDialogAdapter;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.widget.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 在线问答--选择课本、年级
 */
public class ChooseDialog extends BaseDialog<ChooseDialog> {


    @BindView(R.id.choose_dialog_title_tv)
    TextView mTitle;
    @BindView(R.id.choose_dialog_rv)
    RecyclerView recyclerView;

    List<LabelEntity> mDatas;

    Context mContext;
    DialogCallBackListener listener;
    ChooseDialogAdapter chooseDialogAdapter;
    String title;
    public ChooseDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ChooseDialog datas(List<LabelEntity> dialogEntityList){
        this.mDatas = dialogEntityList;
        return this;
    }

    /**
     * 标题
     * @param title
     * @return
     */
    public ChooseDialog title(String title){
        this.title = title;
        return this;
    }


    @Override
    public View onCreateView() {
        widthScale(0.9f);
        showAnim(new BounceTopEnter());
        View inflate = View.inflate(mContext, R.layout.layout_dialog_choose, null);
        ButterKnife.bind(this, inflate);

        GridLayoutManager mgr=new GridLayoutManager(mContext,3);
        recyclerView.setLayoutManager(mgr);
        recyclerView.addItemDecoration(new SpaceItemDecoration(10).isLeft(true));
        if(mDatas == null)
            mDatas = new ArrayList<>();
        chooseDialogAdapter = new ChooseDialogAdapter(mContext,mDatas);
        chooseDialogAdapter.setListener(listener);
        recyclerView.setAdapter(chooseDialogAdapter);

        mTitle.setText(title);
        inflate.setBackgroundResource(R.drawable.dialog_bg);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {

    }

    public ChooseDialog callBackListener(DialogCallBackListener dialogListener){
        this.listener = dialogListener;
        return this;
    }

    public interface DialogCallBackListener {
        void callBack(LabelEntity dialogEntity);
    }

}
