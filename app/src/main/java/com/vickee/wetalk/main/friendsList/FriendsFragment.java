package com.vickee.wetalk.main.friendsList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.FriendServiceObserve;
import com.netease.nimlib.sdk.friend.model.Friend;
import com.netease.nimlib.sdk.friend.model.FriendChangedNotify;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.vickee.wetalk.R;
import com.vickee.wetalk.talkUser.TalkUserActivity;
import com.vickee.wetalk.widget.DividerDecoration;

import java.util.List;


public class FriendsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //    private TextView test_riends_tv;
    private RecyclerView recyclerView;
    //    private MyAdapter adapter;
    private FriendsListAdapter friendsListAdapter;
    private List<NimUserInfo> users;

    public FriendsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final List<String> friendAccounts = NIMClient.getService(FriendService.class).getFriendAccounts();
        users = NIMClient.getService(UserService.class).getUserInfoList(friendAccounts);

        friendsListAdapter = new FriendsListAdapter(getActivity(), users);
        recyclerView = (RecyclerView) view.findViewById(R.id.friendsList_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerDecoration(getActivity()));
        recyclerView.setAdapter(friendsListAdapter);


        Observer<FriendChangedNotify> friendChangedNotifyObserver = new Observer<FriendChangedNotify>() {
            @Override
            public void onEvent(FriendChangedNotify friendChangedNotify) {
                List<Friend> addedOrUpdatedFriends = friendChangedNotify.getAddedOrUpdatedFriends(); // 新增的好友
                List<String> addF = null;
                for (Friend friend : addedOrUpdatedFriends) {
                    addF.add(friend.getAccount());
                }
                Log.e("NewFriend:", addF.toString());
                users.addAll(NIMClient.getService(UserService.class).getUserInfoList(addF));
                friendsListAdapter.notifyDataSetChanged();
            }
        };
        NIMClient.getService(FriendServiceObserve.class).observeFriendChangedNotify(friendChangedNotifyObserver, true);

        friendsListAdapter.setOnItemClickListener(new FriendsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(friendAccounts.get(position));
                Intent intent = new Intent(getActivity(), TalkUserActivity.class);
                intent.putExtra("TalkPersonId", friendAccounts.get(position));
                if (!TextUtils.isEmpty(user.getName())) {
                    intent.putExtra("TalkPersonName", user.getName());
                }
                startActivity(intent);
            }
        });
    }
}
