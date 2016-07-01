package com.vickee.wetalk.talkUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.vickee.wetalk.R;

import java.util.ArrayList;
import java.util.List;

public class TalkUserActivity extends AppCompatActivity {

    private TextView talkUser_tv;
    private EditText content_et;
    private Button send_btn;

    private String talkUser;
    private RecyclerView recyclerView;
    private ChatMsgListAdapter chatMsgListAdapter;
    private List<IMMessage> msg;
    private Observer<List<IMMessage>> incomingMessageObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_user);

        talkUser_tv = (TextView) findViewById(R.id.talkUserName_tv);
        content_et = (EditText) findViewById(R.id.msgText_et);
        send_btn = (Button) findViewById(R.id.sendMsg_btn);

        Intent intent = getIntent();
        talkUser = intent.getStringExtra("TalkPerson");
        Log.e("GetTalkUser:", talkUser);
        talkUser_tv.setText(talkUser);


        msg = new ArrayList<>();
        chatMsgListAdapter = new ChatMsgListAdapter(this);

        recyclerView = (RecyclerView) findViewById(R.id.msgShow_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatMsgListAdapter);


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = content_et.getText().toString();

                IMMessage message = MessageBuilder.createTextMessage(
                        talkUser, SessionTypeEnum.P2P, content);

                NIMClient.getService(MsgService.class).sendMessage(message, true);
                Log.e("SendMessage", "from:" + message.getFromAccount() + ", to:" + talkUser);
                chatMsgListAdapter.UpdateAdapterData(message);

//                NIMClient.getService(MsgService.class).sendMessage(message);

                content_et.setText("");

            }
        });


        incomingMessageObserver = new Observer<List<IMMessage>>() {
            @Override
            public void onEvent(List<IMMessage> messages) {
                msg = messages;
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