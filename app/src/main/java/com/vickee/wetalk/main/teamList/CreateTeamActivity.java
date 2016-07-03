package com.vickee.wetalk.main.teamList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.team.TeamService;
import com.netease.nimlib.sdk.team.constant.TeamFieldEnum;
import com.netease.nimlib.sdk.team.constant.TeamTypeEnum;
import com.netease.nimlib.sdk.team.constant.VerifyTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;
import com.vickee.wetalk.R;
import com.vickee.wetalk.WeTalkApplication;
import com.vickee.wetalk.main.talkGroup.TalkGroupActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateTeamActivity extends AppCompatActivity {

    private static final String TAG = "CreateTeamActivity";

    private EditText editText;
    private EditText editIntroText;
    private EditText editMemberText;
    private Button selectMember;
    private Button createButton;

    private String userAccount;
    private List<String> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("新建群组");

        SharedPreferences sp = CreateTeamActivity.this.getApplicationContext()
                .getSharedPreferences(WeTalkApplication.USER, Context.MODE_PRIVATE);
        userAccount = sp.getString(WeTalkApplication.ACCOUNT, "");

        editText = (EditText) findViewById(R.id.create_group_name_et);
        editIntroText = (EditText) findViewById(R.id.create_group_introduce_et);
        editMemberText = (EditText) findViewById(R.id.create_group_member_et);
        selectMember = (Button) findViewById(R.id.select_member_btn);
        createButton = (Button) findViewById(R.id.createGroup_btn);

        accounts = new ArrayList<>();
        accounts.add(userAccount);
        final List<String> friendAccounts = NIMClient.getService(FriendService.class).getFriendAccounts();
        final String[] friendList = new String[friendAccounts.size()];
        friendAccounts.toArray(friendList);
//        final String[] friendList = new String[friendAccounts.size()];
        final boolean[] boolFriend = new boolean[friendAccounts.size()];
        for (int i = 0; i < friendAccounts.size(); i++) {
            boolFriend[i] = false;
//            friendList[i] = friendAccounts.get(i);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(CreateTeamActivity.this)
                .setTitle("邀请以下好友：")
                .setMultiChoiceItems(friendList, boolFriend,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    accounts.add(friendList[which]);
                                } else {
                                    accounts.remove(friendList[which]);
                                }
                            }
                        })
                .setPositiveButton("添加", null)
                .setNegativeButton("取消", null);
        builder.create();

        selectMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });

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
