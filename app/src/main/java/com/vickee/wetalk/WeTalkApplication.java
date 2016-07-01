package com.vickee.wetalk;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.WindowManager;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;


/**
 * Created by Vickee on 2016/6/8.
 */
public class WeTalkApplication extends Application {

    public static final String ACCOUNT = "account";
    public static final String USER = "user";

    @Override
    public void onCreate() {
        super.onCreate();

        NIMClient.init(this, loginInfo(), options());
    }

    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        options.preloadAttach = true;
        options.thumbnailSize = screenWidth / 2;

        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public int getDefaultIconResId() {
                return R.drawable.avatar_def;
            }

            @Override
            public Bitmap getTeamIcon(String tid) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId
                    , SessionTypeEnum sessionTypeEnum) {
                return null;
            }
        };
        return options;
    }

    public void setUser(String account, String token) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences(USER, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(ACCOUNT, account);
        edit.putString("token", token);
        edit.apply();
    }

    public void logout() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences(USER, MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
    }

    public LoginInfo loginInfo() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences(USER, MODE_PRIVATE);

        // 从本地读取上次登录成功时保存的用户登录信息
        String account = sp.getString(ACCOUNT, "");
        String token = sp.getString("token", "");

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token, "048bb61b76c7b682a040589998446181");
        } else {
            return null;
        }
    }

}

