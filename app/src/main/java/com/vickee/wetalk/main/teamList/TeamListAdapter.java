package com.vickee.wetalk.main.teamList;

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
 * Created by Vickee on 2016/7/2.
 */
public class TeamListAdapter  extends RecyclerView.Adapter<TeamListAdapter.tViewHolder>{
    private Context mContext;
    private List<String> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public TeamListAdapter(Context context){
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public tViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new tViewHolder(LayoutInflater.from(mContext).inflate(R.layout.team_item,null));
    }

    @Override
    public void onBindViewHolder(final tViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position));
        if (mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class tViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public tViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.team_item_tv);
            Log.e("Team: ","new viewholder");
        }
    }

////        textView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText("xid="+getPosition())
////            }
////        });

    public void UpdateAdapterData(List<String> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        Log.e("Team: ","size="+mDatas.size());
        notifyDataSetChanged();
    }

    public static interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
