package com.fangdichan.house.atys;


import android.app.ActionBar;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fangdichan.house.Net.NetCore;
import com.fangdichan.house.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Smarft on 2015/8/21.
 * Writed by WangYan on 2015/11/21.
 */
public class RegisterActivity extends ActionBarActivity {
    Spinner sp_money;
    Button btnConfrim;
    ImageView btnCancel;
    String get_money_way;
    EditText et_name,et_pwd,et_city,et_pwd_confirm,et_phone;
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

    private int getItemPosition(String item,Spinner sp)
    {
        for(int x=0;x<sp.getAdapter().getCount();x++) {
            if (item.equals(sp.getItemAtPosition(x)))
                return x;
        }
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
        et_name=(EditText) findViewById(R.id.et_register_name);
        et_pwd=(EditText) findViewById(R.id.et_register_pwd);
        et_pwd_confirm=(EditText) findViewById(R.id.et_register_pwd_confirm);
        et_city=(EditText) findViewById(R.id.et_register_city);
        et_phone=(EditText) findViewById(R.id.et_register_phone);

        btnConfrim =(Button)findViewById(R.id.btn_register_confrim);
        btnCancel =(ImageView)findViewById(R.id.btn_back);
        btnConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_name.getText().toString().equals(""))
                {
                    Toast.makeText(RegisterActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                }else if(et_pwd.getText().toString().equals(""))
                {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if(!et_pwd_confirm.getText().toString().equals(et_pwd.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                }else if(et_phone.getText().toString().equals(""))
                {
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else if(et_city.getText().toString().equals(""))
                {
                    Toast.makeText(RegisterActivity.this, "请输入城市", Toast.LENGTH_SHORT).show();
                }else
                {
                    pd = ProgressDialog.show(RegisterActivity.this, null, "正在注册……");
                    new RegisterWorkThread(et_name.getText().toString(),et_phone.getText().toString(),et_pwd.getText().toString(),et_city.getText().toString()).start();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });

        sp_money=(Spinner) findViewById(R.id.sp_money);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item);
        String level[] = getResources().getStringArray(R.array.get_money_way);//资源文件
        for (int i = 0; i < level.length; i++) {
            adapter.add(level[i]);
        }
        adapter.setDropDownViewResource(R.layout.simple_spinner_pull_item);
        sp_money.setAdapter(adapter);

        get_money_way="网银";
        sp_money.setSelection(getItemPosition(get_money_way, sp_money));
        sp_money.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                get_money_way = (String) sp_money.getItemAtPosition(position);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    class RegisterWorkThread extends Thread
    {
        String name, phone,pwd,city;
        RegisterWorkThread(String name,String phone,String pwd,String city)
        {
            this.name=name;
            this.city=city;
            this.phone=phone;
            this.pwd=pwd;
        }
        @Override
        public void run()
        {
            super.run();
            Register(name,phone,pwd,city);
        }
    }

    private void Register(String name,String phone,String pwd,String city)
    {
        String jsonData = new NetCore().Register(name,pwd,phone,city);
        Log.e("register",jsonData);
        try {
            JSONObject obj=new JSONObject(jsonData);
            int status=obj.getInt("result");
            Message msg = new Message();
            msg.arg1 = status;
            register_handler.sendMessage(msg);
        } catch (JSONException e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.arg1 = 0;
            register_handler.sendMessage(msg);
        }
    }

    private Handler register_handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            pd.dismiss();// 关闭ProgressDialog
            switch (msg.arg1)
            {
                case 1:
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case 0:
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(RegisterActivity.this, "该账户已被注册", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
