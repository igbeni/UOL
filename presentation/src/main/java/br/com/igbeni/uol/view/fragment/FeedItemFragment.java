package br.com.igbeni.uol.view.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.Date;

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
public class FeedItemFragment extends BaseFragment implements FeedItemView {

    private static final String PARAM_FEED_ITEM_ID = "PARAM_FEED_ITEM_ID";

    @Inject
    FeedItemPresenter feedItemPresenter;

    @BindView(R.id.rl_progress)
    RelativeLayout rl_progress;

    @BindView(R.id.rl_retry)
    RelativeLayout rl_retry;

    @BindView(R.id.title)
    TextView textViewTitle;

    @BindView(R.id.updated)
    TextView textViewUpdated;

    @BindView(R.id.thumb)
    SimpleDraweeView draweeViewThumb;

    public FeedItemFragment() {
        setRetainInstance(true);
    }

    public static FeedItemFragment forItem(String itemId) {
        final FeedItemFragment feedItemFragment = new FeedItemFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(PARAM_FEED_ITEM_ID, itemId);
        feedItemFragment.setArguments(arguments);
        return feedItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(FeedComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_feed_item, container, false);
        ButterKnife.bind(this, fragmentView);
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
            this.textViewTitle.setText(feedItemModel.getTitle());
            Date date = new Date(feedItemModel.getUpdated());
            this.textViewUpdated.setText(date.toString());

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
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }
}
