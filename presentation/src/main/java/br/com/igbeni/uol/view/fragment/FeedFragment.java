package br.com.igbeni.uol.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import br.com.igbeni.uol.R;
import br.com.igbeni.uol.internal.di.components.FeedComponent;
import br.com.igbeni.uol.model.FeedModel;
import br.com.igbeni.uol.presenter.FeedPresenter;
import br.com.igbeni.uol.view.FeedView;
import butterknife.ButterKnife;

/**
 * Fragment that shows a list of Users.
 */
public class FeedFragment extends BaseFragment implements FeedView {

    @Inject
    FeedPresenter feedPresenter;

    public FeedFragment() {
        setRetainInstance(true);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.feedPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadUserList();
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
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void renderFeed(FeedModel feedModel) {
        Log.d("FeedFragment", feedModel.toString());
        Log.d("FeedFragment", feedModel.getFeedItemModels().toString());
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

    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.feedPresenter.initialize();
    }

}
