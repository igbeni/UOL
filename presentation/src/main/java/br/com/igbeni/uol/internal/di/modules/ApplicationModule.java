package br.com.igbeni.uol.internal.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import br.com.igbeni.uol.AndroidApplication;
import br.com.igbeni.uol.UIThread;
import br.com.igbeni.uol.data.executor.JobExecutor;
import br.com.igbeni.uol.data.repository.FeedDataRepository;
import br.com.igbeni.uol.domain.executor.PostExecutionThread;
import br.com.igbeni.uol.domain.executor.ThreadExecutor;
import br.com.igbeni.uol.domain.repository.FeedRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    FeedRepository provideFeedRepository(FeedDataRepository feedDataRepository) {
        return feedDataRepository;
    }
}
