package com.ikechukwuakalu.krypto;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class BaseApplication extends Application {

    public RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        //setupLeakWatcher();
    }

    private void setupLeakWatcher() {
        if (LeakCanary.isInAnalyzerProcess(this))
            return;
        refWatcher = LeakCanary.install(this);
    }
}
