package com.vickee.wetalk.talkUser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.vickee.wetalk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vickee on 2016/7/1.
 */
public class ChatMsgListAdapter extends RecyclerView.Adapter<ChatMsgListAdapter.cViewHolder>{
    private List<IMMessage> mDatas;
    private Context mContext;

    public ChatMsgListAdapter(Context context){
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public cViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new cViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.chat_list_message_item,null));
    }

    @Override
    public void onBindViewHolder(cViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class cViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;

        public cViewHolder(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.tv_item_send_txt);
            imageView = (ImageView)itemView.findViewById(R.id.person_image);
        }
    }

    public void UpdateAdapterData(List<IMMessage> datas){
        mDatas.addAll(datas);
        Log.e("Friends: ","size="+mDatas.size());
        notifyDataSetChanged();
    }
}
