package com.vickee.wetalk.main.teamList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.model.Team;
import com.vickee.wetalk.R;
import com.vickee.wetalk.utils.Utils;

public class GroupInfoActivity extends AppCompatActivity {

    private String talkTeamId;
    private String name;
    private String id;
    private String creator;
    private String create_time;
    private String intro;
    private int member_count;
    private int member_limit;
    private Team t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        talkTeamId = intent.getStringExtra("TalkTeamId");

        TextView info_name = (TextView) findViewById(R.id.team_info_name_tv);
        TextView info_id = (TextView) findViewById(R.id.team_info_id_tv);
        TextView info_creator = (TextView) findViewById(R.id.team_info_creator_tv);
        TextView info_create_time = (TextView) findViewById(R.id.team_info_create_time_tv);
        TextView info_intro = (TextView) findViewById(R.id.team_info_intro_tv);
        TextView info_member_count = (TextView) findViewById(R.id.team_info_member_count_tv);
        TextView info_member_limit = (TextView) findViewById(R.id.team_info_member_limit_tv);

        t = NIMClient.getService(TeamService.class).queryTeamBlock(talkTeamId);
        name = t.getName();
        id = t.getId();
        creator = t.getCreator();
        create_time = Utils.format(t.getCreateTime());
        intro = t.getIntroduce();
        member_count = t.getMemberCount();
        member_limit = t.getMemberLimit();
        Log.e("TeamInfo", "id" + id + "\ncount_member:" + member_count + "\ncreator:" + creator + "\ncreate_time" + create_time + "\nmember_limit" + member_limit);

        info_id.setText(id);
        if (name != null && name.length() != 0) {
            info_name.setText(name);
        }
        if (intro != null && intro.length() != 0) {
            info_intro.setText(intro);
        }
        if (creator != null && creator.length() != 0) {
            info_creator.setText(creator);
        }
        if (create_time != null && create_time.length() != 0) {
            info_create_time.setText(create_time);
        }
        if (member_count != 0) {
            info_member_count.setText(member_count+"人");
        }
        if (member_limit != 0) {
            info_member_limit.setText(member_limit+"人");
        }
    }
}

