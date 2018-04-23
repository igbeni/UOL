package br.com.igbeni.uol.data.repository.datasource;

import br.com.igbeni.uol.data.entity.FeedEntity;
import br.com.igbeni.uol.data.net.RestApi;
import io.reactivex.Observable;

public class CloudFeedDataStore implements FeedDataStore {

    private final RestApi restApi;

    public CloudFeedDataStore(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<FeedEntity> feedEntity() {
        return restApi.feedEntity();
    }
}
