package com.fangdichan.house.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fangdichan.house.R;
import com.fangdichan.house.atys.AddAlipayAccountDialog;
import com.fangdichan.house.atys.AddCashAccountDialog;


/**
 * TODO: document your custom view class.
 */
public class AccountSelectView extends FrameLayout {

    Context context;
    LinearLayout ll_bg;
    LinearLayout ll_bg_list;
    LinearLayout ll_wx,ll_card,ll_alipay;

    public AccountSelectView(Context context) {
        super(context);
        init(context,null, 0);
    }

    public AccountSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs, 0);
    }

    public AccountSelectView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs, defStyle);
    }

    private void init(final Context context,AttributeSet attrs, int defStyle) {
        LayoutInflater.from(context).inflate(R.layout.layout_account_select_list, this);
        this.context=context;
        ll_bg=(LinearLayout) findViewById(R.id.ll_bg_select_view);
        ll_bg.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(getVisibility()==VISIBLE)
                {
                    setVisibility(GONE);
                    return true;
                }
                return false;
            }
        });

        ll_bg_list=(LinearLayout) findViewById(R.id.ll_bg_list);
        ll_bg_list.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        ll_wx=(LinearLayout) findViewById(R.id.ll_weixin);
        ll_wx.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
            }
        });

        ll_card=(LinearLayout) findViewById(R.id.ll_card);

        ll_card.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
                AddCashAccountDialog dialog=new AddCashAccountDialog(context,R.style.DialogTheme);
                dialog.show();
            }
        });

        ll_alipay=(LinearLayout) findViewById(R.id.ll_alipay);
        ll_alipay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
                AddAlipayAccountDialog dialog=new AddAlipayAccountDialog(context,R.style.DialogTheme);
		        dialog.show();
            }
        });
    }


}
