package com.vickee.wetalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView account_show = (TextView)findViewById(R.id.account_show);
        account_show.setText(DemoCache.getAccount());

    }
}
