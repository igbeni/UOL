package br.com.igbeni.uol.view;

import br.com.igbeni.uol.model.FeedModel;

public interface FeedView extends LoadDataView {

    void renderFeed(FeedModel feedModel);
}
