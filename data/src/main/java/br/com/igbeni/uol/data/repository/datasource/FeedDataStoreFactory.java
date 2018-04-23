package br.com.igbeni.uol.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.igbeni.uol.data.db.FeedItemsDatabase;
import br.com.igbeni.uol.data.entity.mapper.FeedEntityJsonMapper;
import br.com.igbeni.uol.data.net.RestApi;
import br.com.igbeni.uol.data.net.RestApiImpl;

@Singleton
public class FeedDataStoreFactory {
    private final Context context;
    private final FeedItemsDatabase feedItemsDatabase;

    @Inject
    public FeedDataStoreFactory(Context context) {
        this.context = context;
        this.feedItemsDatabase = FeedItemsDatabase.getInstance(context);
    }

    public FeedDataStore create(String itemId) {
        FeedDataStore feedDataStore;

        if (itemId == null || itemId.length() == 0) {
            feedDataStore = createCloudDataStore();
        } else {
            feedDataStore = createLocalDataStore();
        }

        return feedDataStore;
    }

    public FeedDataStore createCloudDataStore() {
        final FeedEntityJsonMapper feedEntityJsonMapper = new FeedEntityJsonMapper();
        final RestApi restApi = new RestApiImpl(this.context, feedEntityJsonMapper);

        return new CloudFeedDataStore(restApi, feedItemsDatabase.feedItemDao());
    }

    public FeedDataStore createLocalDataStore() {
        return new LocalFeedDataStore(feedItemsDatabase.feedItemDao());
    }
}
