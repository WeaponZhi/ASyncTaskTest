package com.weaponzhi.asynctasktest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weaponzhi.asynctasktest.ImageLoader;
import com.weaponzhi.asynctasktest.R;
import com.weaponzhi.asynctasktest.bean.ImageBean;

import java.util.List;

/**
 * BaseListView ListView
 * <p>
 * author: 张冠之 <br>
 * time:   2017/03/16 20:14 <br>
 * GitHub: https://github.com/WeaponZhi
 * blog:   http://weaponzhi.online
 * CSDN:   http://blog.csdn.net/qq_34795285
 * </p>
 */

public class BaseListView extends BaseAdapter {
    private List<ImageBean> mList;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader;

    public BaseListView(Context context,List<ImageBean> list){
        mList = list;
        mInflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader();
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_image,null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.img);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        viewHolder.tvTitle.setText(mList.get(position).newsTitle);
        viewHolder.tvContent.setText(mList.get(position).newsContent);
        viewHolder.ivIcon.setTag(mList.get(position).newsIconUrl);
        imageLoader.showImageByAsyncTask(viewHolder.ivIcon,mList.get(position).newsIconUrl);
        return convertView;
    }

    class ViewHolder {
        public TextView tvTitle, tvContent;
        public ImageView ivIcon;
    }
}
