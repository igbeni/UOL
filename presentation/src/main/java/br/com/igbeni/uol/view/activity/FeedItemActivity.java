package br.com.igbeni.uol.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.internal.di.HasComponent;
import br.com.igbeni.uol.internal.di.components.DaggerFeedComponent;
import br.com.igbeni.uol.internal.di.components.FeedComponent;
import br.com.igbeni.uol.view.fragment.FeedItemFragment;

/**
 * Activity that shows a list of FeedItem.
 */
public class FeedItemActivity extends BaseActivity implements HasComponent<FeedComponent> {

    private static final String INTENT_EXTRA_PARAM_FEED_ITEM_ID = "INTENT_EXTRA_PARAM_FEED_ITEM_ID";
    private static final String INSTANCE_STATE_PARAM_FEED_ITEM_ID = "INSTANCE_STATE_PARAM_FEED_ITEM_ID";

    private String itemId;
    private FeedComponent feedComponent;

    public static Intent getCallingIntent(Context context, String feedItemId) {
        Intent callingIntent = new Intent(context, FeedItemActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_FEED_ITEM_ID, feedItemId);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        this.initializeActivity(savedInstanceState);
        this.initializeInjector();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putString(INSTANCE_STATE_PARAM_FEED_ITEM_ID, this.itemId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.itemId = getIntent().getStringExtra(INTENT_EXTRA_PARAM_FEED_ITEM_ID);
            addFragment(R.id.fragmentContainer, FeedItemFragment.forItem(itemId));
        } else {
            this.itemId = savedInstanceState.getString(INSTANCE_STATE_PARAM_FEED_ITEM_ID);
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
}
