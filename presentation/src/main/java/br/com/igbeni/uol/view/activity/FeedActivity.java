package br.com.igbeni.uol.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.internal.di.HasComponent;
import br.com.igbeni.uol.internal.di.components.DaggerFeedComponent;
import br.com.igbeni.uol.internal.di.components.FeedComponent;
import br.com.igbeni.uol.view.fragment.FeedFragment;

/**
 * Activity that shows a list of Users.
 */
public class FeedActivity extends BaseActivity implements HasComponent<FeedComponent> {

    private FeedComponent feedComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, FeedActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
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

}
