package com.fangdichan.house.atys;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fangdichan.house.Net.NetCore;
import com.fangdichan.house.R;
import com.fangdichan.house.entity.UserInfo;
import com.fangdichan.house.utils.StaticValues;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Smarft on 2015/8/20.
 * Writed by WangYan on 2015/11/21.
 */
public class LoginActivity extends ActionBarActivity {
    Button btnLogin;
    Button btnReg;
    EditText et_phone, et_pwd;

    private ProgressDialog pd;

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
        setContentView(R.layout.activity_login);

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

    void init() {
        et_phone = (EditText) findViewById(R.id.et_user_phone);
        et_pwd = (EditText) findViewById(R.id.et_user_pwd);
        btnLogin = (Button) findViewById(R.id.btn_login_login);
        btnReg = (Button) findViewById(R.id.btn_login_reg);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_phone.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (et_pwd.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    pd = ProgressDialog.show(LoginActivity.this, null, "正在登录……");
                    new LoginAsyncTask().execute(et_phone.getText().toString(), et_pwd.getText().toString());
                }
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                LoginActivity.this.finish();
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            String result = new NetCore().Login(params[0], params[1]);
            Log.e("login", result);
            try {
                JSONObject obj = new JSONObject(result);
                int status = obj.getInt("result");
                if (status == 0)
                    return false;
                else return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                StaticValues.user_phone=et_phone.getText().toString();
                new UserInfoWorkThread(StaticValues.user_phone).start();
            }else
            {
                pd.dismiss();// 关闭ProgressDialog
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class UserInfoWorkThread extends Thread
    {
        String phone;
        UserInfoWorkThread(String phone)
        {
            this.phone=phone;
        }
        @Override
        public void run()
        {
            super.run();
            UserInfo(phone);
        }
    }

    private void UserInfo(String phone)
    {
        String jsonData = new NetCore().GetUserInfoByPhone(phone);
        Log.e("userinfo", jsonData);
        if (jsonData != null)
        {
            UserInfo info=null;
            try
            {
                Gson gson = new Gson();
                info = gson.fromJson(jsonData, UserInfo.class);
                StaticValues.user_info=info;
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                pd.dismiss();// 关闭ProgressDialog
                startActivity(intent);
                LoginActivity.this.finish();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                pd.dismiss();// 关闭ProgressDialog
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            pd.dismiss();// 关闭ProgressDialog
            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }
}
