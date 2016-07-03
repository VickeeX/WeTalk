package com.vickee.wetalk.main.recentNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.vickee.wetalk.R;
import com.vickee.wetalk.talkUser.TalkUserActivity;
import com.vickee.wetalk.widget.DividerDecoration;

import java.util.ArrayList;
import java.util.List;


public class RecentFragment extends Fragment {

    private List<RecentContact> recentContactList;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter myRecyclerAdapter;

    public RecentFragment() { }


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


        recyclerView = (RecyclerView)view.findViewById(R.id.recentTalk_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerDecoration(getActivity()));
//        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(myRecyclerAdapter);

        NIMClient.getService(MsgService.class).queryRecentContacts()
                .setCallback(new RequestCallbackWrapper<List<RecentContact>>() {
                    @Override
                    public void onResult(int code, List<RecentContact> recents, Throwable e) {
                        // recents参数即为最近会话列表
                        if (recents != null)
                            recentContactList = recents;

                        Log.e("Recents:","size="+recentContactList.size());
                        myRecyclerAdapter.UpdateAdapterData(recentContactList);
                    }
                });

        myRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String recentId = recentContactList.get(position).getContactId();

                Intent intent = new Intent(getActivity(), TalkUserActivity.class);
                boolean isMyFriend = NIMClient.getService(FriendService.class).isMyFriend(recentId);
                if ( isMyFriend ){
                    intent.putExtra("TalkPerson", recentId);
                }else{
                    intent.putExtra("TalkGroup",recentId);
                }
                startActivity(intent);
            }
        });

    }
}
