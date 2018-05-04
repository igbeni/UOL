package br.com.igbeni.uol.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.List;

import javax.inject.Inject;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.internal.di.components.FeedComponent;
import br.com.igbeni.uol.model.FeedItemModel;
import br.com.igbeni.uol.model.FeedModel;
import br.com.igbeni.uol.model.ItemModel;
import br.com.igbeni.uol.presenter.FeedPresenter;
import br.com.igbeni.uol.view.FeedView;
import br.com.igbeni.uol.view.adapter.FeedItemAdapter;
import br.com.igbeni.uol.view.adapter.FeedLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment that shows a list of FeedItems.
 */
public class FeedItemListFragment extends BaseFragment implements FeedView {

    @Inject
    FeedPresenter feedPresenter;

    @Inject
    FeedItemAdapter feedItemAdapter;

    @BindView(R.id.rv_feed)
    RecyclerView rv_feed;

    @BindView(R.id.rl_progress)
    RelativeLayout rl_progress;

    @BindView(R.id.rl_retry)
    RelativeLayout rl_retry;

    @BindView(R.id.bt_retry)
    Button bt_retry;

    private FeedItemListener feedItemListener;
    private FeedItemAdapter.OnItemClickListener onItemClickListener =
            feedItemModel -> {
                if (FeedItemListFragment.this.feedItemListener != null && feedItemModel != null) {
                    FeedItemListFragment.this.feedPresenter.onUserClicked(feedItemModel);
                }
            };

    public FeedItemListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FeedItemListener) {
            this.feedItemListener = (FeedItemListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(FeedComponent.class).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.feedPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadFeedItemList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.feedPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.feedPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.feedPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.feedItemListener = null;
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
    public void renderFeed(FeedModel feedModel) {
        if (feedModel != null) {
            this.feedItemAdapter.setFeedModel(feedModel);
        }
    }

    @Override
    public void renderItems(List<ItemModel> itemModels) {
        if (itemModels != null) {
            this.feedItemAdapter.setItems(itemModels);
        }
    }

    @Override
    public void viewFeedItem(FeedItemModel feedItemModel) {
        if (this.feedItemListener != null) {
            this.feedItemListener.onUserClicked(feedItemModel);
        }
    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.feedItemAdapter.setOnItemClickListener(onItemClickListener);
        FeedLayoutManager layoutManager = new FeedLayoutManager(context());
        this.rv_feed.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context(), layoutManager.getOrientation());
        this.rv_feed.addItemDecoration(dividerItemDecoration);
        this.rv_feed.setAdapter(feedItemAdapter);
    }

    /**
     * Loads all feedItems.
     */
    private void loadFeedItemList() {
        this.feedPresenter.initialize();
    }

    public interface FeedItemListener {
        void onUserClicked(final FeedItemModel feedItemModel);
    }
}
