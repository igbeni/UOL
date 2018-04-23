package br.com.igbeni.uol.view;

import java.util.List;

import br.com.igbeni.uol.model.FeedItemModel;
import br.com.igbeni.uol.model.FeedModel;

public interface FeedView extends LoadDataView {

    void renderFeed(FeedModel feedModel);

    void renderFeedItems(List<FeedItemModel> feedItemModels);

    void viewFeedItem(FeedItemModel feedItemModel);
}
