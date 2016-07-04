package com.vickee.wetalk.main.talkGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.vickee.wetalk.R;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        talkTeamId = intent.getStringExtra("TalkTeamId");
        talkTeamName = intent.getStringExtra("TalkTeamName");
        Log.e("GetTalkGroTup:", talkTeamId);
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
                IMMessage message;

                message = MessageBuilder.createTextMessage(
                        talkTeamId, SessionTypeEnum.Team, content);
                NIMClient.getService(MsgService.class).sendMessage(message, true);
                chatMsgListAdapter.UpdateAdapterData(message);
                content_et.setText("");
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
                Log.e("GetMessage", "size=" + msg.size());
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
                Toast.makeText(TalkGroupActivity.this, "GroupInfo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_group_exit:
                Toast.makeText(TalkGroupActivity.this, "GroupExit", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_group_dismiss:
                Toast.makeText(TalkGroupActivity.this, "GroupDismiss", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}