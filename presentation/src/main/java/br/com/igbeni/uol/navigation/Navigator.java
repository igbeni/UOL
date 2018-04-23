package br.com.igbeni.uol.navigation;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.igbeni.uol.view.activity.FeedActivity;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

  @Inject
  public Navigator() {
    //empty
  }

  public void navigateToFeed(Context context) {
    if (context != null) {
      Intent intentToLaunch = FeedActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }
}
