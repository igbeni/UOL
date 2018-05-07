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

package br.com.igbeni.uol.mapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import br.com.igbeni.uol.domain.Feed;
import br.com.igbeni.uol.domain.FeedItem;
import br.com.igbeni.uol.internal.di.PerActivity;
import br.com.igbeni.uol.model.FeedItemModel;
import br.com.igbeni.uol.model.FeedModel;

@PerActivity
public class FeedModelDataMapper implements DataMapper<FeedModel, Feed> {

    @Inject
    public FeedModelDataMapper() {
    }

    @NonNull
    @Override
    public FeedModel transform(@Nullable Feed feed) {
        if (feed == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final FeedModel feedModel = new FeedModel();

        List<FeedItemModel> feedItemModels = new ArrayList<>();

        for (FeedItem feedItem : feed.getFeedItems()) {
            FeedItemModel feedItemModel = new FeedItemModel(feedItem.getId());
            feedItemModel.setType(feedItem.getType());
            feedItemModel.setTitle(feedItem.getTitle());
            feedItemModel.setThumb(feedItem.getThumb());
            feedItemModel.setUpdated(feedItem.getUpdated());
            feedItemModel.setShareUrl(feedItem.getShareUrl());
            feedItemModel.setWebviewUrl(feedItem.getWebviewUrl());
            feedItemModels.add(feedItemModel);
        }

        feedModel.setFeedItemModels(feedItemModels);

        return feedModel;
    }

    @Override
    public Collection<FeedModel> transform(@Nullable Collection<Feed> feeds) {
        Collection<FeedModel> feedModelsCollection;

        if (feeds != null && !feeds.isEmpty()) {
            feedModelsCollection = new ArrayList<>();
            for (Feed feed : feeds) {
                feedModelsCollection.add(transform(feed));
            }
        } else {
            feedModelsCollection = Collections.emptyList();
        }

        return feedModelsCollection;
    }

    @NonNull
    public FeedItemModel transform(@Nullable FeedItem feedItem) {
        if (feedItem == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        FeedItemModel feedItemModel = new FeedItemModel(feedItem.getId());
        feedItemModel.setType(feedItem.getType());
        feedItemModel.setTitle(feedItem.getTitle());
        feedItemModel.setThumb(feedItem.getThumb());
        feedItemModel.setUpdated(feedItem.getUpdated());
        feedItemModel.setShareUrl(feedItem.getShareUrl());
        feedItemModel.setWebviewUrl(feedItem.getWebviewUrl());
        return feedItemModel;
    }

    @NonNull
    public List<FeedItemModel> transform(@Nullable List<FeedItem> feedItems) {
        if (feedItems == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final List<FeedItemModel> feedItemModels = new ArrayList<>();

        for (FeedItem feedItem : feedItems) {
            FeedItemModel feedItemModel = new FeedItemModel(feedItem.getId());
            feedItemModel.setType(feedItem.getType());
            feedItemModel.setTitle(feedItem.getTitle());
            feedItemModel.setThumb(feedItem.getThumb());
            feedItemModel.setUpdated(feedItem.getUpdated());
            feedItemModel.setShareUrl(feedItem.getShareUrl());
            feedItemModel.setWebviewUrl(feedItem.getWebviewUrl());
            feedItemModels.add(feedItemModel);
        }

        return feedItemModels;
    }
}
