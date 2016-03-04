package com.fangdichan.house.atys;


import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
import com.fangdichan.house.adapter.RankingAdapter;
import com.fangdichan.house.entity.BonusRankInfo;
import com.fangdichan.house.entity.HouseListInfo;
import com.fangdichan.house.entity.RankingInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wy on 2015/09/17.
 */
public class RankingActivity extends ActionBarActivity {

    ListView lv_ranking;
    RankingAdapter adapter;
    ImageView back;

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
        setContentView(R.layout.activity_ranking);

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

        lv_ranking=(ListView) findViewById(R.id.lv_ranking);
        back=(ImageView) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        List<RankingInfo> list=new ArrayList<>();
//        for(int x=50;x>=0;x--) {
//            RankingInfo info = new RankingInfo();
//            info.money =x*200;
//            info.people=x;
//            info.rank=51-x;
//            list.add(info);
//        }
        new workingThread().start();
    }

    class workingThread extends Thread
    {
        @Override
        public void run()
        {
            super.run();
            get_ranking();
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
                    List<BonusRankInfo> infos=(List<BonusRankInfo>) msg.obj;
                    adapter=new RankingAdapter(RankingActivity.this,infos);
                    lv_ranking.setAdapter(adapter);
                    break;
                case 0:
                    break;
            }
        }
    };

    private void get_ranking()
    {
        String jsonData = new NetCore().GetBonusRank();
        if (jsonData != null)
        {
            List<BonusRankInfo> infos=null;
            try
            {
                Gson gson = new Gson();
                infos = gson.fromJson(jsonData, new TypeToken<List<BonusRankInfo>>(){}.getType());
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
