package com.fangdichan.house.atys;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.fangdichan.house.R;
import com.fangdichan.house.views.AccountSelectView;

import java.lang.reflect.Field;

/**
 * writed by wy on 2015/10/11.
 */

public class CashAccountActivity extends FragmentActivity {

    ImageView iv_add;
    ImageView btn_back;

    AccountSelectView select_list;

    ImageView iv_card,iv_weixin,iv_alipay;

    Activity context=this;

    private void on_alipay_disable()
    {
        iv_card.setImageResource(R.drawable.icon_circle_uncheck);
        iv_weixin.setImageResource(R.drawable.icon_circle_uncheck);
        iv_alipay.setImageResource(R.drawable.icon_circle_uncheck);
    }


    private void set_default_pay()
    {
        iv_card.setImageResource(R.drawable.icon_circle_uncheck);
        iv_weixin.setImageResource(R.drawable.icon_circle_uncheck);
        iv_alipay.setImageResource(R.drawable.icon_circle_checked);
    }

    TextView statusBarView;

    // 获取手机状态栏高度
    public int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_cash_account);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            statusBarView = new TextView(this);
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, getStatusBarHeight());
            statusBarView.setBackgroundColor(getResources().getColor(R.color.orange));
            statusBarView.setLayoutParams(lParams);
            // 获得根视图并把TextView加进去。
            ViewGroup view = (ViewGroup) getWindow().getDecorView();
            view.addView(statusBarView);
        }

        select_list=(AccountSelectView) findViewById(R.id.view_select_list);
        iv_add=(ImageView) findViewById(R.id.iv_add);

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_list.getVisibility() == View.GONE) {
                    select_list.setVisibility(View.VISIBLE);
                } else if (select_list.getVisibility() == View.VISIBLE) {
                    select_list.setVisibility(View.GONE);
                }
            }
        });


        btn_back=(ImageView) findViewById(R.id.iv_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        iv_card=(ImageView) findViewById(R.id.iv_rb_pay_card);
        iv_weixin=(ImageView) findViewById(R.id.iv_rb_pay_weixin);
        iv_alipay=(ImageView) findViewById(R.id.iv_rb_pay_alipay);
        iv_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_card.setImageResource(R.drawable.icon_circle_checked);
                iv_weixin.setImageResource(R.drawable.icon_circle_uncheck);
                iv_alipay.setImageResource(R.drawable.icon_circle_uncheck);
            }
        });
        iv_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iv_card.setImageResource(R.drawable.icon_circle_uncheck);
                iv_weixin.setImageResource(R.drawable.icon_circle_checked);
                iv_alipay.setImageResource(R.drawable.icon_circle_uncheck);
            }
        });
        iv_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_card.setImageResource(R.drawable.icon_circle_uncheck);
                iv_weixin.setImageResource(R.drawable.icon_circle_uncheck);
                iv_alipay.setImageResource(R.drawable.icon_circle_checked);
            }
        });
        set_default_pay();
    }

}
