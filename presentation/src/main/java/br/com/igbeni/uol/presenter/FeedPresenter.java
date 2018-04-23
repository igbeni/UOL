package br.com.igbeni.uol.presenter;

import javax.inject.Inject;

import br.com.igbeni.uol.domain.Feed;
import br.com.igbeni.uol.domain.exception.DefaultErrorBundle;
import br.com.igbeni.uol.domain.exception.ErrorBundle;
import br.com.igbeni.uol.domain.interactor.DefaultObserver;
import br.com.igbeni.uol.domain.interactor.GetFeed;
import br.com.igbeni.uol.exception.ErrorMessageFactory;
import br.com.igbeni.uol.internal.di.PerActivity;
import br.com.igbeni.uol.mapper.FeedModelDataMapper;
import br.com.igbeni.uol.model.FeedModel;
import br.com.igbeni.uol.view.FeedView;
import io.reactivex.annotations.NonNull;

@PerActivity
public class FeedPresenter implements Presenter {

    private FeedView viewFeedView;

    private final GetFeed getFeedUseCase;
    private final FeedModelDataMapper feedModelDataMapper;

    @Inject
    public FeedPresenter(GetFeed getFeedUseCase, FeedModelDataMapper feedModelDataMapper) {
        this.getFeedUseCase = getFeedUseCase;
        this.feedModelDataMapper = feedModelDataMapper;
    }

    public void setView(@NonNull FeedView view) {
        this.viewFeedView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getFeedUseCase.dispose();
        this.viewFeedView = null;
    }

    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        this.loadUserList();
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserList();
    }

    private void showViewLoading() {
        this.viewFeedView.showLoading();
    }

    private void hideViewLoading() {
        this.viewFeedView.hideLoading();
    }

    private void showViewRetry() {
        this.viewFeedView.showRetry();
    }

    private void hideViewRetry() {
        this.viewFeedView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewFeedView.context(),
                errorBundle.getException());
        this.viewFeedView.showError(errorMessage);
    }

    private void showFeedInView(Feed feed) {
        final FeedModel feedModel = this.feedModelDataMapper.transform(feed);
        this.viewFeedView.renderFeed(feedModel);
    }

    private void getUserList() {
        this.getFeedUseCase.execute(new FeedObserver(), null);
    }

    private final class FeedObserver extends DefaultObserver<Feed> {

        @Override
        public void onComplete() {
            FeedPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            FeedPresenter.this.hideViewLoading();
            FeedPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            FeedPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Feed feed) {
            FeedPresenter.this.showFeedInView(feed);
        }
    }
}
