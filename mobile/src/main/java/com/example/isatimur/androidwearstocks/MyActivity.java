package com.example.isatimur.androidwearstocks;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.backend.module.quotesapi.quotesApi.model.QuoteBean;


public class MyActivity extends Activity {
    int notificationID = 0;
    int numNotifications = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        final EditText txtTicker = (EditText) findViewById(R.id.txtTicker);
        final TextView txtPrice = (TextView) findViewById(R.id.txtPrice);
        final TextView txtChange = (TextView) findViewById(R.id.txtChange);
        Button btnQuote = (Button) findViewById(R.id.btnGetQuote);
        btnQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String strTicker = txtTicker.getText().toString();
                    QuoteBean b = new GetQuoteTask().execute(strTicker).get();
                    txtPrice.setText(b.getPrice());
                    txtChange.setText(b.getChange());
                    doNotifications();
                } catch (Exception ex) {
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void doNotifications()
    {
        notificationID++;
        Intent viewIntent = new Intent(this, NotificationResponse.class);
        viewIntent.putExtra("notificationID", notificationID);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(this,0,viewIntent,0);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(viewIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);

        Intent buyIntent = new Intent(this,NotificationBuy.class);
        buyIntent.putExtra("action2", "You triggered a buy command");
        PendingIntent buyPendingIntent = PendingIntent.getActivity(this,0,buyIntent,0);

        Intent sellIntent = new Intent(this,NotificationBuy.class);
        buyIntent.putExtra("action2", "You triggered a sell command");
        PendingIntent sellPendingIntent = PendingIntent.getActivity(this,0,sellIntent,0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.cloud_icon)
                .setContentTitle("Quote for GOOG")
                .setContentText("GOOG has changed by 2%")
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.buy_icon, "Buy", buyPendingIntent)
                .addAction(R.drawable.sell_icon, "Sell", sellPendingIntent);

        notificationBuilder.setNumber(++numNotifications);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(notificationID, notificationBuilder.build());


    }
}
