package com.asking91.app.ui.widget.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.asking91.app.R;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.widget.base.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 一对一答疑--等待超时提示
 */
public class OtoWaitingDialog extends BaseDialog<OtoWaitingDialog> {


    @BindView(R.id.t1)
    TextView t1;

    public OtoWaitingDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new BounceTopEnter());
        View inflate = View.inflate(mContext, R.layout.layout_dialog_oto_waiting, null);
        ButterKnife.bind(this, inflate);
        inflate.setBackgroundResource(R.drawable.dialog_bg);
        return inflate;
    }

    @OnClick({R.id.ok, R.id.del})
    public void onClick(View view) {
        dismiss();
    }

    @Override
    public void setUiBeforShow() {
        String s = getContext().getString(R.string.oto_waiting_tip2);
        int n1 = s.indexOf("查");
        int n2 = "查看详情".length();
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(s);
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.colorAccent)), n1, n1+n2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        t1.setText(stringBuilder);
    }

}
