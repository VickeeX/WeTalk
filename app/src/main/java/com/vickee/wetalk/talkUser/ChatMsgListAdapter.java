package com.vickee.wetalk.talkUser;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.vickee.wetalk.R;
import com.vickee.wetalk.WeTalkApplication;

import java.util.List;

/**
 * Created by Vickee on 2016/7/1.
 */
public class ChatMsgListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IMMessage> mDatas;
    private Context mContext;
    private String userAccount;

    public ChatMsgListAdapter(Context context, List<IMMessage> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;

        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(WeTalkApplication.USER, Context.MODE_PRIVATE);
        userAccount = sp.getString(WeTalkApplication.ACCOUNT, "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            Log.e("SendMessage: ", "viewType: " + viewType);
            return new SendTxtViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.chat_list_own_msg_item, null));
        } else {
            Log.e("SendMessage: ", "viewType: " + viewType);
            return new FromTxtViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.chat_list_message_item, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SendTxtViewHolder) {
            ((SendTxtViewHolder) holder).textView.setText(mDatas.get(position).getContent());
            Log.e("MsgSend", "status: " + mDatas.get(position).getStatus());
            if (mDatas.get(position).getStatus() != MsgStatusEnum.success) {
                ((SendTxtViewHolder) holder).send_failed.setImageResource(R.drawable.ic_error_outline_red_800_24dp);
            }
        } else if (holder instanceof FromTxtViewHolder) {
            ((FromTxtViewHolder) holder).textView.setText(mDatas.get(position).getContent());
            String nick = mDatas.get(position).getFromNick();
            if (nick != null && nick.length() != 0) {
                ((FromTxtViewHolder) holder).textView_name.setText(mDatas.get(position).getFromNick());

            } else {
                ((FromTxtViewHolder) holder).textView_name.setText(mDatas.get(position).getFromAccount());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class SendTxtViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public ImageView send_failed;

        public SendTxtViewHolder(View itemView) {
            super(itemView);
            send_failed = (ImageView) itemView.findViewById(R.id.own_msg_failed);
            textView = (TextView) itemView.findViewById(R.id.tv_item_send_txt_own);
            imageView = (ImageView) itemView.findViewById(R.id.own_person_image);
        }
    }

    class FromTxtViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView textView_name;
        public ImageView imageView;

        public FromTxtViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item_send_txt);
            textView_name = (TextView) itemView.findViewById(R.id.tv_item_send_name_txt);
            imageView = (ImageView) itemView.findViewById(R.id.person_image);
        }
    }

    public void UpdateAdapterData(List<IMMessage> datas) {
        mDatas.addAll(datas);
        Log.e("Friends: ", "size=" + mDatas.size());
        notifyDataSetChanged();
    }

    public void UpdateAdapterData(IMMessage datas) {
        mDatas.add(datas);
        Log.e("Friends: ", "size=" + mDatas.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int positon) {
        IMMessage msg = mDatas.get(positon);
        Log.e("SendMessage", "UserAccount:" + userAccount);
        Log.e("SendMessage", "FromAccount:" + msg.getFromAccount());
        if (msg.getFromAccount().equals(userAccount)) {
            return 0;
        } else {
            return 1;
        }
//        return super.getItemViewType(positon);
    }
}
