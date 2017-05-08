package com.smartsensor.www;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.smartsensor.www.com.kodart.httpzoid.Http;
import com.smartsensor.www.com.kodart.httpzoid.HttpFactory;

/**
 * Created by joeal_000 on 5/7/2017.
 */

public class PushNotification extends FirebaseInstanceIdService {
    final String TAG = "token";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        // TODO: Implement this method to send any registration to your app's servers.

        Http http = HttpFactory.create(this);
        http.post("http://ardusensor.tk/api/v1.0/addID")
                .data("id: " + refreshedToken)
                .send();
    }
}
