package br.com.igbeni.uol.internal.di.components;

import br.com.igbeni.uol.AndroidApplication;
import br.com.igbeni.uol.internal.di.modules.ApplicationModule;
import br.com.igbeni.uol.internal.di.modules.ActivityBindingModule;
import br.com.igbeni.uol.internal.di.scopes.PerApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * A component whose lifetime is the life of the application.
 */
@PerApplication // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class
})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(AndroidApplication application);

        ApplicationComponent build();
    }

    void inject(AndroidApplication application);
}
