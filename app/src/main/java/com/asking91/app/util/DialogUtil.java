package com.asking91.app.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.asking91.app.R;

/**
 * Created by linbin on 2017/7/11.
 */

public class DialogUtil {

    /**
     * @param context
     * @param msg
     * @param cancelable
     * @return
     */
    public static Dialog getProgressDialog(Context context, String msg, final boolean cancelable) {
        AlertDialog pdDialog = new AlertDialog.Builder(context, R.style.dialog).create();
        pdDialog.show();
        pdDialog.setContentView(R.layout.process_dialog_loading);
        TextView msgText = (TextView) pdDialog.findViewById(R.id.tvContent);
        if (!TextUtils.isEmpty(msg)) {
            msgText.setText(msg);
            msgText.setVisibility(View.VISIBLE);
        } else {
            msgText.setVisibility(View.GONE);
        }
        pdDialog.setCanceledOnTouchOutside(false);
        pdDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (cancelable) {
                    return false;
                }
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        return pdDialog;
    }
}
