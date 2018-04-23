package br.com.igbeni.uol.domain.repository;

import br.com.igbeni.uol.domain.FeedItem;
import io.reactivex.Flowable;

public interface FeedRepository {

    Flowable feedItems();

    Flowable<FeedItem> feedItem(String itemId);
}
