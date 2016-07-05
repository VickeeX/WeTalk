package com.vickee.wetalk.talkUser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.friend.FriendService;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum;
import com.vickee.wetalk.R;
import com.vickee.wetalk.UserTeamInfo.UserInfoActivity;
import com.vickee.wetalk.main.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TalkUserActivity extends AppCompatActivity {

    private static final String TAG = "TalkUserActivity";

    //    private TextView talkUser_tv;
    private EditText content_et;
    private Button send_btn;
    private Toolbar talk_toolbar;
    private String talkUserId;
    private String talkUserName;
    private String talkObject;
    private RecyclerView recyclerView;
    private ChatMsgListAdapter chatMsgListAdapter;
    private List<IMMessage> msg;
    private Observer<List<IMMessage>> incomingMessageObserver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_user);

        talk_toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(talk_toolbar);

        Intent intent = getIntent();
        talkUserId = intent.getStringExtra("TalkPersonId");
        talkUserName = intent.getStringExtra("TalkPersonName");
        if (talkUserName != null && talkUserName.length() != 0) {
            setTitle("好友: " + talkUserName);
        } else {
            setTitle("好友: " + talkUserId);
        }

        NIMClient.getService(MsgService.class).setChattingAccount(talkUserId, SessionTypeEnum.P2P);
        talkObject = talkUserId;


        content_et = (EditText) findViewById(R.id.msgText_et);
        send_btn = (Button) findViewById(R.id.sendMsg_btn);

        msg = new ArrayList<>();
        chatMsgListAdapter = new ChatMsgListAdapter(this, msg);

        recyclerView = (RecyclerView) findViewById(R.id.msgShow_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatMsgListAdapter);


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = content_et.getText().toString();
                if (content != null && content.length() != 0) {
                    IMMessage message;

                    message = MessageBuilder.createTextMessage(
                            talkUserId, SessionTypeEnum.P2P, content);

                    NIMClient.getService(MsgService.class).sendMessage(message, true);
                    Log.e("SendMessage", "from:" + message.getFromAccount() + ", to:" + talkUserId);
                    chatMsgListAdapter.UpdateAdapterData(message);
                    recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    content_et.setText("");
                } else {
                    Toast.makeText(TalkUserActivity.this, "请勿发送空消息", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // MessageLoader
        new MessageLoader(null, false);

        incomingMessageObserver = new Observer<List<IMMessage>>() {
            @Override
            public void onEvent(List<IMMessage> messages) {
                for (IMMessage imMessage : messages) {
                    if (imMessage.getSessionId().equals(talkObject)) {
                        msg.add(imMessage);
                    }
                }
//                Log.e("GetMessage", "size=" + msg.size());
                chatMsgListAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, true);
    }

    private class MessageLoader {

        private static final int LOAD_MESSAGE_COUNT = 20;

        private QueryDirectionEnum direction = null;

        private IMMessage anchor;
        private boolean remote;

        private boolean firstLoad = true;

        public MessageLoader(IMMessage anchor, boolean remote) {
            this.anchor = anchor;
            this.remote = remote;
            if (remote) {
                loadFromRemote();
            } else {
                loadFromLocal(anchor == null ? QueryDirectionEnum.QUERY_OLD : QueryDirectionEnum.QUERY_NEW);
            }
        }

        private RequestCallback<List<IMMessage>> callback = new RequestCallbackWrapper<List<IMMessage>>() {
            @Override
            public void onResult(int code, List<IMMessage> messages, Throwable exception) {
                if (messages != null) {
                    onMessageLoaded(messages);
                }
            }
        };

        private void loadFromLocal(QueryDirectionEnum direction) {
            this.direction = direction;
            NIMClient.getService(MsgService.class).queryMessageListEx(anchor(), direction, LOAD_MESSAGE_COUNT, true)
                    .setCallback(callback);
        }

        private void loadFromRemote() {
            this.direction = QueryDirectionEnum.QUERY_OLD;
            NIMClient.getService(MsgService.class).pullMessageHistory(anchor(), LOAD_MESSAGE_COUNT, true)
                    .setCallback(callback);
        }

        private IMMessage anchor() {
            if (msg.size() == 0) {
                return anchor == null ? MessageBuilder.createEmptyMessage(talkUserId, SessionTypeEnum.P2P, 0) : anchor;
            } else {
                int index = (direction == QueryDirectionEnum.QUERY_NEW ? msg.size() - 1 : 0);
                return msg.get(index);
            }
        }

        private void onMessageLoaded(List<IMMessage> messages) {
            int count = messages.size();

            if (remote) {
                Collections.reverse(messages);
            }

            if (firstLoad && msg.size() > 0) {
                // 在第一次加载的过程中又收到了新消息，做一下去重
                for (IMMessage message : messages) {
                    for (IMMessage item : msg) {
                        if (item.isTheSame(message)) {
                            msg.remove(item);
                            break;
                        }
                    }
                }
            }

            if (firstLoad && anchor != null) {
                msg.add(anchor);
            }

            List<IMMessage> result = new ArrayList<>();
            for (IMMessage message : messages) {
                result.add(message);
            }
            if (direction == QueryDirectionEnum.QUERY_NEW) {
                msg.addAll(result);
            } else {
                msg.addAll(0, result);
            }

            // 如果是第一次加载，updateShowTimeItem返回的就是lastShowTimeItem
            if (firstLoad) {
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
//                Log.e(TAG,"size: " + size);
//                ListViewUtil.scrollToBottom(recyclerView);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chatMsgListAdapter.notifyDataSetChanged();
                }
            });

//            Log.e(TAG, Thread.currentThread().toString());

//            refreshMessageList();
//            messageListView.onRefreshComplete(count, LOAD_MESSAGE_COUNT, true);

            firstLoad = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_user_info:
                Intent intent = new Intent(TalkUserActivity.this, UserInfoActivity.class);
                intent.putExtra("TalkPersonId", talkUserId);
                startActivity(intent);
                break;
            case R.id.menu_user_delete:
                deleteFriend();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteFriend() {
        LayoutInflater searchFriendInflater = getLayoutInflater();
        View searchFriendLayout = searchFriendInflater.inflate(R.layout.dialog_delete_friend
                , null, false);

        TextView textView = (TextView) searchFriendLayout.findViewById(R.id.delete_friend_dialog_tv);
        textView.setText("\n用户: " + talkUserName + "\n账号: " + talkUserId);

        new AlertDialog.Builder(this).setTitle("删除好友")
                .setView(searchFriendLayout)
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NIMClient.getService(FriendService.class).deleteFriend(talkUserId)
                                .setCallback(new RequestCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent = new Intent(TalkUserActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(TalkUserActivity.this
                                                , "删除好友成功", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailed(int i) {
                                        Toast.makeText(TalkUserActivity.this
                                                , "删除失败，请检查网络", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onException(Throwable throwable) {
                                    }
                                });
                    }
                }).setNegativeButton("取消", null).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver, false);
    }
}