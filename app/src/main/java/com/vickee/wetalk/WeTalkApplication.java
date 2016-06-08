package com.vickee.wetalk;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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
public class WeTalkApplication extends Application{
    @Override
    public void onCreate(){
        super.onCreate();

        NIMClient.init(this, loginInfo(), options());
    }

    private SDKOptions options(){
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

    private LoginInfo loginInfo() {
        return null;
    }
}

