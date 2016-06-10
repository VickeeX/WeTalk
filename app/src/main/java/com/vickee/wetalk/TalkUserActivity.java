package com.vickee.wetalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class TalkUserActivity extends AppCompatActivity {
    Button sendMsg_btn;
    EditText msgText_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_user);
    }
}
