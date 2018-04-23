
package br.com.igbeni.uol.mapper;

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

    @Override
    public FeedModel transform(Feed feed) {
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
    public Collection<FeedModel> transform(Collection<Feed> feeds) {
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
}
