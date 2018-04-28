package br.com.igbeni.uol.view.activity;

import android.os.Bundle;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.internal.di.HasComponent;
import br.com.igbeni.uol.internal.di.components.DaggerFeedComponent;
import br.com.igbeni.uol.internal.di.components.FeedComponent;
import br.com.igbeni.uol.model.FeedItemModel;
import br.com.igbeni.uol.view.fragment.FeedFragment;

/**
 * Activity that shows a list of FeddItem.
 */
public class FeedActivity extends BaseActivity implements HasComponent<FeedComponent>,
        FeedFragment.FeedItemListener {

    private FeedComponent feedComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        this.initializeInjector();

        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new FeedFragment());
        }
    }

    private void initializeInjector() {
        this.feedComponent = DaggerFeedComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public FeedComponent getComponent() {
        return feedComponent;
    }

    @Override
    public void onUserClicked(FeedItemModel feedItemModel) {
        this.navigator.navigateToFeedItem(this, feedItemModel.getId());
    }
}
