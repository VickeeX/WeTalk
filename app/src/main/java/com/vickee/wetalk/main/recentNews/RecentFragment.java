package com.vickee.wetalk.main.recentNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.vickee.wetalk.R;
import com.vickee.wetalk.main.teamList.TalkGroupActivity;
import com.vickee.wetalk.talkUser.TalkUserActivity;
import com.vickee.wetalk.widget.DividerDecoration;

import java.util.ArrayList;
import java.util.List;


public class RecentFragment extends Fragment {

    private List<RecentContact> recentContactList;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;

    public RecentFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recentContactList = new ArrayList<>();
        myRecyclerAdapter = new MyRecyclerAdapter(getActivity());

        NIMClient.getService(MsgService.class).setChattingAccount
                (MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = (RecyclerView) view.findViewById(R.id.recentTalk_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerDecoration(getActivity()));
        recyclerView.setAdapter(myRecyclerAdapter);

        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable e) {
                        // recents参数即为最近会话列表
                        if (recents != null)
                            recentContactList = recents;
                        myRecyclerAdapter.UpdateAdapterData(recentContactList);
                    }
                });

        myRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent;
                String recentId = recentContactList.get(position).getContactId();
                boolean isMyFriend = NIMClient.getService(FriendService.class).isMyFriend(recentId);
                if (isMyFriend) {
                    NimUserInfo userInfo = NIMClient.getService(UserService.class).getUserInfo(recentId);
                    intent = new Intent(getActivity(), TalkUserActivity.class);
                    intent.putExtra("TalkPersonId", recentId);
                    intent.putExtra("TalkPersonName", userInfo.getName());
                } else {

                    String teamName = null;
                    final List<Team> teamList1 = NIMClient.getService(TeamService.class).queryTeamListBlock();
                    for (Team team : teamList1) {
                        if (team.getId().equals(recentId) && team.getName() != null
                                && team.getName().length() != 0) {
                            teamName = team.getName();
                        }
                    }
                    intent = new Intent(getActivity(), TalkGroupActivity.class);
                    intent.putExtra("TalkTeamId", recentId);
                    intent.putExtra("TalkTeamName", teamName);
//                    intent.putExtra("RecentsContent",recentContactList.get(position).getContent());
//                    intent.putExtra("RecentsId",recentContactList.get(position).getContactId());
                }
                startActivity(intent);
            }
        });

    }
}
