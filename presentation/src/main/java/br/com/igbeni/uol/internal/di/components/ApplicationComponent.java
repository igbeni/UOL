package br.com.igbeni.uol.internal.di.components;


import android.content.Context;

import javax.inject.Singleton;

import br.com.igbeni.uol.domain.executor.PostExecutionThread;
import br.com.igbeni.uol.domain.executor.ThreadExecutor;
import br.com.igbeni.uol.domain.repository.FeedRepository;
import br.com.igbeni.uol.internal.di.modules.ApplicationModule;
import br.com.igbeni.uol.view.activity.BaseActivity;
import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    FeedRepository feedRepository();
}
