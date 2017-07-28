package com.asking91.app.ui.pay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asking91.app.R;
import com.asking91.app.global.PayConstant;
import com.asking91.app.mvpframe.base.BaseFrameActivity;
import com.asking91.app.ui.supertutorial.SuperSelectModelImpl;
import com.asking91.app.ui.supertutorial.SuperSelectPresenterImpl;
import com.asking91.app.ui.widget.camera.utils.BitmapUtil;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.asking91.app.R.id.pay_money;

/**
 * 同步课程购买的详细商品内容
 * Created by wxwang on 2016/11/30.
 */
public class CoursePurchaseDetailActivity extends BaseFrameActivity<SuperSelectPresenterImpl, SuperSelectModelImpl> {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;
    @BindView(R.id.book)
    ImageView book;
    @BindView(R.id.tv_class_name)
    TextView tvClassName;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(pay_money)
    TextView payMoney;
    @BindView(R.id.pay_btn)
    Button payBtn;

    private String subjectImgKey;
    private String commityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_purchase_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        setToolbar(mToolbar, getString(R.string.synchronous_course_purchase));
        payBtn.setText(R.string.pay_now_two);


    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            BitmapUtil.displayImage(bundle.getString("imageUrl"), book, true);
            tvClassName.setText(bundle.getString("className"));//初中数学这种
            tvVersionName.setText(bundle.getString("versionName"));

            try {
                BigDecimal price =new BigDecimal(bundle.getString("price"));
                Double money = price.divide(new BigDecimal("100"), 3, RoundingMode.HALF_UP).doubleValue();
                tvPrice.setText("¥ " + money);//单价
                payMoney.setText(money+"");//应付价格
            }catch (Exception e){}

            commityId = bundle.getString("commodityId");
            subjectImgKey = bundle.getString("subjectImgKey");
        }

        try {
            String imageName = PayConstant.getVersionImages(subjectImgKey);
            if (!TextUtils.isEmpty(imageName)) {
                InputStream in;
                in = getAssets().open("images/km/" + imageName);
                Bitmap bmp = BitmapFactory.decodeStream(in);
                book.setImageBitmap(bmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestEnd() {

    }

    private String[] getIds() {

        String[] ids;//商品只有一个
        ids = new String[1];
        for (int i = 0; i < 1; i++) {
            ids[i] = commityId;
        }
        return ids;
    }

    @OnClick(R.id.pay_btn)
    public void onClick() {//跳转到支付页面
        Bundle bundle = new Bundle();
        bundle.putString("orderType", "3");//订单类型：“3”
        bundle.putStringArray("commodityList", getIds());//
        openActivity(PayNowActivity.class, bundle);
    }


}
