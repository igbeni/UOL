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

package br.com.igbeni.uol.data.entity.mapper;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.igbeni.uol.data.entity.FeedEntity;
import br.com.igbeni.uol.data.entity.FeedItemEntity;
import br.com.igbeni.uol.domain.Feed;
import br.com.igbeni.uol.domain.FeedItem;
import br.com.igbeni.uol.domain.Type;

@Singleton
public class FeedEntityDataMapper implements DataMapper<Feed, FeedEntity> {

    @Inject
    public FeedEntityDataMapper() {
    }

    @Nullable
    @Override
    public Feed transform(@Nullable FeedEntity feedEntity) {
        Feed feed = null;

        if (feedEntity != null) {
            feed = new Feed();

            List<FeedItem> feedItems = new ArrayList<>();

            for (FeedItemEntity entity : feedEntity.getFeedItemEntities()) {
                FeedItem feedItem = new FeedItem(entity.getId());
                feedItem.setType(Type.fromString(entity.getType()));
                feedItem.setTitle(entity.getTitle());
                feedItem.setThumb(entity.getThumb());
                feedItem.setUpdated(entity.getUpdated());
                feedItem.setShareUrl(entity.getShareUrl());
                feedItem.setWebviewUrl(entity.getWebviewUrl());
                feedItems.add(feedItem);
            }

            feed.setFeedItems(feedItems);
        }

        return feed;
    }

    @Nullable
    public FeedItem transform(@Nullable FeedItemEntity feedItemEntity) {
        FeedItem feedItem = null;

        if (feedItemEntity != null) {
            feedItem = new FeedItem(feedItemEntity.getId());
            feedItem.setType(Type.fromString(feedItemEntity.getType()));
            feedItem.setTitle(feedItemEntity.getTitle());
            feedItem.setThumb(feedItemEntity.getThumb());
            feedItem.setUpdated(feedItemEntity.getUpdated());
            feedItem.setShareUrl(feedItemEntity.getShareUrl());
            feedItem.setWebviewUrl(feedItemEntity.getWebviewUrl());
        }

        return feedItem;
    }

    @Nullable
    public List<FeedItem> transform(@Nullable List<FeedItemEntity> feedItemEntities) {
        List<FeedItem> feedItems = null;

        if (feedItemEntities != null) {
            feedItems = new ArrayList<>();

            for (FeedItemEntity entity : feedItemEntities) {
                FeedItem feedItem = new FeedItem(entity.getId());
                feedItem.setType(Type.fromString(entity.getType()));
                feedItem.setTitle(entity.getTitle());
                feedItem.setThumb(entity.getThumb());
                feedItem.setUpdated(entity.getUpdated());
                feedItem.setShareUrl(entity.getShareUrl());
                feedItem.setWebviewUrl(entity.getWebviewUrl());
                feedItems.add(feedItem);
            }

        }

        return feedItems;
    }

    @Nullable
    public List<FeedItemEntity> transformToListOfEntity(@Nullable List<FeedItem> feedItems) {
        List<FeedItemEntity> feedItemEntities = null;

        if (feedItems != null) {
            feedItemEntities = new ArrayList<>();

            for (FeedItem feedItem : feedItems) {
                FeedItemEntity feedItemEntity = new FeedItemEntity();
                feedItemEntity.setType(feedItem.getType().getType());
                feedItemEntity.setTitle(feedItem.getTitle());
                feedItemEntity.setThumb(feedItem.getThumb());
                feedItemEntity.setUpdated(feedItem.getUpdated());
                feedItemEntity.setShareUrl(feedItem.getShareUrl());
                feedItemEntity.setWebviewUrl(feedItem.getWebviewUrl());
                feedItemEntities.add(feedItemEntity);
            }

        }

        return feedItemEntities;
    }
}
