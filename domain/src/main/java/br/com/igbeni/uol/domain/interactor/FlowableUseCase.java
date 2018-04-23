package br.com.igbeni.uol.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;

import br.com.igbeni.uol.domain.executor.PostExecutionThread;
import br.com.igbeni.uol.domain.executor.ThreadExecutor;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public abstract class FlowableUseCase<T, Params> {
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    FlowableUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    abstract Flowable<T> buildUseCaseObservable(Params params);

    public void execute(DisposableSubscriber<T> observer, Params params) {
        Preconditions.checkNotNull(observer);
        final Flowable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    private void addDisposable(DisposableSubscriber<T> disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(disposables);
        disposables.add(disposable);
    }
}
