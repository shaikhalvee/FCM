package com.my.fcm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras.

        // If you want a different intent fired, set the "click_action"
        // field of the notification message to the desired intent.
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.topicSubscribe:
                // Once a token is generated, we subscribe to topic.
                FirebaseMessaging.getInstance().subscribeToTopic("news");
                break;
            case R.id.token:
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Token: " + token);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
