package br.com.igbeni.uol.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import br.com.igbeni.uol.data.entity.FeedEntity;
import br.com.igbeni.uol.data.entity.mapper.FeedEntityDataMapper;
import br.com.igbeni.uol.data.repository.datasource.FeedDataStore;
import br.com.igbeni.uol.data.repository.datasource.FeedDataStoreFactory;
import br.com.igbeni.uol.domain.Feed;
import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class FeedDataRepositoryTest {

    private FeedDataRepository feedDataRepository;

    @Mock
    private FeedDataStoreFactory mockFeedDataStoreFactory;

    @Mock
    private FeedEntityDataMapper mockFeedEntityDataMapper;

    @Mock
    private FeedDataStore mockFeedDataStore;

    @Mock
    private FeedEntity mockFeedEntity;

    @Mock
    private Feed mockFeed;

    @Before
    public void setUp() {
        feedDataRepository = new FeedDataRepository(mockFeedDataStoreFactory, mockFeedEntityDataMapper);
        given(mockFeedDataStoreFactory.createCloudDataStore()).willReturn(mockFeedDataStore);
    }

    @Test
    public void testGetFeedHappyCase() {
        FeedEntity feedEntity = new FeedEntity();
        given(mockFeedDataStore.feedEntity()).willReturn(Observable.just(feedEntity));

        feedDataRepository.feed();

        verify(mockFeedDataStoreFactory).createCloudDataStore();
        verify(mockFeedDataStore).feedEntity();
    }
}