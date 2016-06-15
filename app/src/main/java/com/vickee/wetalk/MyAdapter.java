package com.vickee.wetalk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.nimlib.sdk.friend.model.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vickee on 2016/6/15.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.mViewHolder>{
    private Context context;
    private List<Friend> data;

    public MyAdapter(){

    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new mViewHolder(LayoutInflater.from(context).inflate(R.layout.friends_item,null));
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class mViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;

        public mViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.friends_item_tv);
        }

        public void bindView(int position){
            tv.setText(String.valueOf(data.get(position).getAccount()));
        }
    }
}
