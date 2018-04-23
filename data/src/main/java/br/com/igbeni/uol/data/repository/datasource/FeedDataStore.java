package br.com.igbeni.uol.data.repository.datasource;

import java.util.List;

import br.com.igbeni.uol.data.entity.FeedItemEntity;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface FeedDataStore {

    Flowable<List<FeedItemEntity>> feedItemEntities();

    Flowable<FeedItemEntity> feedItemEntity(String itemId);

    Completable saveFeedItemEnities(List<FeedItemEntity> feedItemEntities);

    Completable clearFeedItems();

    Flowable<List<FeedItemEntity>> getFeedItems();

    void deleteAll();
}
