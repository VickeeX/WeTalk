package com.vickee.wetalk;

import android.content.Context;

import com.netease.nimlib.sdk.StatusBarNotificationConfig;

/**
 * Created by Vickee on 2016/6/9.
 */
public class DemoCache {
    private static Context context;
    private static String account;
    private static StatusBarNotificationConfig notificationConfig;

    private static void clear(){
        account = null;
    }

    public static String getAccount(){
        return account;
    }

    public static void setAccount(String account){
        DemoCache.account = account;
    }

    public static void setNotificationConfig(StatusBarNotificationConfig notificationConfig) {
        DemoCache.notificationConfig = notificationConfig;
    }

    public static StatusBarNotificationConfig getNotificationConfig() {
        return notificationConfig;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DemoCache.context = context.getApplicationContext();
    }
}
