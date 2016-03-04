package com.fangdichan.house.atys;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.fangdichan.house.R;


public class AddAlipayAccountDialog extends Dialog {

		public TextView tv_title;
		Button submit;
		Context context;

	public AddAlipayAccountDialog(Context context, int theme) {
		super(context, theme);
		this.context=context;
	}

	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
//			getSupportActionBar().hide();

			setContentView(R.layout.activity_add_alipay_account);


			tv_title=(TextView) findViewById(R.id.tv_title);
			
			WindowManager m = ((Activity) context).getWindowManager();
	        Display d = m.getDefaultDisplay();  //Ϊ��ȡ��Ļ�?��  
	          
	        LayoutParams p = getWindow().getAttributes();  //��ȡ�Ի���ǰ�Ĳ���ֵ

			p.width = (int) (d.getWidth() * 0.9);
			p.height = p.width/8*7;
	        //p.alpha = 1.0f;                //���ñ���͸����
	        //p.dimAmount = 0.0f;                //���úڰ���
	          
	        getWindow().setAttributes(p);     //������Ч

			submit=(Button) findViewById(R.id.btn_submit);

			
			submit.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

	        		dismiss();
	        		//System.exit(0);
				}});

		}

}
