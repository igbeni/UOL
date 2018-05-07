/*
 * (C) Copyright 2018.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Contributors:
 *      Iggor Alves
 */

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