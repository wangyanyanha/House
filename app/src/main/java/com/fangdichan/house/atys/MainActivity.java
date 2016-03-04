package com.fangdichan.house.atys;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fangdichan.house.Net.NetCore;
import com.fangdichan.house.R;
import com.fangdichan.house.frags.home_Frag_mainAty;
import com.fangdichan.house.frags.mine_Frag_mainAty;
import com.fangdichan.house.frags.qr_code_Frag_mainAty;
import com.fangdichan.house.utils.StaticValues;
import com.fangdichan.house.zxing.atys.ScanQrCodeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static int REQUEST_SCAN=100;

    private ImageView iv_qr_code, iv_home, iv_mine;
    TextView tv_qr_code, tv_home, tv_mine;
    LinearLayout ll_qr_code, ll_home, ll_mine;
    private qr_code_Frag_mainAty qr_code_frag;
    private mine_Frag_mainAty mine_frag;
    private home_Frag_mainAty home_frag;

    private ViewPager vp_container;
    private FragmentStatePagerAdapter mAdapter;

    ImageView iv_scan;

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
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT) {

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

        getViews();
        setListener();

        //设置默认Fragment
        setDefaultFrag();
    }


    private void getViews()
    {
        ll_qr_code =(LinearLayout) findViewById(R.id.ll_bottom_tab_qr_code);
        ll_home =(LinearLayout) findViewById(R.id.ll_bottom_tab_home);
        ll_mine =(LinearLayout) findViewById(R.id.ll_bottom_tab_mine);


        iv_qr_code = (ImageView) findViewById(R.id.iv_bottom_tab_qr_code);
        iv_home = (ImageView) findViewById(R.id.iv_bottom_tab_home);
        iv_mine = (ImageView) findViewById(R.id.iv_bottom_tab_mine);

        tv_qr_code =(TextView) findViewById(R.id.tv_bottom_tab_qr_code);
        tv_home =(TextView) findViewById(R.id.tv_bottom_tab_home);
        tv_mine =(TextView) findViewById(R.id.tv_bottom_tab_mine);

        vp_container = (ViewPager) findViewById(R.id.vp_content_mainAty);
        vp_container.setOffscreenPageLimit(3);

        iv_scan=(ImageView) findViewById(R.id.iv_scan);


        mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int position) {
                switch(position)
                {
                    case 0:
                        return new qr_code_Frag_mainAty();
                    case 1:
                        return new home_Frag_mainAty();
                    case 2:
                        return new mine_Frag_mainAty();
                }
                return new home_Frag_mainAty();
            }

        };
        vp_container.setAdapter(mAdapter);
        setDefaultFrag();
    }

    private void setListener()
    {
        ll_qr_code.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_mine.setOnClickListener(this);

        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ScanQrCodeActivity.class);
                startActivityForResult(i, REQUEST_SCAN);
