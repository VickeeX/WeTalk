package com.vickee.wetalk.main.talkGroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    private String talkGroup;
    private String talkObject;
    private RecyclerView recyclerView;
    private ChatMsgListAdapter chatMsgListAdapter;
    private List<IMMessage> msg;
    private Observer<List<IMMessage>> incomingMessageObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.talkGroup_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        talkGroup = intent.getStringExtra("TalkGroup");
        Log.e("GetTalkGroup:", talkGroup);
        setTitle("群组: " + talkGroup);
        NIMClient.getService(MsgService.class).setChattingAccount(talkGroup, SessionTypeEnum.Team);
        talkObject = talkGroup;

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
                        talkGroup, SessionTypeEnum.Team, content);
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
}


//        extends AppCompatActivity {
//

//
//
//    }
//
//}
