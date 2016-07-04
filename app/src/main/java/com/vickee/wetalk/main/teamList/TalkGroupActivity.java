package com.vickee.wetalk.main.teamList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.team.TeamService;
import com.vickee.wetalk.R;
import com.vickee.wetalk.main.MainActivity;
import com.vickee.wetalk.talkUser.ChatMsgListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TalkGroupActivity extends AppCompatActivity {

    //    private TextView talkUser_tv;
    private EditText content_et;
    private Button send_btn;

    private String talkTeamId;
    private String talkTeamName;
    private String talkObject;
    private RecyclerView recyclerView;
    private ChatMsgListAdapter chatMsgListAdapter;
    private List<IMMessage> msg;
    private Observer<List<IMMessage>> incomingMessageObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_group);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        talkTeamId = intent.getStringExtra("TalkTeamId");
        talkTeamName = intent.getStringExtra("TalkTeamName");
        if (talkTeamName != null && talkTeamName.length() != 0) {
            setTitle("群组: " + talkTeamName);
        } else {
            setTitle("群组: " + talkTeamId);
        }
        NIMClient.getService(MsgService.class).setChattingAccount(talkTeamId, SessionTypeEnum.Team);
        talkObject = talkTeamId;

        content_et = (EditText) findViewById(R.id.msgGroupText_et);
        send_btn = (Button) findViewById(R.id.sendGroupMsg_btn);

        msg = new ArrayList<>();
        chatMsgListAdapter = new ChatMsgListAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.msgGroupShow_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatMsgListAdapter);


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = content_et.getText().toString();

                if (content != null && content.length() != 0) {
                    IMMessage message;

                    message = MessageBuilder.createTextMessage(
                            talkTeamId, SessionTypeEnum.Team, content);
                    NIMClient.getService(MsgService.class).sendMessage(message, true);
                    chatMsgListAdapter.UpdateAdapterData(message);
                    content_et.setText("");
                } else {
                    Toast.makeText(TalkGroupActivity.this, "请勿发送空消息", Toast.LENGTH_SHORT).show();
                }

            }
        });


        incomingMessageObserver = new Observer<List<IMMessage>>() {
            @Override
            public void onEvent(List<IMMessage> messages) {
                msg.clear();
                for (IMMessage imMessage : messages) {
                    if (imMessage.getSessionId().equals(talkObject)) {
                        msg.add(imMessage);
                    }
                }
                chatMsgListAdapter.UpdateAdapterData(msg);
            }
        };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_group_info:
                Intent intent = new Intent(TalkGroupActivity.this, GroupInfoActivity.class);
                intent.putExtra("TalkTeamId", talkTeamId);
                startActivity(intent);
                break;
            case R.id.menu_group_exit:
                exitTeam();
                break;
            case R.id.menu_group_dismiss:
                Toast.makeText(TalkGroupActivity.this, "GroupDismiss", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void exitTeam() {
        LayoutInflater searchFriendInflater = getLayoutInflater();
        View searchFriendLayout = searchFriendInflater.inflate(R.layout.dialog_team_exit
                , null, false);

        TextView textView = (TextView) searchFriendLayout.findViewById(R.id.delete_team_dialog_tv);
        textView.setText("\n群名: " + talkTeamName + "\n账号: " + talkTeamId);

        new AlertDialog.Builder(this).setTitle("退出群组")
                .setView(searchFriendLayout)
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NIMClient.getService(TeamService.class).quitTeam(talkTeamId)
                                .setCallback(new RequestCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent = new Intent(TalkGroupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(TalkGroupActivity.this
                                                , "退出该群成功", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailed(int i) {
                                        Toast.makeText(TalkGroupActivity.this
                                                , "退出失败，请检查网络", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onException(Throwable throwable) {
                                    }
                                });
                    }
                }).setNegativeButton("取消", null).show();
    }
}