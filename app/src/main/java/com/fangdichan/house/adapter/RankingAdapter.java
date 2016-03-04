package com.fangdichan.house.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fangdichan.house.R;
import com.fangdichan.house.entity.BonusRankInfo;
import com.fangdichan.house.entity.RankingInfo;

import java.util.List;

public class RankingAdapter extends BaseAdapter
{
	private List<BonusRankInfo> list;
	private LayoutInflater inflater;
	int maxtemp;
	int mintemp;
	int color;
	Context context;

	public RankingAdapter(Context context, List<BonusRankInfo> list)
	{
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		maxtemp = list.get(0).getPrice() + 1;
		mintemp = list.get(list.size() - 2).getPrice() - 2;
		color = 0xff4400;
//		color = 0xFF666666;
        color = color- 0xff000000;
	}

	public void changecolor(int color)
	{
		this.color = color;
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
			convertView = inflater.inflate(R.layout.ranking_item, null);
			holder.rl_text = (RelativeLayout) convertView.findViewById(R.id.rl_text);
			holder.tv_rank = (TextView) convertView.findViewById(R.id.tv_rank);
			holder.tv_rank_bg=(TextView) convertView.findViewById(R.id.tv_rank_bg);
			holder.tv_num = (TextView) convertView.findViewById(R.id.tv_ranking_people);
			holder.tv_money = (TextView) convertView.findViewById(R.id.tv_ranking_money);
			holder.tv_app_temp_progress = (TextView) convertView.findViewById(R.id.tv_app_temp_progress);
			holder.tv_empty = (TextView) convertView.findViewById(R.id.tv_empty);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		BonusRankInfo info = (BonusRankInfo) getItem(position);


		holder.tv_app_temp_progress.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, info.getPrice() - mintemp));
		holder.tv_empty.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, maxtemp - info.getPrice()));
			holder.tv_money.setText(info.getUserName()+":" + info.getPrice() + "元");
			holder.tv_num.setText("电话:"+info.getUserPhone());
			int tempcolor = 0x2f + 0xcf * (info.getPrice() - mintemp) / (maxtemp - mintemp);
			tempcolor = tempcolor * 0x1000000;
			tempcolor += color;
			holder.tv_rank_bg.setBackgroundColor(tempcolor);
		holder.tv_app_temp_progress.setBackgroundColor(tempcolor);
		holder.tv_rank.setText(""+(position+1));

		return convertView;

	}

	public class ViewHolder
	{
		TextView tv_rank, tv_num, tv_money,tv_rank_bg,tv_app_temp_progress, tv_empty;
		RelativeLayout rl_text;
	}
}
