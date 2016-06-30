package com.vickee.wetalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class TalkUserActivity extends AppCompatActivity {

    private EditText content_et;
    private Button send_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_user);

        content_et = (EditText)findViewById(R.id.msgText_et);
        send_btn = (Button)findViewById(R.id.sendMsg_btn);

    }
}
