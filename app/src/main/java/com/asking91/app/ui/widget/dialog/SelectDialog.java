package com.asking91.app.ui.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.entity.LabelEntity;
import com.asking91.app.global.OnlineQAConstant;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.adapter.NumericWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.widget.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 在线问答--选择课本、年级
 */
public class SelectDialog extends BaseDialog<SelectDialog> {

    @BindView(R.id.choose_dialog_title_tv)
    TextView mTitle;
    @BindView(R.id.heelView)
    WheelView wheelView;
    List<LabelEntity> mDatas;

    Context mContext;
    DialogCallBackListener listener;
    String title, me;
    int position;
    private List<String> list;

    public SelectDialog(Context context, String me) {
        super(context);
        this.mContext = context;
        this.me = me;
    }

    public SelectDialog datas(List<LabelEntity> dialogEntityList) {
        this.mDatas = dialogEntityList;
        return this;
    }

    /**
     * 标题
     *
     * @param title
     * @return
     */
    public SelectDialog title(String title) {
        this.title = title;
        return this;
    }


    @Override
    public View onCreateView() {
        widthScale(0.9f);
        showAnim(new BounceTopEnter());
        View inflate = View.inflate(mContext, R.layout.layout_dialog_select, null);
        ButterKnife.bind(this, inflate);

        list = new ArrayList();
        // 学校
        if (me.equals("meArrayWheel")) {
            for (int i = 0; i < mDatas.size(); i++) {
                list.add(mDatas.get(i).getName());
            }
            ArrayWheelAdapter arrayWheelAdapter = new ArrayWheelAdapter((ArrayList) list);
            wheelView.setAdapter(arrayWheelAdapter);
        }
        // 班级
        if (me.equals("meNumericWheelClass")) {
            NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(1, 50);
            wheelView.setAdapter(numericWheelAdapter);
        }
        // 年级
        if (me.equals("meNumericWheelGrade")) {
            for (int i = 0; i < OnlineQAConstant.gradeVersionValues.length; i++) {
                list.add(OnlineQAConstant.gradeVersionValues[i]);
            }
            ArrayWheelAdapter arrayWheelAdapter = new ArrayWheelAdapter((ArrayList) list);
            wheelView.setAdapter(arrayWheelAdapter);
        }
        // 性别
        if(me.equals("meNumericWheelSex")){
            list.add("男");
            list.add("女");
            ArrayWheelAdapter arrayWheelAdapter = new ArrayWheelAdapter((ArrayList) list);
            wheelView.setAdapter(arrayWheelAdapter);
        }

        //wheelView.setLabel(getString(R.string.pickerview_year));// 添加文字
        wheelView.setCurrentItem(0);// 初始化时显示的数据
        wheelView.setCyclic(false);
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                position = index;
            }
        });

        mTitle.setText(title);
        inflate.setBackgroundResource(R.drawable.dialog_bg);
        return inflate;
    }


    @OnClick({R.id.btnSubmit, R.id.btnCancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                if (me.equals("meArrayWheel")) {
                    listener.callBack(mDatas.get(position), position, null);
                }
                if (me.equals("meNumericWheelClass")) {
                    listener.callBack(null, position, null);
                }
                if (me.equals("meNumericWheelGrade")) {
                    listener.callBack(null, position, null);
                }
                if(me.equals("meNumericWheelSex")){
                    listener.callBack(null, position, list.get(position));
                }
                dismiss();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }


    @Override
    public void setUiBeforShow() {

    }

    public SelectDialog callBackListener(DialogCallBackListener dialogListener) {
        this.listener = dialogListener;
        return this;
    }

    public interface DialogCallBackListener {
        void callBack(LabelEntity dialogEntity, int pos, String str);
    }


}
