package com.vickee.wetalk.main.recentNews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.vickee.wetalk.R;
import com.vickee.wetalk.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vickee on 2016/6/12.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.mViewHolder> {

    private Context mContext;

    private List<RecentContact> mDatas;

    private OnItemClickListener mOnItemClickListener;

    public MyRecyclerAdapter(Context context) {
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(final MyRecyclerAdapter.mViewHolder holder, int position) {
        RecentContact recentContact = mDatas.get(position);
        String recentId = recentContact.getContactId();
        String accountSet = null;

        final List<Team> teamList1 = NIMClient.getService(TeamService.class).queryTeamListBlock();

        boolean isMyFriend = NIMClient.getService(FriendService.class).isMyFriend(recentId);
        if (isMyFriend) {
            NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(recentId);
            accountSet = user.getName();
        } else {
            for (Team team : teamList1) {
                if (team.getId().equals(recentId) && team.getName() != null
                        && team.getName().length() != 0) {
                    accountSet = team.getName();
                    break;
                } else {
                    accountSet = recentId;
                }
            }
        }
        holder.account.setText(accountSet);
        holder.time.setText(Utils.format(recentContact.getTime()));
        if (recentContact.getContent() != null) {
            holder.content.setText(recentContact.getContent());
        } else {
            holder.content.setText("暂无最近消息");
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
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new mViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.recents_item, parent, false));
    }


    class mViewHolder extends RecyclerView.ViewHolder {
        TextView account;
        TextView content;
        TextView time;

        public mViewHolder(View itemview) {
            super(itemview);
            account = (TextView) itemview.findViewById(R.id.account_tv);
            content = (TextView) itemview.findViewById(R.id.content_tv);
            time = (TextView) itemview.findViewById(R.id.time_tv);
            Log.e("Recents: ", "new viewholder");

        }
    }

    public void UpdateAdapterData(List<RecentContact> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        Log.e("Recents: ", "size=" + mDatas.size());
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