//                startActivity(i);
            }
        });


        vp_container.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setState(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void setState(int position)
    {
        resetButton();
        switch (position)
        {
            case 2:
                tv_mine.setTextColor(getResources().getColor(R.color.orange));
                iv_mine.setImageResource(R.drawable.mine_orange);
                break;
            case 1:
                tv_home.setTextColor(getResources().getColor(R.color.orange));
                iv_home.setImageResource(R.drawable.home_orange);
                break;
            case 0:
                tv_qr_code.setTextColor(getResources().getColor(R.color.orange));
                iv_qr_code.setImageResource(R.drawable.qr_code_orange);
                break;
        }
    }


    private void setDefaultFrag() {
        tv_home.setTextColor(getResources().getColor(R.color.orange));
        iv_home.setImageResource(R.drawable.home_orange);
        setState(1);
        vp_container.setCurrentItem(1);
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        qr_code_frag = new qr_code_Frag_mainAty();
//        home_frag = new home_Frag_mainAty();
//        mine_frag = new mine_Frag_mainAty();
//        transaction.replace(R.id.id_Content_mainAty, home_frag);
//        transaction.commit();
    }

    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        Log.e("click","back");
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
        // super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
        resetButton();
        switch (v.getId()) {
            case R.id.ll_bottom_tab_home:
                tv_home.setTextColor(getResources().getColor(R.color.orange));
                iv_home.setImageResource(R.drawable.home_orange);
                setState(1);
                vp_container.setCurrentItem(1);
//                if (home_frag.isVisible()) {
//                } else {
//                    hideVisualfrag(transaction);
//                    if (home_frag != null) {
//                        if (home_frag.isHidden()) {
//                            transaction.show(home_frag);
//                        } else {
//                            if (!home_frag.isVisible()) {
//                                transaction.add(R.id.id_Content_mainAty, home_frag);
//                            }
//                        }
//                    } else if (home_frag == null) {
//                        home_frag = new home_Frag_mainAty();
//                        transaction.add(R.id.id_Content_mainAty, home_frag);
//                    }
//                }
                break;
            case R.id.ll_bottom_tab_mine:
                tv_mine.setTextColor(getResources().getColor(R.color.orange));
                iv_mine.setImageResource(R.drawable.mine_orange);
                setState(2);
                vp_container.setCurrentItem(2);
//                if (mine_frag.isVisible()) {
//                } else {
//                    hideVisualfrag(transaction);
//                    if (mine_frag != null) {
//                        if (mine_frag.isHidden()) {
//                            transaction.show(mine_frag);
//                        } else {
//                            if (!mine_frag.isVisible()) {
//                                transaction.add(R.id.id_Content_mainAty, mine_frag);
//                            }
//                        }
//                    } else if (mine_frag == null) {
//                        mine_frag = new mine_Frag_mainAty();
//                        transaction.add(R.id.id_Content_mainAty, mine_frag);
//                    }
//                }
                break;
            case R.id.ll_bottom_tab_qr_code:
                tv_qr_code.setTextColor(getResources().getColor(R.color.orange));
                iv_qr_code.setImageResource(R.drawable.qr_code_orange);
                setState(0);
                vp_container.setCurrentItem(0);
//                if (qr_code_frag.isVisible()) {
//                } else {
//                    hideVisualfrag(transaction);
//                    if (qr_code_frag != null) {
//                        if (qr_code_frag.isHidden()) {
//                            transaction.show(qr_code_frag);
//                        } else {
//                            if (!qr_code_frag.isVisible()) {
//                                transaction.add(R.id.id_Content_mainAty, qr_code_frag);
//                            }
//                        }
//                    } else if (qr_code_frag == null) {
//                        qr_code_frag = new qr_code_Frag_mainAty();
//                        transaction.add(R.id.id_Content_mainAty, qr_code_frag);
//                    }
//                }
                break;

        }
//        transaction.commit();
    }

    private void resetButton() {
        //button恢复未点击状态
        iv_home.setImageResource(R.drawable.home_white);
        iv_qr_code.setImageResource(R.drawable.qr_code_white);
        iv_mine.setImageResource(R.drawable.mine_white);

        tv_home.setTextColor(getResources().getColor(R.color.white));
        tv_qr_code.setTextColor(getResources().getColor(R.color.white));
        tv_mine.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_SCAN)
        {
            if(resultCode==0)
            {
                Toast.makeText(MainActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
            }else if(resultCode==RESULT_OK)
            {
                new ScanAsyncTask().execute(data.getStringExtra("result"));
            }
        }
    }

    public class ScanAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String result = new NetCore().Scan(StaticValues.user_info.getId(),params[0]);
            try {
                JSONObject obj = new JSONObject(result);
                int status = obj.getInt("result");
                if (status == 1)
                    return true;
                else return false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Toast.makeText(MainActivity.this, "扫码成功", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(MainActivity.this, "扫码失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
