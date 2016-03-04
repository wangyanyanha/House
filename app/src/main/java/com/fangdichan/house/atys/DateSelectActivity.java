package com.fangdichan.house.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.dpizarro.uipicker.library.picker.PickerUI;
import com.dpizarro.uipicker.library.picker.PickerUISettings;
import com.fangdichan.house.R;
import com.fangdichan.house.utils.DateUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateSelectActivity extends Activity
{
    RelativeLayout rl_bg,rl_submit,rl_select;
    TextView tv_cancel,tv_submit;
    private PickerUI mPicker_hour,mPicker_minute;//mPicker_day,
    private List<String> options_hour,options_minute;//options_day,
    private int currentPosition_hour = 0,currentPosition_minute = 0;//currentPosition_day = 0,
//    String day="",
            int hour,minute;
    MaterialCalendarView calender;
    Date m_date;
    LinearLayout ll_date_select;



 	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_select);
		
		WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();

        LayoutParams p = getWindow().getAttributes();
//        p.height = (int) (d.getHeight());
        p.width = (int) (d.getWidth());


        getWindow().setAttributes(p);


        Intent intent=getIntent();
//        day=intent.getStringExtra("day");
//        hour=intent.getStringExtra("hour");
//        minute=intent.getStringExtra("minute");


        rl_select=(RelativeLayout) findViewById(R.id.rl_select);
        rl_select.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        ll_date_select=(LinearLayout) findViewById(R.id.ll_date_select);


        calender=(MaterialCalendarView) findViewById(R.id.calendarView);


        String date=intent.getStringExtra("date");
        if(date.equals(""))
        {
            m_date=new Date(System.currentTimeMillis());
        }else
        m_date= DateUtils.stringToDate(date);
//        Log.e("date",date);
        hour=m_date.getHours();
        minute=m_date.getMinutes();
        minute=((int)(minute/5))*5;
        //Log.e("minute",""+minute);

        calender.setSelectedDate(m_date);
        calender.setOnDateChangedListener(new OnDateChangedListener() {
            @Override
            public void onDateChanged(@NonNull MaterialCalendarView widget, @Nullable CalendarDay date) {
//                Log.e("year",""+(date.getYear()));
                m_date.setYear(date.getYear()-1900);
                m_date.setMonth(date.getMonth());
                m_date.setDate(date.getDay());
            }
        });


//        mPicker_day = (PickerUI) findViewById(R.id.picker_day);
//        //Populate list
//        options_day=new ArrayList<>();
//        for(int x=0;x<31;x++)
//        {
//            options_day.add(x+1+"号");
//            if(day.equals(x+1+"号"))
//            {
//                currentPosition_day=x;
//            }
//        }
//        if(currentPosition_day==0)
//        {
//                day=options_day.get(0);
//        }
//        //Populate list
//        mPicker_day.setItems(this, options_day);
//        mPicker_day.setItemsClickables(false);
//        mPicker_day.setAutoDismiss(false);
//
//        mPicker_day.setOnClickItemPickerUIListener(
//                new PickerUI.PickerUIItemClickListener() {
//                    @Override
//                    public void onItemClickPickerUI(int which, int position, String valueResult) {
//                        currentPosition_day = position;
//                        day=valueResult;
//                    }
//                });
//
//        int randomColor = -1;
//        final PickerUISettings pickerUISettings_day =
//                new PickerUISettings.Builder().withItems(options_day)
//                        .withBackgroundColor(randomColor)
//                        .withAutoDismiss(false)
//                        .withItemsClickables(false)
//                        .withUseBlur(false)
//                        .build();
//
//        mPicker_day.setSettings(pickerUISettings_day);
//
//        mPicker_day.slide(currentPosition_day);



        mPicker_hour = (PickerUI) findViewById(R.id.picker_hour);
        //Populate list
        options_hour=new ArrayList<>();
        for(int x=0;x<24;x++)
        {
            options_hour.add(x+"点");
            if(hour==x)
            {
                currentPosition_hour=x;
            }
        }
        if(currentPosition_hour==0)
        {
            hour=Integer.parseInt(options_hour.get(0).replace("点",""));
        }

        //Populate list
        mPicker_hour.setItems(this, options_hour);
        mPicker_hour.setItemsClickables(false);
        mPicker_hour.setAutoDismiss(false);

        mPicker_hour.setOnClickItemPickerUIListener(
                new PickerUI.PickerUIItemClickListener() {
                    @Override
                    public void onItemClickPickerUI(int which, int position, String valueResult) {
                        currentPosition_hour = position;
                        hour=Integer.parseInt(valueResult.replace("点",""));
                    }
                });

        PickerUISettings pickerUISettings_hour =
                new PickerUISettings.Builder().withItems(options_hour)
                        .withBackgroundColor(-1)
                        .withAutoDismiss(false)
                        .withItemsClickables(false)
                        .withUseBlur(false)
                        .build();

        mPicker_hour.setSettings(pickerUISettings_hour);
        mPicker_hour.slide(currentPosition_hour);


        mPicker_minute = (PickerUI) findViewById(R.id.picker_minute);
        //Populate list
        options_minute=new ArrayList<>();
        for(int x=0;x<12;x++)
        {
            options_minute.add(x*5+"分");
            if(minute==x*5)
            {
                currentPosition_minute=x;
            }
        }
        if(currentPosition_minute==0)
        {
            minute=Integer.parseInt(options_minute.get(0).replace("分",""));
        }


        //Populate list
        mPicker_minute.setItems(this, options_minute);
        mPicker_minute.setItemsClickables(false);
        mPicker_minute.setAutoDismiss(false);

        mPicker_minute.setOnClickItemPickerUIListener(
                new PickerUI.PickerUIItemClickListener() {
                    @Override
                    public void onItemClickPickerUI(int which, int position, String valueResult) {
                        currentPosition_minute = position;
                        minute = Integer.parseInt(valueResult.replace("分",""));
                    }
                });

        PickerUISettings pickerUISettings_minute =
                new PickerUISettings.Builder().withItems(options_minute)
                        .withBackgroundColor(-1)
                        .withAutoDismiss(false)
                        .withItemsClickables(false)
                        .withUseBlur(false)
                        .build();

        mPicker_minute.setSettings(pickerUISettings_minute);
        mPicker_minute.slide(currentPosition_minute);

        rl_bg=(RelativeLayout) findViewById(R.id.rl_bg);
        rl_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_submit=(RelativeLayout) findViewById(R.id.rl_submit);
        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_cancel=(TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ll_date_select.getVisibility()!=View.GONE)
                {
                    ll_date_select.setVisibility(View.GONE);
                    calender.setVisibility(View.VISIBLE);
                }else {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            }
        });
        tv_submit=(TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(calender.getVisibility()!=View.GONE)
                {
                    calender.setVisibility(View.GONE);
                    ll_date_select.setVisibility(View.VISIBLE);
                }else {
                    Intent intent = new Intent();
                    m_date.setHours(hour);
                    m_date.setMinutes(minute);
                    intent.putExtra("date", DateUtils.date2string(m_date));
//                    Date temp=new Date(System.currentTimeMillis());
//                    temp.setYear(2015);
                    Log.e("date",DateUtils.date2string(m_date));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
	}

    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
//        setResult(StaticValues.CALENDER_RESULT_UNSELECT);
        setResult(RESULT_CANCELED);
        super.onDestroy();
    }
}
