package br.com.igbeni.uol.data.net;

import java.util.List;

import br.com.igbeni.uol.data.entity.FeedItemEntity;
import io.reactivex.Flowable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
    String API_BASE_URL = "http://app.servicos.uol.com.br/c/api/v1/list/news/?app=uol-placar-futebol&version=2";

    Flowable<List<FeedItemEntity>> feedItemEntities();
}