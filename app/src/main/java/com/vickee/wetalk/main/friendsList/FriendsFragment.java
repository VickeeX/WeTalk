package com.vickee.wetalk.main.friendsList;

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
import com.netease.nimlib.sdk.friend.FriendService;
import com.vickee.wetalk.R;
import com.vickee.wetalk.talkUser.TalkUserActivity;

import java.util.ArrayList;
import java.util.List;


public class FriendsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //    private TextView test_riends_tv;
    private List<String> friends;
    private RecyclerView recyclerView;
    //    private MyAdapter adapter;
    private FriendsListAdapter friendsListAdapter;

    public FriendsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friends = new ArrayList<>();
        friendsListAdapter = new FriendsListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.friendsList_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(friendsListAdapter);

        List<String> friendAccounts = NIMClient.getService(FriendService.class).getFriendAccounts();
        if (friendAccounts != null)
            friends.addAll(friendAccounts);
        friendsListAdapter.UpdateAdapterData(friends);
        friendsListAdapter.setOnItemClickListener(new FriendsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = friends.get(position);

                Intent intent = new Intent(getActivity(), TalkUserActivity.class);
                intent.putExtra("TalkPerson", id);
                startActivity(intent);
            }
        });
    }
}
