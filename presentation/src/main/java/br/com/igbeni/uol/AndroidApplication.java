package br.com.igbeni.uol;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import br.com.igbeni.uol.internal.di.components.ApplicationComponent;
import br.com.igbeni.uol.internal.di.components.DaggerApplicationComponent;
import br.com.igbeni.uol.internal.di.modules.ApplicationModule;

/**
 * Android Main Application
 */
public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

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
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }


    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}