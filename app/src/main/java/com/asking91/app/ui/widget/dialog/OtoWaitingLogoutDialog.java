package com.asking91.app.ui.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.asking91.app.R;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.widget.base.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 一对一答疑--等待退出提示
 */
public class OtoWaitingLogoutDialog extends BaseDialog<OtoWaitingLogoutDialog> {


    @BindView(R.id.ok)
    Button ok;
    @BindView(R.id.logout)
    Button logout;
//    @BindView(R.id.title)
//    TextView title;
//    @BindView(R.id.content)
//    TextView content;

    private String title,content, okStr, cancleStr;
    private int contentVisiable;
    public OtoWaitingLogoutDialog(Context context) {
        super(context);
    }

    public OtoWaitingLogoutDialog(Context context,String title, String content, int contentVisiable, String okStr, String cancleStr) {
        super(context);
        this.title = title;
        this.content = content;
        this.contentVisiable = contentVisiable;
        this.okStr = okStr;
        this.cancleStr = cancleStr;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new BounceTopEnter());
        View inflate = View.inflate(mContext, R.layout.layout_dialog_oto_waiting_logout, null);
        ButterKnife.bind(this, inflate);
        inflate.setBackgroundResource(R.drawable.dialog_bg);
        if(title!=null) {
            ((TextView) inflate.findViewById(R.id.title)).setText(title);
        }
        if(content!=null){
            ((TextView) inflate.findViewById(R.id.content)).setText(content);
        }
        if(contentVisiable!=0){
            ((TextView) inflate.findViewById(R.id.content)).setVisibility(contentVisiable);
        }
        if(okStr!=null){
            ((Button) inflate.findViewById(R.id.ok)).setText(okStr);
        }
        if(cancleStr!=null){
            ((Button) inflate.findViewById(R.id.logout)).setText(cancleStr);
        }
        return inflate;
    }

    public LououtListener listener;

    public void setLogoutListener(LououtListener listener) {
        this.listener = listener;
    }

    public interface LououtListener {
        void click();
    }

    @OnClick({R.id.ok, R.id.logout})
    public void onClick(View view) {
        if (view.getId() == R.id.logout) {
            if (listener != null) {
                listener.click();
            }
        }
        dismiss();

    }
//    public void setTitle(String title){
//        this.title.setText(title);
//    }
//
//    public void setContent(String content){
//        this.content.setText(content);
//    }

//    public void setContentVisiable(int visiable){
//        content.setVisibility(visiable);
//    }


    @Override
    public void setUiBeforShow() {
    }

}
