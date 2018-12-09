package com.example.loginlianxi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.loginlianxi.Bean.BannerBean;
import com.example.loginlianxi.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter {
     private Context mContext;
     private List<BannerBean.DataBean> data;

    public UserAdapter(Context mContext) {
        this.mContext = mContext;
        data = new ArrayList<>();
    }
    public void setList(List<BannerBean.DataBean> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public BannerBean.DataBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView ==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
            viewHolder.title = convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        }else {
           viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(getItem(position).getTitle());
        return convertView;
    }
    class ViewHolder{
      TextView title;
    }
}
