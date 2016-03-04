package com.fangdichan.house.frags;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fangdichan.house.R;
import com.fangdichan.house.adapter.CommisionAdapter;
import com.fangdichan.house.adapter.HouseAdapter;
import com.fangdichan.house.atys.HouseDetailActivity;
import com.fangdichan.house.entity.CommisionInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wy on 2015/9/14.
 */
public class Commision_Frag_vmAty extends Fragment {

    LinearLayout ll_order,ll_buy;
    TextView tv_order,tv_buy;
    ListView lv_commision;
    CommisionAdapter adapter;
    View view;
    int position=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_commision, container, false);
        getViews();
        return view;
    }

    public void getViews() {

        ll_order=(LinearLayout) view.findViewById(R.id.ll_commision_order);
        ll_buy=(LinearLayout) view.findViewById(R.id.ll_commision_buy);
        tv_buy=(TextView) view.findViewById(R.id.tv_commision_buy_indicator);
        tv_order=(TextView) view.findViewById(R.id.tv_commision_order_indicator);
        ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_order.setVisibility(View.VISIBLE);
                tv_buy.setVisibility(View.INVISIBLE);
            }
        });
        ll_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_order.setVisibility(View.INVISIBLE);
                tv_buy.setVisibility(View.VISIBLE);
            }
        });

        lv_commision=(ListView) view.findViewById(R.id.lv_commision);
//        List<CommisionInfo> list=new ArrayList<>();
//        for(int x=50;x>=0;x--) {
//            CommisionInfo info = new CommisionInfo();
//            info.money =x;
//            info.date="2015-09-17 16:"+x;
//            info.from="王大傻";
//            list.add(info);
//        }
//        adapter=new CommisionAdapter(getActivity(),list);
//        lv_commision.setAdapter(adapter);
    }



}
