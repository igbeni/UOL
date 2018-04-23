package br.com.igbeni.uol.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.igbeni.uol.data.entity.mapper.FeedEntityDataMapper;
import br.com.igbeni.uol.data.repository.datasource.FeedDataStore;
import br.com.igbeni.uol.data.repository.datasource.FeedDataStoreFactory;
import br.com.igbeni.uol.domain.Feed;
import br.com.igbeni.uol.domain.repository.FeedRepository;
import io.reactivex.Observable;

@Singleton
public class FeedDataRepository implements FeedRepository {

    private final FeedDataStoreFactory feedDataStoreFactory;
    private final FeedEntityDataMapper feedEntityDataMapper;

    @Inject
    public FeedDataRepository(FeedDataStoreFactory feedDataStoreFactory, FeedEntityDataMapper feedEntityDataMapper) {
        this.feedDataStoreFactory = feedDataStoreFactory;
        this.feedEntityDataMapper = feedEntityDataMapper;
    }

    @Override
    public Observable<Feed> feed() {
        final FeedDataStore feedDataStore = this.feedDataStoreFactory.createCloudDataStore();
        return feedDataStore.feedEntity().map(this.feedEntityDataMapper::transform);
    }
}
