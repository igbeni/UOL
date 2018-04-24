package br.com.igbeni.uol.view;

import java.util.List;

import br.com.igbeni.uol.model.FeedItemModel;
import br.com.igbeni.uol.model.FeedModel;
import br.com.igbeni.uol.model.ItemModel;

public interface FeedView extends LoadDataView {

    void renderFeed(FeedModel feedModel);

    void renderItems(List<ItemModel> itemModels);

    void viewFeedItem(FeedItemModel feedItemModel);
}
