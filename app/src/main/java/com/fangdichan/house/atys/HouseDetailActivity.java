package com.fangdichan.house.atys;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fangdichan.house.Net.NetCore;
import com.fangdichan.house.R;
import com.fangdichan.house.entity.HouseInfo;
import com.fangdichan.house.entity.HouseListInfo;
import com.fangdichan.house.utils.ImageLoader;
import com.fangdichan.house.zxing.atys.ScanQrCodeActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.List;


public class HouseDetailActivity extends ActionBarActivity {

    ImageView iv_scan,iv_house,iv_back;
    Button btn_order;

    TextView tv_price,tv_time,tv_num,tv_address,tv_desc;

    Context mContext;

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
        setContentView(R.layout.activity_house_detail_2);

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



        Intent intent=getIntent();
        String id=intent.getStringExtra("id");

        mContext=this;

        iv_house=(ImageView) findViewById(R.id.iv_house);
        tv_address=(TextView) findViewById(R.id.tv_house_address);
        tv_price=(TextView) findViewById(R.id.tv_house_price);
        tv_time=(TextView) findViewById(R.id.tv_house_time);
        tv_num=(TextView) findViewById(R.id.tv_house_num);
        tv_desc=(TextView) findViewById(R.id.tv_house_descirbe);

        iv_back=(ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        iv_scan=(ImageView) findViewById(R.id.iv_scan);
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HouseDetailActivity.this, ScanQrCodeActivity.class);
                startActivity(i);
            }
        });
        btn_order=(Button) findViewById(R.id.btn_detail_ok);
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HouseDetailActivity.this,OrderActivity.class);
                startActivity(intent);
            }
        });

        new workingThread(id).start();
    }


    class workingThread extends Thread
    {
        String id;
        workingThread(String id)
        {
            this.id=id;
        }
        @Override
        public void run()
        {
            super.run();
            get_house_info(id);
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
                    HouseInfo info =(HouseInfo) msg.obj;
                    tv_address.setText(info.getAddress());
                    tv_desc.setText(info.getDescr());
                    tv_num.setText("剩余："+info.getHouseNum()+"套");
                    tv_price.setText("最低价："+info.getPrice()+"元/平");
                    ImageLoader.getInstance(mContext).DisplayImage(NetCore.PicAddr+info.getImgUrl(),iv_house);
                    break;
                case 0:
                    finish();
                    break;
            }
        }
    };

    private void get_house_info(String id)
    {
        String jsonData = new NetCore().GetHouseInfo(id);
        Log.e("houseinfo", jsonData);
        if (jsonData != null)
        {
            HouseInfo info=null;
            try
            {
                Gson gson = new Gson();
                info = gson.fromJson(jsonData, HouseInfo.class);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Message msg = new Message();
            msg.obj=info;
            if(info==null)
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
