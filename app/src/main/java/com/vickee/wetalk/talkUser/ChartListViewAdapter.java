package com.vickee.wetalk.talkUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.List;

/**
 * Created by Vickee on 2016/7/1.
 */
public class ChartListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<IMMessage> mDatas;
    private Context mContext;
}
