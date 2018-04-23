package br.com.igbeni.uol.data.dao;

import android.arch.persistence.room.Room;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import br.com.igbeni.uol.data.db.FeedItemsDatabase;
import br.com.igbeni.uol.data.entity.FeedItemEntity;
import br.com.igbeni.uol.data.repository.datasource.LocalFeedDataStore;

@RunWith(RobolectricTestRunner.class)
public class FeedItemDaoTest {

    private FeedItemsDatabase feedItemsDatabase;

    @Before
    public void setUp() {
        feedItemsDatabase = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.getBaseContext(),
                FeedItemsDatabase.class)
                .allowMainThreadQueries()
                .build();

        LocalFeedDataStore localFeedDataStore = new LocalFeedDataStore(feedItemsDatabase.feedItemDao());

        localFeedDataStore.clearFeedItems().test();
    }

    @Test
    public void getFeedItemsRetrievesData() {
        List<FeedItemEntity> feedItemEntities = new ArrayList<>();

        feedItemEntities.add(new FeedItemEntity());
        feedItemEntities.add(new FeedItemEntity());

        for (FeedItemEntity feedItemEntity : feedItemEntities) {
            feedItemsDatabase.feedItemDao().insertFeedItemEntity(feedItemEntity);
        }

        LocalFeedDataStore localFeedDataStore = new LocalFeedDataStore(feedItemsDatabase.feedItemDao());

        localFeedDataStore.saveFeedItemEnities(feedItemEntities);

        localFeedDataStore.feedItemEntity(feedItemEntities.get(0).getId()).test();
    }
}