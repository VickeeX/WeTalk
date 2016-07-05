package com.vickee.wetalk.UserTeamInfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.friend.constant.VerifyType;
import com.netease.nimlib.sdk.friend.model.AddFriendData;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.vickee.wetalk.R;
import com.vickee.wetalk.WeTalkApplication;

public class MyInfoActivity extends AppCompatActivity {

//    private String id;
//    private String nick;
//    private String sex;
//    private String birth;
//    private String tele;
//    private String mail;
//    private String sign;
    private NimUserInfo info;
    private String userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sp = this.getApplicationContext().getSharedPreferences(WeTalkApplication.USER, Context.MODE_PRIVATE);
        userAccount = sp.getString(WeTalkApplication.ACCOUNT, "");
        NimUserInfo user = NIMClient.getService(UserService.class).getUserInfo(userAccount);

        TextView id_tv = (TextView) findViewById(R.id.my_info_id_tv);
        TextView nick_tv = (TextView) findViewById(R.id.my_info_nick_tv);
        TextView sex_tv = (TextView) findViewById(R.id.my_info_sex_tv);
        TextView birth_tv = (TextView) findViewById(R.id.my_info_birth_tv);
        TextView tele_tv = (TextView) findViewById(R.id.my_info_tele_tv);
        TextView mail_tv = (TextView) findViewById(R.id.my_info_mail_tv);
        TextView sign_tv = (TextView) findViewById(R.id.my_info_sign_tv);

        Button nick_btn = (Button) findViewById(R.id.my_info_modify_nick);
        Button sex_btn = (Button) findViewById(R.id.my_info_modify_sex);
        Button birth_btn = (Button) findViewById(R.id.my_info_modify_birth);
        Button tele_btn = (Button) findViewById(R.id.my_info_modify_tele);
        Button mail_btn = (Button) findViewById(R.id.my_info_modify_mail);
        Button sign_btn = (Button) findViewById(R.id.my_info_modify_sign);

        id_tv.setText(user.getAccount());
        if (user.getName() != null && user.getName().length() != 0) {
            nick_tv.setText(user.getName());
        }
        if (user.getGenderEnum().toString() != "UNKNOWN") {
            sex_tv.setText(user.getGenderEnum().toString());
        }
        if (user.getBirthday() != null && user.getBirthday().length() != 0) {
            birth_tv.setText(user.getBirthday());
        }
        if (user.getMobile()!= null && user.getMobile().length() != 0) {
            tele_tv.setText(user.getMobile());
        }
        if (user.getEmail()!= null && user.getEmail().length() != 0) {
            mail_tv.setText(user.getEmail());
        }
        if (user.getSignature()!= null && user.getSignature().length() != 0) {
            sign_tv.setText(user.getSignature());
        }

        nick_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MyInfoActivity.this).setTitle("添加用户")
                        .setView(searchFriendLayout)
                        .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String searchUser = editText.getText().toString();
                                NIMClient.getService(FriendService.class)
                                        .addFriend(new AddFriendData(searchUser, VerifyType.DIRECT_ADD, ""))
                                        .setCallback(new RequestCallback<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
//                                        textView.setText("添加 " + searchUser + "为好友成功");
                                                Toast.makeText(getApplicationContext(),
                                                        "添加" + searchUser + "成功", Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onFailed(int i) {
//                                        textView.setText("添加 " + searchUser + "为好友失败");
                                                Toast.makeText(getApplicationContext(),
                                                        "添加好友失败", Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onException(Throwable throwable) {

                                            }
                                        });
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });

    }

}
