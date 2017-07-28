package com.asking91.app.ui.widget.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.BaseAdapter;

import com.asking91.app.R;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.flyco.dialog.widget.base.BaseDialog;

import java.util.ArrayList;

/**
 * Created by wxwang on 2016/11/2.
 */
public class AlertDialogUtil {


    private static String title = "请选择";

    private static BaseDialog  dialog = null;

    private static BaseAnimatorSet mBasIn = new BounceTopEnter();
    private static BaseAnimatorSet mBasOut = new SlideBottomExit();

    public static void showNormalListDialogStringArr(Context context, String[] items,OnOperItemClickL operItemClickL){
        showNormalListDialogStringArr(context,items,0,operItemClickL);
    }

    public static void showNormalListDialogStringArr(Context context, String[] items, int animStyle,OnOperItemClickL operItemClickL) {
        if (dialog==null || !dialog.isShowing()) {
            dialog = new NormalListDialog(context, items);
            ((NormalListDialog) dialog).title(title)
                    .titleBgColor(ContextCompat.getColor(context, R.color.theme_blue_theme))
                    .layoutAnimation(null)
                    .showAnim(mBasIn)
                    .dismissAnim(null)
                    .show(animStyle);
            ((NormalListDialog) dialog).setOnOperItemClickL(operItemClickL);
        }
    }

    public static void showNormalListDialogStringArr(Context context, ArrayList<DialogMenuItem> items,OnOperItemClickL operItemClickL){
        showNormalListDialogStringArr(context,items,0,operItemClickL);
    }

    public static void showNormalListDialogStringArr(Context context, ArrayList<DialogMenuItem> items, int animStyle, OnOperItemClickL operItemClickL) {
        if (dialog==null || !dialog.isShowing()) {
            dialog = new NormalListDialog(context, items);
            ((NormalListDialog) dialog).title(title)
                    .titleBgColor(ContextCompat.getColor(context, R.color.theme_blue_theme))
                    .layoutAnimation(null)
                    .showAnim(mBasIn)
                    .dismissAnim(null)
                    .show(animStyle);
            ((NormalListDialog) dialog).setOnOperItemClickL(operItemClickL);
        }
    }


    public static void showNormalListDialogAdapter(Context context, BaseAdapter adapter, OnOperItemClickL operItemClickL) {
        dialog = dialog==null? new NormalListDialog(context, adapter):dialog;
        ((NormalListDialog)dialog).title(title)
                .titleBgColor(ContextCompat.getColor(context, R.color.theme_blue_theme))
                .layoutAnimation(null)
                .showAnim(mBasIn)
                .dismissAnim(null)
                .show();
        ((NormalListDialog)dialog).setOnOperItemClickL(operItemClickL);
    }

    public static void showActionSheetDialogNoTitle(Context context,String[] stringItems,OnOperItemClickL operItemClickL) {
        dialog = dialog==null? new ActionSheetDialog(context,stringItems,null):dialog;
        ((ActionSheetDialog)dialog).isTitleShow(false).show();
        ((ActionSheetDialog)dialog).setOnOperItemClickL(operItemClickL);
    }

    public static void dismiss(){
        if (dialog != null) {
            dialog.cancel();
            dialog.dismiss();
        }
    }


}
