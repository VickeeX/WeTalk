package com.vickee.wetalk.UserTeamInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.vickee.wetalk.R;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("个人资料");

        Intent intent = getIntent();
        String talkUserId = intent.getStringExtra("TalkPersonId");
        NimUserInfo userInfo = NIMClient.getService(UserService.class).getUserInfo(talkUserId);

        TextView info_name = (TextView) findViewById(R.id.user_info_name_tv);
        TextView info_id = (TextView) findViewById(R.id.user_info_id_tv);
        TextView info_sex = (TextView) findViewById(R.id.user_info_sex_tv);
        TextView info_birth = (TextView) findViewById(R.id.user_info_birth_tv);
        TextView info_tele = (TextView) findViewById(R.id.user_info_tele_tv);
        TextView info_mail = (TextView) findViewById(R.id.user_info_mail_tv);
        TextView info_sign = (TextView) findViewById(R.id.user_info_sign_tv);


        info_id.setText(userInfo.getAccount());

        if (userInfo.getName() != null && userInfo.getName().length() != 0) {
            info_name.setText(userInfo.getName());
        }
        if (userInfo.getGenderEnum().toString() != "UNKNOWN") {
            info_sex.setText(userInfo.getGenderEnum().toString());
        }
        if (userInfo.getBirthday() != null && userInfo.getBirthday().length() != 0) {
            info_birth.setText(userInfo.getBirthday());
        }
        if (userInfo.getMobile() != null && userInfo.getMobile().length() != 0) {
            info_tele.setText(userInfo.getMobile());
        }
        if (userInfo.getEmail() != null && userInfo.getEmail().length() != 0) {
            info_mail.setText(userInfo.getEmail());
        }
        if (userInfo.getSignature() != null && userInfo.getSignature().length() != 0) {
            info_sign.setText(userInfo.getSignature());
        }
    }
}
