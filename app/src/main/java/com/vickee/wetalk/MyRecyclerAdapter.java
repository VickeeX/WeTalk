package com.vickee.wetalk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nrtc.engine.rawapi.toolbox.Ringer;

import java.util.List;

/**
 * Created by Vickee on 2016/6/12.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
    private List<RecentContact> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<RecentContact> datas){
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount(){
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, int position) {
        holder.account.setText(mDatas.get(position).getContactId());
        holder.content.setText(mDatas.get(position).getContent());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.recents_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView account;
        TextView content;

        public MyViewHolder(View view){
            super(view);
            account = (TextView)view.findViewById(R.id.account_tv);
            content = (TextView)view.findViewById(R.id.content_tv);

        }

    }
}
