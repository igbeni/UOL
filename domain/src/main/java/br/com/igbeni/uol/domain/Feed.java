package br.com.igbeni.uol.domain;

import java.util.List;

public class Feed {
    private List<FeedItem> feedItems;

    public Feed() {
    }

    public List<FeedItem> getFeedItems() {
        return feedItems;
    }

    public void setFeedItems(List<FeedItem> feedItems) {
        this.feedItems = feedItems;
    }
}
