package com.vickee.wetalk;

import android.inputmethodservice.KeyboardView;
import android.opengl.ETC1;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;

/**
 * Created by Vickee on 2016/6/8.
 */
public class LoginActivity extends AppCompatActivity{

    final Button login_btn = (Button)findViewById(R.id.bt_login);
    final Button register_btn = (Button)findViewById(R.id.bt_register);
    final EditText account_et = (EditText)findViewById(R.id.et_account);
    final EditText password_et = (EditText)findViewById(R.id.et_password);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login();
            }
        });
    }

    private void login(){
        final String account = account_et.getEditableText().toString().toLowerCase();
        final String password = password_et.getEditableText().toString().toLowerCase();

        LoginInfo loginInfo = new LoginInfo(account,password);
        RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo loginInfo) {
                // the auto-login can be realized here
            };

            @Override
            public void onFailed(int i) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        };


    }

}
