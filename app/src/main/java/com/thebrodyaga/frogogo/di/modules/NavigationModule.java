package com.thebrodyaga.frogogo.di.modules;

import android.app.Application;
import android.content.Context;

import com.thebrodyaga.frogogo.frameworks.navigation.RouterTransition;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class NavigationModule {
    private Cicerone<RouterTransition> cicerone;

    public NavigationModule() {
        cicerone = Cicerone.create(new RouterTransition());
    }

    @Provides
    @Singleton
    RouterTransition provideTransitionRouter() {
        return cicerone.getRouter();
    }

    @Provides
    @Singleton
    Router provideRouter() {
        return cicerone.getRouter();
    }

    @Provides
    @Singleton
    NavigatorHolder provideNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }
}
