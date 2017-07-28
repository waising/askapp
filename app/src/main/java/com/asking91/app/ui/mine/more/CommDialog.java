package com.asking91.app.ui.mine.more;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.asking91.app.R;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.widget.base.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jswang on 2017/3/23.
 */

public class CommDialog extends BaseDialog<CommDialog> {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_content)
    TextView tv_content;

    String tit;
    String content;

    public CommDialog(Context context, String tit, String content) {
        super(context);
        this.tit = tit;
        this.content = content;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new BounceTopEnter());
        View inflate = View.inflate(mContext, R.layout.layout_dialog_comm_txt, null);
        ButterKnife.bind(this, inflate);
        inflate.setBackgroundResource(R.drawable.dialog_bg);
        return inflate;
    }

    @OnClick({R.id.ok})
    public void onClick(View view) {
        dismiss();
    }

    @Override
    public void setUiBeforShow() {
        tv_title.setText(tit);
        tv_content.setText(content);
    }

}
