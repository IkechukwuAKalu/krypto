package com.ikechukwuakalu.krypto.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class AppUtils {

    private static final String APP_TAG = "KryptoApp";

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                .show();
    }

    public static void debug(String msg) {
        Log.d(APP_TAG, msg);
    }

    public static void warn(String msg) {
        Log.w(APP_TAG, msg);
    }
}
