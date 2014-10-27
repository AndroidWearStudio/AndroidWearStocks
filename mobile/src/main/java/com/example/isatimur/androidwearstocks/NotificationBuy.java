package com.example.isatimur.androidwearstocks;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Тимакс on 26.10.2014.
 */
public class NotificationBuy extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_buy);
        TextView textView=(TextView)findViewById(R.id.textView);
        Bundle extras=getIntent().getExtras();
        if(extras==null)
        {
            textView.setText("Nothingsent");
        }
        else
        {
            textView.setText(extras.getString("action2"));
        }
    }
}
