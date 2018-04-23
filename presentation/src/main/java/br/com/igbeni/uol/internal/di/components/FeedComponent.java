package br.com.igbeni.uol.internal.di.components;

import br.com.igbeni.uol.internal.di.PerActivity;
import br.com.igbeni.uol.internal.di.modules.ActivityModule;
import br.com.igbeni.uol.internal.di.modules.FeedModule;
import br.com.igbeni.uol.view.fragment.FeedFragment;
import br.com.igbeni.uol.view.fragment.FeedItemFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, FeedModule.class})
public interface FeedComponent extends ActivityComponent {
    void inject(FeedFragment feedFragment);

    void inject(FeedItemFragment feedItemFragment);
}
