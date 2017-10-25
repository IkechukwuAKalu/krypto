package com.ikechukwuakalu.krypto.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppUtils {

    private static final String APP_TAG = "KryptoApp";

    public static void showShortToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT)
                .show();
    }

    public static void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG)
                .show();
    }

    public static void showShortSnackbar(View v, String msg) {
        Snackbar.make(v, msg, Snackbar.LENGTH_SHORT)
                .show();
    }

    public static void debug(String msg) {
        Log.d(APP_TAG, msg);
    }

    public static <T> T createService(Class<T> serviceClass) {
         Retrofit retrofit = new Retrofit.Builder()
                 .addConverterFactory(GsonConverterFactory.create())
                 .baseUrl("https://min-api.cryptocompare.com/")
                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .build();
        return retrofit.create(serviceClass);
    }
}
