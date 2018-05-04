package br.com.igbeni.uol.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

    /*
    @BindView(R.id.rl_retry)
    RelativeLayout rl_retry;

    @BindView(R.id.title)
    TextView textViewTitle;

    @BindView(R.id.updated)
    TextView textViewUpdated;

    @BindView(R.id.thumb)
    SimpleDraweeView draweeViewThumb;
    */

    @BindView(R.id.webview)
    WebView webView;

    private FeedItemModel feedItemModel;

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

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                FeedItemDetailFragment.this.rl_progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                FeedItemDetailFragment.this.rl_progress.setVisibility(View.GONE);
            }
        });
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
//        /*
        if (feedItemModel != null) {
            this.feedItemModel = feedItemModel;
            /*
            this.textViewTitle.setText(feedItemModel.getTitle());
            this.textViewUpdated.setText(Utils.formatDate(feedItemModel.getUpdated()));

            if (feedItemModel.getThumb() != null) {
                Uri uri = Uri.parse(feedItemModel.getThumb());
                if (uri != null) {
                    this.draweeViewThumb.setImageURI(uri);
                    this.draweeViewThumb.setVisibility(View.VISIBLE);
                } else {
                    this.draweeViewThumb.setVisibility(View.GONE);
                }
            } else {
                this.draweeViewThumb.setVisibility(View.GONE);
            }
            */
            this.webView.loadUrl(feedItemModel.getWebviewUrl());
        }
    }

    @Override
    public void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
//        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
//        this.rl_retry.setVisibility(View.GONE);
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
        MenuItem menuItem = menu.findItem(R.id.action_share);
        if (feedItemModel != null) {
            ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareFeedItemIntent(feedItemModel));
            } else {
                Log.d(FeedItemDetailFragment.class.getSimpleName(), "Share Action Provider is null?");
            }
        }
    }

    private Intent createShareFeedItemIntent(FeedItemModel feedItemModel) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, feedItemModel.getShareUrl());
        return shareIntent;
    }
}
