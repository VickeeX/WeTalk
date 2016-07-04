package com.vickee.wetalk.main.teamList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.nimlib.sdk.team.model.Team;
import com.vickee.wetalk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vickee on 2016/7/2.
 */
public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.tViewHolder> {
    private Context mContext;
    private List<Team> mDatas;
    private OnItemClickListener mOnItemClickListener;

    public TeamListAdapter(Context context) {
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public tViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new tViewHolder(LayoutInflater.from(mContext).inflate(R.layout.team_item, null));
    }

    @Override
    public void onBindViewHolder(final tViewHolder holder, int position) {
        final Team temp = mDatas.get(position);
        final String name = temp.getName();
        final String sign = temp.getIntroduce();
        if (name != null && name.length() != 0) {
            holder.textView.setText(name);
        } else {
            holder.textView.setText(mDatas.get(position).getId());
        }
        if (sign != null && sign.length() != 0) {
            holder.textView_sign.setText(sign);
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    class tViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView textView_sign;

        public tViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.team_item_tv);
            textView_sign = (TextView) itemView.findViewById(R.id.team_item_sign_tv);
        }
    }

    public void UpdateAdapterData(List<Team> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
