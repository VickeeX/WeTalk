package com.vickee.wetalk;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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

        recent_ll = (LinearLayout)findViewById(R.id.recentTalk_ll);
        friends_ll = (LinearLayout)findViewById(R.id.friends_ll);
        team_ll = (LinearLayout) findViewById(R.id.team_ll);

        recent_iv = (ImageView)findViewById(R.id.recentTalk_iv);
        friends_iv = (ImageView)findViewById(R.id.friends_iv);
        team_iv = (ImageView)findViewById(R.id.team_iv);

        recent_tv = (TextView)findViewById(R.id.recentTalk_tv);
        friends_tv = (TextView)findViewById(R.id.friends_tv);
        team_tv = (TextView)findViewById(R.id.team_tv);

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                restartButton();
                switch (v.getId()){
                    case R.id.recentTalk_ll:
/*
                recent_iv.setImageResource(R.drawable.pressed);
*/
                        recent_tv.setTextColor(0xff1B940A);
                        initFragment(0);
                        break;
                    case R.id.friends_ll:
/*
                friends_iv.setImageResource(R.drawable.pressed);
*/
                        friends_tv.setTextColor(0xff1B940A);
                        initFragment(1);
                        break;
                    case R.id.team_ll:
/*
                team_iv.setImageResource(R.drawable.pressed);
*/
                        team_tv.setTextColor(0xff1B940A);
                        initFragment(2);
                        break;
                    default:
                        break;
                }

            }
        };

        recent_ll.setOnClickListener(listener);
        friends_ll.setOnClickListener(listener);
        team_ll.setOnClickListener(listener);

        initFragment(0);
    }

    private void initFragment(int index){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index){
            case 0:
                if(recentFragment == null){
                    recentFragment = new RecentFragment();
                    transaction.add(R.id.f1_content, recentFragment);
                }else{
                    transaction.show(recentFragment);
                }
                break;
            case 1:
                if(friendsFragment == null){
                    friendsFragment = new FriendsFragment();
                    transaction.add(R.id.f1_content, friendsFragment);
                }else{
                    transaction.show(friendsFragment);
                }
                break;
            case 2:
                if(teamFragment == null){
                    teamFragment = new TeamFragment();
                    transaction.add(R.id.f1_content, teamFragment);
                }else{
                    transaction.show(teamFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction){
        if(recentFragment != null){
            transaction.hide(recentFragment);
        }
        if(friendsFragment != null){
            transaction.hide(friendsFragment);
        }
        if(teamFragment != null){
            transaction.hide(teamFragment);
        }
    }

    private void restartButton(){
        recent_iv.setImageResource(R.drawable.logo_temp);
        friends_iv.setImageResource(R.drawable.logo_temp);
        team_iv.setImageResource(R.drawable.logo_temp);

        recent_tv.setTextColor(0xffffffff);
        friends_tv.setTextColor(0xffffffff);
        team_tv.setTextColor(0xffffffff);
    }

}



