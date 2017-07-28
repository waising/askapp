package com.asking91.app.ui.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.asking91.app.R;
import com.asking91.app.ui.widget.AskRadioGroup;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.widget.base.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * 在线问答--选择课本、年级
 */
public class OnlineQAListBaseDialog extends BaseDialog<OnlineQAListBaseDialog> {

    @BindView(R.id.tBtn1)
    RadioButton tBtn1;
    @BindView(R.id.tBtn2)
    RadioButton tBtn2;
    @BindView(R.id.tBtn11)
    RadioButton tBtn11;
    @BindView(R.id.tBtn12)
    RadioButton tBtn12;
    @BindView(R.id.tBtn13)
    RadioButton tBtn13;
    @BindView(R.id.tBtn21)
    RadioButton tBtn21;
    @BindView(R.id.tBtn22)
    RadioButton tBtn22;
    @BindView(R.id.tBtn23)
    RadioButton tBtn23;
    @BindView(R.id.ok)
    Button ok;
    @BindView(R.id.askRadioGroup)
    AskRadioGroup askRadioGroup;
    private OnBtnClickListener listener;

    public OnlineQAListBaseDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
//      showAnim(new Swing());
        showAnim(new BounceTopEnter());
        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.layout_dialog_content_onlineqa, null);
        ButterKnife.bind(this, inflate);
//        inflate.setBackgroundDrawable(
//                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));
        inflate.setBackgroundResource(R.drawable.dialog_bg);
        return inflate;
    }

    public void setOnClickListener(OnBtnClickListener listener){
        this.listener = listener;
    }


    public interface OnBtnClickListener{
        void onClick1(String str);
        void onClick2(String str);
        void onClick11(String str);
        void onClick12(String str);
        void onClick13(String str);
        void onClick21(String str);
        void onClick22(String str);
        void onClick23(String str);
        void onClickOk();
    }

    @Override
    public void setUiBeforShow() {
        tBtn1.setOnClickListener(clickListener);
        tBtn2.setOnClickListener(clickListener);
        ok.setOnClickListener(clickListener);
        askRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                   switch (checkedId){
                        case R.id.tBtn11:
                            if(listener!=null) {
                                listener.onClick11(tBtn11.getText().toString());
                            }
                            break;
                        case R.id.tBtn12:
                            if(listener!=null) {
                                listener.onClick12(tBtn12.getText().toString());
                            }
                            break;
                        case R.id.tBtn13:
                            if(listener!=null) {
                                listener.onClick13(tBtn13.getText().toString());
                            }
                            break;
                        case R.id.tBtn21:
                            if(listener!=null) {
                                listener.onClick21(tBtn21.getText().toString());
                            }
                            break;
                        case R.id.tBtn22:
                            if(listener!=null) {
                                listener.onClick22(tBtn22.getText().toString());
                            }
                            break;
                        case R.id.tBtn23:
                            if(listener!=null) {
                                listener.onClick23(tBtn23.getText().toString());
                            }
                            break;
                    }
                                             }
//                                         }
//            CheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    switch (checkedId){
//                        case R.id.tBtn11:
//                            if(listener!=null) {
//                                listener.onClick11(tBtn11.getText().toString());
//                            }
//                            break;
//                        case R.id.tBtn12:
//                            if(listener!=null) {
//                                listener.onClick12(tBtn12.getText().toString());
//                            }
//                            break;
//                        case R.id.tBtn13:
//                            if(listener!=null) {
//                                listener.onClick13(tBtn13.getText().toString());
//                            }
//                            break;
//                        case R.id.tBtn21:
//                            if(listener!=null) {
//                                listener.onClick21(tBtn21.getText().toString());
//                            }
//                            break;
//                        case R.id.tBtn22:
//                            if(listener!=null) {
//                                listener.onClick22(tBtn22.getText().toString());
//                            }
//                            break;
//                        case R.id.tBtn23:
//                            if(listener!=null) {
//                                listener.onClick23(tBtn23.getText().toString());
//                            }
//                            break;
//                    }
//                }
//            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tBtn1:
                    if(listener!=null) {
                        listener.onClick1(tBtn1.getText().toString());
                        tBtn11.setVisibility(View.VISIBLE );
                    }
                    break;
                case R.id.tBtn2:
                    if(listener!=null) {
                        listener.onClick2(tBtn2.getText().toString());
                        tBtn11.setVisibility(View.INVISIBLE);
                        if(tBtn11.isChecked()){
                            tBtn11.setChecked(false);
                            tBtn12.setChecked(true);
                        }
                    }
                    break;

                case R.id.ok:
                    if(listener!=null) {
                        listener.onClickOk();
                    }
                    break;
            }
        }
    };
}
