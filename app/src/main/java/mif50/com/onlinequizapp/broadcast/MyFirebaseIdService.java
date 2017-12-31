package mif50.com.onlinequizapp.broadcast;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by mohamed on 11/19/17.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {
    // ctrl + o

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshToken);
    }
    /* */
    private void sendRegistrationToServer(String refreshToken) {
        Log.d("token",refreshToken);
    }
}
