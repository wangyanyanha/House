package com.fangdichan.house.atys;


import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fangdichan.house.Net.NetCore;
import com.fangdichan.house.R;
import com.fangdichan.house.adapter.CommisionAdapter;
import com.fangdichan.house.adapter.SpreadAdapter;
import com.fangdichan.house.entity.BonusInfo;
import com.fangdichan.house.entity.CommisionInfo;
import com.fangdichan.house.entity.SpreadInfo;
import com.fangdichan.house.utils.StaticValues;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wy on 2015/09/17.
 */
public class CommisionActivity extends ActionBarActivity {

    ListView lv_commision;
    CommisionAdapter adapter;
    ImageView iv_back;

    LinearLayout ll_bg;
    LinearLayout ll_bg_list;
//    LinearLayout ll_wx,ll_card,ll_alipay;
    TextView tv_select;

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
        setContentView(R.layout.activity_commision);

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

        iv_back=(ImageView) findViewById(R.id.btn_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        lv_commision=(ListView) findViewById(R.id.lv_commision);

        ll_bg=(LinearLayout) findViewById(R.id.ll_bg_select_view);
        ll_bg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(ll_bg.getVisibility()==View.VISIBLE)
                {
                    ll_bg.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });

        ll_bg_list=(LinearLayout) findViewById(R.id.ll_bg_list);
        ll_bg_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        tv_select=(TextView) findViewById(R.id.tv_select);
        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_bg.getVisibility() == View.GONE) {
                    ll_bg.setVisibility(View.VISIBLE);
                } else if (ll_bg.getVisibility() == View.VISIBLE) {
                    ll_bg.setVisibility(View.GONE);
                }
            }
        });

        new workingThread().start();
    }


    class workingThread extends Thread
    {
        @Override
        public void run()
        {
            super.run();
            get_commision();
        }
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.arg1)
            {
                case 1:
                    List<BonusInfo> infos=(List<BonusInfo>) msg.obj;
                    adapter=new CommisionAdapter(CommisionActivity.this,infos);
                    lv_commision.setAdapter(adapter);
                    break;
                case 0:
                    break;
            }
        }
    };

    private void get_commision()
    {
        String jsonData = new NetCore().GetMyBonus(StaticValues.user_info.getId());
        if (jsonData != null)
        {
            List<BonusInfo> infos=null;
            try
            {
                Gson gson = new Gson();
                infos = gson.fromJson(jsonData, new TypeToken<List<BonusInfo>>(){}.getType());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Message msg = new Message();
            msg.obj=infos;
            if(infos==null||infos.size()==0)
                msg.arg1 = 0;
            else
                msg.arg1 = 1;
            handler.sendMessage(msg);
        }else
        {
            Message msg = new Message();
            msg.arg1 = 0;
            handler.sendMessage(msg);
        }
    }
}
