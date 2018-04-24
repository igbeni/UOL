package br.com.igbeni.uol.domain.interactor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.igbeni.uol.domain.executor.PostExecutionThread;
import br.com.igbeni.uol.domain.executor.ThreadExecutor;
import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.DisposableSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UseCaseTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private UseCaseTestClass useCase;
    private TestDisposableObserver<Object> testDisposableObserver;
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;

    @Before
    public void setUp() {
        this.useCase = new UseCaseTestClass(mockThreadExecutor, mockPostExecutionThread);
        this.testDisposableObserver = new TestDisposableObserver<>();
        given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
    }

    @Test
    public void testBuildUseCaseObservableReturnCorrectResult() {
        useCase.execute(testDisposableObserver, Params.EMPTY);

        assertThat(testDisposableObserver.valuesCount).isZero();
    }

    @Test
    public void testSubscriptionWhenExecutingUseCase() {
        useCase.execute(testDisposableObserver, Params.EMPTY);
        useCase.dispose();

        assertThat(testDisposableObserver.isDisposed()).isTrue();
    }

    @Test
    public void testShouldFailWhenExecuteWithNullObserver() {
        expectedException.expect(NullPointerException.class);
        useCase.execute(null, Params.EMPTY);
    }

    private static class UseCaseTestClass extends FlowableUseCase<Object, Params> {

        UseCaseTestClass(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
            super(threadExecutor, postExecutionThread);
        }

        @Override
        Flowable<Object> buildUseCaseObservable(Params params) {
            return Flowable.empty();
        }

        @Override
        public void execute(DisposableSubscriber<Object> observer, Params params) {
            super.execute(observer, params);
        }
    }

    private static class TestDisposableObserver<T> extends DisposableSubscriber<T> {
        private int valuesCount = 0;

        @Override
        public void onNext(T value) {
            valuesCount++;
        }

        @Override
        public void onError(Throwable e) {
            // no-op by default.
        }

        @Override
        public void onComplete() {
            // no-op by default.
        }
    }

    private static class Params {
        private static Params EMPTY = new Params();

        private Params() {
        }
    }
}