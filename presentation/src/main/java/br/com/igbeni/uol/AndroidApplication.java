package br.com.igbeni.uol;

import android.app.Activity;
import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import javax.inject.Inject;

import br.com.igbeni.uol.internal.di.components.DaggerApplicationComponent;
import br.com.igbeni.uol.internal.di.modules.ApplicationModule;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeFresco();
    }

    private void initializeFresco() {
        Fresco.initialize(this);
    }

    private void initializeInjector() {
        DaggerApplicationComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
