package com.vickee.wetalk;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

/**
 * Created by Vickee on 2016/6/8.
 */
public class LoginActivity extends AppCompatActivity{

    private Button login_btn;
    private Button register_btn;
    private EditText account_et;
    private EditText password_et;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        login_btn = (Button)findViewById(R.id.bt_login);
        register_btn = (Button)findViewById(R.id.bt_register);
        account_et = (EditText)findViewById(R.id.et_account);
        password_et = (EditText)findViewById(R.id.et_password);

        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String account = account_et.getText().toString().toLowerCase();
                final String password = password_et.getText().toString().toLowerCase();

                LoginInfo loginInfo = new LoginInfo(account,password);
                RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        // the auto-login can be realized here

                        DemoCache.setAccount(account);
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(LoginActivity.this, MainActivity.class));
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailed(int code) {
                        Toast.makeText(LoginActivity.this, "登录失败" + code, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Toast.makeText(LoginActivity.this, "登录异常", Toast.LENGTH_SHORT).show();
                    }
                };
                NIMClient.getService(AuthService.class).login(loginInfo).setCallback(callback);
            }
        });
    }

}
