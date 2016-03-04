package com.fangdichan.house.frags;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fangdichan.house.R;
import com.fangdichan.house.adapter.CommisionAdapter;
import com.fangdichan.house.adapter.SpreadAdapter;
import com.fangdichan.house.entity.CommisionInfo;
import com.fangdichan.house.entity.SpreadInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wy on 2015/9/14.
 */
public class Spread_Frag_vmAty extends Fragment {

    LinearLayout ll_all,ll_level_1,ll_level_2,ll_other;
    TextView tv_all,tv_level_1,tv_level_2,tv_other;
    ListView lv_sprad;
    SpreadAdapter adapter;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_spread, container, false);
        getViews();
        return view;
    }

    public void getViews() {

        ll_all=(LinearLayout) view.findViewById(R.id.ll_spread_all);
        ll_level_1=(LinearLayout) view.findViewById(R.id.ll_spread_level_1);
        ll_level_2=(LinearLayout) view.findViewById(R.id.ll_spread_level_2);
        ll_other=(LinearLayout) view.findViewById(R.id.ll_spread_other);

        tv_all=(TextView) view.findViewById(R.id.tv_spread_all);
        tv_level_1=(TextView) view.findViewById(R.id.tv_spread_level_1);
        tv_level_2=(TextView) view.findViewById(R.id.tv_spread_level_2);
        tv_other=(TextView) view.findViewById(R.id.tv_spread_other);

        ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_all.setVisibility(View.VISIBLE);
                tv_level_1.setVisibility(View.INVISIBLE);
                tv_level_2.setVisibility(View.INVISIBLE);
                tv_other.setVisibility(View.INVISIBLE);
            }
        });

        ll_level_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_all.setVisibility(View.INVISIBLE);
                tv_level_1.setVisibility(View.VISIBLE);
                tv_level_2.setVisibility(View.INVISIBLE);
                tv_other.setVisibility(View.INVISIBLE);
            }
        });
        ll_level_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_all.setVisibility(View.INVISIBLE);
                tv_level_1.setVisibility(View.INVISIBLE);
                tv_level_2.setVisibility(View.VISIBLE);
                tv_other.setVisibility(View.INVISIBLE);
            }
        });
        ll_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_all.setVisibility(View.INVISIBLE);
                tv_level_1.setVisibility(View.INVISIBLE);
                tv_level_2.setVisibility(View.INVISIBLE);
                tv_other.setVisibility(View.VISIBLE);
            }
        });

        lv_sprad=(ListView) view.findViewById(R.id.lv_spread);
//        List<SpreadInfo> list=new ArrayList<>();
//        for(int x=10;x>=0;x--) {
//            SpreadInfo info = new SpreadInfo();
//            info.name="王大傻";
//            info.phone="12345678"+x;
//            list.add(info);
//        }
//        adapter=new SpreadAdapter(getActivity(),list);
//        lv_sprad.setAdapter(adapter);
    }



}
