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
import br.com.igbeni.uol.data.net.RestApi;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class CloudFeedDataStore implements FeedDataStore {

    private final RestApi restApi;

    public CloudFeedDataStore(RestApi restApi, FeedItemDao feedItemDao) {
        this.restApi = restApi;
    }

    @Override
    public Flowable<List<FeedItemEntity>> feedItemEntities() {
        return restApi.feedItemEntities();
    }

    @NonNull
    @Override
    public Flowable<FeedItemEntity> feedItemEntity(String itemId) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @NonNull
    @Override
    public Completable saveFeedItemEnities(List<FeedItemEntity> feedItemEntities) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @NonNull
    @Override
    public Completable clearFeedItems() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @NonNull
    @Override
    public Flowable<List<FeedItemEntity>> getFeedItems() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
