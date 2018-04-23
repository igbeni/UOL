package br.com.igbeni.uol.model;

import br.com.igbeni.uol.domain.FeedItem;

public class FeedItemModel {

    private final int id;

    private FeedItem.Type type;

    private String title;

    private String thumb;

    private Long updated;

    private String shareUrl;

    private String webviewUrl;

    public FeedItemModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public FeedItem.Type getType() {
        return type;
    }

    public void setType(FeedItem.Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getWebviewUrl() {
        return webviewUrl;
    }

    public void setWebviewUrl(String webviewUrl) {
        this.webviewUrl = webviewUrl;
    }
}
