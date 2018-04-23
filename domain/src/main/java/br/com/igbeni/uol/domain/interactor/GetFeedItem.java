package br.com.igbeni.uol.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import br.com.igbeni.uol.domain.FeedItem;
import br.com.igbeni.uol.domain.executor.PostExecutionThread;
import br.com.igbeni.uol.domain.executor.ThreadExecutor;
import br.com.igbeni.uol.domain.repository.FeedRepository;
import io.reactivex.Flowable;

public class GetFeedItem extends FlowableUseCase<FeedItem, GetFeedItem.Params> {

    private final FeedRepository feedRepository;

    @Inject
    GetFeedItem(FeedRepository feedRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.feedRepository = feedRepository;
    }

    @Override
    Flowable<FeedItem> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.feedRepository.feedItem(params.itemId);
    }

    public static final class Params {

        private final String itemId;

        public Params(String itemId) {
            this.itemId = itemId;
        }

        public static Params forItem(String itemId) {
            return new Params(itemId);
        }
    }
}
