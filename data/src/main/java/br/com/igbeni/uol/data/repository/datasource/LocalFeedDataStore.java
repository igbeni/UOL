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

package br.com.igbeni.uol.data.repository.datasource;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.igbeni.uol.data.dao.FeedItemDao;
import br.com.igbeni.uol.data.entity.FeedItemEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalFeedDataStore implements FeedDataStore {

    private final FeedItemDao feedItemDao;

    public LocalFeedDataStore(FeedItemDao feedItemDao) {
        this.feedItemDao = feedItemDao;
    }

    @NonNull
    @Override
    public Flowable<List<FeedItemEntity>> feedItemEntities() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Flowable<FeedItemEntity> feedItemEntity(String itemId) {
        return feedItemDao.getFeedItem(itemId);
    }

    @Override
    public Completable saveFeedItemEnities(@NonNull List<FeedItemEntity> feedItemEntities) {
        return Completable.defer(() -> {
            for (FeedItemEntity feedItemEntity : feedItemEntities) {
                feedItemDao.insertFeedItemEntity(feedItemEntity);
            }
            return Completable.complete();
        });
    }

    @Override
    public Completable clearFeedItems() {
        return Completable.defer(() -> {
            feedItemDao.deleteAllFeedItemEntities();
            return Completable.complete();
        });
    }

    @Override
    public Flowable<List<FeedItemEntity>> getFeedItems() {
        return feedItemDao.getFeedItems();
    }

    @Override
    public void deleteAll() {
        feedItemDao.deleteAllFeedItemEntities();
    }
}
