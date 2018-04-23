package br.com.igbeni.uol.model;

import java.util.List;

public class FeedModel {
    private List<FeedItemModel> feedItemModels;

    public FeedModel() {
    }

    public List<FeedItemModel> getFeedItemModels() {
        return feedItemModels;
    }

    public void setFeedItemModels(List<FeedItemModel> feedItemModels) {
        this.feedItemModels = feedItemModels;
    }
}
