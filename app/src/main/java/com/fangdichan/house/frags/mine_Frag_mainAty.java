package com.fangdichan.house.frags;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangdichan.house.Net.NetCore;
import com.fangdichan.house.R;
import com.fangdichan.house.atys.CommisionActivity;
import com.fangdichan.house.atys.RankingActivity;
import com.fangdichan.house.atys.SettingActivity;
import com.fangdichan.house.atys.SpreadActivity;
import com.fangdichan.house.entity.UserInfo;
import com.fangdichan.house.utils.ImageLoader;
import com.fangdichan.house.utils.StaticValues;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by wy on 2015/9/14.
 */
public class mine_Frag_mainAty extends Fragment {

    View view;
    RelativeLayout rl_ranking,rl_commision,rl_spread,rl_setting;
    TextView tv_name,tv_phone,tv_child_num,tv_money;
    ImageView iv_head;
    int money=-1;
    int people=-1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mine_mainaty, container, false);
        iv_head=(ImageView) view.findViewById(R.id.iv_mine_head);
//        ImageLoader.getInstance(getActivity()).DisplayImage(NetCore.HeadPicAddr+StaticValues.user_info.);
        tv_name=(TextView) view.findViewById(R.id.tv_user_name);
        tv_phone=(TextView) view.findViewById(R.id.tv_user_phone);
        tv_child_num=(TextView) view.findViewById(R.id.tv_user_child_num);
        tv_money=(TextView) view.findViewById(R.id.tv_user_money);
        setNumber();
        rl_ranking=(RelativeLayout) view.findViewById(R.id.rl_ranking);
        rl_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RankingActivity.class);
                getActivity().startActivity(intent);
            }
        });
        rl_commision=(RelativeLayout) view.findViewById(R.id.rl_commision);
        rl_commision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), CommisionActivity.class);
//                intent.putExtra("type","commision");
                getActivity().startActivity(intent);
            }
        });
        rl_spread=(RelativeLayout) view.findViewById(R.id.rl_spread);
        rl_spread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SpreadActivity.class);
//                intent.putExtra("type", "spread");
                getActivity().startActivity(intent);
            }
        });
        rl_setting=(RelativeLayout) view.findViewById(R.id.rl_setting);
        rl_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
//                intent.putExtra("type", "spread");
                getActivity().startActivity(intent);
            }
        });
        if(StaticValues.user_info!=null) {
            tv_name.setText(StaticValues.user_info.getName());
            tv_phone.setText(StaticValues.user_info.getPhone());
            tv_child_num.setText("" + StaticValues.user_info.getChildSum());
        }
        new WorkThread(StaticValues.user_info.getId()).start();
        return view;
    }


    private void setNumber()
    {
        if(people<0)
            tv_child_num.setText("未知");
        else
        tv_child_num.setText(people+"人");
        if(money<0)
            tv_money.setText("未知");
        else
            tv_money.setText(money+"元");
    }


    class WorkThread extends Thread
    {
        String phone;
        WorkThread(String phone)
        {
            this.phone=phone;
        }
        @Override
        public void run()
        {
            super.run();
            getNumber(phone);
        }
    }

    private void getNumber(String id)
    {
        String jsonData = new NetCore().GetNumber(id);
        if (jsonData != null)
        {
            Message msg = new Message();
            msg.obj=jsonData;
            msg.arg1 = 1;
            handler.sendMessage(msg);
        }else
        {
            Message msg = new Message();
            msg.arg1 = 0;
            handler.sendMessage(msg);
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
                    String jsondata=(String) msg.obj;
                    try {
                        JSONObject obj=new JSONObject(jsondata);
                        money=obj.getInt("bonusSize");
                        people=obj.getInt("childSize");
                        setNumber();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0:
                    break;
            }
        }
    };

}
