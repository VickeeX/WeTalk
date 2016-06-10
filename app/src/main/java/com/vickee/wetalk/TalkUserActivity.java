package com.vickee.wetalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.model.IMMessage;

public class TalkUserActivity extends AppCompatActivity {
    Button sendMsg_btn;
    EditText msgText_et;
    String sessionId;
    String content;
    String sessionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_user);

        msgText_et = (EditText)findViewById(R.id.msgText_et);
        sendMsg_btn = (Button)findViewById(R.id.senMsg_et);
        sessionId = "user1_test";

        sendMsg_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                IMMessage message = MessageBuilder.createTextMessage(
                        sessionId, // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                        sessionType, // 聊天类型，单聊或群组
                        content // 文本内容
                );
                NIMClient.getService(MsgService.class).sendMessage(message);
            }
        });
    }
}
