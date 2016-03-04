package com.fangdichan.house.atys;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.fangdichan.house.R;


public class AddCashAccountDialog extends Dialog {

		public TextView tv_title;
		Button submit;
		Context context;
	Spinner sp_bank;

	public AddCashAccountDialog(Context context, int theme) {
		super(context, theme);
		this.context=context;
	}

	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
//			getSupportActionBar().hide();

			setContentView(R.layout.activity_add_cash_account);


			tv_title=(TextView) findViewById(R.id.tv_title);
			
			WindowManager m = ((Activity) context).getWindowManager();
	        Display d = m.getDefaultDisplay();  //Ϊ��ȡ��Ļ�?��  
	          
	        LayoutParams p = getWindow().getAttributes();  //��ȡ�Ի���ǰ�Ĳ���ֵ

			p.width = (int) (d.getWidth() * 0.9);
			p.height = p.width/8*9;
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

		sp_bank=(Spinner) findViewById(R.id.sp_bank);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);
				//下面这句if似乎注释掉也不会产生影响
//                if (position == getCount()) {
//                    Log.w(TAG_CRY, "NOW : position == getCount()");
//                    ((CheckedTextView)v.findViewById(R.id.spinner_showed_text)).setText("被显示的默认文字");
//                    ((CheckedTextView)v.findViewById(R.id.spinner_showed_text)).setHint("被显示的默认提示"); //"Hint to be displayed"
//                }
				return v;
			}

			@Override
			public int getCount() {
				return super.getCount()-1; // you don't display last item. It is used as hint.
			}

		};

		adapter.setDropDownViewResource(R.layout.simple_spinner_pull_item);





//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.simple_spinner_item);
		String level[] = context.getResources().getStringArray(R.array.get_money_way);//资源文件
		for (int i = 0; i < level.length; i++) {
			adapter.add(level[i]);
		}
//		adapter.setDropDownViewResource(R.layout.simple_spinner_pull_item);
		sp_bank.setAdapter(adapter);
		sp_bank.setSelection(adapter.getCount()); //display hint

//		get_money_way="网银";
//		sp_money.setSelection(getItemPosition(get_money_way, sp_money));
//		sp_money.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//				get_money_way = (String) sp_money.getItemAtPosition(position);
//
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//			}
//		});

		}

}
