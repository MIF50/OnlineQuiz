package mif50.com.onlinequizapp.broadcast;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import mif50.com.onlinequizapp.common.Common;

/**
 * Created by mohamed on 11/19/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    // ctrl + o

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        handleNotification(remoteMessage.getNotification().getBody());
    }

    private void handleNotification(String body) {
        Intent pushNotification = new Intent(Common.STR_PUSH);
        pushNotification.putExtra(Common.MESSAGE_KEY,body);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
    }
}
