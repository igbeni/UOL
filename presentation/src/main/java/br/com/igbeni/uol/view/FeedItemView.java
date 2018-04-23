package br.com.igbeni.uol.view;

import br.com.igbeni.uol.model.FeedItemModel;

public interface FeedItemView extends LoadDataView {
    void renderFeedItem(FeedItemModel feedItemModel);
}
