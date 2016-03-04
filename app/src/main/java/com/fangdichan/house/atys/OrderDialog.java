package com.fangdichan.house.atys;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.fangdichan.house.R;
import com.fangdichan.house.utils.DateUtils;

import java.util.Date;


public class OrderDialog extends Dialog {

		public TextView tv_title,tv_time;
		Button submit;
		Context context;
	String date="";
static int REQUEST_DATE=102;
	public OrderDialog(Context context, int theme) {
		super(context, theme);
		this.context=context;
	}

	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
//			getSupportActionBar().hide();

			setContentView(R.layout.dialog_order);


			tv_title=(TextView) findViewById(R.id.tv_title);
			
			WindowManager m = ((Activity) context).getWindowManager();
	        Display d = m.getDefaultDisplay();  //Ϊ��ȡ��Ļ�?��  
	          
	        LayoutParams p = getWindow().getAttributes();  //��ȡ�Ի���ǰ�Ĳ���ֵ

			p.width = (int) (d.getWidth() * 0.9);
			p.height = p.width/8*10;
	        //p.alpha = 1.0f;                //���ñ���͸����
	        //p.dimAmount = 0.0f;                //���úڰ���
	          
	        getWindow().setAttributes(p);     //������Ч

		tv_time=(TextView) findViewById(R.id.tv_time);
		tv_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, DateSelectActivity.class);
				intent.putExtra("date",date);
				((Activity)context).startActivityForResult(intent, REQUEST_DATE);
			}
		});

			submit=(Button) findViewById(R.id.btn_submit);

			
			submit.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
                Intent intent =new Intent(context,OrderActivity.class);
                context.startActivity(intent);
	        		dismiss();
	        		//System.exit(0);
				}});

		}

	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == REQUEST_DATE&&data!=null)
		{
			date=data.getStringExtra("date");
			if(!date.equals(""))
			{
				Date temp= DateUtils.stringToDate(date);
				tv_time.setText((temp.getYear()+1900)+"年"+(temp.getMonth()+1)+"月"+temp.getDate()+"日 "+temp.getHours()+"点"+temp.getMinutes()+"分");
			}
		}
	}

}
