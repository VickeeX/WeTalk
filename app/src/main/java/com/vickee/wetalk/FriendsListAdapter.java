package com.vickee.wetalk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vickee on 2016/6/15.
 */
public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.MyViewHolder>{

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public FriendsListAdapter(Context context, List<String> datas){
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount(){
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(FriendsListAdapter.MyViewHolder holder, int position) {
        holder.account.setText(mDatas.get(position));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.friends_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView account;

        public MyViewHolder(View view){
            super(view);
            account = (TextView)view.findViewById(R.id.friends_item_tv);
        }
    }

}
