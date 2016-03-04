package com.fangdichan.house.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fangdichan.house.Net.NetCore;
import com.fangdichan.house.R;
import com.fangdichan.house.entity.HouseListInfo;
import com.fangdichan.house.utils.ImageLoader;

import java.util.List;

/**
 * Created by wy on 2015/9/14.
 * Class for house.adapter
 */

public class HouseAdapter extends BaseAdapter {
    private Context mContext;
    private List<HouseListInfo> infos;
    private LayoutInflater mLayoutInflater;

    public HouseAdapter(Context c) {
        this.mContext = c;
    }

    public HouseAdapter(Context c, List<HouseListInfo> infos)
    {
        super();
        mLayoutInflater = LayoutInflater.from(c);
        this.mContext = c;
        this.infos = infos;
    }

    public void clear()
    {
        infos.clear();
        notifyDataSetChanged();
    }

    public void add(List<HouseListInfo> list)
    {
        this.infos.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(infos == null)
            return  null;
        ViewHolder vh;
        if(convertView == null)
        {
            convertView = mLayoutInflater.inflate(R.layout.list_house_item, parent, false);
            vh = new ViewHolder();
            vh.image = (ImageView) convertView.findViewById(R.id.iv_lt);
            vh.tv_name = (TextView) convertView.findViewById(R.id.tv_lt_name);
            vh.tv_status = (TextView) convertView.findViewById(R.id.tv_lt_status);
            vh.tv_addr1 = (TextView) convertView.findViewById(R.id.tv_lt_addr1);
            vh.tv_addr2 = (TextView) convertView.findViewById(R.id.tv_lt_addr2);
            vh.tv_price = (TextView) convertView.findViewById(R.id.tv_lt_price);

            convertView.setTag(vh);
        }else
        {
            vh = (ViewHolder) convertView.getTag();
        }

        HouseListInfo houseInfo=infos.get(position);
        ImageLoader.getInstance(mContext).DisplayImage(NetCore.PicAddr+houseInfo.getImgUrl(),vh.image);
        vh.tv_name.setText(houseInfo.getName());
        vh.tv_status.setText(""+houseInfo.getHouseNum()+"套在售");
        vh.tv_price.setText(""+houseInfo.getPrice()+"/平");
        vh.tv_addr1.setText(houseInfo.getCity());
        vh.tv_addr2.setText(houseInfo.getAddress());


        return convertView;
    }

    static class ViewHolder {
        private ImageView image;
        private TextView tv_name, tv_status, tv_addr1, tv_addr2, tv_price;
    }

}
