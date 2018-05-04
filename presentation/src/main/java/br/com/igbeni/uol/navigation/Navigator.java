package br.com.igbeni.uol.navigation;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.igbeni.uol.view.activity.FeedItemDetailsActivity;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }

    public void navigateToFeedItem(Context context, String itemId) {
        if (context != null) {
            Intent intentToLaunch = FeedItemDetailsActivity.getCallingIntent(context, itemId);
            context.startActivity(intentToLaunch);
        }
    }
}
