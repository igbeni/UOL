package br.com.igbeni.uol.data.entity.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.igbeni.uol.data.entity.FeedEntity;
import br.com.igbeni.uol.data.entity.FeedItemEntity;
import br.com.igbeni.uol.domain.Feed;
import br.com.igbeni.uol.domain.FeedItem;

@Singleton
public class FeedEntityDataMapper implements DataMapper<Feed, FeedEntity> {

    @Inject
    public FeedEntityDataMapper() {
    }

    @Override
    public Feed transform(FeedEntity feedEntity) {
        Feed feed = null;

        if (feedEntity != null) {
            feed = new Feed();

            List<FeedItem> feedItems = new ArrayList<>();

            for (FeedItemEntity entity : feedEntity.getFeedItemEntities()) {
                FeedItem feedItem = new FeedItem(entity.getId());
                feedItem.setType(FeedItem.Type.fromString(entity.getType()));
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
}
