/**   
 * @Title: ActivityLogin.java 
 * @Package com.example.zszl 
 * @Description: TODO 
 * @author YBZ   
 * @date 2014-4-20 下午8:15:59 
 * @version V1.0   
 */
package com.fangdichan.house.atys;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
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


public class LoginActivity2 extends ActionBarActivity
{
	private Button btn_login;
	private Button btn_register;
	private AutoCompleteTextView et_telephone;
	private EditText et_pwd;
	private CheckBox cb_rember;
	private CheckBox cb_auto;
	private SharedPreferences sp;
	private TextView tv_forget;
	private long exitTime = 0;
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
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_2);


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

		// NetWorkUtil.NetWorkConnect(ActivityLogin.this);
		sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		FindViews();

		AddListeners();
		AutoLogin();
		autoFill();



	}
	
//	@Override
//	public void onBackPressed()
//	{
//		if (System.currentTimeMillis() - exitTime > 2000)
//		{
//			Toast.makeText(this, R.string.to_exit, Toast.LENGTH_SHORT).show();
//			exitTime = System.currentTimeMillis();
//		}
//		else
//		{
//			this.finish();
//		}
//		// super.onBackPressed();
//	}

	private void FindViews()
	{
		btn_login = (Button) findViewById(R.id.btn_login_login);
		btn_register = (Button) findViewById(R.id.btn_login_reg);
		et_telephone = (AutoCompleteTextView) findViewById(R.id.et_login_telephone);
		et_pwd = (EditText) findViewById(R.id.et_login_pwd);
		cb_rember = (CheckBox) findViewById(R.id.cb_login);
		tv_forget = (TextView) findViewById(R.id.textView1);
		cb_auto = (CheckBox) findViewById(R.id.cb_auto);
		cb_auto.setChecked(true);
		cb_rember.setChecked(true);// 默认为记住密码
		et_telephone.setThreshold(1);// 输入1个字母就开始自动提示
	}

	/**
	 * 判断是否是自动登陆，若是，则自动登录
	 */
	private void AutoLogin()
	{
		if (sp.getBoolean("auto_login", false))
		{
			cb_auto.setChecked(true);
			String telephone = sp.getString("auto_telephone", "");
			String pwd = sp.getString("auto_pwd", "");
			et_telephone.setText(telephone);
			et_pwd.setText(pwd);
			//login
			StaticValues.user_phone=telephone;
			pd = ProgressDialog.show(LoginActivity2.this, null, "正在登录……");
			new LoginAsyncTask().execute(telephone, pwd);
		}
		else
		{
			cb_auto.setChecked(false);
		}
	}

	private void autoFill()
	{
		// TODO Auto-generated method stub
		String telephone = sp.getString("telephone", "");
		et_telephone.setText(telephone);
		if (sp.getBoolean("remember", false))
		{
			cb_rember.setChecked(true);
			String pwd = sp.getString(telephone, "");
			et_pwd.setText(pwd);
		}
		else
		{
			cb_rember.setChecked(false);
		}
	}

	private void AddListeners()
	{
		// 为et_phone添加输入时的监控
		et_telephone.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
				String history = sp.getString("telephone_history", "");
				String[] hisArrays = history.split(",");
				for (int x = 0; x < hisArrays.length; x++)
					System.out.println(hisArrays[x]);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity2.this, R.layout.simple_dropdown_item_1line, hisArrays);
				((AutoCompleteTextView) et_telephone).setAdapter(adapter);// 设置数据适配器
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				if (sp.getBoolean("remember", false)) et_pwd.setText(sp.getString(et_telephone.getText().toString(), ""));// 自动输入密码
			}

		});
		btn_login.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				// Intent i = new Intent();
				// i.setClass(ActivityLogin.this, ActivityLoading.class);
				// startActivityForResult(i, 100);
				//
				String telephone = et_telephone.getText().toString();
				String pwd = et_pwd.getText().toString();
				// 检测内容是否填写完整

				if (!telephone.equals(""))
				{

					if (pwd.equals(""))
					{
						Toast.makeText(LoginActivity2.this, "密码不能为空", Toast.LENGTH_SHORT).show();
					}
					else
					{
						saveHistory("telephone_history", et_telephone);

						SharedPreferences.Editor mEditor = sp.edit();
						mEditor.putString("telephone", telephone);

						if (cb_auto.isChecked())
						{
							mEditor.putBoolean("remember", true);
							cb_rember.setChecked(true);
							mEditor.putBoolean("auto_login", true);
							mEditor.putString("auto_telephone", telephone);
							mEditor.putString("auto_pwd", pwd);
						}
						else
						{
							mEditor.putBoolean("auto_login", false);
							if (cb_rember.isChecked())
							{
								mEditor.putBoolean("remember", true);
								mEditor.putString(telephone, pwd);
							}
							else
							{
								mEditor.putBoolean("remember", false);
								mEditor.putString(telephone, "");
							}
						}
						mEditor.commit();

						//login
						StaticValues.user_phone=telephone;
						pd = ProgressDialog.show(LoginActivity2.this, null, "正在登录……");
						new LoginAsyncTask().execute(telephone, pwd);
					}
				} else
				{
					Toast.makeText(LoginActivity2.this, "请输入手机号", Toast.LENGTH_SHORT).show();
				}
			}
		});
		btn_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(LoginActivity2.this, RegisterActivity.class);
				startActivity(i);
			}
		});
		tv_forget.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String telephone = et_telephone.getText().toString();
				if (telephone.equals("")) {
					Toast.makeText(LoginActivity2.this, "请输入手机号",
							Toast.LENGTH_SHORT).show();
				} else {
					//find pwd
				}

			}
		});
	}

	private void saveHistory(String field, AutoCompleteTextView auto)
	{
		String text = auto.getText().toString();
		String history = sp.getString(field, "");
		if (history.length() >= 400)
		{
			for (int x = history.length() - 2; x >= 0; x--)
			{
				if (history.charAt(x) == ',')
				{
					history = history.substring(0, x + 1);
					break;
				}
			}
		}

		if (!history.contains(text + ","))
		{
			StringBuilder sb = new StringBuilder(history);
			sb.insert(0, text + ",");
			sp.edit().putString(field, sb.toString()).commit();
		}

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
				new UserInfoWorkThread(StaticValues.user_phone).start();
			}else
			{
				pd.dismiss();// 关闭ProgressDialog
				Toast.makeText(LoginActivity2.this, "登录失败", Toast.LENGTH_SHORT).show();
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
				Intent intent = new Intent(LoginActivity2.this, MainActivity.class);
				pd.dismiss();// 关闭ProgressDialog
				startActivity(intent);
				LoginActivity2.this.finish();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				pd.dismiss();// 关闭ProgressDialog
				Toast.makeText(LoginActivity2.this, "登录失败", Toast.LENGTH_SHORT).show();
			}
		}else
		{
			pd.dismiss();// 关闭ProgressDialog
			Toast.makeText(LoginActivity2.this, "登录失败", Toast.LENGTH_SHORT).show();
		}
	}

}
