package com.vickee.wetalk.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.vickee.wetalk.DemoCache;
import com.vickee.wetalk.R;
import com.vickee.wetalk.main.MainActivity;

/**
 * Created by Vickee on 2016/6/8.
 */
public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "LoginActivity";

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
                final String account = account_et.getText().toString();
                String token = password_et.getText().toString();
                Log.e(TAG, "onClick() called with: [" + token + "]");
//                token = MD5.getStringMD5(token);

                LoginInfo loginInfo = new LoginInfo(account, token, "048bb61b76c7b682a040589998446181");
//                LoginInfo loginInfo = new LoginInfo(account, token, "048bb61b76c7b682a040589998446181");
                RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        // the auto-login can be realized here
                        Log.d(TAG, "onSuccess() called with: " + "loginInfo = [" + loginInfo + "]");

                        DemoCache.setAccount(account);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
    private String tokenFromPassword(String password) {
        String appKey = readAppKey(this);
        boolean isDemo = ("048bb61b76c7b682a040589998446181").equals(appKey);
        return isDemo ? MD5.getStringMD5(password) : password;
    }

    private static String readAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().
                    getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.vickee.wetalk.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
