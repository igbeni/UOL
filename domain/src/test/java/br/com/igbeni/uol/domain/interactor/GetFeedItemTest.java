package br.com.igbeni.uol.domain.interactor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import br.com.igbeni.uol.domain.executor.PostExecutionThread;
import br.com.igbeni.uol.domain.executor.ThreadExecutor;
import br.com.igbeni.uol.domain.interactor.GetFeedItem.Params;
import br.com.igbeni.uol.domain.repository.FeedRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetFeedItemTest {

    public static final String FAKE_FEED_ITEM_ID = UUID.randomUUID().toString();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private GetFeedItem getFeedItem;
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private FeedRepository mockFeedRepository;

    @Before
    public void setUp() {
        getFeedItem = new GetFeedItem(mockFeedRepository, mockThreadExecutor, mockPostExecutionThread);
    }

    @Test
    public void testGetFeedItemUseCaseObservableHappyCase() {
        getFeedItem.buildUseCaseObservable(Params.forItem(FAKE_FEED_ITEM_ID));

        verify(mockFeedRepository).feedItem(FAKE_FEED_ITEM_ID);
        verifyNoMoreInteractions(mockFeedRepository);
        verifyZeroInteractions(mockPostExecutionThread);
        verifyZeroInteractions(mockThreadExecutor);
    }

    @Test
    public void testShouldFailWhenNoOrEmptyParameters() {
        expectedException.expect(NullPointerException.class);
        getFeedItem.buildUseCaseObservable(null);
    }
}