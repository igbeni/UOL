package br.com.igbeni.uol.data.net;

import br.com.igbeni.uol.data.entity.FeedEntity;
import io.reactivex.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
    String API_BASE_URL = "http://app.servicos.uol.com.br/c/api/v1/list/news/?app=uol-placar-futebol&version=2";

    Observable<FeedEntity> feedEntity();
}