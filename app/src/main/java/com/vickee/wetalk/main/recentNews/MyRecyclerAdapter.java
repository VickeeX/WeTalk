package com.vickee.wetalk.main.recentNews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.vickee.wetalk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vickee on 2016/6/12.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.mViewHolder>{
    private List<RecentContact> mDatas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public MyRecyclerAdapter(Context context){
        this.mContext = context;
        mDatas = new ArrayList<>();

    }

    @Override
    public int getItemCount(){
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyRecyclerAdapter.mViewHolder holder, int position) {

//        holder.account.setText("xxxxxx");
//        holder.content.setText("xxxxxxxxxxxx");

        holder.account.setText(mDatas.get(position).getContactId());
        if(mDatas.get(position).getContent() != null){
            holder.content.setText(mDatas.get(position).getContent());
        }else{
            holder.content.setText("暂无最近消息");
        }

        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return new mViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.recents_item,null));
    }


    class mViewHolder extends RecyclerView.ViewHolder{
        TextView account;
        TextView content;

        public mViewHolder(View itemview){
            super(itemview);
            account = (TextView)itemview.findViewById(R.id.account_tv);
            content = (TextView)itemview.findViewById(R.id.content_tv);
            Log.e("Recents: ","new viewholder");

        }
    }

    public void UpdateAdapterData(List<RecentContact> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        Log.e("Recents: ","size="+mDatas.size());
        notifyDataSetChanged();
    }

    public static interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
