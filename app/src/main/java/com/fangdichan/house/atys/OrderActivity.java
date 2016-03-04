package com.fangdichan.house.atys;


import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fangdichan.house.R;
import com.fangdichan.house.zxing.encoding.EncodingHandler;
import com.google.zxing.WriterException;

import java.lang.reflect.Field;

/**
 * Created by WY on 2015/9/20.
 */
public class OrderActivity extends ActionBarActivity {
ImageView btn_back;

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
        setContentView(R.layout.activity_order);

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

        init();
    }
void init(){
    btn_back=(ImageView) findViewById(R.id.btn_back);
    btn_back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });

    ImageView iv_mar = (ImageView) findViewById(R.id.iv_edit_mar);
    Bitmap qrCodeBitmap;
    String contentString = "1234567qwerty";
    try {
        qrCodeBitmap = EncodingHandler.createQRCode(contentString, 200);
        iv_mar.setImageBitmap(qrCodeBitmap);
    } catch (WriterException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
}
