package br.com.igbeni.uol.presenter;

import javax.inject.Inject;

import br.com.igbeni.uol.domain.FeedItem;
import br.com.igbeni.uol.domain.exception.DefaultErrorBundle;
import br.com.igbeni.uol.domain.exception.ErrorBundle;
import br.com.igbeni.uol.domain.interactor.GetFeedItem;
import br.com.igbeni.uol.exception.ErrorMessageFactory;
import br.com.igbeni.uol.internal.di.PerActivity;
import br.com.igbeni.uol.mapper.FeedModelDataMapper;
import br.com.igbeni.uol.model.FeedItemModel;
import br.com.igbeni.uol.view.FeedItemView;
import io.reactivex.annotations.NonNull;
import io.reactivex.subscribers.DisposableSubscriber;

@PerActivity
public class FeedItemPresenter implements Presenter {

    private final GetFeedItem getFeedItemUseCase;
    private final FeedModelDataMapper feedModelDataMapper;
    private FeedItemView viewFeedItemView;

    @Inject
    public FeedItemPresenter(GetFeedItem getFeedItemUseCase, FeedModelDataMapper feedModelDataMapper) {
        this.getFeedItemUseCase = getFeedItemUseCase;
        this.feedModelDataMapper = feedModelDataMapper;
    }

    public void setView(@NonNull FeedItemView view) {
        this.viewFeedItemView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.viewFeedItemView = null;
    }

    public void initialize(String itemId) {
        this.loadFeedItem(itemId);
    }

    private void loadFeedItem(String itemId) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getFeedItem(itemId);
    }

    private void showViewLoading() {
        this.viewFeedItemView.showLoading();
    }

    private void hideViewLoading() {
        this.viewFeedItemView.hideLoading();
    }

    private void showViewRetry() {
        this.viewFeedItemView.showRetry();
    }

    private void hideViewRetry() {
        this.viewFeedItemView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewFeedItemView.context(),
                errorBundle.getException());
        this.viewFeedItemView.showError(errorMessage);
    }

    private void getFeedItem(String itemId) {
        this.getFeedItemUseCase.execute(new FeedItemObserver(), GetFeedItem.Params.forItem(itemId));
    }

    private void showFeedItemInView(FeedItem feedItem) {
        final FeedItemModel feedItemModel = this.feedModelDataMapper.transform(feedItem);
        this.viewFeedItemView.renderFeedItem(feedItemModel);
    }

    private final class FeedItemObserver extends DisposableSubscriber<FeedItem> {

        @Override
        public void onComplete() {
            FeedItemPresenter.this.hideViewLoading();
        }

        @Override
        public void onNext(FeedItem feedItem) {
            FeedItemPresenter.this.showFeedItemInView(feedItem);
        }

        @Override
        public void onError(Throwable e) {
            FeedItemPresenter.this.hideViewLoading();
            FeedItemPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            FeedItemPresenter.this.showViewRetry();
        }
    }
}
