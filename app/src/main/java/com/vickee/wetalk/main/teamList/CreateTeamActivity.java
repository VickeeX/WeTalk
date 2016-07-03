package com.vickee.wetalk.main.teamList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;
import com.netease.nimlib.sdk.team.constant.TeamTypeEnum;
import com.netease.nimlib.sdk.team.constant.VerifyTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;
import com.vickee.wetalk.R;
import com.vickee.wetalk.main.talkGroup.TalkGroupActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateTeamActivity extends AppCompatActivity {

    private EditText editText;
    private EditText editIntroText;
    private EditText editMemberText;
    private Button createButton;

    private List<String> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("新建群组");

        editText = (EditText) findViewById(R.id.create_group_name_et);
        editIntroText = (EditText) findViewById(R.id.create_group_introduce_et);
        editMemberText = (EditText) findViewById(R.id.create_group_member_et);
        createButton = (Button) findViewById(R.id.createGroup_btn);

        accounts = new ArrayList<>();
        accounts.add("vickee");
        accounts.add("user1_test");

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 群组类型
                TeamTypeEnum type = TeamTypeEnum.Advanced;
                HashMap<TeamFieldEnum, Serializable> fields = new HashMap<TeamFieldEnum, Serializable>();
                fields.put(TeamFieldEnum.Name, editText.getText().toString());
                fields.put(TeamFieldEnum.Introduce, editIntroText.getText().toString());
                fields.put(TeamFieldEnum.VerifyType, VerifyTypeEnum.Free);
                NIMClient.getService(TeamService.class).createTeam(fields, TeamTypeEnum.Normal, "", accounts)
                        .setCallback(new RequestCallback<Team>() {
                            @Override
                            public void onSuccess(Team team) {
                                Intent intent = new Intent(CreateTeamActivity.this, TalkGroupActivity.class);
                                intent.putExtra("TalkGroup", team.getId());
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailed(int i) {
                                Toast.makeText(CreateTeamActivity.this, "创建失败 ", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onException(Throwable throwable) {
                            }
                        });
            }
        });

    }

}
