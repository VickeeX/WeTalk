package com.vickee.wetalk.main.friendsList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vickee.wetalk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vickee on 2016/6/15.
 */
public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.mViewHolder>{

    private List<String> mDatas;
    private Context mContext;


    public FriendsListAdapter(Context context){
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new mViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.friends_item,null));
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public mViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.friends_item_tv);
        Log.e("Friends: ","new viewholder");
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText("xid="+getPosition())
//            }
//        });
    }
}

    public void UpdateAdapterData(List<String> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        Log.e("Friends: ","size="+mDatas.size());
        notifyDataSetChanged();
    }
}
