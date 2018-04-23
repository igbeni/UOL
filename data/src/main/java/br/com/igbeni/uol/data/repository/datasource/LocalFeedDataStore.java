package br.com.igbeni.uol.data.repository.datasource;

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

    @Override
    public Flowable<List<FeedItemEntity>> feedItemEntities() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Flowable<FeedItemEntity> feedItemEntity(String itemId) {
        return feedItemDao.getFeedItem(itemId);
    }

    @Override
    public Completable saveFeedItemEnities(List<FeedItemEntity> feedItemEntities) {
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
