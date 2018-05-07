/*
 * (C) Copyright 2018.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Contributors:
 *      Iggor Alves
 */

package br.com.igbeni.uol.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.fernandocejas.arrow.checks.Preconditions;

import java.util.Objects;

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

    @Nullable
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Nullable
    @BindView(R.id.webview)
    WebView webView;

    @Nullable
    private FeedItemModel feedItemModel;

    private boolean isLoading;

    public FeedItemDetailFragment() {
        setRetainInstance(true);
    }

    @NonNull
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

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

        WebSettings webViewSettings = Objects.requireNonNull(this.webView).getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        this.webView.setWebViewClient(webViewClient);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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

    @Nullable
    private String currentItemId() {
        final Bundle arguments = getArguments();
        Preconditions.checkNotNull(Objects.requireNonNull(arguments), "Fragment arguments cannot be null");
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
    public void renderFeedItem(@Nullable FeedItemModel feedItemModel) {
        if (feedItemModel != null) {
            this.feedItemModel = feedItemModel;
            Objects.requireNonNull(this.webView).loadUrl(feedItemModel.getWebviewUrl());
        }
    }

    @Override
    public void showLoading() {
        isLoading = true;
        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
        Objects.requireNonNull(this.progressBar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        isLoading = false;
        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
        Objects.requireNonNull(this.progressBar).setVisibility(View.GONE);
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
        return Objects.requireNonNull(this.getActivity()).getApplicationContext();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_feed_item_detail, menu);

        if (isLoading) {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, Objects.requireNonNull(feedItemModel).getShareUrl());
                startActivity(Intent.createChooser(shareIntent, "Shearing Option"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
