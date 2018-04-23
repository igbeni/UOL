package br.com.igbeni.uol.data.repository.datasource;

import java.util.List;

import br.com.igbeni.uol.data.dao.FeedItemDao;
import br.com.igbeni.uol.data.entity.FeedItemEntity;
import br.com.igbeni.uol.data.net.RestApi;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class CloudFeedDataStore implements FeedDataStore {

    private final RestApi restApi;
    private final FeedItemDao feedItemDao;

    public CloudFeedDataStore(RestApi restApi, FeedItemDao feedItemDao) {
        this.restApi = restApi;
        this.feedItemDao = feedItemDao;
    }

    @Override
    public Flowable<List<FeedItemEntity>> feedItemEntities() {
        return restApi.feedItemEntities();
    }

    @Override
    public Flowable<FeedItemEntity> feedItemEntity(String itemId) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Completable saveFeedItemEnities(List<FeedItemEntity> feedItemEntities) {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Completable clearFeedItems() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public Flowable<List<FeedItemEntity>> getFeedItems() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}
