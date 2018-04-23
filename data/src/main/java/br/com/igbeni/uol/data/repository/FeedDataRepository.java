package br.com.igbeni.uol.data.repository;

import org.reactivestreams.Publisher;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.igbeni.uol.data.entity.FeedItemEntity;
import br.com.igbeni.uol.data.entity.mapper.FeedEntityDataMapper;
import br.com.igbeni.uol.data.repository.datasource.FeedDataStore;
import br.com.igbeni.uol.data.repository.datasource.FeedDataStoreFactory;
import br.com.igbeni.uol.domain.FeedItem;
import br.com.igbeni.uol.domain.repository.FeedRepository;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Function;

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
    public Flowable<List<FeedItem>> feedItems() {
        final FeedDataStore feedDataStore = this.feedDataStoreFactory.create(null);

        return Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            feedDataStoreFactory.createLocalDataStore().deleteAll();
            emitter.onSuccess(true);
        }).flatMapPublisher((Function<Boolean, Publisher<List<FeedItemEntity>>>) aBoolean -> feedDataStore.feedItemEntities())
                .flatMap((Function<List<FeedItemEntity>, Flowable<List<FeedItemEntity>>>) feedItemEntities -> FeedDataRepository.this.saveFeedItems(feedItemEntities).toSingle(() -> feedItemEntities).toFlowable())
                .flatMap((Function<List<FeedItemEntity>, Flowable<List<FeedItem>>>) feedItemEntities -> Flowable.just(feedEntityDataMapper.transform(feedItemEntities)));
    }

    @Override
    public Flowable<FeedItem> feedItem(String itemId) {
        final FeedDataStore feedDataStore = this.feedDataStoreFactory.create(itemId);
        return feedDataStore.feedItemEntity(itemId).map(feedEntityDataMapper::transform);
    }

    public Completable saveFeedItems(List<FeedItemEntity> feedItemEntities) {
        return feedDataStoreFactory.createLocalDataStore().saveFeedItemEnities(feedItemEntities);
    }
}
