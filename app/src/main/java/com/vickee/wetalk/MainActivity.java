package com.vickee.wetalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button talkUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView account_show = (TextView)findViewById(R.id.account_show);
        account_show.setText(DemoCache.getAccount());

        talkUser = (Button)findViewById(R.id.talkUser1);
        talkUser.setOnClickListener(new View.OnClickListener(){
            @Override
           public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, TalkUserActivity.class);
                startActivity(intent);
            }
        });

    }
}
