package br.com.igbeni.uol.domain.interactor;

import javax.inject.Inject;

import br.com.igbeni.uol.domain.Feed;
import br.com.igbeni.uol.domain.executor.PostExecutionThread;
import br.com.igbeni.uol.domain.executor.ThreadExecutor;
import br.com.igbeni.uol.domain.repository.FeedRepository;
import io.reactivex.Observable;

public class GetFeed extends UseCase<Feed, Void> {

    private final FeedRepository feedRepository;

    @Inject
    GetFeed( FeedRepository feedRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.feedRepository = feedRepository;
    }

    @Override
    Observable<Feed> buildUseCaseObservable(Void aVoid) {
        return this.feedRepository.feed();
    }
}
