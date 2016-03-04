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
import com.fangdichan.house.entity.BonusInfo;
import com.fangdichan.house.entity.CommisionInfo;
import com.fangdichan.house.utils.ImageLoader;

import java.util.List;

public class CommisionAdapter extends BaseAdapter
{
	private List<BonusInfo> list;
	private LayoutInflater inflater;
	Context context;

	public CommisionAdapter(Context context, List<BonusInfo> list)
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
			convertView = inflater.inflate(R.layout.commision_item_2, null);
			holder.tv_income_from = (TextView) convertView.findViewById(R.id.tv_income_from);
			holder.tv_income_num=(TextView) convertView.findViewById(R.id.tv_income_num);
			holder.tv_income_type = (TextView) convertView.findViewById(R.id.tv_income_type);
			holder.iv_head=(ImageView) convertView.findViewById(R.id.iv_commision_head);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		BonusInfo info = (BonusInfo) getItem(position);

		holder.tv_income_num.setText("返利："+info.getPrice());
		holder.tv_income_from.setText(info.getUserName());
		holder.tv_income_type.setText("电话："+info.getUserPhone());
		ImageLoader.getInstance(context).DisplayImage(NetCore.HeadPicAddr+info.getUserHead(),holder.iv_head);
//		holder.tv_income_from.setText(info.);

		return convertView;

	}

	public class ViewHolder
	{
		TextView tv_income_num, tv_income_type, tv_income_from;
		ImageView iv_head;
	}
}
