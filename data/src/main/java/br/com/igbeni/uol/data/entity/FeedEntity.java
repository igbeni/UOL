package br.com.igbeni.uol.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.igbeni.uol.domain.FeedItem;

public class FeedEntity {

    @SerializedName("feed")
    private List<FeedItemEntity> feedItemEntities;

    public FeedEntity() {
    }

    public List<FeedItemEntity> getFeedItemEntities() {
        return feedItemEntities;
    }

    public void setFeedItemEntities(List<FeedItemEntity> feedItemEntities) {
        this.feedItemEntities = feedItemEntities;
    }
}
