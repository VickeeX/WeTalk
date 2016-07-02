package com.vickee.wetalk.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.vickee.wetalk.R;
import com.vickee.wetalk.main.friendsList.FriendsFragment;
import com.vickee.wetalk.main.recentNews.RecentFragment;
import com.vickee.wetalk.main.teamList.TeamFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    private ImageButton search_button;

    private Toolbar toolbar;

    private LinearLayout recent_ll;
    private LinearLayout friends_ll;
    private LinearLayout team_ll;

    private ImageView recent_iv;
    private ImageView friends_iv;
    private ImageView team_iv;

    private TextView recent_tv;
    private TextView friends_tv;
    private TextView team_tv;

    private Fragment recentFragment;
    private Fragment friendsFragment;
    private Fragment teamFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        search_button = (ImageButton) findViewById(R.id.search_btn);

        recent_ll = (LinearLayout) findViewById(R.id.recentTalk_ll);
        friends_ll = (LinearLayout) findViewById(R.id.friends_ll);
        team_ll = (LinearLayout) findViewById(R.id.team_ll);

        recent_iv = (ImageView) findViewById(R.id.recentTalk_iv);
        friends_iv = (ImageView) findViewById(R.id.friends_iv);
        team_iv = (ImageView) findViewById(R.id.team_iv);

        recent_tv = (TextView) findViewById(R.id.recentTalk_tv);
        friends_tv = (TextView) findViewById(R.id.friends_tv);
        team_tv = (TextView) findViewById(R.id.team_tv);

        final VerifyType verifyType = VerifyType.DIRECT_ADD;

//        search_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NIMClient.getService(FriendService.class)
//                        .addFriend(new AddFriendData("user2_test", verifyType, ""))
//                        .setCallback(new RequestCallback<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(getApplicationContext(),
//                                        "添加好友成功", Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onFailed(int i) {
//                                Toast.makeText(getApplicationContext(),
//                                        "添加好友失败", Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onException(Throwable throwable) {
//
//                            }
//                        });
//            }
//        });


        recent_ll.setOnClickListener(this);
        friends_ll.setOnClickListener(this);
        team_ll.setOnClickListener(this);

        initFragment(0);
    }

    private void initFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (recentFragment == null) {
                    recentFragment = new RecentFragment();
                    transaction.add(R.id.f1_content, recentFragment);
                } else {
                    transaction.show(recentFragment);
                }
                break;
            case 1:
                if (friendsFragment == null) {
                    friendsFragment = new FriendsFragment();
                    transaction.add(R.id.f1_content, friendsFragment);
                } else {
                    transaction.show(friendsFragment);
                }
                break;
            case 2:
                if (teamFragment == null) {
                    teamFragment = new TeamFragment();
                    transaction.add(R.id.f1_content, teamFragment);
                } else {
                    transaction.show(teamFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (recentFragment != null) {
            transaction.hide(recentFragment);
        }
        if (friendsFragment != null) {
            transaction.hide(friendsFragment);
        }
        if (teamFragment != null) {
            transaction.hide(teamFragment);
        }
    }

    private void restartButton() {
        recent_iv.setImageResource(R.drawable.logo_temp);
        friends_iv.setImageResource(R.drawable.logo_temp);
        team_iv.setImageResource(R.drawable.logo_temp);

        recent_tv.setTextColor(recent_tv.getResources().getColor(R.color.black));
        friends_tv.setTextColor(friends_tv.getResources().getColor(R.color.black));
        team_tv.setTextColor(team_tv.getResources().getColor(R.color.black));
    }

    @Override
    public void onClick(View v) {
        restartButton();
        switch (v.getId()) {
            case R.id.recentTalk_ll:
//                recent_iv.setImageResource(R.drawable.pressed);
                recent_tv.setTextColor(recent_tv.getResources().getColor(R.color.colorPrimary));
                initFragment(0);
                break;
            case R.id.friends_ll:
//                friends_iv.setImageResource(R.drawable.pressed);
                friends_tv.setTextColor(friends_tv.getResources().getColor(R.color.colorPrimary));
                initFragment(1);
                break;
            case R.id.team_ll:
//                team_iv.setImageResource(R.drawable.pressed);
                team_tv.setTextColor(team_tv.getResources().getColor(R.color.colorPrimary));
                initFragment(2);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_find:
                Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}



