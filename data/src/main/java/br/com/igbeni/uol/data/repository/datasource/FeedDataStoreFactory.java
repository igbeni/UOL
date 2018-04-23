package br.com.igbeni.uol.data.repository.datasource;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.igbeni.uol.data.entity.mapper.FeedEntityJsonMapper;
import br.com.igbeni.uol.data.net.RestApi;
import br.com.igbeni.uol.data.net.RestApiImpl;

@Singleton
public class FeedDataStoreFactory {
    private final Context context;

    @Inject
    public FeedDataStoreFactory(Context context) {
        this.context = context;
    }

    public FeedDataStore create() {
        return createCloudDataStore();
    }

    public FeedDataStore createCloudDataStore() {
        final FeedEntityJsonMapper feedEntityJsonMapper = new FeedEntityJsonMapper();
        final RestApi restApi = new RestApiImpl(this.context, feedEntityJsonMapper);

        return new CloudFeedDataStore(restApi);
    }
}
