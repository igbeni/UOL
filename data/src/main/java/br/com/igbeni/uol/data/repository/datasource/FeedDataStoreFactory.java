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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.igbeni.uol.data.db.FeedItemsDatabase;
import br.com.igbeni.uol.data.entity.mapper.FeedEntityJsonMapper;
import br.com.igbeni.uol.data.net.RestApi;
import br.com.igbeni.uol.data.net.RestApiImpl;

@Singleton
public class FeedDataStoreFactory {
    @NonNull
    private final Context context;
    private final FeedItemsDatabase feedItemsDatabase;

    @Inject
    public FeedDataStoreFactory(@NonNull Context context) {
        this.context = context;
        this.feedItemsDatabase = FeedItemsDatabase.getInstance(context);
    }

    public FeedDataStore create(@Nullable String itemId) {
        FeedDataStore feedDataStore;

        if (itemId == null || itemId.length() == 0) {
            feedDataStore = createCloudDataStore();
        } else {
            feedDataStore = createLocalDataStore();
        }

        return feedDataStore;
    }

    @NonNull
    private FeedDataStore createCloudDataStore() {
        final FeedEntityJsonMapper feedEntityJsonMapper = new FeedEntityJsonMapper();
        final RestApi restApi = new RestApiImpl(this.context, feedEntityJsonMapper);

        return new CloudFeedDataStore(restApi, feedItemsDatabase.feedItemDao());
    }

    @NonNull
    public FeedDataStore createLocalDataStore() {
        return new LocalFeedDataStore(feedItemsDatabase.feedItemDao());
    }
}
