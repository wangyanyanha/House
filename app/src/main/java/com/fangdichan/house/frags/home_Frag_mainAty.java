package com.fangdichan.house.frags;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.fangdichan.house.Net.NetCore;
import com.fangdichan.house.R;
import com.fangdichan.house.adapter.HouseAdapter;
import com.fangdichan.house.atys.HouseDetailActivity;
import com.fangdichan.house.entity.HouseListInfo;
import com.fangdichan.house.views.MediaView;
import com.fangdichan.house.views.PullToRefreshListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wy on 2015/9/14.
 */
public class home_Frag_mainAty extends Fragment {

    private Context mContext;
    private PullToRefreshListView lv;
    private HouseAdapter adapter;
    View view;
    int page=0;
    MediaView videoView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home_mainaty, container, false);
        getViews();
        return view;
    }

    public void getViews() {

        this.mContext = this.getActivity();

        lv = (PullToRefreshListView) view.findViewById(R.id.lv_mp_list);

        lv.setOverScrollMode(View.OVER_SCROLL_NEVER);

        videoView=new MediaView(getActivity());
        lv.addHeaderView(videoView);
//        initList(null);


        new workingThread(1,10).start();

        lv.setonRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new workingThread(1, 10).start();
            }
        });

        lv.setOnLoadMoreListener(new PullToRefreshListView.OnLoadMoreListener() {
            @Override
            public void OnLoadMore() {
                new workingThread(++page, 10).start();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>=2) {
                    Intent intent = new Intent(getActivity(), HouseDetailActivity.class);
                    intent.putExtra("id", ((HouseListInfo) adapter.getItem(position - 2)).getId());
                    startActivity(intent);
                }
            }
        });
    }

//    public void loadDatas() {
//        for (int i = 1; i < 7; i++) {
//            HouseListInfo houseinfo = new HouseListInfo();
//            if (i == 1) {
//                houseinfo.setImage(R.drawable.icon);
//            } else if (i == 2) {
//                houseinfo.setImage(R.drawable.gaoxin1);
//            } else if (i == 3) {
//                houseinfo.setImage(R.drawable.gaoxin2);
//            } else if (i == 4) {
//                houseinfo.setImage(R.drawable.gaoxin5);
//            } else if (i == 5) {
//                houseinfo.setImage(R.drawable.gaoxin4);
//            } else if (i == 6) {
//                houseinfo.setImage(R.drawable.gaoxin5);
//            }
//            houseinfo.setName("高新华府小A户型");
//            houseinfo.setAddr1("高新区");
//            houseinfo.setAddr2("济南市高新区奥体中路龙奥大厦近邻高新华府");
//            houseinfo.setPrice("8888/平");
//            houseinfo.setStatus("在售");
//            houseinfos.add(houseinfo);
//        }
//    }

    private void initList(List<HouseListInfo> list)
    {
        if(lv.getAdapter()==null)
        {
            adapter=new HouseAdapter(getActivity(),list);
            lv.setAdapter(adapter);
        }else
        {
            adapter.clear();
            adapter.add(list);
            lv.onRefreshComplete(1);
        }
        lv.setSelection(0);
        lv.onLoadMoreComplete(1);
    }

    private void addToList(List<HouseListInfo> list)
    {
        adapter.add(list);
        lv.onLoadMoreComplete(1);
    }

    class workingThread extends Thread
    {
        int page,page_size;
        workingThread(int page,int page_size)
        {
            this.page=page;
            this.page_size=page_size;
        }
        @Override
        public void run()
        {
            super.run();
            get_house_list(page, page_size);
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
                    List<HouseListInfo> infos=(List<HouseListInfo>) msg.obj;
                    Log.e("houselist", "aaaaaaaaaaaaaaaaa");
                    if(page==1) {
                        initList(infos);
                    }
                    else if(page>1)
                        addToList(infos);
                    break;
                case 0:
                    lv.onRefreshComplete(0);
                    lv.onLoadMoreComplete(0);
//                    List<HouseListInfo> infos2=new ArrayList<>();
//                    HouseListInfo a=new HouseListInfo();
//                    infos2.add(a);
//                        initList(infos2);
                    break;
            }
        }
    };

    private void get_house_list(int page,int page_size)
    {
        this.page=page;
        String jsonData = new NetCore().GetHouseList(page,page_size);
        Log.e("houselist", jsonData);
        if (jsonData != null)
        {
            List<HouseListInfo> infos=null;
            try
            {
                Gson gson = new Gson();
                infos = gson.fromJson(jsonData, new TypeToken<List<HouseListInfo>>(){}.getType());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Message msg = new Message();
            msg.obj=infos;
//            Log.e("size",""+infos.size());
            if(infos==null||infos.size()==0)
                msg.arg1 = 0;
            else
                msg.arg1 = 1;
            handler.sendMessage(msg);
        }else
        {
            Message msg = new Message();
            msg.arg1 = 0;
            handler.sendMessage(msg);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        videoView.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        videoView.pause();
    }

}
