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
//        Log.e("FriendsERROR","size="+friends.size()+"; po0="+friends.get(0));
        friendsListAdapter.UpdateAdapterData(friends);
        friendsListAdapter.setOnItemClickListener(new FriendsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(getActivity(), friends.get(position),Toast.LENGTH_SHORT).show();
                String id = friends.get(position);

//                Bundle bundle = new Bundle();
//                bundle.putString("TalkPerson",id);
                Intent intent = new Intent(getActivity(), TalkUserActivity.class);
                intent.putExtra("TalkPerson", id);
                startActivity(intent);
            }
        });


//        test_friends_tv = (TextView)view.findViewById(R.id.test_friend_tv);
//        boolean isMyFriend = NIMClient.getService(FriendService.class).isMyFriend("user1_test");
//        if(isMyFriend){
//            test_friends_tv.setText("添加新好友");
//        }
//        else{
//            test_friends_tv.setText("暂无更新");
//        }

    }
}
