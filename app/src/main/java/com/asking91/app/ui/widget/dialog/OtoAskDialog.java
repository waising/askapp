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
 * 在线问答--选择课本、年级
 */
public class OtoAskDialog extends BaseDialog<OtoAskDialog> {


    @BindView(R.id.t2)
    TextView t2;

    public OtoAskDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new BounceTopEnter());
        View inflate = View.inflate(mContext, R.layout.layout_dialog_oto_ask, null);
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
        String s = getContext().getString(R.string.oto_ask_tip2);
        int n1 = s.indexOf("初");
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(s);
        stringBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),R.color.oto_ask_tip)), n1, s.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        t2.setText(stringBuilder);
    }

}
