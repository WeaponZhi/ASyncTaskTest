package com.weaponzhi.asynctasktest.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weaponzhi.asynctasktest.ImageLoader;
import com.weaponzhi.asynctasktest.R;
import com.weaponzhi.asynctasktest.bean.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/03/16.
 */

public class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerAdapter.ImageViewHolder> {
    private List<ImageBean> mList;
    private ImageLoader imageLoader;
    public Context mContext;

    public BaseRecyclerAdapter(Activity activity,List<ImageBean> list) {
        mList = new ArrayList<>();
        mList = list;
        mContext = activity;
        imageLoader = new ImageLoader();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image,parent,false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        ImageBean imageBean = mList.get(position);
        holder.tvTitle.setText(imageBean.newsTitle);
        holder.tvContent.setText(imageBean.newsContent);
        holder.imageView.setImageResource(R.mipmap.ic_launcher);
        holder.imageView.setTag(imageBean.newsIconUrl);
        imageLoader.showImageByAsyncTask(holder.imageView,imageBean.newsIconUrl);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvTitle,tvContent;
        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
