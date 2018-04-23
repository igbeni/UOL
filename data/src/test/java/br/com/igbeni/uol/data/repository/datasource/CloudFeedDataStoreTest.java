package br.com.igbeni.uol.data.repository.datasource;


import android.arch.persistence.room.Room;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.robolectric.RuntimeEnvironment;

import br.com.igbeni.uol.data.db.FeedItemsDatabase;
import br.com.igbeni.uol.data.net.RestApi;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CloudFeedDataStoreTest {

    private CloudFeedDataStore cloudFeedDataStore;

    @Mock
    private RestApi mockRestApi;

    private FeedItemsDatabase mDatabase;

    @Before
    public void setUp() {
        mDatabase = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application.getBaseContext(),
                FeedItemsDatabase.class)
                .allowMainThreadQueries()
                .build();

        cloudFeedDataStore = new CloudFeedDataStore(mockRestApi, mDatabase.feedItemDao());
    }

    @Test
    public void testGetUserEntityListFromApi() {
        cloudFeedDataStore.feedItemEntities();
        verify(mockRestApi).feedItemEntities();
    }
}