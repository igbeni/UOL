package br.com.igbeni.uol.internal.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import br.com.igbeni.uol.AndroidApplication;
import br.com.igbeni.uol.UIThread;
import br.com.igbeni.uol.data.executor.JobExecutor;
import br.com.igbeni.uol.domain.executor.PostExecutionThread;
import br.com.igbeni.uol.domain.executor.ThreadExecutor;
import br.com.igbeni.uol.internal.di.scopes.PerApplication;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {

    @Provides
    @PerApplication
    Context provideApplicationContext(AndroidApplication application) {
        return application.getApplicationContext();
    }

    @Provides
    @PerApplication
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @PerApplication
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }
}
