package br.com.igbeni.uol.domain.repository;

import br.com.igbeni.uol.domain.Feed;
import io.reactivex.Observable;

public interface FeedRepository {

    Observable<Feed> feed();
}
