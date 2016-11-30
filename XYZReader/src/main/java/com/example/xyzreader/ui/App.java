package com.example.xyzreader.ui;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by ahmed on 11/20/2016.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
