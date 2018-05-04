package br.com.igbeni.uol.domain.interactor;

import java.util.List;

import javax.inject.Inject;

import br.com.igbeni.uol.domain.FeedItem;
import br.com.igbeni.uol.domain.executor.PostExecutionThread;
import br.com.igbeni.uol.domain.executor.ThreadExecutor;
import br.com.igbeni.uol.domain.repository.FeedRepository;
import io.reactivex.Flowable;

public class GetFeed extends FlowableUseCase<List<FeedItem>, Void> {

    private final FeedRepository feedRepository;

    @Inject
    public GetFeed(FeedRepository feedRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.feedRepository = feedRepository;
    }

    @Override
    Flowable<List<FeedItem>> buildUseCaseObservable(Void aVoid) {
        return this.feedRepository.feedItems();
    }
}
