package com.vickee.wetalk.main.friendsList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.vickee.wetalk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vickee on 2016/6/15.
 */
public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.mViewHolder> {

    private List<String> mDatas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private NimUserInfo friendInfo;


    public FriendsListAdapter(Context context) {
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new mViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.friends_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, int position) {
        friendInfo = NIMClient.getService(UserService.class).getUserInfo(mDatas.get(position));
        holder.textView.setText(friendInfo.getName());
        if (friendInfo.getSignature() != null && friendInfo.getSignature().length() != 0) {
            holder.textView_sign.setText(friendInfo.getSignature());
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

    class mViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView textView_sign;

        public mViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.friends_item_tv);
            textView_sign = (TextView) itemView.findViewById(R.id.friends_item_sign_tv);
        }
    }

    public void UpdateAdapterData(List<String> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
//        Log.e("Friends: ", "size=" + mDatas.size());
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
