package com.thebrodyaga.frogogo;

import android.app.Application;

import com.thebrodyaga.frogogo.di.AppComponent;
import com.thebrodyaga.frogogo.di.DaggerAppComponent;


public class App extends Application {
    public static App INSTANCE;
    public AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        appComponent = DaggerAppComponent.builder().application(this).build();
    }
}
