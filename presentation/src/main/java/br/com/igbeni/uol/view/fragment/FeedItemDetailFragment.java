package br.com.igbeni.uol.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.internal.di.components.FeedComponent;
import br.com.igbeni.uol.model.FeedItemModel;
import br.com.igbeni.uol.presenter.FeedItemPresenter;
import br.com.igbeni.uol.view.FeedItemView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment that shows a list of FeedItem.
 */
public class FeedItemDetailFragment extends BaseFragment implements FeedItemView {

    private static final String PARAM_FEED_ITEM_ID = "PARAM_FEED_ITEM_ID";

    @Inject
    FeedItemPresenter feedItemPresenter;

    @BindView(R.id.rl_progress)
    RelativeLayout rl_progress;

    @BindView(R.id.webview)
    WebView webView;

    private FeedItemModel feedItemModel;

    private boolean isLoading;

    public FeedItemDetailFragment() {
        setRetainInstance(true);
    }

    public static FeedItemDetailFragment forItem(String itemId) {
        final FeedItemDetailFragment feedItemDetailFragment = new FeedItemDetailFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(PARAM_FEED_ITEM_ID, itemId);
        feedItemDetailFragment.setArguments(arguments);
        return feedItemDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(FeedComponent.class).inject(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_feed_item_detail, container, false);
        ButterKnife.bind(this, fragmentView);

        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();
            }
        };

        WebSettings webViewSettings = this.webView.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        this.webView.setWebViewClient(webViewClient);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.feedItemPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadFeedItem();
        }
    }

    private void loadFeedItem() {
        if (this.feedItemPresenter != null) {
            this.feedItemPresenter.initialize(currentItemId());
        }
    }

    private String currentItemId() {
        final Bundle arguments = getArguments();
        Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
        return arguments.getString(PARAM_FEED_ITEM_ID);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.feedItemPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.feedItemPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.feedItemPresenter.destroy();
    }

    @Override
    public void renderFeedItem(FeedItemModel feedItemModel) {
        if (feedItemModel != null) {
            this.feedItemModel = feedItemModel;
            this.webView.loadUrl(feedItemModel.getWebviewUrl());
        }
    }

    @Override
    public void showLoading() {
        isLoading = true;
        getActivity().invalidateOptionsMenu();
        this.rl_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        isLoading = false;
        getActivity().invalidateOptionsMenu();
        this.rl_progress.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_feed_item_detail, menu);

        if (isLoading) {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, feedItemModel.getShareUrl());
                startActivity(Intent.createChooser(shareIntent, "Shearing Option"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
