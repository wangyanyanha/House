package com.fangdichan.house.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangdichan.house.Net.NetCore;
import com.fangdichan.house.R;
import com.fangdichan.house.entity.CommisionInfo;
import com.fangdichan.house.entity.SpreadInfo;
import com.fangdichan.house.utils.ImageLoader;
import com.fangdichan.house.utils.StaticValues;

import java.util.List;

public class SpreadAdapter extends BaseAdapter
{
	private List<SpreadInfo> list;
	private LayoutInflater inflater;
	Context context;

	public SpreadAdapter(Context context, List<SpreadInfo> list)
	{
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
	}


	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final ViewHolder holder;
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.spread_item, null);
			holder.rl_spread = (RelativeLayout) convertView.findViewById(R.id.rl_spread);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_spread_name);
			holder.tv_phone = (TextView) convertView.findViewById(R.id.tv_spread_phone);
			holder.tv_level=(TextView) convertView.findViewById(R.id.tv_spread_level);
			holder.iv_head=(ImageView) convertView.findViewById(R.id.iv_spread_head);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		SpreadInfo info = (SpreadInfo) getItem(position);



		holder.tv_name.setText(info.getName());
		holder.tv_phone.setText("电话："+info.getPhone());
		switch (info.getLevel()- StaticValues.user_info.getLevel())
		{
			case 1:
			holder.tv_level.setText("一级会员");
			break;
			case 2:
				holder.tv_level.setText("二级会员");
				break;
		}
		ImageLoader.getInstance(context).DisplayImage(NetCore.HeadPicAddr+info.getHead(),holder.iv_head);


		return convertView;

	}


	public class ViewHolder
	{
		TextView tv_name,tv_phone,tv_level;
		RelativeLayout rl_spread;
		ImageView iv_head;
	}
}
