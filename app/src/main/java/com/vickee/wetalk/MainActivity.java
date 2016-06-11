package com.vickee.wetalk;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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


    }
}
