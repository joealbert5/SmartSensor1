package com.smartsensor.www.com.kodart.httpzoid;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * (c) Artur Sharipov
 */
public class HttpFactory {
    public static Http create(Context context) {
        return new HttpUrlConnection(new JsonHttpSerializer(),
                new NetworkImpl((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)));
    }
}
