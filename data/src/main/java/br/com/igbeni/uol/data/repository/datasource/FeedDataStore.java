package br.com.igbeni.uol.data.repository.datasource;

import br.com.igbeni.uol.data.entity.FeedEntity;
import br.com.igbeni.uol.domain.Feed;
import io.reactivex.Observable;

public interface FeedDataStore {

    Observable<FeedEntity> feedEntity();
}
